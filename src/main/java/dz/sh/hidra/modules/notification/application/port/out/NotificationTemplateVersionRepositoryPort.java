/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationTemplateVersionRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.application.port.out
 *
 * @Description : Repository port for NotificationTemplateVersion.
 *
 */
package dz.sh.hidra.modules.notification.application.port.out;

import dz.sh.hidra.modules.notification.domain.model.NotificationTemplateVersion;

import java.util.Optional;

/**
 * Repository port for NotificationTemplateVersion.
 */
public interface NotificationTemplateVersionRepositoryPort {

    NotificationTemplateVersion save(NotificationTemplateVersion model);

    Optional<NotificationTemplateVersion> findById(String id);
}
