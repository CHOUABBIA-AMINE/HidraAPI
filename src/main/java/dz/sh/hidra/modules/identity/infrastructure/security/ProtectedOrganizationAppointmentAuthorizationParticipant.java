/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedOrganizationAppointmentAuthorizationParticipant
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-22
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.security
 *
 * @Description : Qualifies a bounded appointment decision group using one genuine native admission.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.security;

import dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract;
import dz.sh.hidra.modules.identity.domain.service.AuthorizationPolicyEvaluator;
import dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcIdentityAuthorityFence;
import dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcOrganizationAppointmentCommitProofKeyAdapter;
import dz.sh.hidra.modules.identity.infrastructure.configuration.OrganizationAppointmentCommitProofProperties;
import dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcProtectedAuthorizationEvidenceReader;
import dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcProtectedAuthorizationEvidenceReader.OrganizationAuthorizationEvidence;
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
public class ProtectedOrganizationAppointmentAuthorizationParticipant
        implements OrganizationProtectedAppointmentAuthorizationContract {
    private final ProtectedOperationIdentityParticipant identity;
    private final JdbcProtectedAuthorizationEvidenceReader reader;
    private final JdbcTemplate jdbc;
    private final OrganizationAppointmentCommitProofProperties proofProperties;
    private final JdbcOrganizationAppointmentCommitProofKeyAdapter proofKeys;
    private final AuthorizationPolicyEvaluator evaluator = new AuthorizationPolicyEvaluator();

    public ProtectedOrganizationAppointmentAuthorizationParticipant(ProtectedOperationIdentityParticipant identity,
            JdbcProtectedAuthorizationEvidenceReader reader, JdbcTemplate jdbc) {
        this(identity, reader, jdbc, new OrganizationAppointmentCommitProofProperties(), null);
    }

    @org.springframework.beans.factory.annotation.Autowired
    public ProtectedOrganizationAppointmentAuthorizationParticipant(ProtectedOperationIdentityParticipant identity,
            JdbcProtectedAuthorizationEvidenceReader reader, JdbcTemplate jdbc,
            OrganizationAppointmentCommitProofProperties proofProperties,
            JdbcOrganizationAppointmentCommitProofKeyAdapter proofKeys) {
        this.identity = Objects.requireNonNull(identity);
        this.reader = Objects.requireNonNull(reader);
        this.jdbc = Objects.requireNonNull(jdbc);
        this.proofProperties = Objects.requireNonNull(proofProperties);
        this.proofKeys = proofKeys;
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
            // Native24 precedes these3; no Party/Organization fence has been acquired yet.
            if (proofProperties.isEnabled() && !request.action().name().startsWith("READ"))
                ticket.keyContext = Objects.requireNonNull(proofKeys).acquire();
            TransactionSynchronizationManager.registerSynchronization(ticket);
            return ticket;
        } catch (RuntimeException failure) { poison(prepared); throw failure; }
    }

    @Override public Contribution contribute(Admission admission) {
        try {
            var ticket = ticket(admission);
            if (!ticket.used.compareAndSet(false, true)) throw denied();
            ticket.group = identity.qualifyOrganizationGroup(ticket.admitted, ticket.request);
            ticket.graphs = reader.loadQualifiedOrganizationGroup(ticket.group, ticket.request);
            Contribution contribution = evaluate(ticket.request, ticket.graphs);
            if (contribution.overallDecision() != Decision.PERMIT) throw denied();
            ticket.complete = true;
            ticket(ticket);
            identity.requireCurrentOrganizationGroup(ticket.group, ticket.request);
            return contribution;
        } catch (RuntimeException failure) { poison(admission); throw failure; }
    }

    @Override public void requireCurrent(Admission admission) {
        try {
            var ticket = ticket(admission);
            if (!ticket.complete) throw denied();
            var current = reader.recheckOrganizationGroup(ticket.group, ticket.request);
            // Value comparison catches deleted evidence as well as changed normalized authority.
            if (!ticket.graphs.equals(current) || evaluate(ticket.request, current).overallDecision() != Decision.PERMIT) throw denied();
            identity.requireCurrentOrganizationGroup(ticket.group, ticket.request);
            if (ticket.keyContext != null) proofKeys.requireCurrent(ticket.keyContext);
        } catch (RuntimeException failure) { poison(admission); throw failure; }
    }

    @Override public List<ProtectedRelationFence> finalFences(Admission admission) {
        requireCurrent(admission);
        return JdbcIdentityAuthorityFence.requiredRelationFences();
    }

    @Override public CommitProof issueCommitProof(Admission admission, CommitIntent intent) {
        try {
            requireCurrent(admission);
            var ticket = ticket(admission);
            if (ticket.keyContext == null || ticket.request.action().name().startsWith("READ")
                    || !ticket.proofIssued.compareAndSet(false, true)) throw denied();
            var proof = proofProperties.issuerCodec().issue(evaluate(ticket.request, ticket.graphs),
                    Objects.requireNonNull(intent), ticket.keyContext);
            // Detect an authentication/holder/key change during the cryptographic operation.
            requireCurrent(admission);
            return proof;
        } catch (RuntimeException failure) { poison(admission); throw failure; }
    }

    @Override public List<ProtectedRelationFence> commitProofFences(Admission admission) {
        try {
            requireCurrent(admission);
            if (ticket(admission).keyContext == null) throw denied();
            return JdbcOrganizationAppointmentCommitProofKeyAdapter.requiredRelationFences();
        } catch (RuntimeException failure) { poison(admission); throw failure; }
    }

    private Contribution evaluate(Request request, OrganizationAuthorizationEvidence graphs) {
        var bound = graphs.contribution();
        var requirements = request.requirements();
        if (requirements.size() != graphs.requests().size()) throw denied();
        var decisions = new ArrayList<ResourceDecision>();
        for (int i = 0; i < requirements.size(); i++) {
            var expected = requirements.get(i);
            var actual = graphs.requests().get(i);
            var scope = expected.kind() == ResourceKind.UNIT
                    ? dz.sh.hidra.modules.identity.domain.value.ScopeType.ORGANIZATION_UNIT
                    : dz.sh.hidra.modules.identity.domain.value.ScopeType.CUSTOM;
            if (!bound.evidence().userId().equals(actual.userId())
                    || !OrganizationProtectedAppointmentAuthorizationContract.permissionCode(request.action(), expected.kind()).equals(actual.permissionCode())
                    || !expected.kind().resourceType().equals(actual.resourceType())
                    || !expected.reference().equals(actual.resourceReferenceId()) || actual.scope() == null
                    || actual.scope().scopeType() != scope
                    || !expected.reference().equals(actual.scope().scopeReferenceId())
                    || actual.scope().scopeCodeSnapshot() != null) throw denied();
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
            identity.invalidateOrganizationGroup(t.group);
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
                    if (holder.isRollbackOnly()) throw new org.springframework.security.access.AccessDeniedException("Appointment owner rejected transaction");
                }
            });
        }
    }

    private static AccessDeniedException denied() { return new AccessDeniedException("Appointment authorization denied."); }

    private static final class Entry implements Prepared {
        private final ProtectedOrganizationAppointmentAuthorizationParticipant owner;
        private final ProtectedOperationIdentityParticipant.Prepared prepared;
        private final Authentication actual;
        private final Thread thread = Thread.currentThread();
        private final AtomicBoolean used = new AtomicBoolean();
        private volatile ConnectionHolder holder;
        private Entry(ProtectedOrganizationAppointmentAuthorizationParticipant owner,
                ProtectedOperationIdentityParticipant.Prepared prepared, Authentication actual) {
            this.owner = owner; this.prepared = prepared; this.actual = actual;
        }
        @Override public String toString() { return "Prepared[opaque appointment Identity]"; }
    }

    private static final class Ticket implements Admission, TransactionSynchronization {
        private final ProtectedOrganizationAppointmentAuthorizationParticipant owner;
        private final Entry entry;
        private final Request request;
        private final ProtectedOperationIdentityParticipant.Admitted admitted;
        private final ConnectionHolder holder;
        private final java.sql.Connection connection;
        private final Thread thread = Thread.currentThread();
        private final AtomicBoolean used = new AtomicBoolean();
        private volatile boolean active = true;
        private final AtomicBoolean proofIssued = new AtomicBoolean();
        private JdbcOrganizationAppointmentCommitProofKeyAdapter.Context keyContext;
        private boolean complete;
        private ProtectedOperationIdentityParticipant.OrganizationClaimGroup group;
        private OrganizationAuthorizationEvidence graphs;
        private Ticket(ProtectedOrganizationAppointmentAuthorizationParticipant owner, Entry entry, Request request,
                ProtectedOperationIdentityParticipant.Admitted admitted, ConnectionHolder holder) {
            this.owner = owner; this.entry = entry; this.request = request; this.admitted = admitted;
            this.holder = holder; this.connection = holder.getConnection();
        }
        @Override public void suspend() { active = false; }
        @Override public void beforeCommit(boolean readOnly) {
            if (!active || holder.isRollbackOnly()) throw new org.springframework.security.access.AccessDeniedException("Appointment admission invalidated");
        }
        @Override public void afterCompletion(int status) { active = false; }
        @Override public String toString() { return "Admission[opaque appointment Identity]"; }
    }
}

