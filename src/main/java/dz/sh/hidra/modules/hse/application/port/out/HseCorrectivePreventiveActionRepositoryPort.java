/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseCorrectivePreventiveActionRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.application.port.out
 *
 * @Description : Repository port for HseCorrectivePreventiveAction.
 *
 */
package dz.sh.hidra.modules.hse.application.port.out;

import dz.sh.hidra.modules.hse.domain.model.HseCorrectivePreventiveAction;

import java.util.Optional;

/**
 * Repository port for HseCorrectivePreventiveAction.
 */
public interface HseCorrectivePreventiveActionRepositoryPort {

    HseCorrectivePreventiveAction save(HseCorrectivePreventiveAction model);

    Optional<HseCorrectivePreventiveAction> findById(String id);
}
