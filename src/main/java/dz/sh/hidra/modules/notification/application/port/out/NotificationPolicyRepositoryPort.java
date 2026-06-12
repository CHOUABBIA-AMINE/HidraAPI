/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationPolicyRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.application.port.out
 *
 * @Description : Repository port for NotificationPolicy.
 *
 */
package dz.sh.hidra.modules.notification.application.port.out;

import dz.sh.hidra.modules.notification.domain.model.NotificationPolicy;

import java.util.Optional;

/**
 * Repository port for NotificationPolicy.
 */
public interface NotificationPolicyRepositoryPort {

    NotificationPolicy save(NotificationPolicy model);

    Optional<NotificationPolicy> findById(String id);
}
