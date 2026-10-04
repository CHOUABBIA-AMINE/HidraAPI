/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationRequestRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.application.port.out
 *
 * @Description : Repository port for NotificationRequest.
 *
 */
package dz.sh.hidra.modules.notification.application.port.out;

import dz.sh.hidra.modules.notification.domain.model.NotificationRequest;

import java.util.Optional;

/**
 * Repository port for NotificationRequest.
 */
public interface NotificationRequestRepositoryPort {

    NotificationRequest save(NotificationRequest model);

    Optional<NotificationRequest> findById(String id);

    boolean isCatalogEntryInFamily(String entryId, String catalogName);

    boolean policyExists(String policyId);

    boolean templateExists(String templateId);

    boolean templateVersionExists(String templateVersionId);
}
