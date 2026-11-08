package dz.sh.hidra.modules.identity.domain.model;

import java.time.Instant;

/** One fixed-lifetime LOCAL refresh grant, never a role or permission authority. */
public record RefreshTokenFamily(
        String id, String userId, String providerId, long providerGeneration,
        String credentialId, long credentialGeneration, byte[] rightsDigest,
        Instant createdAt, Instant deadline) {
    public RefreshTokenFamily {
        if (blank(id) || blank(userId) || blank(providerId) || blank(credentialId)
                || providerGeneration <= 0 || credentialGeneration <= 0
                || rightsDigest == null || rightsDigest.length != 32 || createdAt == null
                || deadline == null || !createdAt.isBefore(deadline))
            throw new IllegalArgumentException("Invalid refresh family.");
        rightsDigest = rightsDigest.clone();
    }
    @Override public byte[] rightsDigest() { return rightsDigest.clone(); }
    @Override public String toString() { return "RefreshTokenFamily[metadata=<redacted>]"; }
    private static boolean blank(String value) { return value == null || value.isBlank(); }
}
