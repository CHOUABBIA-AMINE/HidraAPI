/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskMitigationMeasureRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.application.port.out
 *
 * @Description : Repository port for RiskMitigationMeasure.
 *
 */
package dz.sh.hidra.modules.risk.application.port.out;

import dz.sh.hidra.modules.risk.domain.model.RiskMitigationMeasure;

import java.util.Optional;

/**
 * Repository port for RiskMitigationMeasure.
 */
public interface RiskMitigationMeasureRepositoryPort {

    RiskMitigationMeasure save(RiskMitigationMeasure model);

    Optional<RiskMitigationMeasure> findById(String id);
}
