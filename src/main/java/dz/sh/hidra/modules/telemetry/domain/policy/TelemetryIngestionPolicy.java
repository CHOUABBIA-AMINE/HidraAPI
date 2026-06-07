/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryIngestionPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.policy
 *
 * @Description : Domain policy for telemetry ingestion batches.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.policy;

import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryIngestionBatch;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetrySource;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryIngestionBatchStatus;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetrySourceStatus;

/**
 * Domain policy for telemetry ingestion batches.
 *
 * <p>Business role:
 * Guards telemetry ingestion execution and batch state transitions.
 *
 * <p>Architecture role:
 * Pure domain policy. It does not perform protocol ingestion, store logs, or import infrastructure
 * clients.
 */
public final class TelemetryIngestionPolicy {

    /**
     * Ensures a source can start ingestion.
     *
     * @param source telemetry source
     */
    public void requireSourceCanIngest(TelemetrySource source) {
        Objects.requireNonNull(source, "Telemetry source must not be null.");

        if (!TelemetrySourceStatus.ACTIVE.equals(source.status())) {
            throw new BusinessRuleViolationException("Telemetry ingestion can start only for an active source.");
        }
    }

    /**
     * Ensures a batch can move to processing.
     *
     * @param batch ingestion batch
     */
    public void requireBatchCanStartProcessing(TelemetryIngestionBatch batch) {
        Objects.requireNonNull(batch, "Telemetry ingestion batch must not be null.");

        if (!TelemetryIngestionBatchStatus.RECEIVED.equals(batch.status())) {
            throw new BusinessRuleViolationException("Only received telemetry ingestion batches can start processing.");
        }
    }

    /**
     * Ensures a batch can complete with the supplied counters.
     *
     * @param batch ingestion batch
     * @param received received count
     * @param accepted accepted count
     * @param rejected rejected count
     * @param duplicates duplicate count
     * @param quarantined quarantined count
     */
    public void requireBatchCanComplete(
            TelemetryIngestionBatch batch,
            int received,
            int accepted,
            int rejected,
            int duplicates,
            int quarantined) {

        Objects.requireNonNull(batch, "Telemetry ingestion batch must not be null.");

        if (!TelemetryIngestionBatchStatus.PROCESSING.equals(batch.status())) {
            throw new BusinessRuleViolationException("Only processing telemetry ingestion batches can complete.");
        }

        requireNonNegative(received, "received");
        requireNonNegative(accepted, "accepted");
        requireNonNegative(rejected, "rejected");
        requireNonNegative(duplicates, "duplicates");
        requireNonNegative(quarantined, "quarantined");

        int classified = accepted + rejected + duplicates + quarantined;

        if (classified > received) {
            throw new BusinessRuleViolationException("Telemetry ingestion classified count must not exceed received count.");
        }
    }

    /**
     * Ensures a batch can fail with a reason.
     *
     * @param batch ingestion batch
     * @param reason failure reason
     */
    public void requireBatchCanFail(TelemetryIngestionBatch batch, String reason) {
        Objects.requireNonNull(batch, "Telemetry ingestion batch must not be null.");

        if (TelemetryIngestionBatchStatus.COMPLETED.equals(batch.status())
                || TelemetryIngestionBatchStatus.COMPLETED_WITH_ERRORS.equals(batch.status())
                || TelemetryIngestionBatchStatus.FAILED.equals(batch.status())) {
            throw new BusinessRuleViolationException("Terminal telemetry ingestion batch cannot fail again.");
        }

        if (reason == null || reason.isBlank()) {
            throw new BusinessRuleViolationException("Failing a telemetry ingestion batch requires a reason.");
        }
    }

    private static void requireNonNegative(int value, String fieldName) {
        if (value < 0) {
            throw new BusinessRuleViolationException("Telemetry ingestion " + fieldName + " count must not be negative.");
        }
    }
}
