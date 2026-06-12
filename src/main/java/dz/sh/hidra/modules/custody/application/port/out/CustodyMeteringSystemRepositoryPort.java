/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyMeteringSystemRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.application.port.out
 *
 * @Description : Repository port for CustodyMeteringSystem.
 *
 */
package dz.sh.hidra.modules.custody.application.port.out;

import dz.sh.hidra.modules.custody.domain.model.CustodyMeteringSystem;

import java.util.Optional;

/**
 * Repository port for CustodyMeteringSystem.
 */
public interface CustodyMeteringSystemRepositoryPort {

    CustodyMeteringSystem save(CustodyMeteringSystem model);

    Optional<CustodyMeteringSystem> findById(String id);
}
