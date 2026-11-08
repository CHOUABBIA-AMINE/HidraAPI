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
import dz.sh.hidra.modules.identity.domain.model.RefreshTokenFamily;
import dz.sh.hidra.modules.identity.domain.service.RefreshTokenRotationPolicy;
import dz.sh.hidra.modules.identity.domain.model.AuthenticationEvent;
import dz.sh.hidra.modules.identity.domain.value.AuthenticationEventType;
import dz.sh.hidra.modules.identity.domain.value.AuthenticationProtocol;
import dz.sh.hidra.modules.identity.domain.value.ProviderType;
import org.springframework.security.authentication.BadCredentialsException;

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
    private final RefreshTokenRepositoryPort refreshTokens;
    private final RefreshTokenEvidencePort refreshEvidence;
    private final AuthenticationEventRepositoryPort refreshEvents;

    public AuthenticationCompletionApplicationService(
            AccessTokenIssuerPort issuer,
            AuthenticationSessionLifecycleApplicationService lifecycle,
            TrustedAuthenticationVerificationPort verification,
            OperationAuthenticationBindingRepositoryPort bindings,
            AuthenticationSecurityStateQueryPort states,
            @Qualifier("identityAuthenticationClock") Clock clock) {
        this(issuer,lifecycle,verification,bindings,states,clock,null,null,null,null);
    }

    public AuthenticationCompletionApplicationService(
            AccessTokenIssuerPort issuer,
            AuthenticationSessionLifecycleApplicationService lifecycle,
            TrustedAuthenticationVerificationPort verification,
            OperationAuthenticationBindingRepositoryPort bindings,
            AuthenticationSecurityStateQueryPort states,
            @Qualifier("identityAuthenticationClock") Clock clock,
            BoundSourceClaimEvidenceRepositoryPort sourceClaims) {
        this(issuer,lifecycle,verification,bindings,states,clock,sourceClaims,null,null,null);
    }

    @org.springframework.beans.factory.annotation.Autowired
    public AuthenticationCompletionApplicationService(
            AccessTokenIssuerPort issuer,
            AuthenticationSessionLifecycleApplicationService lifecycle,
            TrustedAuthenticationVerificationPort verification,
            OperationAuthenticationBindingRepositoryPort bindings,
            AuthenticationSecurityStateQueryPort states,
            @Qualifier("identityAuthenticationClock") Clock clock,
            BoundSourceClaimEvidenceRepositoryPort sourceClaims,
            RefreshTokenRepositoryPort refreshTokens,
            RefreshTokenEvidencePort refreshEvidence,
            AuthenticationEventRepositoryPort refreshEvents) {
        this.refreshTokens=refreshTokens;
        this.refreshEvidence=refreshEvidence;
        this.refreshEvents=refreshEvents;
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
        return completeOwner(input,clientIp,userAgent,correlationId,null,null);
    }

    /**
     * A successful refresh retires the old session and creates a fresh attested session/JWT
     * in the SAME new owner transaction. Only known-token replay or stale Identity denial
     * commits its revocation; any issuance failure rolls back every mutation.
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class,
            noRollbackFor={RefreshTokenRotationPolicy.Replay.class,
                           RefreshTokenRotationPolicy.Rejected.class})
    public AuthenticationResult rotateLocal(String raw, String clientIp,
            String userAgent, String correlationId) {
        if (refreshEvidence==null || refreshTokens==null || !refreshEvidence.enabled())
            throw new BadCredentialsException("Invalid refresh credentials.");
        var proof=refreshEvidence.proof(raw);
        var consumed=refreshTokens.consume(proof.familyId(),proof.digest(),clock.instant());
        try {
            AuthenticatedPrincipalInput input;
            try {
                input=refreshEvidence.verifyAndBegin(consumed,clock.instant());
            } catch(BadCredentialsException denied) {
                refreshTokens.revokeFamily(consumed.family().id(),clock.instant());
                throw new RefreshTokenRotationPolicy.Rejected();
            }
            var next=refreshEvidence.fresh(consumed.family().id());
            return completeOwner(input,clientIp,userAgent,correlationId,consumed,next);
        } finally {
            refreshEvidence.finish();
        }
    }

    private AuthenticationResult completeOwner(AuthenticatedPrincipalInput input,
            String clientIp, String userAgent, String correlationId,
            RefreshTokenRepositoryPort.Consumed rotated, RefreshTokenEvidencePort.Opaque next) {
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
        String refreshRaw=null;
        Instant refreshExpiry=null;
        if (rotated!=null) {
            if (refreshTokens==null || refreshEvidence==null || next==null
                    || principal.authenticationType()!=ProviderType.LOCAL
                    || !rotated.family().userId().equals(principal.userId()))
                throw new IllegalStateException("Invalid LOCAL refresh completion.");
            refreshTokens.attachRotation(rotated.family().id(),rotated.generation(),next.digest(),
                    binding.bindingId(),binding.nativeIssuer(),binding.jti(),
                    binding.sessionId(),issued);
            refreshRaw=next.raw();
            refreshExpiry=rotated.family().deadline();
        } else if (principal.authenticationType()==ProviderType.LOCAL
                && refreshEvidence!=null && refreshEvidence.enabled()) {
            if (refreshTokens==null)throw new IllegalStateException("Refresh storage unavailable.");
            var familyId=UUID.randomUUID().toString();
            var opaque=refreshEvidence.fresh(familyId);
            refreshExpiry=RefreshTokenRotationPolicy.deadline(issued,refreshEvidence.familyTtlSeconds());
            var family=new RefreshTokenFamily(familyId,principal.userId(),binding.providerId(),
                    binding.providerSecurityVersion(),binding.credentialId(),
                    binding.credentialSecurityVersion(),refreshEvidence.rightsDigest(principal),
                    issued,refreshExpiry);
            refreshTokens.create(family,opaque.digest(),binding.bindingId(),
                    binding.nativeIssuer(),binding.jti(),binding.sessionId());
            refreshRaw=opaque.raw();
        }
        var evidence = trusted.attestSourceClaims(binding);
        if (capturedClaims != evidence.isPresent())
            throw new IllegalStateException("Bound source-claim capture is inconsistent.");
        if (capturedClaims) sourceClaims.append(evidence.orElseThrow());
        if (rotated==null) {
            lifecycle.recordBoundSuccess(principal,session);
        } else {
            if(refreshEvents==null)throw new IllegalStateException("Refresh audit repository unavailable.");
            refreshEvents.save(new AuthenticationEvent(UUID.randomUUID().toString(),
                    principal.userId(),principal.identityProviderId(),null,null,
                    AuthenticationEventType.TOKEN_REFRESH,AuthenticationProtocol.LOCAL,
                    clientIp,userAgent,null,null,issued,correlationId));
        }
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
                principal.permissions(),
                refreshRaw,
                refreshExpiry);
    }
}
