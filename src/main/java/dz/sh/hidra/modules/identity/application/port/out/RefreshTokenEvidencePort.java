package dz.sh.hidra.modules.identity.application.port.out;

import dz.sh.hidra.modules.identity.application.model.AuthenticatedPrincipalInput;
import dz.sh.hidra.modules.identity.domain.model.HidraPrincipal;
import java.time.Instant;

/** Security-owned keyed token proof, fresh LOCAL producer attestation and grant digest. */
public interface RefreshTokenEvidencePort {
    record Opaque(String raw, String familyId, byte[] digest) {
        public Opaque { digest = digest.clone(); }
        @Override public byte[] digest() { return digest.clone(); }
        @Override public String toString() { return "Opaque[<redacted>]"; }
    }
    record Proof(String familyId, byte[] digest) {
        public Proof { digest = digest.clone(); }
        @Override public byte[] digest() { return digest.clone(); }
        @Override public String toString() { return "Proof[<redacted>]"; }
    }
    boolean enabled();
    long familyTtlSeconds();
    Opaque fresh(String familyId);
    Proof proof(String token);
    byte[] rightsDigest(HidraPrincipal principal);
    AuthenticatedPrincipalInput verifyAndBegin(RefreshTokenRepositoryPort.Consumed consumed,
                                               Instant observedAt);
    void finish();
}
