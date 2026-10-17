/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedOperationIdentityParticipant
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-15
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

    private Object connectionResource() {
        if (!TransactionSynchronizationManager.isActualTransactionActive()
                || jdbc.getDataSource() == null) return null;
        return TransactionSynchronizationManager.getResource(jdbc.getDataSource());
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

        private QualifiedClaimRead(
                ProtectedOperationIdentityParticipant owner, Authentication actual,
                OperationAuthenticationBinding locator, AuthorizationEvaluationRequest request,
                Contribution contribution, Object transactionResource) {
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
