/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskTreatmentPlanRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.application.port.out
 *
 * @Description : Repository port for RiskTreatmentPlan.
 *
 */
package dz.sh.hidra.modules.risk.application.port.out;

import dz.sh.hidra.modules.risk.domain.model.RiskTreatmentPlan;

import java.util.Optional;

/**
 * Repository port for RiskTreatmentPlan.
 */
public interface RiskTreatmentPlanRepositoryPort {

    RiskTreatmentPlan save(RiskTreatmentPlan model);

    Optional<RiskTreatmentPlan> findById(String id);
}
