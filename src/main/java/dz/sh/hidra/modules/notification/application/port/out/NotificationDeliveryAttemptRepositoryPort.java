/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationDeliveryAttemptRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.application.port.out
 *
 * @Description : Repository port for NotificationDeliveryAttempt.
 *
 */
package dz.sh.hidra.modules.notification.application.port.out;

import dz.sh.hidra.modules.notification.domain.model.NotificationDeliveryAttempt;

import java.util.Optional;

/**
 * Repository port for NotificationDeliveryAttempt.
 */
public interface NotificationDeliveryAttemptRepositoryPort {

    NotificationDeliveryAttempt save(NotificationDeliveryAttempt model);

    Optional<NotificationDeliveryAttempt> findById(String id);
}
