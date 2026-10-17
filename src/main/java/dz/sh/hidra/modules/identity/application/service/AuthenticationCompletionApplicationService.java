/**
 * @Project : HidraAPI @Product : Hidra - Hydrocarbon Intelligence for Data, Risk, and
 * Analytics @Author : Abir MEDJERAB @Owner : Sonatrach / TRC : Digitalization Initiative @Name :
 * AuthenticationCompletionApplicationService @CreatedOn : 2025-06-26 @UpdatedOn : 2026-10-17 @Type
 * : Class @Layer : Application @Module : identity @Package :
 * dz.sh.hidra.modules.identity.application.service @Description : Completes normalized
 * authentication by issuing the unified Hidra token and creating the logical login session.
 */
package dz.sh.hidra.modules.identity.application.service;

import dz.sh.hidra.modules.identity.application.model.*;
import dz.sh.hidra.modules.identity.application.port.in.CompleteAuthenticatedPrincipalUseCase;
import dz.sh.hidra.modules.identity.application.port.out.*;
import dz.sh.hidra.modules.identity.domain.model.HidraPrincipal;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.UUID;

/** One actual owner transaction; proxy commit succeeds before the login result is delivered. */
@Service
public class AuthenticationCompletionApplicationService
        implements CompleteAuthenticatedPrincipalUseCase {
    private final AccessTokenIssuerPort issuer;
    private final AuthenticationSessionLifecycleApplicationService lifecycle;
    private final TrustedAuthenticationVerificationPort verification;
    private final OperationAuthenticationBindingRepositoryPort bindings;
    private final AuthenticationSecurityStateQueryPort states;
    private final Clock clock;
    private final BoundSourceClaimEvidenceRepositoryPort sourceClaims;

    public AuthenticationCompletionApplicationService(
            AccessTokenIssuerPort issuer,
            AuthenticationSessionLifecycleApplicationService lifecycle,
            TrustedAuthenticationVerificationPort verification,
            OperationAuthenticationBindingRepositoryPort bindings,
            AuthenticationSecurityStateQueryPort states,
            @Qualifier("identityAuthenticationClock") Clock clock) {
        this(issuer, lifecycle, verification, bindings, states, clock, null);
    }

    @org.springframework.beans.factory.annotation.Autowired
    public AuthenticationCompletionApplicationService(
            AccessTokenIssuerPort issuer,
            AuthenticationSessionLifecycleApplicationService lifecycle,
            TrustedAuthenticationVerificationPort verification,
            OperationAuthenticationBindingRepositoryPort bindings,
            AuthenticationSecurityStateQueryPort states,
            @Qualifier("identityAuthenticationClock") Clock clock,
            BoundSourceClaimEvidenceRepositoryPort sourceClaims) {
        this.issuer = Objects.requireNonNull(issuer);
        this.lifecycle = Objects.requireNonNull(lifecycle);
        this.verification = Objects.requireNonNull(verification);
        this.bindings = Objects.requireNonNull(bindings);
        this.states = Objects.requireNonNull(states);
        this.clock = Objects.requireNonNull(clock);
        this.sourceClaims = sourceClaims;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public AuthenticationResult complete(
            AuthenticatedPrincipalInput input,
            String clientIp,
            String userAgent,
            String correlationId) {
        if (!TransactionSynchronizationManager.isActualTransactionActive()
                || TransactionSynchronizationManager.isCurrentTransactionReadOnly()
                || !TransactionSynchronizationManager.isSynchronizationActive()
                || !org.springframework.transaction.interceptor.TransactionAspectSupport
                        .currentTransactionStatus()
                        .isNewTransaction())
            throw new IllegalStateException("Bound completion requires an owner transaction.");
        var trusted = verification.consume(Objects.requireNonNull(input));
        boolean capturedClaims = trusted.prepareSourceClaims();
        if (capturedClaims && sourceClaims == null)
            throw new IllegalStateException("Bound source-claim persistence is unavailable.");
        var snapshot = trusted.snapshot();
        var settings = issuer.settings();
        Instant issued = clock.instant().truncatedTo(ChronoUnit.SECONDS),
                expires = issued.plusSeconds(settings.ttlSeconds());
        if (snapshot.sourceExpiresAt() != null && snapshot.sourceExpiresAt().isBefore(expires))
            expires = snapshot.sourceExpiresAt().truncatedTo(ChronoUnit.SECONDS);
        if (!expires.isAfter(clock.instant()))
            throw new IllegalStateException("Verified authentication deadline elapsed.");
        var principal =
                new HidraPrincipal(
                        input.userId(),
                        input.username(),
                        input.displayName(),
                        input.authenticationType(),
                        input.identityProviderId(),
                        input.roles(),
                        input.permissions(),
                        input.externalIdentityId());
        var context = new AccessTokenIssuanceContext(UUID.randomUUID().toString(), issued, expires);
        var binding =
                trusted.attest(
                        UUID.randomUUID().toString(),
                        UUID.randomUUID().toString(),
                        settings,
                        context);
        var session =
                lifecycle.startBoundSession(
                        principal,
                        binding.sessionId(),
                        issued,
                        expires,
                        clientIp,
                        userAgent,
                        correlationId);
        if (!states.current(binding, clock.instant()))
            throw new IllegalStateException("Verification generations or eligibility changed.");
        var token = issuer.issue(principal, context);
        if (!Objects.equals(token.issuer(), binding.nativeIssuer())
                || !Objects.equals(token.audience(), binding.nativeAudience())
                || !Objects.equals(token.subject(), binding.userId())
                || !Objects.equals(token.authenticationType(), binding.authenticationType())
                || !Objects.equals(token.providerId(), binding.providerId())
                || !Objects.equals(token.jti(), binding.jti())
                || !Objects.equals(token.issuedAt(), issued)
                || !Objects.equals(token.expiresAt(), expires))
            throw new IllegalStateException(
                    "Issued native token does not match its authenticated tuple.");
        bindings.append(binding);
        var evidence = trusted.attestSourceClaims(binding);
        if (capturedClaims != evidence.isPresent())
            throw new IllegalStateException("Bound source-claim capture is inconsistent.");
        if (capturedClaims) sourceClaims.append(evidence.orElseThrow());
        lifecycle.recordBoundSuccess(principal, session);
        if (!clock.instant().isBefore(expires))
            throw new IllegalStateException("Completion deadline elapsed.");
        trusted.publishAfterCommit(binding);
        return new AuthenticationResult(
                session.id(),
                token.tokenValue(),
                token.tokenType(),
                token.jti(),
                issued,
                expires,
                principal.userId(),
                principal.username(),
                principal.displayName(),
                principal.authenticationType(),
                principal.identityProviderId(),
                principal.roles(),
                principal.permissions());
    }
}
