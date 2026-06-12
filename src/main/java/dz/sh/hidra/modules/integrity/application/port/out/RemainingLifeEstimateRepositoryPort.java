/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RemainingLifeEstimateRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.application.port.out
 *
 * @Description : Repository port for RemainingLifeEstimate.
 *
 */
package dz.sh.hidra.modules.integrity.application.port.out;

import dz.sh.hidra.modules.integrity.domain.model.RemainingLifeEstimate;

import java.util.Optional;

/**
 * Repository port for RemainingLifeEstimate.
 */
public interface RemainingLifeEstimateRepositoryPort {

    RemainingLifeEstimate save(RemainingLifeEstimate model);

    Optional<RemainingLifeEstimate> findById(String id);
}
