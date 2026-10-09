/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationTrustedReadingQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-09
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.service
 *
 * @Description : Resolves actual Telemetry evidence through the owner repository.
 *
 */
package dz.sh.hidra.modules.telemetry.application.service;

import dz.sh.hidra.modules.telemetry.application.contract.simulation.SimulationTrustedReadingContract;
import dz.sh.hidra.modules.telemetry.application.port.out.TrustedTelemetryReadingRepositoryPort;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public final class SimulationTrustedReadingQueryService implements SimulationTrustedReadingContract {

    private final TrustedTelemetryReadingRepositoryPort readings;

    public SimulationTrustedReadingQueryService(TrustedTelemetryReadingRepositoryPort readings) {
        this.readings = Objects.requireNonNull(readings, "Trusted reading repository must not be null.");
    }

    @Override
    public Optional<ReadingEvidence> resolve(String id) {
        if (id == null || id.isBlank()) return Optional.empty();
        String key = id.trim();
        return readings.findById(key).filter(reading -> key.equals(reading.id()))
                .map(reading -> new ReadingEvidence(
                        reading.id(), reading.readingId(), reading.pointId(), reading.numericValue(),
                        reading.textValue(), reading.booleanValue(), reading.unitId(), reading.qualityCodeId(),
                        reading.trustLevel().name(), reading.sourceTimestamp(), reading.trustedAt(),
                        reading.qualityAssessmentId(), reading.topologyAssetTypeCode(), reading.topologyAssetId(),
                        reading.topologyAssetCode(), reading.topologySnapshotId(), reading.ingestionBatchId()
                ));
    }
}
