/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryIngestionDomainService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.service
 *
 * @Description : Domain service for telemetry ingestion batch lifecycle workflows.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.service;

import java.util.Objects;

import dz.sh.hidra.modules.telemetry.domain.model.TelemetryIngestionBatch;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetrySource;
import dz.sh.hidra.modules.telemetry.domain.policy.TelemetryIngestionPolicy;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryCorrelationId;

/**
 * Domain service for telemetry ingestion batch lifecycle workflows.
 *
 * <p>Business role:
 * Starts, processes, completes, and fails telemetry ingestion batches while checking batch lifecycle
 * rules.
 *
 * <p>Architecture role:
 * Pure domain service. It tracks ingestion lifecycle but does not store raw build logs, unresolved
 * stack traces, protocol clients, or analytics output.
 */
public final class TelemetryIngestionDomainService {

    private final TelemetryIngestionPolicy ingestionPolicy;

    public TelemetryIngestionDomainService(TelemetryIngestionPolicy ingestionPolicy) {
        this.ingestionPolicy = Objects.requireNonNull(ingestionPolicy, "TelemetryIngestionPolicy must not be null.");
    }

    public TelemetryIngestionBatch startBatch(TelemetrySource source, TelemetryCorrelationId correlationId) {
        ingestionPolicy.requireSourceCanIngest(source);
        return TelemetryIngestionBatch.start(source.id(), correlationId);
    }

    public TelemetryIngestionBatch markProcessing(TelemetryIngestionBatch batch) {
        ingestionPolicy.requireBatchCanStartProcessing(batch);
        return batch.markProcessing();
    }

    public TelemetryIngestionBatch completeBatch(
            TelemetryIngestionBatch batch,
            int received,
            int accepted,
            int rejected,
            int duplicates,
            int quarantined) {

        ingestionPolicy.requireBatchCanComplete(batch, received, accepted, rejected, duplicates, quarantined);

        return batch.complete(received, accepted, rejected, duplicates, quarantined);
    }

    public TelemetryIngestionBatch failBatch(TelemetryIngestionBatch batch, String reason) {
        ingestionPolicy.requireBatchCanFail(batch, reason);
        return batch.fail(reason);
    }
}
