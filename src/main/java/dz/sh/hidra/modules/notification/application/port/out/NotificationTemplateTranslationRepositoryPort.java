/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationTemplateTranslationRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.application.port.out
 *
 * @Description : Repository port for NotificationTemplateTranslation.
 *
 */
package dz.sh.hidra.modules.notification.application.port.out;

import dz.sh.hidra.modules.notification.domain.model.NotificationTemplateTranslation;

import java.util.Optional;

/**
 * Repository port for NotificationTemplateTranslation.
 */
public interface NotificationTemplateTranslationRepositoryPort {

    NotificationTemplateTranslation save(NotificationTemplateTranslation model);

    Optional<NotificationTemplateTranslation> findById(String id);
}
