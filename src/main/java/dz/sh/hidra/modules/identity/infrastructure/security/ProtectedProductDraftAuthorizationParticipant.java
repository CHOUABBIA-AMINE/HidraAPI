/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedProductDraftAuthorizationParticipant
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-26
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.security
 *
 * @Description : Implements the bounded company-scoped engineering draft contract.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.security;

import dz.sh.hidra.modules.identity.application.contract.custody.CustodyProtectedProductDraftAuthorizationContract;
import dz.sh.hidra.modules.identity.application.contract.custody.CustodyProtectedProductDraftAuthorizationContract.Request;
import dz.sh.hidra.modules.identity.domain.service.AuthorizationPolicyEvaluator;
import dz.sh.hidra.modules.identity.domain.value.AuthorizationDecisionValue;
import dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcProtectedAuthorizationEvidenceReader;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/** Calls real F and qualified graph readers; standalone Boolean decisions are never inputs. */
@Component
public class ProtectedProductDraftAuthorizationParticipant
        implements CustodyProtectedProductDraftAuthorizationContract {
    private final ProtectedOperationIdentityParticipant identity;
    private final JdbcProtectedAuthorizationEvidenceReader reader;
    private final org.springframework.jdbc.core.JdbcTemplate jdbc;
    private final AuthorizationPolicyEvaluator evaluator = new AuthorizationPolicyEvaluator();

    public ProtectedProductDraftAuthorizationParticipant(
            ProtectedOperationIdentityParticipant identity,
            JdbcProtectedAuthorizationEvidenceReader reader, org.springframework.jdbc.core.JdbcTemplate jdbc) {
        this.identity = Objects.requireNonNull(identity);
        this.reader = Objects.requireNonNull(reader);
        this.jdbc = Objects.requireNonNull(jdbc);
    }

    @Override
    @Transactional(propagation = Propagation.NEVER)
    public Prepared prepare() {
        Authentication actual = SecurityContextHolder.getContext().getAuthentication();
        var prepared = identity.prepare();
        if (actual == null
                || !actual.isAuthenticated()
                || actual != SecurityContextHolder.getContext().getAuthentication()) throw denied();
        return new Entry(this, prepared, actual);
    }

    private Contribution evaluate(Entry entry,Request request,JdbcProtectedAuthorizationEvidenceReader.PairedAuthorizationEvidence graphs) {
        var bound = graphs.contribution();
        Object at=graphs.company().attributes().get("context.evaluatedAt");
        if(!(at instanceof String))at=graphs.facility().attributes().get("context.evaluatedAt");
        // Rejected owner graphs can have no attributes; they still yield an explicit DENY.
        var sampled = at instanceof String time ? java.time.Instant.parse(time) : bound.evidence().observedAt();
        var company =
                decision(
                        evaluator
                                .evaluate(graphs.companyRequest(), graphs.company(), sampled)
                                .decision());
        var facility =
                decision(
                        evaluator
                                .evaluate(graphs.facilityRequest(), graphs.facility(), sampled)
                                .decision());

        current(entry);
        return new Contribution(request,company,facility,bound.evidence().bindingId(),bound.evidence().sessionId(),
            bound.evidence().userId(),bound.evidence().observedAt(),bound.validUntil(),bound.backendPid(),bound.transactionId());
    }

    @Override
    public Admission acquire(Prepared prepared,Request request) {
        try {
            var holder=holder();
            if (!(prepared instanceof Entry entry) || !entry.used.compareAndSet(false,true)) throw denied();
            current(entry);
            entry.holder=holder;
            if(request==null || !TransactionSynchronizationManager.isSynchronizationActive()) throw denied();
            var nativeAdmission=identity.admit(entry.prepared);
            var ticket=new Ticket(this,entry,request,nativeAdmission,holder);
            TransactionSynchronizationManager.registerSynchronization(ticket);
            return ticket;
        } catch(RuntimeException failure) { poison(prepared);throw failure; }
    }

    @Override
    public Contribution contribute(Admission admission) {
        try {
            var ticket=ticket(admission);
            if(!ticket.used.compareAndSet(false,true)) throw denied();
            ticket.pair=identity.qualifyProductPair(ticket.admitted,ticket.request);
            ticket.graphs=reader.loadQualifiedProductPair(ticket.pair,ticket.request);
            Contribution result=evaluate(ticket.entry,ticket.request,ticket.graphs);
            ticket.complete=true;
            requireCurrent(ticket);
            return result;
        } catch(RuntimeException failure) { poison(admission);throw failure; }
    }

    @Override
    public void requireCurrent(Admission admission) {
        try {
            var ticket=ticket(admission);
            if(!ticket.complete) throw denied();
            identity.requireCurrentProductPair(ticket.pair,ticket.request);
            var graphs=reader.recheckProductPair(ticket.pair,ticket.request);
            if(!sameGraph(ticket.graphs.company(),graphs.company()) || !sameGraph(ticket.graphs.facility(),graphs.facility()))throw denied();
            var before=evaluate(ticket.entry,ticket.request,ticket.graphs);
            var now=evaluate(ticket.entry,ticket.request,graphs);
            if(before.companyDecision()!=now.companyDecision() || before.productDecision()!=now.productDecision())throw denied();
        } catch(RuntimeException failure) { poison(admission);throw failure; }
    }

    @Override
    public java.util.List<dz.sh.hidra.platform.persistence.ProtectedRelationFence> finalFences(Admission admission) {
        try {
            ticket(admission);
            return dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcIdentityAuthorityFence.requiredRelationFences();
        } catch(RuntimeException failure){poison(admission);throw failure;}
    }

    private Ticket ticket(Admission admission) {
        if(!(admission instanceof Ticket t) || t.owner!=this || !t.active || t.thread!=Thread.currentThread()
                || holder()!=t.holder || t.connection!=t.holder.getConnection()) throw denied();
        current(t.entry);identity.requireAdmitted(t.admitted);return t;
    }

    private static boolean sameGraph(dz.sh.hidra.modules.identity.domain.policy.AuthorizationEvidence a,
            dz.sh.hidra.modules.identity.domain.policy.AuthorizationEvidence b) {
        var x=new java.util.TreeMap<>(a.attributes());var y=new java.util.TreeMap<>(b.attributes());
        // The one fresh database sample is expected to advance; it is still evaluated in policies.
        x.remove("context.evaluatedAt");y.remove("context.evaluatedAt");
        return Objects.equals(a.failure(),b.failure()) && a.grants().equals(b.grants())
            && a.rules().equals(b.rules()) && x.equals(y);
    }
    private ConnectionHolder holder() {
        if(!TransactionSynchronizationManager.isActualTransactionActive()
            || !TransactionSynchronizationManager.isSynchronizationActive()
            || TransactionSynchronizationManager.isCurrentTransactionReadOnly() || jdbc.getDataSource()==null
            || !(TransactionSynchronizationManager.getResource(jdbc.getDataSource()) instanceof ConnectionHolder h)
            || h.isRollbackOnly())throw denied();
        return h;
    }
    private void poison(Object candidate) {
        if(candidate instanceof Ticket t){t.active=false;rollbackHolder(t.holder);}
        else if(candidate instanceof Entry e && e.holder!=null)rollbackHolder(e.holder);
        for(Object resource:TransactionSynchronizationManager.getResourceMap().values())
            if(resource instanceof ConnectionHolder h)rollbackHolder(h);
        try {TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();}
        catch(org.springframework.transaction.NoTransactionException absent){ /* Direct template caller. */ }
    }
    private static void rollbackHolder(ConnectionHolder holder) {
        holder.setRollbackOnly();
        if(TransactionSynchronizationManager.isSynchronizationActive()
            && TransactionSynchronizationManager.getResourceMap().containsValue(holder))
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization(){
                @Override public void beforeCommit(boolean readOnly){if(holder.isRollbackOnly())throw denied();}
            });
    }

    private static final class Ticket implements Admission,TransactionSynchronization {
        private final ProtectedProductDraftAuthorizationParticipant owner;
        private final Entry entry;
        private final Request request;
        private final ProtectedOperationIdentityParticipant.Admitted admitted;
        private final ConnectionHolder holder;
        private final java.sql.Connection connection;
        private final Thread thread=Thread.currentThread();
        private final AtomicBoolean used=new AtomicBoolean();
        private ProtectedOperationIdentityParticipant.ProductClaimPair pair;
        private volatile boolean active=true;
        private boolean complete;
        private JdbcProtectedAuthorizationEvidenceReader.PairedAuthorizationEvidence graphs;
        private Ticket(ProtectedProductDraftAuthorizationParticipant owner,Entry entry,Request request,ProtectedOperationIdentityParticipant.Admitted admitted,ConnectionHolder holder) {
            this.owner=owner;this.entry=entry;this.request=request;this.admitted=admitted;
            this.holder=holder;this.connection=holder.getConnection();
        }
        @Override public void suspend() { active=false; }
        @Override public void beforeCommit(boolean readOnly){if(!active || !complete || holder.isRollbackOnly())throw denied();}
        @Override public void afterCompletion(int status) { active=false; }
        @Override public String toString() { return "Admission[opaque paired Identity]"; }
    }

    private static Decision decision(AuthorizationDecisionValue value) {
        if (value == null) throw new IllegalStateException("Paired authorization unavailable.");
        return switch (value) {
            case PERMIT -> Decision.PERMIT;
            case DENY -> Decision.DENY;
            case INDETERMINATE -> Decision.INDETERMINATE;
            default -> throw new IllegalStateException("Unsupported paired decision.");
        };
    }

    private void current(Entry entry) {
        if (entry.owner != this
                || entry.thread != Thread.currentThread()
                || entry.actual != SecurityContextHolder.getContext().getAuthentication()
                || !entry.actual.isAuthenticated()) throw denied();
    }

    private static AccessDeniedException denied() {
        return new AccessDeniedException("Paired authorization denied.");
    }

    private static final class Entry implements Prepared {
        private final ProtectedProductDraftAuthorizationParticipant owner;
        private final ProtectedOperationIdentityParticipant.Prepared prepared;
        private final Authentication actual;
        private final Thread thread = Thread.currentThread();
        private final AtomicBoolean used = new AtomicBoolean();
        private volatile ConnectionHolder holder;

        private Entry(
                ProtectedProductDraftAuthorizationParticipant owner,
                ProtectedOperationIdentityParticipant.Prepared prepared,
                Authentication actual) {
            this.owner = owner;
            this.prepared = prepared;
            this.actual = actual;
        }

        @Override
        public String toString() {
            return "Prepared[opaque paired Identity]";
        }
    }
}
