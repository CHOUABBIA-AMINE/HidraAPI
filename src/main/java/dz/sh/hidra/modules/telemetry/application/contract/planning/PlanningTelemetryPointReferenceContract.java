/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningTelemetryPointReferenceContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.contract.planning
 *
 * @Description : Exports Telemetry-owned point identity and code to Planning.
 *
 */
package dz.sh.hidra.modules.telemetry.application.contract.planning;

import java.util.Optional;

public interface PlanningTelemetryPointReferenceContract {
    Optional<Point> resolve(String id);
    record Point(String id, String code) { }
}
