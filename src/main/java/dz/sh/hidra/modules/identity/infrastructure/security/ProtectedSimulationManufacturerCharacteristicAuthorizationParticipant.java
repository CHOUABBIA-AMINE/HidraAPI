/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedSimulationManufacturerCharacteristicAuthorizationParticipant
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-30
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.security
 *
 * @Description : Native operation-bound, fail-closed manufacturer scope admission.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.security;

import dz.sh.hidra.modules.identity.application.contract.simulation.SimulationManufacturerCharacteristicAuthorizationContract;
import dz.sh.hidra.modules.identity.application.contract.simulation.SimulationManufacturerCharacteristicAuthorizationContract.*;
import dz.sh.hidra.modules.identity.domain.policy.AuthorizationEvaluationRequest;
import dz.sh.hidra.modules.identity.domain.policy.AuthorizationEvidence;
import dz.sh.hidra.modules.identity.domain.service.AuthorizationPolicyEvaluator;
import dz.sh.hidra.modules.identity.domain.value.AuthorizationScope;
import dz.sh.hidra.modules.identity.domain.value.AuthorizationDecisionValue;
import dz.sh.hidra.modules.identity.domain.value.ScopeType;
import dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcProtectedAuthorizationEvidenceReader;
import dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcIdentityAuthorityFence;
import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.HexFormat;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/** Source and company scopes are independently required; neither generic Workflow roles nor certificates grant them. */
@Component
public class ProtectedSimulationManufacturerCharacteristicAuthorizationParticipant
        implements SimulationManufacturerCharacteristicAuthorizationContract {
    private final ProtectedOperationIdentityParticipant identity;
    private final JdbcProtectedAuthorizationEvidenceReader reader;
    private final JdbcTemplate jdbc;
    private final DataSource source;
    private final AuthorizationPolicyEvaluator evaluator=new AuthorizationPolicyEvaluator();
    public ProtectedSimulationManufacturerCharacteristicAuthorizationParticipant(
            ProtectedOperationIdentityParticipant identity,JdbcProtectedAuthorizationEvidenceReader reader,
            JdbcTemplate jdbc) {
        this.identity=Objects.requireNonNull(identity);this.reader=Objects.requireNonNull(reader);
        this.jdbc=Objects.requireNonNull(jdbc);this.source=Objects.requireNonNull(jdbc.getDataSource());
    }
    @Override @Transactional(propagation=Propagation.NEVER)
    public Prepared prepare() {
        var authentication=SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null||!authentication.isAuthenticated()
                ||TransactionSynchronizationManager.isActualTransactionActive()
                ||TransactionSynchronizationManager.isSynchronizationActive())throw denied();
        return new Entry(this,identity.prepare(),authentication);
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Admission acquire(Prepared prepared,Request request) {
        Ticket ticket=null;
        try {
            Objects.requireNonNull(request);
            if(!(prepared instanceof Entry e)||e.owner!=this||e.thread!=Thread.currentThread()
                    ||!e.used.compareAndSet(false,true)
                    ||e.authentication!=SecurityContextHolder.getContext().getAuthentication())throw denied();
            var holder=holder();
            var admission=identity.admit(e.prepared);
            var contribution=identity.contribute(admission);
            var origin=contribution.evidence();
            var resource=digest(request);
            var company=claim(origin.userId(),request,resource,"simulation-company:"+request.companyPartyId());
            var sourceClaim=claim(origin.userId(),request,resource,"simulation-source:"+resource);
            var first=reader.loadManufacturer(origin,company,contribution.backendPid(),contribution.transactionId());
            var second=reader.loadManufacturer(origin,sourceClaim,contribution.backendPid(),contribution.transactionId());
            var now=origin.observedAt();
            if(evaluator.evaluate(company,first,now).decision()!=AuthorizationDecisionValue.PERMIT
                    ||evaluator.evaluate(sourceClaim,second,now).decision()!=AuthorizationDecisionValue.PERMIT)
                throw denied();
            var role=jdbc.queryForObject("SELECT current_user",String.class);
            var decision=new Contribution(request,Decision.PERMIT,origin.bindingId(),origin.sessionId(),
                    origin.userId(),now,contribution.validUntil(),contribution.backendPid(),contribution.transactionId(),role);
            ticket=new Ticket(this,e,request,admission,contribution,holder,company,sourceClaim,first,second,decision);
            TransactionSynchronizationManager.registerSynchronization(ticket);
            return ticket;
        }catch(RuntimeException failure){poison(ticket);throw denied();}
    }
    @Override public Contribution contribute(Admission admission) {
        try {Ticket t=ticket(admission);if(!t.used.compareAndSet(false,true))throw denied();
            requireCurrent(t);return t.result;
        }catch(RuntimeException failure){poison(admission);throw denied();}
    }
    @Override public void requireCurrent(Admission admission) {
        try {
            Ticket t=ticket(admission);
            if(!t.used.get())throw denied();
            identity.requireAdmitted(t.admitted);
            var sourceOrigin=t.origin.evidence();
            var first=reader.loadManufacturer(sourceOrigin,t.company,t.result.backendPid(),t.result.transactionId());
            var second=reader.loadManufacturer(sourceOrigin,t.resource,t.result.backendPid(),t.result.transactionId());
            if(!t.first.equals(first)||!t.second.equals(second)
                    ||evaluator.evaluate(t.company,first,t.result.observedAt()).decision()!=AuthorizationDecisionValue.PERMIT
                    ||evaluator.evaluate(t.resource,second,t.result.observedAt()).decision()!=AuthorizationDecisionValue.PERMIT)
                throw denied();
            Instant now=jdbc.queryForObject("SELECT pg_catalog.clock_timestamp()",java.sql.Timestamp.class).toInstant();
            if(now.isBefore(t.result.observedAt()) || !now.isBefore(t.result.validUntil())
                    ||!Boolean.TRUE.equals(jdbc.queryForObject(
                      "SELECT pg_catalog.pg_backend_pid()=? AND pg_catalog.txid_current()=? AND current_user=?",
                      Boolean.class,t.result.backendPid(),t.result.transactionId(),t.result.databaseRole())))
                throw denied();
        }catch(RuntimeException failure){poison(admission);throw denied();}
    }
    @Override public List<ProtectedRelationFence> finalFences(Admission admission){
        requireCurrent(admission);return JdbcIdentityAuthorityFence.requiredRelationFences();
    }
    private static AuthorizationEvaluationRequest claim(String actor,Request r,String digest,String scope) {
        return new AuthorizationEvaluationRequest(actor,SimulationManufacturerCharacteristicAuthorizationContract.permissionCode(r.operation()),RESOURCE_TYPE,digest,
                new AuthorizationScope(ScopeType.CUSTOM,scope,null));
    }
    private static String digest(Request r) {
        String value="HIDRA_MANUFACTURER_NATIVE_SCOPE_V1|"
                +r.companyPartyId().length()+":"+r.companyPartyId()
                +"|"+r.sourceId().length()+":"+r.sourceId()+"|"
                +(r.revisionId()==null?"HISTORY":r.revisionId().length()+":"+r.revisionId());
        try {return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256")
                .digest(value.getBytes(StandardCharsets.UTF_8)));}
        catch(java.security.NoSuchAlgorithmException ex){throw new IllegalStateException("SHA-256 unavailable",ex);}
    }
    private Ticket ticket(Admission a){
        if(!(a instanceof Ticket t)||t.owner!=this||!t.active||t.thread!=Thread.currentThread()
                ||t.holder!=holder()||t.connection!=t.holder.getConnection()
                ||t.entry.authentication!=SecurityContextHolder.getContext().getAuthentication())throw denied();
        identity.requireAdmitted(t.admitted);
        return t;
    }
    private ConnectionHolder holder() {
        if(!TransactionSynchronizationManager.isActualTransactionActive()
                ||!TransactionSynchronizationManager.isSynchronizationActive()
                ||TransactionSynchronizationManager.isCurrentTransactionReadOnly()
                ||!(TransactionSynchronizationManager.getResource(source) instanceof ConnectionHolder h)
                ||h.isRollbackOnly())throw denied();
        return h;
    }
    private void poison(Object obj){
        if(obj instanceof Ticket t){t.active=false;t.holder.setRollbackOnly();}
        Object holder=TransactionSynchronizationManager.getResource(source);
        if(holder instanceof ConnectionHolder h)h.setRollbackOnly();
    }
    private static AccessDeniedException denied(){return new AccessDeniedException("Manufacturer native authorization unavailable");}
    private static final class Entry implements Prepared{
        private final ProtectedSimulationManufacturerCharacteristicAuthorizationParticipant owner;
        private final ProtectedOperationIdentityParticipant.Prepared prepared;
        private final Authentication authentication;
        private final Thread thread=Thread.currentThread();
        private final AtomicBoolean used=new AtomicBoolean();
        private Entry(ProtectedSimulationManufacturerCharacteristicAuthorizationParticipant owner,
                ProtectedOperationIdentityParticipant.Prepared prepared,Authentication authentication){
            this.owner=owner;this.prepared=prepared;this.authentication=authentication;
        }
        @Override public String toString(){return "ManufacturerNativePrepared[opaque]";}
    }
    private static final class Ticket implements Admission,TransactionSynchronization {
        private final ProtectedSimulationManufacturerCharacteristicAuthorizationParticipant owner;
        private final Entry entry;private final Request request;
        private final ProtectedOperationIdentityParticipant.Admitted admitted;
        private final ProtectedOperationIdentityParticipant.Contribution origin;
        private final ConnectionHolder holder;private final java.sql.Connection connection;
        private final AuthorizationEvaluationRequest company,resource;
        private final AuthorizationEvidence first,second;private final Contribution result;
        private final Thread thread=Thread.currentThread();private final AtomicBoolean used=new AtomicBoolean();
        private boolean active=true;
        Ticket(ProtectedSimulationManufacturerCharacteristicAuthorizationParticipant owner,Entry entry,Request request,
                ProtectedOperationIdentityParticipant.Admitted admitted,
                ProtectedOperationIdentityParticipant.Contribution origin,
                ConnectionHolder holder,AuthorizationEvaluationRequest company,AuthorizationEvaluationRequest resource,
                AuthorizationEvidence first,AuthorizationEvidence second,Contribution result){
            this.owner=owner;this.entry=entry;this.request=request;this.admitted=admitted;this.origin=origin;
            this.holder=holder;this.connection=holder.getConnection();this.company=company;this.resource=resource;
            this.first=first;this.second=second;this.result=result;
        }
        @Override public void beforeCommit(boolean readOnly){
            if(!active||readOnly||holder.isRollbackOnly()||!used.get())throw denied();
        }
        @Override public void afterCompletion(int status){active=false;}
        @Override public void suspend(){active=false;}
        @Override public String toString(){return "ManufacturerNativeAdmission[opaque]";}
    }
}
