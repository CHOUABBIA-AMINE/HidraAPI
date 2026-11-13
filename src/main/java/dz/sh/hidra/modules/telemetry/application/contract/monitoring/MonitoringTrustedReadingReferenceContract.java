/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringTrustedReadingReferenceContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.contract.monitoring
 *
 * @Description : Exports actual trusted-reading identity and scalar point provenance.
 *
 */
package dz.sh.hidra.modules.telemetry.application.contract.monitoring;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

public interface MonitoringTrustedReadingReferenceContract {
    Optional<Reading> resolve(String id);
    default Optional<EvaluationReading> forEvaluation(String id, String point, String assetType, String assetId) {
        return Optional.empty();
    }

    record EvaluationReading(String id, String rawId, String assessmentId, String bindingId, String unitId,
                             String qualityCodeId, String trustLevel, Instant observedAt, Instant trustedAt,
                             BigDecimal value, boolean qualified) { }

    record Reading(String id, String pointId, String trustLevel) { }
}
