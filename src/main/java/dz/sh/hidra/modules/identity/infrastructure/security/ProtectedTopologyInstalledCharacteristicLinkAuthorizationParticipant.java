/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedTopologyInstalledCharacteristicLinkAuthorizationParticipant
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-01
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.security
 *
 * @Description : One genuine native issuer checks Topology link/unit and Simulation source permissions.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.security;

import dz.sh.hidra.modules.identity.application.contract.topology.TopologyInstalledCharacteristicLinkAuthorizationContract;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyInstalledCharacteristicLinkAuthorizationContract.*;
import dz.sh.hidra.modules.identity.domain.policy.AuthorizationEvaluationRequest;
import dz.sh.hidra.modules.identity.domain.policy.AuthorizationEvidence;
import dz.sh.hidra.modules.identity.domain.service.AuthorizationPolicyEvaluator;
import dz.sh.hidra.modules.identity.domain.value.AuthorizationDecisionValue;
import dz.sh.hidra.modules.identity.domain.value.AuthorizationScope;
import dz.sh.hidra.modules.identity.domain.value.ScopeType;
import dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcIdentityAuthorityFence;
import dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcProtectedAuthorizationEvidenceReader;
import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

/** No structural DTO, URL, manufacturer assertion or Party status grants Identity permission. */
@Component
public class ProtectedTopologyInstalledCharacteristicLinkAuthorizationParticipant
        implements TopologyInstalledCharacteristicLinkAuthorizationContract {
    private final ProtectedOperationIdentityParticipant identity;
    private final JdbcProtectedAuthorizationEvidenceReader reader;
    private final JdbcTemplate jdbc;
    private final DataSource ds;
    private final AuthorizationPolicyEvaluator evaluator=new AuthorizationPolicyEvaluator();
    public ProtectedTopologyInstalledCharacteristicLinkAuthorizationParticipant(
            ProtectedOperationIdentityParticipant identity,JdbcProtectedAuthorizationEvidenceReader reader,
            JdbcTemplate jdbc) {
        this.identity=Objects.requireNonNull(identity);this.reader=Objects.requireNonNull(reader);
        this.jdbc=Objects.requireNonNull(jdbc);this.ds=Objects.requireNonNull(jdbc.getDataSource());
    }
    @Override public Prepared prepare() {
        if(TransactionSynchronizationManager.isActualTransactionActive()
                ||TransactionSynchronizationManager.isSynchronizationActive())throw denied();
        Authentication actual=SecurityContextHolder.getContext().getAuthentication();
        if(actual==null||!actual.isAuthenticated())throw denied();
        return new Entry(this,identity.prepare(),actual);
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Admission acquire(Prepared prepared,Request request) {
        Ticket ticket=null;
        try {
            Objects.requireNonNull(request);
            if(!(prepared instanceof Entry entry)||entry.owner!=this
                    ||entry.thread!=Thread.currentThread()
                    ||entry.authentication!=SecurityContextHolder.getContext().getAuthentication()
                    ||!entry.used.compareAndSet(false,true))throw denied();
            ConnectionHolder holder=holder();
            var nativeTicket=identity.admit(entry.prepared);
            var contribution=identity.contribute(nativeTicket);
            var evidence=contribution.evidence();
            // The native issuer, not a Workflow/DTO claimant, must be a current
            // active human approver in the same original physical transaction.
            if(request.operation()==Operation.APPROVE) requireHumanIssuer(evidence.userId());

            var requests=List.of(
                new AuthorizationEvaluationRequest(evidence.userId(),TopologyInstalledCharacteristicLinkAuthorizationContract.permission(request.operation()),
                        LINK_RESOURCE,request.linkId(),companyScope(request.companyPartyId())),
                new AuthorizationEvaluationRequest(evidence.userId(),"topology:installed-unit:read",
                        UNIT_RESOURCE,request.installedUnitId(),companyScope(request.companyPartyId())));
            List<AuthorizationEvidence> graphs=loadTopology(contribution,requests);
            String role=jdbc.queryForObject("SELECT current_user",String.class);
            var origin=new Origin(evidence.bindingId(),evidence.sessionId(),evidence.userId(),
                    evidence.observedAt(),contribution.validUntil(),contribution.backendPid(),
                    contribution.transactionId(),role);
            ticket=new Ticket(this,entry,request,nativeTicket,contribution,origin,holder,requests,graphs);
            TransactionSynchronizationManager.registerSynchronization(ticket);
            return ticket;
        }catch(RuntimeException fault){poison(ticket);throw denied();}
    }
    @Override public Origin contribute(Admission admission) {
        try {
            Ticket t=ticket(admission);
            if(!t.used.compareAndSet(false,true))throw denied();
            requireCurrent(t);
            return t.origin;
        }catch(RuntimeException fault){poison(admission);throw denied();}
    }
    @Override public void requireSourceRead(Admission admission,Source source) {
        try {
            Ticket t=ticket(admission);
            if(!t.used.get()||!t.request.companyPartyId().equals(source.companyPartyId())
                    ||t.finalized)throw denied();
            var requests=manufacturerRequests(t.origin.userId(),source);
            List<AuthorizationEvidence> graphs=loadManufacturer(t.contribution,requests);
            SourceDecision previous=t.sources.putIfAbsent(source,new SourceDecision(requests,graphs));
            if(previous!=null && !previous.equals(new SourceDecision(requests,graphs)))throw denied();
        }catch(RuntimeException fault){poison(admission);throw denied();}
    }
    @Override public void requireCurrent(Admission admission) {
        try {
            Ticket t=ticket(admission);
            if(!t.used.get())throw denied();
            identity.requireAdmitted(t.nativeTicket);
            if(!t.graphs.equals(loadTopology(t.contribution,t.requests)))throw denied();
            if(t.request.operation()==Operation.APPROVE) requireHumanIssuer(t.origin.userId());

            for(var entry:t.sources.entrySet())
                if(!entry.getValue().graphs().equals(loadManufacturer(t.contribution,entry.getValue().requests())))
                    throw denied();
            Instant now=jdbc.queryForObject("SELECT pg_catalog.clock_timestamp()",java.sql.Timestamp.class).toInstant();
            if(now.isBefore(t.origin.observedAt())||!now.isBefore(t.origin.validUntil())
                    ||!Boolean.TRUE.equals(jdbc.queryForObject(
                        "SELECT pg_catalog.pg_backend_pid()=? AND pg_catalog.txid_current()=? AND current_user=?",
                        Boolean.class,t.origin.backendPid(),t.origin.transactionId(),t.origin.databaseRole())))throw denied();
        }catch(RuntimeException fault){poison(admission);throw denied();}
    }
    @Override public List<ProtectedRelationFence> finalFences(Admission admission) {
        requireCurrent(admission);
        return JdbcIdentityAuthorityFence.requiredRelationFences();
    }
    @Override public void markFinal(Admission admission) {
        // The executor has issued the final SQL guard. This marker MUST be SQL-free:
        // invoking ticket()/requireAdmitted() would violate the last-statement invariant.
        if(!(admission instanceof Ticket t)||t.owner!=this||!t.active
                ||t.thread!=Thread.currentThread()
                ||t.entry.authentication!=SecurityContextHolder.getContext().getAuthentication()
                ||!t.used.get()||t.finalized||t.holder.isRollbackOnly())
            throw denied();
        t.finalized=true;
    }
    private void requireHumanIssuer(String actor) {
        var matches=jdbc.queryForList(
            "SELECT id FROM public.hidra_identity_user WHERE id=? AND user_type='HUMAN' AND status='ACTIVE'",
            String.class, actor);
        if(matches.size()!=1 || !actor.equals(matches.getFirst()))throw denied();
    }
    private List<AuthorizationEvidence> loadTopology(ProtectedOperationIdentityParticipant.Contribution c,
                                                     List<AuthorizationEvaluationRequest> requests) {
        var result=new ArrayList<AuthorizationEvidence>();
        for(var request:requests){
            var graph=reader.loadInstalledCharacteristic(c.evidence(),request,c.backendPid(),c.transactionId());
            if(evaluator.evaluate(request,graph,c.evidence().observedAt()).decision()!=AuthorizationDecisionValue.PERMIT)
                throw denied();
            result.add(graph);
        }
        return List.copyOf(result);
    }
    private List<AuthorizationEvidence> loadManufacturer(ProtectedOperationIdentityParticipant.Contribution c,
                                                         List<AuthorizationEvaluationRequest> requests) {
        var result=new ArrayList<AuthorizationEvidence>();
        for(var request:requests){
            var graph=reader.loadManufacturer(c.evidence(),request,c.backendPid(),c.transactionId());
            if(evaluator.evaluate(request,graph,c.evidence().observedAt()).decision()!=AuthorizationDecisionValue.PERMIT)
                throw denied();
            result.add(graph);
        }
        return List.copyOf(result);
    }
    private static AuthorizationScope companyScope(String company) {
        return new AuthorizationScope(ScopeType.CUSTOM,"topology-company:"+company,null);
    }
    private static List<AuthorizationEvaluationRequest> manufacturerRequests(String actor,Source source){
        String scopeDigest=digest(source);
        String right="simulation:manufacturer-characteristic:read";
        return List.of(
          new AuthorizationEvaluationRequest(actor,right,SOURCE_RESOURCE,scopeDigest,
                  new AuthorizationScope(ScopeType.CUSTOM,"simulation-company:"+source.companyPartyId(),null)),
          new AuthorizationEvaluationRequest(actor,right,SOURCE_RESOURCE,scopeDigest,
                  new AuthorizationScope(ScopeType.CUSTOM,"simulation-source:"+scopeDigest,null)));
    }
    private static String digest(Source r) {
        String data="HIDRA_MANUFACTURER_NATIVE_SCOPE_V1|"+
                r.companyPartyId().length()+":"+r.companyPartyId()+
                "|"+r.sourceId().length()+":"+r.sourceId()+"|"+
                r.revisionId().length()+":"+r.revisionId();
        try{return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256")
                .digest(data.getBytes(StandardCharsets.UTF_8)));}
        catch(java.security.NoSuchAlgorithmException impossible){throw new IllegalStateException(impossible);}
    }
    private ConnectionHolder holder() {
        if(!TransactionSynchronizationManager.isActualTransactionActive()
                ||!TransactionSynchronizationManager.isSynchronizationActive()
                ||TransactionSynchronizationManager.isCurrentTransactionReadOnly()
                ||!(TransactionSynchronizationManager.getResource(ds) instanceof ConnectionHolder h)
                ||h.isRollbackOnly())throw denied();
        return h;
    }
    private Ticket ticket(Admission admission) {
        if(!(admission instanceof Ticket t)||t.owner!=this||!t.active
                ||t.thread!=Thread.currentThread()||t.holder!=holder()
                ||t.connection!=t.holder.getConnection()
                ||t.entry.authentication!=SecurityContextHolder.getContext().getAuthentication())throw denied();
        identity.requireAdmitted(t.nativeTicket);
        return t;
    }
    private void poison(Object candidate) {
        if(candidate instanceof Ticket t){t.active=false;t.holder.setRollbackOnly();}
        Object resource=TransactionSynchronizationManager.getResource(ds);
        if(resource instanceof ConnectionHolder h)h.setRollbackOnly();
    }
    private static AccessDeniedException denied(){return new AccessDeniedException("Characteristic link permission denied");}
    private static final class Entry implements Prepared{
        final ProtectedTopologyInstalledCharacteristicLinkAuthorizationParticipant owner;
        final ProtectedOperationIdentityParticipant.Prepared prepared;
        final Authentication authentication;
        final Thread thread=Thread.currentThread();
        final AtomicBoolean used=new AtomicBoolean();
        Entry(ProtectedTopologyInstalledCharacteristicLinkAuthorizationParticipant owner,
              ProtectedOperationIdentityParticipant.Prepared prepared,Authentication authentication){
            this.owner=owner;this.prepared=prepared;this.authentication=authentication;
        }
    }
    private record SourceDecision(List<AuthorizationEvaluationRequest> requests,List<AuthorizationEvidence> graphs) {
        SourceDecision {requests=List.copyOf(requests);graphs=List.copyOf(graphs);}
    }
    private static final class Ticket implements Admission,TransactionSynchronization {
        final ProtectedTopologyInstalledCharacteristicLinkAuthorizationParticipant owner;
        final Entry entry;final Request request;
        final ProtectedOperationIdentityParticipant.Admitted nativeTicket;
        final ProtectedOperationIdentityParticipant.Contribution contribution;
        final Origin origin;
        final ConnectionHolder holder;final java.sql.Connection connection;
        final List<AuthorizationEvaluationRequest> requests;
        final List<AuthorizationEvidence> graphs;
        final Map<Source,SourceDecision> sources=new LinkedHashMap<>();
        final Thread thread=Thread.currentThread();
        final AtomicBoolean used=new AtomicBoolean();
        boolean active=true,finalized=false;
        Ticket(ProtectedTopologyInstalledCharacteristicLinkAuthorizationParticipant owner,Entry entry,
               Request request,ProtectedOperationIdentityParticipant.Admitted nativeTicket,
               ProtectedOperationIdentityParticipant.Contribution contribution,Origin origin,
               ConnectionHolder holder,List<AuthorizationEvaluationRequest> requests,
               List<AuthorizationEvidence> graphs) {
            this.owner=owner;this.entry=entry;this.request=request;this.nativeTicket=nativeTicket;
            this.contribution=contribution;this.origin=origin;
            this.holder=holder;this.connection=holder.getConnection();
            this.requests=List.copyOf(requests);this.graphs=List.copyOf(graphs);
        }
        @Override public void suspend(){active=false;}
        @Override public void beforeCommit(boolean readOnly){if(!active||!finalized||holder.isRollbackOnly())throw denied();}
        @Override public void afterCompletion(int code){active=false;}
        @Override public String toString(){return "CharacteristicLinkNativeTicket[opaque]";}
    }
}
