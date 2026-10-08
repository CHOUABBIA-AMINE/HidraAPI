/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringPlanTargetReferenceContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.application.contract.monitoring
 *
 * @Description : Exports scalar Planning target context for Monitoring without lifecycle inventions.
 *
 */
package dz.sh.hidra.modules.planning.application.contract.monitoring;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

public interface MonitoringPlanTargetReferenceContract {
    Optional<Target> resolve(String id);
    record Target(String id, String revisionId, String topologyAssetType, String topologyAssetId,
                  String telemetryPointId, String status, BigDecimal targetValue, String targetTextValue,
                  String unitId, Instant validFrom, Instant validTo) { }
}
