/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationAppointmentCommitProofCodec
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-22
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.security
 *
 * @Description : Authenticates transaction-bound appointment commit evidence within Identity.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.security;

import dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract.*;
import dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcOrganizationAppointmentCommitProofKeyAdapter.Context;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.HexFormat;
import java.util.UUID;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/** Version 1 is byte-for-byte mirrored by the private SQL encoder; no public signing oracle. */
public final class OrganizationAppointmentCommitProofCodec {
    static final String DOMAIN = "HIDRA/IDENTITY/ORG-APPOINTMENT-COMMIT/1";
    private final SecretKeySpec key;
    private final SecureRandom random = new SecureRandom();

    public OrganizationAppointmentCommitProofCodec(byte[] secret) {
        if (secret == null || secret.length != 32) throw new IllegalArgumentException("Invalid proof key");
        key = new SecretKeySpec(secret.clone(), "HmacSHA256");
    }

    CommitProof issue(Contribution nativeEvidence, CommitIntent intent, Context context) {
        byte[] nonce = new byte[32];
        random.nextBytes(nonce);
        return authenticate(nativeEvidence, intent, context, nonce);
    }

    // Package-private deterministic seam for independent known-answer tests, never a module contract.
    CommitProof authenticate(Contribution evidence, CommitIntent intent, Context context, byte[] nonce) {
        if (evidence.overallDecision() != Decision.PERMIT || evidence.request().action().name().startsWith("READ")
                || nonce == null || nonce.length != 32
                || context.backendPid() != evidence.backendPid() || context.transactionId() != evidence.transactionId()
                || !intent.operationValidUntil().isAfter(evidence.sampledAt())
                || intent.operationValidUntil().isAfter(evidence.validUntil())) throw invalid();
        try {
            var bytes = new ByteArrayOutputStream();
            var out = new DataOutputStream(bytes);
            text(out, DOMAIN); out.writeInt(1);
            uuid(out, context.audience()); text(out, context.publisher());
            text(out, context.keyId()); out.writeLong(context.revision()); out.write(nonce);
            uuid(out, UUID.fromString(evidence.bindingId())); uuid(out, UUID.fromString(evidence.sessionId()));
            text(out, evidence.userId()); text(out, evidence.request().action().name());
            out.writeInt(evidence.request().companyUnits().size());
            for (var pair : evidence.request().companyUnits()) {
                text(out, pair.companyPartyId()); text(out, pair.organizationUnitId());
            }
            text(out, evidence.request().employeeId());
            out.writeLong(micros(evidence.sampledAt())); out.writeLong(micros(evidence.validUntil()));
            out.writeLong(micros(intent.operationValidUntil()));
            out.writeInt(evidence.backendPid()); out.writeLong(evidence.transactionId());
            uuid(out, intent.eventId());
            out.write(HexFormat.of().parseHex(intent.commandSha256()));
            out.write(HexFormat.of().parseHex(intent.payloadSha256()));
            out.flush();
            var frame = bytes.toByteArray();
            if (frame.length > 4096) throw invalid();
            var mac = Mac.getInstance("HmacSHA256");
            mac.init(key);
            return new CommitProof(frame, mac.doFinal(frame));
        } catch (Exception failure) { throw invalid(); }
    }

    static long micros(Instant instant) {
        if (instant.getNano() % 1000 != 0) throw invalid();
        return Math.addExact(Math.multiplyExact(instant.getEpochSecond(), 1_000_000L), instant.getNano() / 1000);
    }
    private static void uuid(DataOutputStream out, UUID id) throws java.io.IOException {
        out.writeLong(id.getMostSignificantBits()); out.writeLong(id.getLeastSignificantBits());
    }
    private static void text(DataOutputStream out, String value) throws java.io.IOException {
        // Reject unpaired surrogates rather than UTF-8's lossy replacement.
        if (value == null || value.codePoints().anyMatch(c -> c >= 0xd800 && c <= 0xdfff)) throw invalid();
        byte[] encoded = value.getBytes(StandardCharsets.UTF_8);
        out.writeInt(encoded.length); out.write(encoded);
    }
    private static IllegalArgumentException invalid() { return new IllegalArgumentException("Invalid appointment commit proof context"); }
    @Override public String toString() { return "OrganizationAppointmentCommitProofCodec[redacted]"; }
}

