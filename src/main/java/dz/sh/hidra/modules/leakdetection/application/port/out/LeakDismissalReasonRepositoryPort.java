/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakDismissalReasonRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.application.port.out
 *
 * @Description : Repository port for LeakDismissalReason.
 *
 */
package dz.sh.hidra.modules.leakdetection.application.port.out;

import dz.sh.hidra.modules.leakdetection.domain.model.LeakDismissalReason;

import java.util.Optional;

/**
 * Repository port for LeakDismissalReason.
 */
public interface LeakDismissalReasonRepositoryPort {

    LeakDismissalReason save(LeakDismissalReason model);

    Optional<LeakDismissalReason> findById(String id);
}
