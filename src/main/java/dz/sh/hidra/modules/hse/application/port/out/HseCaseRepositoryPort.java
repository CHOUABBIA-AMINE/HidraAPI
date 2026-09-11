/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseCaseRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.application.port.out
 *
 * @Description : Repository port for HseCase.
 *
 */
package dz.sh.hidra.modules.hse.application.port.out;

import dz.sh.hidra.modules.hse.domain.model.HseCase;
import java.util.List;
import java.util.Optional;

public interface HseCaseRepositoryPort {

    HseCase save(HseCase model);

    Optional<HseCase> findById(String id);

    List<HseCase> findAll(int page, int size);

    long count();
}
