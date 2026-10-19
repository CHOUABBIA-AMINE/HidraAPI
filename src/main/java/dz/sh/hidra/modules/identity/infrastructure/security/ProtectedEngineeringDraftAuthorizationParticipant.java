/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedEngineeringDraftAuthorizationParticipant
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-19
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

import dz.sh.hidra.modules.identity.application.contract.topology.TopologyProtectedEngineeringDraftAuthorizationContract;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyProtectedEngineeringDraftAuthorizationContract.Request;
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
public class ProtectedEngineeringDraftAuthorizationParticipant
        implements TopologyProtectedEngineeringDraftAuthorizationContract {
    private final ProtectedOperationIdentityParticipant identity;
    private final JdbcProtectedAuthorizationEvidenceReader reader;
    private final AuthorizationPolicyEvaluator evaluator = new AuthorizationPolicyEvaluator();

    public ProtectedEngineeringDraftAuthorizationParticipant(
            ProtectedOperationIdentityParticipant identity,
            JdbcProtectedAuthorizationEvidenceReader reader) {
        this.identity = Objects.requireNonNull(identity);
        this.reader = Objects.requireNonNull(reader);
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

    private Contribution evaluate(Entry entry,Request request,ProtectedOperationIdentityParticipant.DraftClaimPair pair) {
        var graphs = reader.loadQualifiedDraftPair(pair, request);
        var bound = graphs.contribution();
        var sampled = bound.evidence().observedAt();
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
        identity.requireCurrentDraftPair(pair, request);
        current(entry);
        return new Contribution(request,company,facility,bound.evidence().bindingId(),bound.evidence().sessionId(),
            bound.evidence().userId(),sampled,bound.validUntil(),bound.backendPid(),bound.transactionId());
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Admission acquire(Prepared prepared,Request request) {
        try {
            if (!(prepared instanceof Entry entry) || !entry.used.compareAndSet(false,true)) throw denied();
            current(entry);
            if(request==null || !TransactionSynchronizationManager.isSynchronizationActive()) throw denied();
            var nativeAdmission=identity.admit(entry.prepared);
            var ticket=new Ticket(this,entry,request,nativeAdmission);
            TransactionSynchronizationManager.registerSynchronization(ticket);
            return ticket;
        } catch(RuntimeException failure) { rollback();throw failure; }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Contribution contribute(Admission admission) {
        try {
            var ticket=ticket(admission);
            if(!ticket.used.compareAndSet(false,true)) throw denied();
            ticket.pair=identity.qualifyDraftPair(ticket.admitted,ticket.request);
            Contribution result=evaluate(ticket.entry,ticket.request,ticket.pair);
            ticket.complete=true;
            requireCurrent(ticket);
            return result;
        } catch(RuntimeException failure) { rollback();throw failure; }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void requireCurrent(Admission admission) {
        try {
            var ticket=ticket(admission);
            if(!ticket.complete) throw denied();
            identity.requireCurrentDraftPair(ticket.pair,ticket.request);
        } catch(RuntimeException failure) { rollback();throw failure; }
    }

    @Override @Transactional(propagation=Propagation.MANDATORY)
    public java.util.List<dz.sh.hidra.platform.persistence.ProtectedRelationFence> finalFences(Admission admission) {
        try {
            ticket(admission);
            return dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcIdentityAuthorityFence.requiredRelationFences();
        } catch(RuntimeException failure){rollback();throw failure;}
    }

    private Ticket ticket(Admission admission) {
        if(!(admission instanceof Ticket t) || t.owner!=this || !t.active || t.thread!=Thread.currentThread()) throw denied();
        current(t.entry);identity.requireAdmitted(t.admitted);return t;
    }

    private static void rollback() {
        for(Object resource:TransactionSynchronizationManager.getResourceMap().values())
            if(resource instanceof ConnectionHolder holder) holder.setRollbackOnly();
        try { TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); }
        catch(org.springframework.transaction.NoTransactionException absent) { /* Direct template caller. */ }
    }

    private static final class Ticket implements Admission,TransactionSynchronization {
        private final ProtectedEngineeringDraftAuthorizationParticipant owner;
        private final Entry entry;
        private final Request request;
        private final ProtectedOperationIdentityParticipant.Admitted admitted;
        private final Thread thread=Thread.currentThread();
        private final AtomicBoolean used=new AtomicBoolean();
        private ProtectedOperationIdentityParticipant.DraftClaimPair pair;
        private boolean active=true,complete;
        private Ticket(ProtectedEngineeringDraftAuthorizationParticipant owner,Entry entry,Request request,ProtectedOperationIdentityParticipant.Admitted admitted) {
            this.owner=owner;this.entry=entry;this.request=request;this.admitted=admitted;
        }
        @Override public void suspend() { active=false; }
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
        private final ProtectedEngineeringDraftAuthorizationParticipant owner;
        private final ProtectedOperationIdentityParticipant.Prepared prepared;
        private final Authentication actual;
        private final Thread thread = Thread.currentThread();
        private final AtomicBoolean used = new AtomicBoolean();

        private Entry(
                ProtectedEngineeringDraftAuthorizationParticipant owner,
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
