/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationRetryPolicyRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.application.port.out
 *
 * @Description : Repository port for NotificationRetryPolicy.
 *
 */
package dz.sh.hidra.modules.notification.application.port.out;

import dz.sh.hidra.modules.notification.domain.model.NotificationRetryPolicy;

import java.util.Optional;

/**
 * Repository port for NotificationRetryPolicy.
 */
public interface NotificationRetryPolicyRepositoryPort {

    NotificationRetryPolicy save(NotificationRetryPolicy model);

    Optional<NotificationRetryPolicy> findById(String id);
}
