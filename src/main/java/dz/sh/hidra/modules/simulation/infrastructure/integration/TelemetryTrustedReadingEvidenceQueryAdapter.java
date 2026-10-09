/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryTrustedReadingEvidenceQueryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-09
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.integration
 *
 * @Description : Copies owner evidence into the Simulation application projection.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.integration;

import dz.sh.hidra.modules.simulation.application.port.out.SimulationTrustedReadingEvidencePort;
import dz.sh.hidra.modules.telemetry.application.contract.simulation.SimulationTrustedReadingContract;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public final class TelemetryTrustedReadingEvidenceQueryAdapter implements SimulationTrustedReadingEvidencePort {

    private final SimulationTrustedReadingContract readings;

    public TelemetryTrustedReadingEvidenceQueryAdapter(SimulationTrustedReadingContract readings) {
        this.readings = Objects.requireNonNull(readings, "Telemetry evidence contract must not be null.");
    }

    @Override
    public Optional<ReadingEvidence> resolve(String id) {
        if (id == null || id.isBlank()) return Optional.empty();
        String key = id.trim();
        return readings.resolve(key).filter(reading -> key.equals(reading.id()))
                .map(reading -> new ReadingEvidence(
                        reading.id(), reading.readingId(), reading.pointId(), reading.numericValue(),
                        reading.textValue(), reading.booleanValue(), reading.unitId(), reading.qualityCodeId(),
                        reading.trustLevel(), reading.sourceTimestamp(), reading.trustedAt(),
                        reading.qualityAssessmentId(), reading.topologyAssetTypeCode(), reading.topologyAssetId(),
                        reading.topologyAssetCode(), reading.topologySnapshotId(), reading.ingestionBatchId()
                ));
    }
}
