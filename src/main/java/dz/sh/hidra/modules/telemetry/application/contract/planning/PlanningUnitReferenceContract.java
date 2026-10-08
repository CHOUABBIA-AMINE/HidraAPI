/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningUnitReferenceContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.contract.planning
 *
 * @Description : Enforces owner-controlled Nomination reference integrity.
 *
 */
package dz.sh.hidra.modules.telemetry.application.contract.planning;

import java.util.Optional;
public interface PlanningUnitReferenceContract {
    Optional<Units> resolve(String quantityUnitId, String rateUnitId);
    record Unit(String id, String code, String symbol, String dimension, boolean active) { }
    record Units(Unit quantity, Unit rate, boolean pairActive) { }
}
