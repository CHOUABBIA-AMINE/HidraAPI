/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationTrustedReadingEvidencePort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-09
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.application.port.out
 *
 * @Description : Exports raw trusted-reading evidence without physical qualification.
 *
 */
package dz.sh.hidra.modules.simulation.application.port.out;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

/** Raw owner evidence; presence does not establish suitability for physical simulation. */
public interface SimulationTrustedReadingEvidencePort {

    Optional<ReadingEvidence> resolve(String id);

    /** Immutable transport projection; nullable values and provenance remain explicit. */
    record ReadingEvidence(
            String id,
            String readingId,
            String pointId,
            BigDecimal numericValue,
            String textValue,
            Boolean booleanValue,
            String unitId,
            String qualityCodeId,
            String trustLevel,
            Instant sourceTimestamp,
            Instant trustedAt,
            String qualityAssessmentId,
            String topologyAssetTypeCode,
            String topologyAssetId,
            String topologyAssetCode,
            String topologySnapshotId,
            String ingestionBatchId
    ) { }
}
