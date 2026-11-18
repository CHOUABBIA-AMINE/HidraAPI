/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : SyntheticExchange
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.domain.model
 * @Description : Immutable Integration-owned identity and fenced attempt state for a scoped synthetic exchange.
 */
package dz.sh.hidra.modules.integration.domain.model;
import java.time.Instant;
import java.util.Objects;
import java.util.NoSuchElementException;

public record SyntheticExchange(String id, Scope scope, String requestKey, String submitter,
        String bindingId, String profileId, String schemaVersionId, String schemaChecksum,
        String mappingDigest, String payloadDigest, String projection, String titleFr,
        String documentTypeId, String classificationId, String jobDefinitionId,
        String externalSystemId, String messageTypeId, String payloadFormatId,
        Phase phase, int latestAttempt, String documentId, Instant createdAt) {
    public record Scope(String companyId, String facilityId) {
        public Scope {
            companyId = bounded(companyId,96);
            facilityId = bounded(facilityId,120);
        }
    }
    public enum Phase {
        CLAIMED, FAILED, SUCCEEDED, ABANDONED;
        public boolean terminal() { return this == FAILED || this == SUCCEEDED || this == ABANDONED; }
    }
    public record Attempt(String id, String exchangeId, int ordinal, String actor,
            String requestKey, String reason, String runId, String messageId,
            String inboundId, String deadLetterId, Phase phase, String failureCode,
            String documentId, Instant claimedAt, Instant expiresAt, Instant finishedAt) {
        public Attempt {
            if (ordinal < 1 || ordinal > 2) throw new IllegalArgumentException("Attempt limit exceeded.");
            Objects.requireNonNull(phase, "Attempt phase");
            if (phase == Phase.SUCCEEDED && documentId == null)
                throw new IllegalArgumentException("Success requires a receiver.");
            if (phase != Phase.SUCCEEDED && documentId != null)
                throw new IllegalArgumentException("Uncommitted receiver cannot be disclosed.");
            if (phase == Phase.FAILED && failureCode == null)
                throw new IllegalArgumentException("Failed attempt requires a safe code.");
        }
    }
    public SyntheticExchange {
        Objects.requireNonNull(scope,"scope");
        Objects.requireNonNull(phase,"phase");
        if (latestAttempt < 1 || latestAttempt > 2)
            throw new IllegalArgumentException("Invalid latest attempt.");
        if ((phase == Phase.SUCCEEDED) != (documentId != null))
            throw new IllegalArgumentException("Receiver receipt and success must agree.");
    }
    public static String bounded(String input, int length) {
        if (input == null || input.isBlank() || input.length() > length
                || !input.equals(input.trim()) || input.chars().anyMatch(Character::isISOControl))
            throw new IllegalArgumentException("Invalid bounded exchange value.");
        return input;
    }
    public void requireReplay(String newActor, int expectedAttempt) {
        if (phase != Phase.FAILED && phase != Phase.ABANDONED)
            throw new IllegalStateException("Exchange is not replayable.");
        if (latestAttempt != expectedAttempt || latestAttempt >= 2)
            throw new IllegalStateException("Stale or exhausted exchange attempt.");
        if (submitter.equals(newActor))
            throw new NoSuchElementException("Protected exchange unavailable.");
    }
}
