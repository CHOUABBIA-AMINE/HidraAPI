/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakVerificationActionRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.application.port.out
 *
 * @Description : Repository port for LeakVerificationAction.
 *
 */
package dz.sh.hidra.modules.leakdetection.application.port.out;

import dz.sh.hidra.modules.leakdetection.domain.model.LeakVerificationAction;

import java.util.Optional;

/**
 * Repository port for LeakVerificationAction.
 */
public interface LeakVerificationActionRepositoryPort {

    LeakVerificationAction save(LeakVerificationAction model);

    Optional<LeakVerificationAction> findById(String id);
}
