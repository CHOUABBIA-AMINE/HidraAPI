/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskScenarioRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.application.port.out
 *
 * @Description : Repository port for RiskScenario.
 *
 */
package dz.sh.hidra.modules.risk.application.port.out;

import dz.sh.hidra.modules.risk.domain.model.RiskScenario;

import java.util.Optional;

/**
 * Repository port for RiskScenario.
 */
public interface RiskScenarioRepositoryPort {

    RiskScenario save(RiskScenario model);

    Optional<RiskScenario> findById(String id);
}
