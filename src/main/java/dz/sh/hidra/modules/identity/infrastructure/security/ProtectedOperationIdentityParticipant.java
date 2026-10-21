/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedOperationIdentityParticipant
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-21
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.security
 *
 * @Description : Contributes fenced current bound authentication within the caller physical transaction.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.security;

import dz.sh.hidra.modules.identity.application.contract.topology.TopologyOperationAuthenticationContract.Evidence;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyProtectedAuthorizationContract;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyScopedAccessContract.Request;
import dz.sh.hidra.modules.identity.domain.value.AuthorizationScope;
import dz.sh.hidra.modules.identity.domain.value.ScopeType;
import dz.sh.hidra.modules.identity.application.port.out.AccessTokenIssuerPort;
import dz.sh.hidra.modules.identity.application.port.out.AuthenticationSecurityStateQueryPort;
import dz.sh.hidra.modules.identity.application.port.out.OperationAuthenticationBindingRepositoryPort;
import dz.sh.hidra.modules.identity.domain.model.OperationAuthenticationBinding;
import dz.sh.hidra.modules.identity.domain.policy.AuthorizationEvaluationRequest;
import dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcIdentityAuthorityFence;
import dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcIdentityTemporalEventCollector.Candidate;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

/** B1 only. A trusted coordinator must establish the other owner fences and final commit guard. */
@Component
public class ProtectedOperationIdentityParticipant {
    private final OperationAuthenticationBindingRepositoryPort bindings;
    private final AuthenticationSecurityStateQueryPort states;
    private final ObjectProvider<JwtDecoder> decoder;
    private final AccessTokenIssuerPort issuer;
    private final TrustedAuthenticationVerificationRegistry registry;
    private final JdbcIdentityAuthorityFence fence;
    private final JdbcTemplate jdbc;
    private final int maxBytes;

    public ProtectedOperationIdentityParticipant(
            OperationAuthenticationBindingRepositoryPort bindings,
            AuthenticationSecurityStateQueryPort states,
            @Qualifier("hidraJwtDecoder") ObjectProvider<JwtDecoder> decoder,
            AccessTokenIssuerPort issuer,
            TrustedAuthenticationVerificationRegistry registry,
            JdbcIdentityAuthorityFence fence,
            JdbcTemplate jdbc,
            @Value("${hidra.identity.bound-authentication.max-token-bytes:65536}") int maxBytes) {
        this.bindings = Objects.requireNonNull(bindings);
        this.states = Objects.requireNonNull(states);
        this.decoder = Objects.requireNonNull(decoder);
        this.issuer = Objects.requireNonNull(issuer);
        this.registry = Objects.requireNonNull(registry);
        this.fence = Objects.requireNonNull(fence);
        this.jdbc = Objects.requireNonNull(jdbc);
        if (maxBytes <= 0) throw new IllegalArgumentException("Invalid native byte envelope.");
        this.maxBytes = maxBytes;
    }

    /** Non-authoritative locator preparation; expensive signature work precedes the work budget. */
    @Transactional(propagation = Propagation.NEVER)
    public Prepared prepare() {
        if (TransactionSynchronizationManager.isActualTransactionActive())
            throw new IllegalStateException("Preparation requires no transaction.");
        Authentication actual = SecurityContextHolder.getContext().getAuthentication();
        if (actual == null || !actual.isAuthenticated()) throw denied();
        try {
            OperationAuthenticationBinding binding;
            Jwt verified = null;
            TopologyOperationAuthenticationQueryAdapter.RawClaims claims = null;
            if (actual instanceof JwtAuthenticationToken bearer) {
                String raw = bearer.getToken().getTokenValue();
                if (raw == null
                        || raw.isBlank()
                        || raw.getBytes(StandardCharsets.UTF_8).length > maxBytes) throw denied();
                try {
                    verified = decoder.getObject().decode(raw);
                } catch (BadJwtException invalid) {
                    throw denied();
                }
                if (verified == null || !raw.equals(verified.getTokenValue())) throw unavailable();
                claims = TopologyOperationAuthenticationQueryAdapter.rawClaims(raw);
                if (claims == null) throw denied();
                binding =
                        bindings.findByNativeIssuerAndJti(claims.issuer(), claims.jti())
                                .orElseThrow(ProtectedOperationIdentityParticipant::denied);
                registry.validatePersistentOrigin(binding);
                if (!matches(claims, verified, binding)) throw denied();
            } else {
                var receipt = registry.completed(actual);
                if (receipt == null) throw denied();
                binding =
                        bindings.findByNativeIssuerAndJti(receipt.nativeIssuer(), receipt.jti())
                                .orElseThrow(ProtectedOperationIdentityParticipant::denied);
                registry.validatePersistentOrigin(binding);
                if (!receipt.equals(binding)) throw unavailable();
            }
            settingsMatch(binding);
            if (SecurityContextHolder.getContext().getAuthentication() != actual
                    || !actual.isAuthenticated()) throw denied();
            return new Prepared(this, actual, binding, verified, claims);
        } catch (AccessDeniedException denial) {
            throw denial;
        } catch (RuntimeException fault) {
            throw unavailable();
        }
    }

    /** No fallback transaction, independent connection, wall-clock eligibility or permission. */
    @Transactional(propagation = Propagation.MANDATORY)
    public Contribution contribute(Prepared prepared) {
        if (prepared == null || !prepared.used.compareAndSet(false, true)) throw denied();
        context(prepared);
        if (!TransactionSynchronizationManager.isActualTransactionActive()
                || TransactionSynchronizationManager.isCurrentTransactionReadOnly())
            throw new IllegalStateException("Writable participating transaction required.");
        try {
            if (!Boolean.TRUE.equals(
                    jdbc.queryForObject(
                            "SELECT current_setting('transaction_isolation')='read committed'"
                                    + " AND current_setting('transaction_read_only')='off'",
                            Boolean.class))) throw unavailable();
        } catch (RuntimeException fault) {
            throw unavailable();
        }
        // Preserve the existing fence/collector failures, including syntax, census and expiry.
        Candidate candidate;
        try {
            candidate = fence.collect(prepared.locator.bindingId());
        } catch (DataAccessException storage) {
            throw unavailable();
        }
        return finish(prepared,candidate);
    }

    private Contribution finish(Prepared prepared,Candidate candidate) {
        try {
            var binding =
                    bindings.findByNativeIssuerAndJti(
                                    prepared.locator.nativeIssuer(), prepared.locator.jti())
                            .orElseThrow(ProtectedOperationIdentityParticipant::denied);
            registry.validatePersistentOrigin(binding);
            if (!binding.equals(prepared.locator)) throw unavailable();
            settingsMatch(binding);
            if (prepared.claims != null && !matches(prepared.claims, prepared.verified, binding))
                throw unavailable();
            if (prepared.claims == null && !binding.equals(registry.completed(prepared.actual)))
                throw unavailable();
            Instant sample = candidate.sampledAt(), until = candidate.earliestFuture();
            if (sample == null
                    || sample.isBefore(binding.issuedAt())
                    || !sample.isBefore(binding.expiresAt())
                    || (binding.sourceExpiresAt() != null
                            && !sample.isBefore(binding.sourceExpiresAt()))) throw denied();
            if (!states.current(binding, sample)) throw denied();
            if (!finite(until)
                    || !until.isAfter(sample)
                    || until.isAfter(binding.expiresAt())
                    || (binding.sourceExpiresAt() != null
                            && until.isAfter(binding.sourceExpiresAt()))) throw unavailable();
            if (!Boolean.TRUE.equals(
                    jdbc.queryForObject(
                            "SELECT pg_backend_pid()=? AND txid_current()=?",
                            Boolean.class,
                            candidate.backendPid(),
                            candidate.transactionId()))) throw unavailable();
            context(prepared);
            return new Contribution(
                    new Evidence(
                            binding.bindingId(),
                            binding.sessionId(),
                            binding.userId(),
                            binding.providerId(),
                            binding.authenticationType(),
                            binding.providerSecurityVersion(),
                            binding.externalIdentityId(),
                            binding.externalSecurityVersion(),
                            binding.credentialId(),
                            binding.credentialSecurityVersion(),
                            binding.nativeIssuer(),
                            binding.jti(),
                            sample,
                            binding.expiresAt(),
                            binding.sourceExpiresAt()),
                    until,
                    candidate.backendPid(),
                    candidate.transactionId());
        } catch (AccessDeniedException denial) {
            throw denial;
        } catch (RuntimeException fault) {
            throw unavailable();
        }
    }


    @Transactional(propagation = Propagation.MANDATORY)
    public Admitted admit(Prepared prepared) {
        if (prepared==null || !prepared.used.compareAndSet(false,true)) throw denied();
        context(prepared);
        var admission=fence.acquireNowait(prepared.locator.bindingId());
        return new Admitted(this,prepared,admission);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public Contribution contribute(Admitted admitted) {
        requireAdmitted(admitted);
        if (!admitted.used.compareAndSet(false,true)) throw denied();
        var result=finish(admitted.prepared,fence.collect(admitted.fence));
        requireAdmitted(admitted);
        return result;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void requireAdmitted(Admitted admitted) {
        if (admitted==null || admitted.owner!=this) throw denied();
        context(admitted.prepared);
        fence.requireCurrent(admitted.fence);
    }

    public static final class Admitted {
        private final ProtectedOperationIdentityParticipant owner;
        private final Prepared prepared;
        private final JdbcIdentityAuthorityFence.Admission fence;
        private final AtomicBoolean used=new AtomicBoolean();
        private Admitted(ProtectedOperationIdentityParticipant owner,Prepared prepared,JdbcIdentityAuthorityFence.Admission fence) {
            this.owner=owner;this.prepared=prepared;this.fence=fence;
        }
        @Override public String toString() { return "Admitted[opaque native Identity]"; }
    }

    /**
     * Consume genuine prepared authentication once and bind the result to the actual caller
     * transaction and exact request. This is Identity-private diagnostic evidence, not PERMIT.
     */
    @Transactional(propagation = Propagation.MANDATORY)
    public QualifiedClaimRead qualifyClaims(Prepared prepared, AuthorizationEvaluationRequest request) {
        if (request == null || request.scope() == null) throw denied();
        Contribution contribution = contribute(prepared);
        try {
            Object transactionResource = connectionResource();
            if (transactionResource == null) throw unavailable();
            if (!prepared.locator.bindingId().equals(contribution.evidence().bindingId())
                    || !prepared.locator.sessionId().equals(contribution.evidence().sessionId())
                    || !prepared.locator.userId().equals(request.userId()))
                throw unavailable();
            context(prepared);
            return new QualifiedClaimRead(
                    this, prepared.actual, prepared.locator, request,
                    contribution, transactionResource);
        } catch (RuntimeException failure) {
            throw unavailable();
        }
    }

    /**
     * The private-constructor object, not the returned structural Contribution, authenticates
     * this entry. A failed or repeated consume irreversibly invalidates its one use.
     */
    @Transactional(propagation = Propagation.MANDATORY)
    public Contribution consumeClaims(QualifiedClaimRead qualified, AuthorizationEvaluationRequest request) {
        if (qualified != null && qualified.organization != null) {
            try { organizationChildContext(qualified.organization); }
            catch (RuntimeException failure) { poisonOrganization(qualified.organization); throw failure; }
        }
        if (qualified == null || !qualified.used.compareAndSet(false, true)) throw denied();
        if (qualified.owner != this
                || qualified.origin != Thread.currentThread()
                || qualified.actual != SecurityContextHolder.getContext().getAuthentication()
                || !qualified.actual.isAuthenticated()
                || request == null || !qualified.request.equals(request)
                || !TransactionSynchronizationManager.isActualTransactionActive()
                || TransactionSynchronizationManager.isCurrentTransactionReadOnly()
                || connectionResource() != qualified.transactionResource) throw denied();
        try {
            Contribution contribution = qualified.contribution;
            Evidence e = contribution.evidence();
            if (!e.bindingId().equals(qualified.locator.bindingId())
                    || !e.sessionId().equals(qualified.locator.sessionId())
                    || !e.jti().equals(qualified.locator.jti())
                    || !e.issuer().equals(qualified.locator.nativeIssuer())
                    || !e.userId().equals(request.userId())
                    || !Boolean.TRUE.equals(
                            jdbc.queryForObject(
                                    "SELECT pg_backend_pid()=? AND txid_current()=? AND "
                                      + "current_setting('transaction_isolation')='read committed' AND "
                                      + "current_setting('transaction_read_only')='off'",
                                    Boolean.class, contribution.backendPid(), contribution.transactionId())))
                throw unavailable();
            OperationAuthenticationBinding stored =
                    bindings.findByNativeIssuerAndJti(e.issuer(), e.jti())
                            .orElseThrow(ProtectedOperationIdentityParticipant::denied);
            registry.validatePersistentOrigin(stored);
            if (!stored.equals(qualified.locator)
                    || !stored.bindingId().equals(e.bindingId())
                    || !stored.userId().equals(e.userId())
                    || !stored.sessionId().equals(e.sessionId())
                    || !stored.providerId().equals(e.providerId())
                    || !stored.authenticationType().equals(e.authenticationType())
                    || stored.providerSecurityVersion() != e.providerGeneration()
                    || !Objects.equals(stored.externalIdentityId(), e.externalIdentityId())
                    || !Objects.equals(stored.externalSecurityVersion(), e.externalGeneration())
                    || !Objects.equals(stored.credentialId(), e.credentialId())
                    || !Objects.equals(stored.credentialSecurityVersion(), e.credentialGeneration())
                    || !Objects.equals(stored.expiresAt(), e.expiresAt())
                    || !Objects.equals(stored.sourceExpiresAt(), e.sourceExpiresAt())
                    || !states.current(stored, e.observedAt())) throw unavailable();
            if (SecurityContextHolder.getContext().getAuthentication() != qualified.actual)
                throw denied();
            return contribution;
        } catch (AccessDeniedException denial) {
            throw denial;
        } catch (RuntimeException fault) {
            throw unavailable();
        }
    }


    /**
     * Check the same already-consumed qualified credential after the full JDBC graph read.
     * This cannot mint a new credential or reset its one-shot state.
     */
    @Transactional(propagation = Propagation.MANDATORY)
    public void requireCurrentClaims(
            QualifiedClaimRead qualified, AuthorizationEvaluationRequest request) {
        if (qualified != null && qualified.organization != null) {
            try { organizationChildContext(qualified.organization); }
            catch (RuntimeException failure) { poisonOrganization(qualified.organization); throw failure; }
        }
        if (qualified == null || !qualified.used.get()
                || qualified.owner != this
                || qualified.origin != Thread.currentThread()
                || qualified.actual != SecurityContextHolder.getContext().getAuthentication()
                || !qualified.actual.isAuthenticated()
                || request == null || !qualified.request.equals(request)
                || !TransactionSynchronizationManager.isActualTransactionActive()
                || TransactionSynchronizationManager.isCurrentTransactionReadOnly()
                || connectionResource() == null
                || connectionResource() != qualified.transactionResource) throw denied();
        try {
            Contribution contribution = qualified.contribution;
            Evidence e = contribution.evidence();
            if (!e.bindingId().equals(qualified.locator.bindingId())
                    || !e.sessionId().equals(qualified.locator.sessionId())
                    || !e.jti().equals(qualified.locator.jti())
                    || !e.userId().equals(request.userId())
                    || !Boolean.TRUE.equals(
                            jdbc.queryForObject(
                                    "SELECT pg_backend_pid()=? AND txid_current()=?"
                                            + " AND current_setting('transaction_isolation')='read committed'"
                                            + " AND current_setting('transaction_read_only')='off'",
                                    Boolean.class,
                                    contribution.backendPid(),
                                    contribution.transactionId()))) throw unavailable();
            if (qualified.actual != SecurityContextHolder.getContext().getAuthentication())
                throw denied();
        } catch (AccessDeniedException denial) {
            throw denial;
        } catch (RuntimeException fault) {
            throw unavailable();
        }
    }

    /** Collect F once; derive both exact owner requests from the actual bound actor. */
    @Transactional(propagation = Propagation.MANDATORY)
    public QualifiedClaimPair qualifyPair(Prepared prepared, Request request) {
        if (request == null) throw denied();
        Contribution contribution = contribute(prepared);
        return pair(prepared,request,contribution);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public QualifiedClaimPair qualifyPair(Admitted admitted, Request request) {
        if(request==null) throw denied();
        return pair(admitted.prepared,request,contribute(admitted));
    }

    private QualifiedClaimPair pair(Prepared prepared,Request request,Contribution contribution) {
        Object resource = connectionResource();
        if (resource == null) throw unavailable();
        context(prepared);
        String code = TopologyProtectedAuthorizationContract.permissionCode(request.operation());
        var company =
                new AuthorizationEvaluationRequest(
                        contribution.evidence().userId(),
                        code,
                        "STATION_GRAPH_REVISION",
                        request.facilityId(),
                        new AuthorizationScope(
                                ScopeType.CUSTOM,
                                "topology-company:" + request.companyPartyId(),
                                null));
        var facility =
                new AuthorizationEvaluationRequest(
                        contribution.evidence().userId(),
                        code,
                        "STATION_GRAPH_REVISION",
                        request.facilityId(),
                        new AuthorizationScope(ScopeType.FACILITY, request.facilityId(), null));
        return new QualifiedClaimPair(
                this,
                request,
                new QualifiedClaimRead(
                        this, prepared.actual, prepared.locator, company, contribution, resource),
                new QualifiedClaimRead(
                        this, prepared.actual, prepared.locator, facility, contribution, resource));
    }

    /**
     * The reader alone unwraps one opaque pair; children retain their original single-use checks.
     */
    @Transactional(propagation = Propagation.MANDATORY)
    public PairReads consumePair(QualifiedClaimPair pair, Request request) {
        if (pair == null || !pair.used.compareAndSet(false, true)) throw denied();
        if (pair.owner != this || request == null || !pair.request.equals(request)) throw denied();
        // Do not consume the children here: loadQualified performs their actual fresh validation.
        pairContext(pair);
        return new PairReads(
                pair.company,
                pair.company.request,
                pair.facility,
                pair.facility.request,
                pair.company.contribution);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void requireCurrentPair(QualifiedClaimPair pair, Request request) {
        if (pair == null
                || !pair.used.get()
                || pair.owner != this
                || request == null
                || !pair.request.equals(request)) throw denied();
        pairContext(pair);
        requireCurrentClaims(pair.company, pair.company.request);
        requireCurrentClaims(pair.facility, pair.facility.request);
    }

    private void pairContext(QualifiedClaimPair pair) {
        QualifiedClaimRead a = pair.company, b = pair.facility;
        if (a.owner != this
                || b.owner != this
                || a.origin != Thread.currentThread()
                || b.origin != Thread.currentThread()
                || a.actual != b.actual
                || a.actual != SecurityContextHolder.getContext().getAuthentication()
                || !a.actual.isAuthenticated()
                || a.contribution != b.contribution
                || a.transactionResource == null
                || a.transactionResource != b.transactionResource
                || connectionResource() != a.transactionResource
                || TransactionSynchronizationManager.isCurrentTransactionReadOnly()) throw denied();
    }

    /** Private children cannot be swapped or supplied by a caller. */
    public static final class QualifiedClaimPair {
        private final ProtectedOperationIdentityParticipant owner;
        private final Request request;
        private final QualifiedClaimRead company, facility;
        private final AtomicBoolean used = new AtomicBoolean();

        private QualifiedClaimPair(
                ProtectedOperationIdentityParticipant owner,
                Request request,
                QualifiedClaimRead company,
                QualifiedClaimRead facility) {
            this.owner = owner;
            this.request = request;
            this.company = company;
            this.facility = facility;
        }

        @Override
        public String toString() {
            return "QualifiedClaimPair[opaque]";
        }
    }

    /** Identity-private reader transport, never a public contract or independent authority. */
    public record PairReads(
            QualifiedClaimRead company,
            AuthorizationEvaluationRequest companyRequest,
            QualifiedClaimRead facility,
            AuthorizationEvaluationRequest facilityRequest,
            Contribution contribution) {}


    /** A distinct issuer-tagged family; station and diagnostic children can never impersonate it. */
    @Transactional(propagation=Propagation.MANDATORY)
    public DraftClaimPair qualifyDraftPair(Admitted admitted,
            dz.sh.hidra.modules.identity.application.contract.topology.TopologyProtectedEngineeringDraftAuthorizationContract.Request request) {
        Objects.requireNonNull(request);
        Contribution c=contribute(admitted);Prepared p=admitted.prepared;
        Object resource=connectionResource();if(resource==null)throw unavailable();context(p);
        String code=dz.sh.hidra.modules.identity.application.contract.topology.TopologyProtectedEngineeringDraftAuthorizationContract.permissionCode(request.action());
        String resourceType=dz.sh.hidra.modules.identity.application.contract.topology.TopologyProtectedEngineeringDraftAuthorizationContract.resourceType(request.action());
        var company=new AuthorizationEvaluationRequest(c.evidence().userId(),code,resourceType,request.pipelineSystemId(),
            new AuthorizationScope(ScopeType.CUSTOM,"topology-company:"+request.companyPartyId(),null));
        var network=new AuthorizationEvaluationRequest(c.evidence().userId(),code,resourceType,request.pipelineSystemId(),
            new AuthorizationScope(ScopeType.CUSTOM,"topology-network:"+request.pipelineSystemId(),null));
        var a=new QualifiedClaimRead(this,p.actual,p.locator,company,c,resource,true);
        var b=new QualifiedClaimRead(this,p.actual,p.locator,network,c,resource,true);
        return new DraftClaimPair(this,request,a,b);
    }
    public static final class DraftClaimPair {
        private final ProtectedOperationIdentityParticipant owner;
        private final dz.sh.hidra.modules.identity.application.contract.topology.TopologyProtectedEngineeringDraftAuthorizationContract.Request request;
        private final QualifiedClaimRead company,network;
        private final AtomicBoolean used=new AtomicBoolean();
        private DraftClaimPair(ProtectedOperationIdentityParticipant owner,
            dz.sh.hidra.modules.identity.application.contract.topology.TopologyProtectedEngineeringDraftAuthorizationContract.Request request,
            QualifiedClaimRead company,QualifiedClaimRead network){this.owner=owner;this.request=request;this.company=company;this.network=network;}
        @Override public String toString(){return "DraftClaimPair[opaque]";}
    }
    @Transactional(propagation=Propagation.MANDATORY)
    public PairReads consumeDraftPair(DraftClaimPair pair,
            dz.sh.hidra.modules.identity.application.contract.topology.TopologyProtectedEngineeringDraftAuthorizationContract.Request request){
        if(pair==null || !pair.used.compareAndSet(false,true) || pair.owner!=this || !pair.request.equals(request))throw denied();
        draftContext(pair);
        return new PairReads(pair.company,pair.company.request,pair.network,pair.network.request,pair.company.contribution);
    }
    @Transactional(propagation=Propagation.MANDATORY)
    public void requireCurrentDraftPair(DraftClaimPair pair,
            dz.sh.hidra.modules.identity.application.contract.topology.TopologyProtectedEngineeringDraftAuthorizationContract.Request request){
        if(pair==null || !pair.used.get() || pair.owner!=this || !pair.request.equals(request))throw denied();
        draftContext(pair);requireCurrentClaims(pair.company,pair.company.request);requireCurrentClaims(pair.network,pair.network.request);
    }
    private void draftContext(DraftClaimPair p){
        var a=p.company;var b=p.network;
        if(!a.engineeringDraft || !b.engineeringDraft || a.owner!=this || b.owner!=this || a.origin!=Thread.currentThread()
            || b.origin!=Thread.currentThread() || a.actual!=b.actual || a.actual!=SecurityContextHolder.getContext().getAuthentication()
            || !a.actual.isAuthenticated() || a.contribution!=b.contribution || a.transactionResource==null
            || a.transactionResource!=b.transactionResource || connectionResource()!=a.transactionResource
            || TransactionSynchronizationManager.isCurrentTransactionReadOnly())throw denied();
    }
    /** This is only a family discriminator after genuine consume; it creates no credential. */
    public boolean engineeringDraftCredential(QualifiedClaimRead read){
        return read!=null && read.owner==this && read.used.get() && read.engineeringDraft;
    }

    private Object connectionResource() {
        if (!TransactionSynchronizationManager.isActualTransactionActive()
                || jdbc.getDataSource() == null) return null;
        return TransactionSynchronizationManager.getResource(jdbc.getDataSource());
    }

    /** One native sample issues an entire appointment family; no draft/station credential is reused. */
    public OrganizationClaimGroup qualifyOrganizationGroup(Admitted admitted,
            dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract.Request request) {
        OrganizationClaimGroup group = null;
        try {
            Objects.requireNonNull(request);
            var holder = organizationHolder();
            Contribution contribution = contribute(admitted);
            group = new OrganizationClaimGroup(this, admitted, request, contribution, holder);
            var children = new java.util.ArrayList<QualifiedClaimRead>();
            for (var required : request.requirements()) {
                var scope = required.kind() == dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract.ResourceKind.UNIT
                        ? ScopeType.ORGANIZATION_UNIT : ScopeType.CUSTOM;
                var evaluation = new AuthorizationEvaluationRequest(contribution.evidence().userId(),
                        dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract.permissionCode(request.action(), required.kind()),
                        required.kind().resourceType(), required.reference(),
                        new AuthorizationScope(scope, required.reference(), null));
                var child = new QualifiedClaimRead(this, admitted.prepared.actual, admitted.prepared.locator,
                        evaluation, contribution, holder, false, group);
                children.add(child);
            }
            group.children = List.copyOf(children);
            TransactionSynchronizationManager.registerSynchronization(group);
            organizationContext(group);
            return group;
        } catch (RuntimeException failure) { poisonOrganization(group); throw failure; }
    }

    /** Synchronous Identity-private transport. Public structural construction grants nothing. */
    public record OrganizationReads(List<QualifiedClaimRead> credentials,
            List<AuthorizationEvaluationRequest> requests, Contribution contribution) {
        public OrganizationReads { credentials = List.copyOf(credentials); requests = List.copyOf(requests); }
    }

    public OrganizationReads consumeOrganizationGroup(OrganizationClaimGroup group,
            dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract.Request request) {
        try {
            organizationContext(group);
            if (!group.request.equals(request) || !group.used.compareAndSet(false, true)) throw denied();
            return new OrganizationReads(group.children,
                    group.children.stream().map(c -> c.request).toList(), group.contribution);
        } catch (RuntimeException failure) { poisonOrganization(group); throw failure; }
    }

    public void requireCurrentOrganizationGroup(OrganizationClaimGroup group,
            dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract.Request request) {
        try {
            organizationContext(group);
            if (!group.used.get() || !group.request.equals(request)) throw denied();
            for (var child : group.children) requireCurrentClaims(child, child.request);
            // Retained locks exclude other writers, but an own-transaction/savepoint write must
            // never shorten the bound derived at the original sample. Mirror the existing
            // temporal collector's fixed columns; this recheck does not issue another sample.
            String futureBoundaries = List.of(
                    "authorization_policy_version:effective_from:effective_to",
                    "group_role_grant:valid_from:valid_to", "login_session:started_at:expires_at",
                    "operation_binding:issued_at:expires_at", "operation_binding:source_expires_at",
                    "role_permission_grant:valid_from:valid_to", "subject_security_attribute:valid_from:valid_to",
                    "user:locked_until", "user_group_membership:valid_from:valid_to",
                    "user_permission_grant:valid_from:valid_to", "user_role_grant:valid_from:valid_to")
                    .stream().flatMap(projection -> {
                        String[] parts = projection.split(":");
                        return java.util.Arrays.stream(parts).skip(1).map(column ->
                                "SELECT " + column + " AS boundary FROM public.hidra_identity_" + parts[0]);
                    }).collect(java.util.stream.Collectors.joining(" UNION ALL "));
            if (!Boolean.TRUE.equals(jdbc.queryForObject(
                    "SELECT NOT EXISTS (SELECT 1 FROM (" + futureBoundaries
                            + ") organization_boundaries WHERE boundary>?::timestamptz AND boundary<?::timestamptz)",
                    Boolean.class, java.sql.Timestamp.from(group.contribution.evidence().observedAt()),
                    java.sql.Timestamp.from(group.contribution.validUntil())))) throw denied();
            var contribution = group.contribution;
            if (!Boolean.TRUE.equals(jdbc.queryForObject(
                    "WITH organization_clock AS MATERIALIZED (SELECT pg_catalog.clock_timestamp() AS t) "
                            + "SELECT t>=? AND t<? AND pg_catalog.pg_backend_pid()=? AND pg_catalog.txid_current()=? FROM organization_clock",
                    Boolean.class, java.sql.Timestamp.from(contribution.evidence().observedAt()),
                    java.sql.Timestamp.from(contribution.validUntil()), contribution.backendPid(), contribution.transactionId()))) throw denied();
            context(group.admitted.prepared);
        } catch (RuntimeException failure) { poisonOrganization(group); throw failure; }
    }

    /** Only a consumed genuine child in its own consumed group can request a final graph recheck. */
    public OrganizationReads currentOrganizationReads(OrganizationClaimGroup group,
            dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract.Request request) {
        requireCurrentOrganizationGroup(group, request);
        return new OrganizationReads(group.children, group.children.stream().map(c -> c.request).toList(), group.contribution);
    }

    public Contribution currentOrganizationClaims(QualifiedClaimRead child, AuthorizationEvaluationRequest request) {
        try {
            if (!organizationCredential(child)) throw denied();
            requireCurrentClaims(child, request);
            return child.contribution;
        } catch (RuntimeException failure) { poisonOrganization(child == null ? null : child.organization); throw failure; }
    }

    public boolean organizationCredential(QualifiedClaimRead child) {
        return child != null && child.owner == this && child.used.get() && !child.engineeringDraft
                && child.organization != null && child.organization.owner == this
                && child.organization.used.get() && child.organization.children.contains(child);
    }

    public void invalidateOrganizationGroup(OrganizationClaimGroup group) { poisonOrganization(group); }

    private org.springframework.jdbc.datasource.ConnectionHolder organizationHolder() {
        if (!TransactionSynchronizationManager.isActualTransactionActive()
                || !TransactionSynchronizationManager.isSynchronizationActive()
                || TransactionSynchronizationManager.isCurrentTransactionReadOnly()
                || !(connectionResource() instanceof org.springframework.jdbc.datasource.ConnectionHolder holder)
                || holder.isRollbackOnly()) throw denied();
        try {
            if (org.springframework.transaction.interceptor.TransactionAspectSupport.currentTransactionStatus().isRollbackOnly()) throw denied();
        } catch (org.springframework.transaction.NoTransactionException absent) { /* TransactionTemplate caller. */ }
        return holder;
    }

    private void organizationContext(OrganizationClaimGroup group) {
        organizationChildContext(group);
        var holder = group.holder;
        requireAdmitted(group.admitted);
        var c = group.contribution;
        var p = group.admitted.prepared;
        settingsMatch(p.locator);
        var stored = bindings.findByNativeIssuerAndJti(p.locator.nativeIssuer(), p.locator.jti()).orElseThrow(ProtectedOperationIdentityParticipant::denied);
        registry.validatePersistentOrigin(stored);
        var now = jdbc.queryForObject("SELECT pg_catalog.clock_timestamp()", java.sql.Timestamp.class);
        if (!stored.equals(p.locator) || now == null || now.toInstant().isBefore(c.evidence().observedAt())
                || !now.toInstant().isBefore(c.validUntil()) || !states.current(stored, now.toInstant())
                || !Boolean.TRUE.equals(jdbc.queryForObject(
                        "SELECT pg_catalog.pg_backend_pid()=? AND pg_catalog.txid_current()=?",
                        Boolean.class, c.backendPid(), c.transactionId()))) throw denied();
        for (var child : group.children) {
            if (child.organization != group || child.owner != this || child.engineeringDraft
                    || child.contribution != c || child.transactionResource != holder
                    || child.actual != p.actual || child.origin != group.thread) throw denied();
        }
    }

    private void organizationChildContext(OrganizationClaimGroup group) {
        var holder = organizationHolder();
        if (group == null || group.owner != this || !group.active || group.thread != Thread.currentThread()
                || group.holder != holder || group.connection != holder.getConnection()) throw denied();
        context(group.admitted.prepared);
    }

    private void poisonOrganization(OrganizationClaimGroup group) {
        if (group != null) { group.holder.setRollbackOnly(); group.active = false; }
        if (connectionResource() instanceof org.springframework.jdbc.datasource.ConnectionHolder h) h.setRollbackOnly();
        try { org.springframework.transaction.interceptor.TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); }
        catch (org.springframework.transaction.NoTransactionException absent) { /* Direct template caller. */ }
    }

    public static final class OrganizationClaimGroup implements org.springframework.transaction.support.TransactionSynchronization {
        private final ProtectedOperationIdentityParticipant owner;
        private final Admitted admitted;
        private final dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract.Request request;
        private final Contribution contribution;
        private final org.springframework.jdbc.datasource.ConnectionHolder holder;
        private final java.sql.Connection connection;
        private final Thread thread = Thread.currentThread();
        private final AtomicBoolean used = new AtomicBoolean();
        private volatile boolean active = true;
        private List<QualifiedClaimRead> children = List.of();
        private OrganizationClaimGroup(ProtectedOperationIdentityParticipant owner, Admitted admitted,
                dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract.Request request,
                Contribution contribution, org.springframework.jdbc.datasource.ConnectionHolder holder) {
            this.owner = owner; this.admitted = admitted; this.request = request; this.contribution = contribution;
            this.holder = holder; this.connection = holder.getConnection();
        }
        @Override public void suspend() { active = false; }
        @Override public void afterCompletion(int status) { active = false; }
        @Override public String toString() { return "OrganizationClaimGroup[opaque]"; }
    }

    /** Opaque one-shot claim-read credential. No public factory and no authority via fields. */
    public static final class QualifiedClaimRead {
        private final ProtectedOperationIdentityParticipant owner;
        private final Thread origin = Thread.currentThread();
        private final Authentication actual;
        private final OperationAuthenticationBinding locator;
        private final AuthorizationEvaluationRequest request;
        private final Contribution contribution;
        private final Object transactionResource;
        private final AtomicBoolean used = new AtomicBoolean();
        private final boolean engineeringDraft;
        private final OrganizationClaimGroup organization;

        private QualifiedClaimRead(
                ProtectedOperationIdentityParticipant owner, Authentication actual,
                OperationAuthenticationBinding locator, AuthorizationEvaluationRequest request,
                Contribution contribution, Object transactionResource) {
            this(owner,actual,locator,request,contribution,transactionResource,false);
        }

        private QualifiedClaimRead(
                ProtectedOperationIdentityParticipant owner, Authentication actual,
                OperationAuthenticationBinding locator, AuthorizationEvaluationRequest request,
                Contribution contribution, Object transactionResource,boolean engineeringDraft) {
            this(owner, actual, locator, request, contribution, transactionResource, engineeringDraft, null);
        }

        private QualifiedClaimRead(
                ProtectedOperationIdentityParticipant owner, Authentication actual,
                OperationAuthenticationBinding locator, AuthorizationEvaluationRequest request,
                Contribution contribution, Object transactionResource, boolean engineeringDraft,
                OrganizationClaimGroup organization) {
            this.engineeringDraft=engineeringDraft;
            this.organization=organization;
            this.owner = owner;
            this.actual = actual;
            this.locator = locator;
            this.request = request;
            this.contribution = contribution;
            this.transactionResource = transactionResource;
        }

        @Override
        public String toString() {
            return "QualifiedClaimRead[opaque]";
        }
    }

    private void settingsMatch(OperationAuthenticationBinding binding) {
        var settings = issuer.settings();
        if (!settings.issuer().equals(binding.nativeIssuer())
                || !settings.audience().equals(binding.nativeAudience())) throw denied();
    }

    private static boolean matches(
            TopologyOperationAuthenticationQueryAdapter.RawClaims claims,
            Jwt verified,
            OperationAuthenticationBinding b) {
        return claims.matches(b)
                && HidraAccessTokenIssuer.exactClaims(
                        verified,
                        b.nativeIssuer(),
                        b.nativeAudience(),
                        b.userId(),
                        b.jti(),
                        b.authenticationType(),
                        b.providerId(),
                        b.issuedAt(),
                        b.expiresAt());
    }

    private void context(Prepared prepared) {
        if (prepared.owner != this
                || prepared.thread != Thread.currentThread()
                || SecurityContextHolder.getContext().getAuthentication() != prepared.actual
                || !prepared.actual.isAuthenticated()) throw denied();
    }

    /** Opaque, one-attempt locator; never eligible identity evidence. */
    public static final class Prepared {
        private final ProtectedOperationIdentityParticipant owner;
        private final Thread thread;
        private final Authentication actual;
        private final OperationAuthenticationBinding locator;
        private final Jwt verified;
        private final TopologyOperationAuthenticationQueryAdapter.RawClaims claims;
        private final AtomicBoolean used = new AtomicBoolean();

        private Prepared(
                ProtectedOperationIdentityParticipant owner,
                Authentication actual,
                OperationAuthenticationBinding locator,
                Jwt verified,
                TopologyOperationAuthenticationQueryAdapter.RawClaims claims) {
            this.owner = owner;
            this.thread = Thread.currentThread();
            this.actual = actual;
            this.locator = locator;
            this.verified = verified;
            this.claims = claims;
        }

        @Override
        public String toString() {
            return "Prepared[opaque]";
        }
    }

    /** Immutable same-transaction B1 data. Public construction is not an attestation or PERMIT. */
    public record Contribution(
            Evidence evidence, Instant validUntil, int backendPid, long transactionId) {
        public Contribution {
            if (evidence == null
                    || backendPid <= 0
                    || transactionId <= 0
                    || !finite(evidence.observedAt())
                    || !finite(evidence.expiresAt())
                    || !finite(validUntil)
                    || !validUntil.isAfter(evidence.observedAt())
                    || validUntil.isAfter(evidence.expiresAt())
                    || (evidence.sourceExpiresAt() != null
                            && (!finite(evidence.sourceExpiresAt())
                                    || validUntil.isAfter(evidence.sourceExpiresAt())))
                    || !uuid(evidence.bindingId())
                    || !uuid(evidence.sessionId())
                    || !uuid(evidence.jti())
                    || !text(evidence.userId(), 80)
                    || !text(evidence.providerId(), 80)
                    || !text(evidence.issuer(), 512)
                    || evidence.issuer().getBytes(StandardCharsets.UTF_8).length > 512
                    || evidence.providerGeneration() <= 0
                    || evidence.authenticationType() == null
                    || !List.of("LOCAL", "LDAP", "ACTIVE_DIRECTORY", "OIDC")
                            .contains(evidence.authenticationType())) throw invalidContribution();
            boolean local = "LOCAL".equals(evidence.authenticationType());
            if (local
                    ? (!text(evidence.credentialId(), 80)
                            || evidence.credentialGeneration() == null
                            || evidence.credentialGeneration() <= 0
                            || evidence.externalIdentityId() != null
                            || evidence.externalGeneration() != null
                            || evidence.sourceExpiresAt() != null)
                    : (evidence.credentialId() != null
                            || evidence.credentialGeneration() != null
                            || !text(evidence.externalIdentityId(), 80)
                            || evidence.externalGeneration() == null
                            || evidence.externalGeneration() <= 0
                            || ("OIDC".equals(evidence.authenticationType())
                                    != (evidence.sourceExpiresAt() != null))))
                throw invalidContribution();
        }
    }

    private static boolean finite(Instant instant) {
        return instant != null
                && instant.getEpochSecond() >= -210866803200L
                && instant.getEpochSecond() < 9224318016000L;
    }

    private static boolean uuid(String value) {
        try {
            return value != null && UUID.fromString(value).toString().equals(value);
        } catch (IllegalArgumentException invalid) {
            return false;
        }
    }

    private static boolean text(String value, int max) {
        if (value == null
                || value.isBlank()
                || !value.equals(value.strip())
                || !value.equals(value.trim())
                || value.codePointCount(0, value.length()) > max) return false;
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (c == 0) return false;
            if (Character.isHighSurrogate(c)) {
                if (++i >= value.length() || !Character.isLowSurrogate(value.charAt(i)))
                    return false;
            } else if (Character.isLowSurrogate(c)) return false;
        }
        return true;
    }

    private static IllegalArgumentException invalidContribution() {
        return new IllegalArgumentException("Invalid authentication contribution.");
    }

    private static AccessDeniedException denied() {
        return new AccessDeniedException("Bound authentication denied.");
    }

    private static IllegalStateException unavailable() {
        return new IllegalStateException(
                "Protected authentication infrastructure is unavailable or corrupt.");
    }
}
