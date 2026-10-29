/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedTopologyInstalledUnitAuthorizationParticipant
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-29
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.security
 *
 * @Description : Qualifies exact installed-unit operation scopes using one genuine native admission.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.security;

import dz.sh.hidra.modules.identity.application.contract.topology.TopologyInstalledUnitAuthorizationContract;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyInstalledUnitAuthorizationContract.*;
import dz.sh.hidra.modules.identity.domain.service.AuthorizationPolicyEvaluator;
import dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcIdentityAuthorityFence;
import dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcProtectedAuthorizationEvidenceReader;
import dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcProtectedAuthorizationEvidenceReader.InstalledUnitAuthorizationEvidence;
import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.NoTransactionException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/** All ticket checks execute inside the body so a foreign thread also poisons the original holder. */
@Component
public class ProtectedTopologyInstalledUnitAuthorizationParticipant
        implements TopologyInstalledUnitAuthorizationContract {
    private final ProtectedOperationIdentityParticipant identity;
    private final JdbcProtectedAuthorizationEvidenceReader reader;
    private final JdbcTemplate jdbc;
    private final AuthorizationPolicyEvaluator evaluator = new AuthorizationPolicyEvaluator();

    public ProtectedTopologyInstalledUnitAuthorizationParticipant(ProtectedOperationIdentityParticipant identity,
            JdbcProtectedAuthorizationEvidenceReader reader, JdbcTemplate jdbc) {
        this.identity=Objects.requireNonNull(identity); this.reader=Objects.requireNonNull(reader); this.jdbc=Objects.requireNonNull(jdbc);
    }

    @Override @Transactional(propagation = Propagation.NEVER)
    public Prepared prepare() {
        var actual = SecurityContextHolder.getContext().getAuthentication();
        var prepared = identity.prepare();
        if (actual == null || !actual.isAuthenticated()
                || actual != SecurityContextHolder.getContext().getAuthentication()) throw denied();
        return new Entry(this, prepared, actual);
    }

    @Override public Admission acquire(Prepared prepared, Request request) {
        try {
            Objects.requireNonNull(request); // Structural rejection precedes native consumption.
            var holder = holder();
            if (!(prepared instanceof Entry entry)) throw denied();
            current(entry);
            if (!entry.used.compareAndSet(false, true)) throw denied();
            entry.holder = holder;
            var admitted = identity.admit(entry.prepared);
            var ticket = new Ticket(this, entry, request, admitted, holder);
            TransactionSynchronizationManager.registerSynchronization(ticket);
            return ticket;
        } catch (RuntimeException failure) { poison(prepared); throw failure; }
    }

    @Override public Contribution contribute(Admission admission) {
        try {
            var ticket = ticket(admission);
            if (!ticket.used.compareAndSet(false, true)) throw denied();
            ticket.group = identity.qualifyInstalledUnitGroup(ticket.admitted, ticket.request);
            ticket.graphs = reader.loadQualifiedInstalledUnitGroup(ticket.group, ticket.request);
            Contribution contribution = evaluate(ticket.request, ticket.graphs);
            if (contribution.overallDecision() != Decision.PERMIT) throw denied();
            ticket.complete = true;
            ticket(ticket);
            identity.requireCurrentInstalledUnitGroup(ticket.group, ticket.request);
            return contribution;
        } catch (RuntimeException failure) { poison(admission); throw failure; }
    }

    @Override public void requireCurrent(Admission admission) {
        try {
            var ticket = ticket(admission);
            if (!ticket.complete) throw denied();
            var current = reader.recheckInstalledUnitGroup(ticket.group, ticket.request);
            // Value comparison catches deleted evidence as well as changed normalized authority.
            if (!ticket.graphs.equals(current) || evaluate(ticket.request, current).overallDecision() != Decision.PERMIT) throw denied();
            identity.requireCurrentInstalledUnitGroup(ticket.group, ticket.request);
        } catch (RuntimeException failure) { poison(admission); throw failure; }
    }

    @Override public List<ProtectedRelationFence> finalFences(Admission admission) {
        requireCurrent(admission);
        return JdbcIdentityAuthorityFence.requiredRelationFences();
    }

    private Contribution evaluate(Request request, InstalledUnitAuthorizationEvidence graphs) {
        var bound = graphs.contribution();
        var requirements = request.requirements();
        if (requirements.size() != graphs.requests().size()) throw denied();
        var decisions = new ArrayList<ResourceDecision>();
        for (int i = 0; i < requirements.size(); i++) {
            var expected = requirements.get(i);
            var actual = graphs.requests().get(i);
            var scope = expected.scope() == Scope.COMPANY
                    ? dz.sh.hidra.modules.identity.domain.value.ScopeType.CUSTOM
                    : dz.sh.hidra.modules.identity.domain.value.ScopeType.FACILITY;
            String ref=scope==dz.sh.hidra.modules.identity.domain.value.ScopeType.CUSTOM
                    ? "topology-company:"+expected.scopeReference() : expected.scopeReference();
            if (!bound.evidence().userId().equals(actual.userId())
                    || !TopologyInstalledUnitAuthorizationContract.permissionCode(request.operation(),expected.resourceType()).equals(actual.permissionCode())
                    || !expected.resourceType().equals(actual.resourceType()) || !expected.resourceId().equals(actual.resourceReferenceId())
                    || actual.scope()==null || actual.scope().scopeType()!=scope
                    || !ref.equals(actual.scope().scopeReferenceId()) || actual.scope().scopeCodeSnapshot()!=null) throw denied();
            var value = evaluator.evaluate(actual, graphs.evidence().get(i), bound.evidence().observedAt()).decision();
            Decision decision = switch (value) {
                case PERMIT -> Decision.PERMIT;
                case DENY -> Decision.DENY;
                case INDETERMINATE -> Decision.INDETERMINATE;
                default -> throw denied();
            };
            decisions.add(new ResourceDecision(expected, decision));
        }
        return new Contribution(request, decisions, bound.evidence().bindingId(), bound.evidence().sessionId(),
                bound.evidence().userId(), bound.evidence().observedAt(), bound.validUntil(), bound.backendPid(), bound.transactionId());
    }

    private Ticket ticket(Admission admission) {
        if (!(admission instanceof Ticket ticket) || ticket.owner != this || !ticket.active
                || ticket.thread != Thread.currentThread() || holder() != ticket.holder
                || ticket.connection != ticket.holder.getConnection()) throw denied();
        current(ticket.entry);
        identity.requireAdmitted(ticket.admitted);
        return ticket;
    }

    private void current(Entry entry) {
        if (entry.owner != this || entry.thread != Thread.currentThread()
                || entry.actual != SecurityContextHolder.getContext().getAuthentication()
                || !entry.actual.isAuthenticated()) throw denied();
    }

    private ConnectionHolder holder() {
        if (!TransactionSynchronizationManager.isActualTransactionActive()
                || !TransactionSynchronizationManager.isSynchronizationActive()
                || TransactionSynchronizationManager.isCurrentTransactionReadOnly() || jdbc.getDataSource() == null
                || !(TransactionSynchronizationManager.getResource(jdbc.getDataSource()) instanceof ConnectionHolder holder)
                || holder.isRollbackOnly()) throw denied();
        try { if (TransactionAspectSupport.currentTransactionStatus().isRollbackOnly()) throw denied(); }
        catch (NoTransactionException absent) { /* Direct TransactionTemplate caller. */ }
        return holder;
    }

    private void poison(Object candidate) {
        if (candidate instanceof Ticket t) {
            rollbackHolder(t.holder); t.active = false;
            identity.invalidateInstalledUnitGroup(t.group);
        } else if (candidate instanceof Entry e && e.holder != null) rollbackHolder(e.holder);
        for (Object resource : TransactionSynchronizationManager.getResourceMap().values()) {
            if (resource instanceof ConnectionHolder holder) rollbackHolder(holder);
        }
        try { TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); }
        catch (NoTransactionException absent) { /* Direct TransactionTemplate caller. */ }
    }

    private static void rollbackHolder(ConnectionHolder holder) {
        holder.setRollbackOnly();
        // JpaTransactionManager need not consult its bound JDBC holder's rollback flag.
        // A SQL-free synchronization vetoes commit even after the caller swallowed the failure.
        if (TransactionSynchronizationManager.isSynchronizationActive()
                && TransactionSynchronizationManager.getResourceMap().containsValue(holder)) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override public void beforeCommit(boolean readOnly) {
                    if (holder.isRollbackOnly()) throw new org.springframework.security.access.AccessDeniedException("Installed-unit owner rejected transaction");
                }
            });
        }
    }

    private static AccessDeniedException denied() { return new AccessDeniedException("Installed-unit authorization denied."); }

    private static final class Entry implements Prepared {
        private final ProtectedTopologyInstalledUnitAuthorizationParticipant owner;
        private final ProtectedOperationIdentityParticipant.Prepared prepared;
        private final Authentication actual;
        private final Thread thread = Thread.currentThread();
        private final AtomicBoolean used = new AtomicBoolean();
        private volatile ConnectionHolder holder;
        private Entry(ProtectedTopologyInstalledUnitAuthorizationParticipant owner,
                ProtectedOperationIdentityParticipant.Prepared prepared, Authentication actual) {
            this.owner = owner; this.prepared = prepared; this.actual = actual;
        }
        @Override public String toString() { return "Prepared[opaque installed-unit Identity]"; }
    }

    private static final class Ticket implements Admission, TransactionSynchronization {
        private final ProtectedTopologyInstalledUnitAuthorizationParticipant owner;
        private final Entry entry;
        private final Request request;
        private final ProtectedOperationIdentityParticipant.Admitted admitted;
        private final ConnectionHolder holder;
        private final java.sql.Connection connection;
        private final Thread thread = Thread.currentThread();
        private final AtomicBoolean used = new AtomicBoolean();
        private volatile boolean active = true;
        private boolean complete;
        private ProtectedOperationIdentityParticipant.InstalledUnitClaimGroup group;
        private InstalledUnitAuthorizationEvidence graphs;
        private Ticket(ProtectedTopologyInstalledUnitAuthorizationParticipant owner, Entry entry, Request request,
                ProtectedOperationIdentityParticipant.Admitted admitted, ConnectionHolder holder) {
            this.owner = owner; this.entry = entry; this.request = request; this.admitted = admitted;
            this.holder = holder; this.connection = holder.getConnection();
        }
        @Override public void suspend() { active = false; }
        @Override public void beforeCommit(boolean readOnly) {
            if (!active || holder.isRollbackOnly()) throw new org.springframework.security.access.AccessDeniedException("Installed-unit admission invalidated");
        }
        @Override public void afterCompletion(int status) { active = false; }
        @Override public String toString() { return "Admission[opaque installed-unit Identity]"; }
    }
}

