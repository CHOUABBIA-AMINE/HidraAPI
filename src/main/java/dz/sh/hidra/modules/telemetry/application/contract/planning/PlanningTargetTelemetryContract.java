/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningTargetTelemetryContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-10
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.contract.planning
 *
 * @Description : Checks current units and exact point binding for Planning draft targets.
 *
 */
package dz.sh.hidra.modules.telemetry.application.contract.planning;

import java.time.Instant;

public interface PlanningTargetTelemetryContract {

    void requireCompatible(String pointId, String assetType, String assetId,
            String unitId, boolean numeric, Instant from, Instant to);
}
