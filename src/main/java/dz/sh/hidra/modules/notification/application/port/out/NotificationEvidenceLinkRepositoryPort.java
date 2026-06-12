/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationEvidenceLinkRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.application.port.out
 *
 * @Description : Repository port for NotificationEvidenceLink.
 *
 */
package dz.sh.hidra.modules.notification.application.port.out;

import dz.sh.hidra.modules.notification.domain.model.NotificationEvidenceLink;

import java.util.Optional;

/**
 * Repository port for NotificationEvidenceLink.
 */
public interface NotificationEvidenceLinkRepositoryPort {

    NotificationEvidenceLink save(NotificationEvidenceLink model);

    Optional<NotificationEvidenceLink> findById(String id);
}
