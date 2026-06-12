/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationPreferenceRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.application.port.out
 *
 * @Description : Repository port for NotificationPreference.
 *
 */
package dz.sh.hidra.modules.notification.application.port.out;

import dz.sh.hidra.modules.notification.domain.model.NotificationPreference;

import java.util.Optional;

/**
 * Repository port for NotificationPreference.
 */
public interface NotificationPreferenceRepositoryPort {

    NotificationPreference save(NotificationPreference model);

    Optional<NotificationPreference> findById(String id);
}
