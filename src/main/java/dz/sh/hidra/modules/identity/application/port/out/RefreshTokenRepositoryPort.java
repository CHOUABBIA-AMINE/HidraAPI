package dz.sh.hidra.modules.identity.application.port.out;

import dz.sh.hidra.modules.identity.domain.model.RefreshTokenFamily;
import java.time.Instant;

/** Database-owned single-use descendants; all mutations join the original owner transaction. */
public interface RefreshTokenRepositoryPort {
    record Consumed(RefreshTokenFamily family, String bindingId, String issuer, String jti,
                    String sessionId, long generation) {
        @Override public String toString() { return "Consumed[<redacted>]"; }
    }
    void create(RefreshTokenFamily family, byte[] digest, String bindingId, String issuer,
                String jti, String sessionId);
    Consumed consume(String familyId, byte[] digest, Instant observedAt);
    void attachRotation(String familyId, long previousGeneration, byte[] nextDigest,
                        String bindingId, String issuer, String jti, String newSessionId,
                        Instant observedAt);
    void revokeFamily(String familyId, Instant observedAt);
    /** Idempotent; does not reveal whether the presented token or family existed. */
    void logout(String familyId, byte[] digest, Instant observedAt);
}
