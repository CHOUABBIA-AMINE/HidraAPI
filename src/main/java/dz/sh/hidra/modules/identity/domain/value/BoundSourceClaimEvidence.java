/**
 * @Project : HidraAPI @Product : Hidra - Hydrocarbon Intelligence for Data, Risk, and
 * Analytics @Author : Abir MEDJERAB @Owner : Sonatrach / TRC : Digitalization Initiative @Name :
 * BoundSourceClaimEvidence @CreatedOn : 2025-06-26 @UpdatedOn : 2026-10-17 @Type : Record @Layer :
 * Domain @Module : identity @Package : dz.sh.hidra.modules.identity.domain.value @Description :
 * Models immutable bounded claim data without granting producer or authorization authority.
 */
package dz.sh.hidra.modules.identity.domain.value;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

/** Structural immutable data only. Construction is never evidence of verified producer origin. */
public record BoundSourceClaimEvidence(
        String bindingId,
        short evidenceFormat,
        long mappingGeneration,
        long issuanceTransactionId,
        ClaimCoverage coverage,
        byte[] producerAuthenticator) {
    public BoundSourceClaimEvidence {
        Objects.requireNonNull(bindingId);
        Objects.requireNonNull(coverage);
        Objects.requireNonNull(producerAuthenticator);
        if (!UUID.fromString(bindingId).toString().equals(bindingId)
                || evidenceFormat != 1
                || mappingGeneration <= 0
                || issuanceTransactionId <= 0
                || producerAuthenticator.length != 32) throw invalid();
        producerAuthenticator = producerAuthenticator.clone();
    }

    @Override
    public byte[] producerAuthenticator() {
        return producerAuthenticator.clone();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof BoundSourceClaimEvidence e
                && bindingId.equals(e.bindingId)
                && evidenceFormat == e.evidenceFormat
                && mappingGeneration == e.mappingGeneration
                && issuanceTransactionId == e.issuanceTransactionId
                && coverage.equals(e.coverage)
                && Arrays.equals(producerAuthenticator, e.producerAuthenticator);
    }

    @Override
    public int hashCode() {
        return 31
                        * Objects.hash(
                                bindingId,
                                evidenceFormat,
                                mappingGeneration,
                                issuanceTransactionId,
                                coverage)
                + Arrays.hashCode(producerAuthenticator);
    }

    @Override
    public String toString() {
        return "BoundSourceClaimEvidence[provenance=<redacted>, claims=<redacted>]";
    }

    private static IllegalArgumentException invalid() {
        return new IllegalArgumentException("Invalid bound source-claim evidence.");
    }
}
