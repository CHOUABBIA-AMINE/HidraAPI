/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseCaseStatusHistoryRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.application.port.out
 *
 * @Description : Repository port for HseCaseStatusHistory.
 *
 */
package dz.sh.hidra.modules.hse.application.port.out;

import dz.sh.hidra.modules.hse.domain.model.HseCaseStatusHistory;

import java.util.Optional;

/**
 * Repository port for HseCaseStatusHistory.
 */
public interface HseCaseStatusHistoryRepositoryPort {

    HseCaseStatusHistory save(HseCaseStatusHistory model);

    Optional<HseCaseStatusHistory> findById(String id);
}
