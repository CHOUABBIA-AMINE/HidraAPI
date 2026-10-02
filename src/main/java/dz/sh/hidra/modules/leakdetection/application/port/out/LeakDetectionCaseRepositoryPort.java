/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakDetectionCaseRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.application.port.out
 *
 * @Description : Repository port for LeakDetectionCase.
 *
 */
package dz.sh.hidra.modules.leakdetection.application.port.out;

import dz.sh.hidra.modules.leakdetection.domain.model.LeakDetectionCase;
import java.util.List;
import java.util.Optional;

/**
 * Repository port for LeakDetectionCase.
 */
public interface LeakDetectionCaseRepositoryPort {

    LeakDetectionCase save(LeakDetectionCase model);

    Optional<LeakDetectionCase> findById(String id);

    List<LeakDetectionCase> findAll(int page, int size);

    long count();
}
