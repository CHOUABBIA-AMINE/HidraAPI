/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationCatalogEntryRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.application.port.out
 *
 * @Description : Repository port for NotificationCatalogEntry.
 *
 */
package dz.sh.hidra.modules.notification.application.port.out;

import dz.sh.hidra.modules.notification.domain.model.NotificationCatalogEntry;

import java.util.Optional;

/**
 * Repository port for NotificationCatalogEntry.
 */
public interface NotificationCatalogEntryRepositoryPort {

    NotificationCatalogEntry save(NotificationCatalogEntry model);

    Optional<NotificationCatalogEntry> findById(String id);
}
