/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedEngineeringBaselineAuthorizationParticipant
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-03
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.security
 *
 * @Description : Fences genuine baseline and source read decisions inside their original native transaction.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.security;

import dz.sh.hidra.modules.identity.application.contract.topology.TopologyEngineeringBaselineAuthorizationContract;
import dz.sh.hidra.modules.identity.domain.policy.AuthorizationEvaluationRequest;
import dz.sh.hidra.modules.identity.domain.policy.AuthorizationEvidence;
import dz.sh.hidra.modules.identity.domain.service.AuthorizationPolicyEvaluator;
import dz.sh.hidra.modules.identity.domain.value.AuthorizationDecisionValue;
import dz.sh.hidra.modules.identity.domain.value.AuthorizationScope;
import dz.sh.hidra.modules.identity.domain.value.ScopeType;
import dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcIdentityAuthorityFence;
import dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcProtectedAuthorizationEvidenceReader;
import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
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

@Component
public class ProtectedEngineeringBaselineAuthorizationParticipant implements TopologyEngineeringBaselineAuthorizationContract {
    private final ProtectedOperationIdentityParticipant nativeIdentity;
    private final JdbcProtectedAuthorizationEvidenceReader reader;
    private final JdbcTemplate jdbc;
    private final DataSource datasource;
    private final int maxSourceResources;
    private final AuthorizationPolicyEvaluator evaluator = new AuthorizationPolicyEvaluator();
    public ProtectedEngineeringBaselineAuthorizationParticipant(ProtectedOperationIdentityParticipant nativeIdentity,
            JdbcProtectedAuthorizationEvidenceReader reader, JdbcTemplate jdbc,
            @Value("${hidra.topology.engineering-baseline.max-source-resources:512}") int maxSourceResources) {
        this.nativeIdentity=Objects.requireNonNull(nativeIdentity); this.reader=Objects.requireNonNull(reader);
        this.jdbc=Objects.requireNonNull(jdbc); this.datasource=Objects.requireNonNull(jdbc.getDataSource());
        if(maxSourceResources<1 || maxSourceResources>4096)throw new IllegalArgumentException("Invalid baseline source work bound");
        this.maxSourceResources=maxSourceResources;
    }
    @Override @Transactional(propagation=Propagation.NEVER)
    public Prepared prepare() {
        if(TransactionSynchronizationManager.isActualTransactionActive()
                || TransactionSynchronizationManager.isSynchronizationActive())throw denied();
        Authentication actual=SecurityContextHolder.getContext().getAuthentication();
        var nativePrepared=nativeIdentity.prepare();
        if(actual==null || !actual.isAuthenticated() || actual!=SecurityContextHolder.getContext().getAuthentication())throw denied();
        return new Entry(this,nativePrepared,actual);
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Admission acquire(Prepared prepared,Request request) {
        Ticket ticket=null;
        try {
            if(!(prepared instanceof Entry entry) || entry.owner!=this || entry.thread!=Thread.currentThread()
                    || entry.authentication!=SecurityContextHolder.getContext().getAuthentication()
                    || !entry.authentication.isAuthenticated() || !entry.used.compareAndSet(false,true))throw denied();
            Objects.requireNonNull(request);
            var holder=holder(); var admitted=nativeIdentity.admit(entry.prepared);
            var contribution=nativeIdentity.contribute(admitted);
            var e=contribution.evidence();
            var origin=new Origin(e.bindingId(),e.sessionId(),e.userId(),e.observedAt(),contribution.validUntil(),
                    contribution.backendPid(),contribution.transactionId(),jdbc.queryForObject("SELECT current_user",String.class));
            ticket=new Ticket(this,entry,request,admitted,contribution,origin,holder);
            TransactionSynchronizationManager.registerSynchronization(ticket);
            checkPhysical(ticket);
            for(var evaluation:rootRequests(ticket))ticket.graphs.put(evaluation,evaluate(ticket,evaluation));
            ticket.rootAuthorized=true;
            return ticket;
        } catch(RuntimeException failure) { poison(ticket); throw denied(); }
    }
    @Override public Origin contribute(Admission admission) {
        try {
            var t=ticket(admission);
            if(!t.rootAuthorized || !t.contributed.compareAndSet(false,true))throw denied();
            requireCurrent(t); return t.origin;
        } catch(RuntimeException failure) { poison(admission); throw denied(); }
    }
    @Override public void requireSourceRead(Admission admission,Source source) {
        try {
            var t=ticket(admission); Objects.requireNonNull(source);
            if(!t.contributed.get() || t.finalized)throw denied();
            if(!t.sources.containsKey(source) && t.sources.size()>=maxSourceResources)throw denied();
            var evaluations=sourceRequests(t,source);
            var graphs=new ArrayList<AuthorizationEvidence>();
            for(var evaluation:evaluations)graphs.add(evaluate(t,evaluation));
            List<AuthorizationEvidence> observed=List.copyOf(graphs);
            var previous=t.sources.putIfAbsent(source,observed);
            if(previous!=null && !sameGraphs(previous,observed))throw denied();
            for(int i=0;i<evaluations.size();i++) {
                var old=t.graphs.putIfAbsent(evaluations.get(i),observed.get(i));
                if(old!=null && !sameGraph(old,observed.get(i)))throw denied();
            }
            checkPhysical(t);
        } catch(RuntimeException failure) { poison(admission); throw denied(); }
    }
    @Override public void requireCurrent(Admission admission) {
        try {
            var t=ticket(admission);
            if(!t.rootAuthorized || !t.contributed.get() || t.finalized)throw denied();
            for(var entry:t.graphs.entrySet())
                if(!sameGraph(entry.getValue(),evaluate(t,entry.getKey())))throw denied();
            checkPhysical(t);
        } catch(RuntimeException failure) { poison(admission); throw denied(); }
    }
    @Override public List<ProtectedRelationFence> finalFences(Admission admission) {
        requireCurrent(admission); return JdbcIdentityAuthorityFence.requiredRelationFences();
    }
    @Override public void markFinal(Admission admission) {
        if(!(admission instanceof Ticket t) || t.owner!=this || !t.active || t.finalized
                || t.thread!=Thread.currentThread() || !t.contributed.get()
                || t.entry.authentication!=SecurityContextHolder.getContext().getAuthentication()
                || !t.entry.authentication.isAuthenticated() || t.holder.isRollbackOnly()) {
            poison(admission); throw denied();
        }
        // Deliberately SQL-free after the real protected last-statement guard.
        t.finalized=true;
    }
    private AuthorizationEvidence evaluate(Ticket t,AuthorizationEvaluationRequest request) {
        var handle=new ReadHandle(this,t,request);
        var graph=reader.loadEngineeringBaseline(handle,request);
        if(evaluator.evaluate(request,graph,graphTime(graph,t.origin)).decision()!=AuthorizationDecisionValue.PERMIT)throw denied();
        return graph;
    }
    private static Instant graphTime(AuthorizationEvidence graph,Origin origin) {
        Object value=graph.attributes().get("context.evaluatedAt");
        if(!(value instanceof String text))throw denied();
        Instant at=Instant.parse(text);
        if(at.isBefore(origin.sampledAt())||!at.isBefore(origin.validUntil()))throw denied();
        return at;
    }
    private static boolean sameGraph(AuthorizationEvidence before,AuthorizationEvidence after) {
        var a=new java.util.TreeMap<>(before.attributes());var b=new java.util.TreeMap<>(after.attributes());
        a.remove("context.evaluatedAt");b.remove("context.evaluatedAt");
        return Objects.equals(before.failure(),after.failure())&&before.grants().equals(after.grants())
            &&before.rules().equals(after.rules())&&a.equals(b);
    }
    private static boolean sameGraphs(List<AuthorizationEvidence> before,List<AuthorizationEvidence> after) {
        if(before.size()!=after.size())return false;
        for(int i=0;i<before.size();i++)if(!sameGraph(before.get(i),after.get(i)))return false;
        return true;
    }
    private static List<AuthorizationEvaluationRequest> rootRequests(Ticket t) {
        String code=TopologyEngineeringBaselineAuthorizationContract.permission(t.request.action());
        return List.of(request(t,code,RESOURCE_TYPE,t.request.pipelineSystemId(),ScopeType.CUSTOM,"topology-company:"+t.request.companyPartyId()),
                request(t,code,RESOURCE_TYPE,t.request.pipelineSystemId(),ScopeType.CUSTOM,"topology-network:"+t.request.pipelineSystemId()));
    }
    private static List<AuthorizationEvaluationRequest> sourceRequests(Ticket t,Source source) {
        String id=source.referenceId(),company=t.request.companyPartyId();
        return switch(source.kind()) {
            case NETWORK_PUBLICATION -> {
                if(!id.equals(t.request.pipelineSystemId()))throw denied();
                yield List.of(request(t,"topology:engineering-publication:read","ENGINEERING_NETWORK_PUBLICATION",id,ScopeType.CUSTOM,"topology-company:"+company),
                        request(t,"topology:engineering-publication:read","ENGINEERING_NETWORK_PUBLICATION",id,ScopeType.CUSTOM,"topology-network:"+id));
            }
            case STATION_CONFIGURATION -> {
                var required=new ArrayList<AuthorizationEvaluationRequest>();
                required.add(request(t,"topology:installed-configuration:read","STATION_INSTALLED_CONFIGURATION",id,ScopeType.CUSTOM,"topology-company:"+company));
                required.add(request(t,"topology:installed-configuration:read","STATION_INSTALLED_CONFIGURATION",id,ScopeType.FACILITY,id));
                for(String unit:source.referencedUnitIds())
                    required.add(request(t,"topology:installed-unit:read","INSTALLED_UNIT",unit,ScopeType.CUSTOM,"topology-company:"+company));
                yield List.copyOf(required);
            }
            case INSTALLED_UNIT -> List.of(request(t,"topology:installed-unit:read","INSTALLED_UNIT",id,ScopeType.CUSTOM,"topology-company:"+company));
            case CHARACTERISTIC_LINK -> List.of(
                    request(t,"topology:installed-characteristic:read","INSTALLED_CHARACTERISTIC_LINK",id,ScopeType.CUSTOM,"topology-company:"+company),
                    request(t,"topology:installed-unit:read","INSTALLED_UNIT",source.referencedUnitIds().get(0),ScopeType.CUSTOM,"topology-company:"+company));
            case MANUFACTURER_CHARACTERISTIC -> List.of(
                    request(t,"simulation:manufacturer-characteristic:read","MANUFACTURER_CHARACTERISTIC_REVISION",id,ScopeType.CUSTOM,"simulation-company:"+company),
                    request(t,"simulation:manufacturer-characteristic:read","MANUFACTURER_CHARACTERISTIC_REVISION",id,ScopeType.CUSTOM,"simulation-source:"+id));
            case PRODUCT_PUBLICATION -> List.of(
                    request(t,"custody:engineering-product-publication:read","PRODUCT_ENGINEERING_PUBLICATION",id,ScopeType.CUSTOM,"custody-company:"+company),
                    request(t,"custody:engineering-product-publication:read","PRODUCT_ENGINEERING_PUBLICATION",id,ScopeType.CUSTOM,"custody-product:"+id));
        };
    }
    private static AuthorizationEvaluationRequest request(Ticket t,String permission,String type,String reference,ScopeType scope,String scopeRef) {
        return new AuthorizationEvaluationRequest(t.origin.userId(),permission,type,reference,new AuthorizationScope(scope,scopeRef,null));
    }
    private ConnectionHolder holder() {
        if(!TransactionSynchronizationManager.isActualTransactionActive()
                || !TransactionSynchronizationManager.isSynchronizationActive()
                || TransactionSynchronizationManager.isCurrentTransactionReadOnly()
                || !(TransactionSynchronizationManager.getResource(datasource) instanceof ConnectionHolder holder)
                || holder.isRollbackOnly())throw denied();
        return holder;
    }
    private Ticket ticket(Admission admission) {
        if(!(admission instanceof Ticket t) || t.owner!=this || !t.active || t.finalized
                || t.thread!=Thread.currentThread() || t.holder!=holder()
                || t.connection!=t.holder.getConnection()
                || t.entry.authentication!=SecurityContextHolder.getContext().getAuthentication()
                || !t.entry.authentication.isAuthenticated())throw denied();
        checkPhysical(t); return t;
    }
    private void checkPhysical(Ticket t) {
        if(t.owner!=this || !t.active || t.finalized || t.thread!=Thread.currentThread()
                || t.holder!=holder() || t.connection!=t.holder.getConnection()
                || t.entry.authentication!=SecurityContextHolder.getContext().getAuthentication()
                || !t.entry.authentication.isAuthenticated())throw denied();
        nativeIdentity.requireAdmitted(t.nativeAdmission);
        if(!Boolean.TRUE.equals(jdbc.queryForObject("""
                WITH sampled_clock AS MATERIALIZED (SELECT pg_catalog.clock_timestamp() AS t)
                SELECT pg_catalog.pg_backend_pid()=? AND pg_catalog.txid_current()=? AND current_user=?
                  AND (SELECT t FROM sampled_clock)>=?::timestamptz AND (SELECT t FROM sampled_clock)<?::timestamptz
                """,Boolean.class,t.origin.backendPid(),t.origin.transactionId(),t.origin.databaseRole(),
                Timestamp.from(t.origin.sampledAt()),Timestamp.from(t.origin.validUntil()))))throw denied();
    }
    private void poison(Object admission) {
        if(admission instanceof Ticket t && t.owner==this){t.active=false;t.holder.setRollbackOnly();}
        for(Object resource:TransactionSynchronizationManager.getResourceMap().values())
            if(resource instanceof ConnectionHolder holder)holder.setRollbackOnly();
    }
    private static AccessDeniedException denied() { return new AccessDeniedException("Engineering baseline authority denied"); }
    private static final class Entry implements Prepared {
        final ProtectedEngineeringBaselineAuthorizationParticipant owner;
        final ProtectedOperationIdentityParticipant.Prepared prepared;
        final Authentication authentication; final Thread thread=Thread.currentThread();
        final AtomicBoolean used=new AtomicBoolean();
        Entry(ProtectedEngineeringBaselineAuthorizationParticipant owner,ProtectedOperationIdentityParticipant.Prepared prepared,Authentication authentication) {
            this.owner=owner;this.prepared=prepared;this.authentication=authentication;
        }
        @Override public String toString(){return "PreparedBaselineAuthority[opaque]";}
    }
    private static final class Ticket implements Admission,TransactionSynchronization {
        final ProtectedEngineeringBaselineAuthorizationParticipant owner; final Entry entry; final Request request;
        final ProtectedOperationIdentityParticipant.Admitted nativeAdmission;
        final ProtectedOperationIdentityParticipant.Contribution contribution; final Origin origin;
        final ConnectionHolder holder; final java.sql.Connection connection; final Thread thread=Thread.currentThread();
        final AtomicBoolean contributed=new AtomicBoolean();
        final Map<AuthorizationEvaluationRequest,AuthorizationEvidence> graphs=new LinkedHashMap<>();
        final Map<Source,List<AuthorizationEvidence>> sources=new LinkedHashMap<>();
        boolean active=true,finalized,rootAuthorized;
        Ticket(ProtectedEngineeringBaselineAuthorizationParticipant owner,Entry entry,Request request,
                ProtectedOperationIdentityParticipant.Admitted nativeAdmission,ProtectedOperationIdentityParticipant.Contribution contribution,
                Origin origin,ConnectionHolder holder) {
            this.owner=owner;this.entry=entry;this.request=request;this.nativeAdmission=nativeAdmission;
            this.contribution=contribution;this.origin=origin;this.holder=holder;this.connection=holder.getConnection();
        }
        @Override public void suspend(){active=false;holder.setRollbackOnly();}
        @Override public void afterCompletion(int status){active=false;}
        @Override public void beforeCommit(boolean readOnly){if(readOnly||!active||!finalized||holder.isRollbackOnly())throw denied();}
        @Override public String toString(){return "BaselineAuthority[opaque]";}
    }
    /** Identity-private synchronous reader capability; no public constructor, scalar impersonation or reuse. */
    public static final class ReadHandle {
        private final ProtectedEngineeringBaselineAuthorizationParticipant issuer; private final Ticket root;
        private final AuthorizationEvaluationRequest request; private final Thread thread=Thread.currentThread();
        private final AtomicBoolean consumed=new AtomicBoolean();
        private ReadHandle(ProtectedEngineeringBaselineAuthorizationParticipant issuer,Ticket root,AuthorizationEvaluationRequest request) {
            this.issuer=issuer;this.root=root;this.request=request;
        }
        public ProtectedOperationIdentityParticipant.Contribution consume(AuthorizationEvaluationRequest expected) {
            try {
                if(!caller() || thread!=Thread.currentThread() || !request.equals(expected) || !consumed.compareAndSet(false,true))throw denied();
                issuer.checkPhysical(root); return root.contribution;
            }catch(RuntimeException failure){issuer.poison(root);throw denied();}
        }
        public void requireCurrent(AuthorizationEvaluationRequest expected) {
            try {
                if(!caller() || thread!=Thread.currentThread() || !consumed.get() || !request.equals(expected))throw denied();
                issuer.checkPhysical(root);
            }catch(RuntimeException failure){issuer.poison(root);throw denied();}
        }
        private static boolean caller() {
            return StackWalker.getInstance().walk(frames->frames.skip(1).limit(3).anyMatch(f->f.getClassName().equals(
                    "dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcProtectedAuthorizationEvidenceReader")));
        }
        @Override public String toString(){return "BaselineReadHandle[opaque]";}
    }
}
