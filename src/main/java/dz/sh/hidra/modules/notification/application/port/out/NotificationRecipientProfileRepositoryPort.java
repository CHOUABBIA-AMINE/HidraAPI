/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationRecipientProfileRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.application.port.out
 *
 * @Description : Repository port for NotificationRecipientProfile.
 *
 */
package dz.sh.hidra.modules.notification.application.port.out;

import dz.sh.hidra.modules.notification.domain.model.NotificationRecipientProfile;

import java.util.Optional;

/**
 * Repository port for NotificationRecipientProfile.
 */
public interface NotificationRecipientProfileRepositoryPort {

    NotificationRecipientProfile save(NotificationRecipientProfile model);

    Optional<NotificationRecipientProfile> findById(String id);
}
