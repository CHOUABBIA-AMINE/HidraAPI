/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationContactPointRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.application.port.out
 *
 * @Description : Repository port for NotificationContactPoint.
 *
 */
package dz.sh.hidra.modules.notification.application.port.out;

import dz.sh.hidra.modules.notification.domain.model.NotificationContactPoint;

import java.util.Optional;

/**
 * Repository port for NotificationContactPoint.
 */
public interface NotificationContactPointRepositoryPort {

    NotificationContactPoint save(NotificationContactPoint model);

    Optional<NotificationContactPoint> findById(String id);
}
