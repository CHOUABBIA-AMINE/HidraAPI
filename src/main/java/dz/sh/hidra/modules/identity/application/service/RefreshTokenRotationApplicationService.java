package dz.sh.hidra.modules.identity.application.service;

import dz.sh.hidra.modules.identity.application.model.AuthenticationResult;
import dz.sh.hidra.modules.identity.application.port.in.RotateLocalRefreshTokenUseCase;
import dz.sh.hidra.modules.identity.application.port.out.RefreshTokenEvidencePort;
import dz.sh.hidra.modules.identity.application.port.out.RefreshTokenRepositoryPort;
import java.time.Instant;
import java.util.Objects;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Delegates issuance to the already registered original-transaction completion owner.
 * Logout uses a distinct writable transaction and no bearer/identity assertions.
 */
@Service
public class RefreshTokenRotationApplicationService implements RotateLocalRefreshTokenUseCase {
    private final AuthenticationCompletionApplicationService completion;
    private final RefreshTokenRepositoryPort tokens;
    private final RefreshTokenEvidencePort evidence;
    public RefreshTokenRotationApplicationService(AuthenticationCompletionApplicationService completion,
            RefreshTokenRepositoryPort tokens, RefreshTokenEvidencePort evidence) {
        this.completion = Objects.requireNonNull(completion);
        this.tokens = Objects.requireNonNull(tokens);
        this.evidence = Objects.requireNonNull(evidence);
    }
    @Override
    public AuthenticationResult rotate(String token, String ip, String agent, String correlation) {
        if (!evidence.enabled()) throw new BadCredentialsException("Invalid refresh credentials.");
        return completion.rotateLocal(token, ip, agent, correlation);
    }
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void logout(String token) {
        if (!evidence.enabled()) return;
        try {
            var proof = evidence.proof(token);
            tokens.logout(proof.familyId(), proof.digest(), Instant.now());
        } catch (BadCredentialsException ignored) {
            // A non-disclosing 204 is idempotent for absent, malformed and revoked credentials.
        }
    }
}
