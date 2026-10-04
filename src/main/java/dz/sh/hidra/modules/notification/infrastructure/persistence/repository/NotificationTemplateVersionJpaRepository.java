/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationTemplateVersionJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for NotificationTemplateVersion.
 *
 */
package dz.sh.hidra.modules.notification.infrastructure.persistence.repository;

import dz.sh.hidra.modules.notification.domain.value.NotificationTemplateVersionStatus;
import dz.sh.hidra.modules.notification.infrastructure.persistence.entity.NotificationTemplateVersionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for NotificationTemplateVersion.
 */
@Repository
public interface NotificationTemplateVersionJpaRepository extends JpaRepository<NotificationTemplateVersionJpaEntity, String> {

    boolean existsByTemplateIdAndVersionNumber(String templateId, int versionNumber);

    boolean existsByTemplateIdAndVersionNumberAndStatus(
            String templateId,
            int versionNumber,
            NotificationTemplateVersionStatus status
    );

    boolean existsByIdAndTemplateIdAndStatus(
            String id,
            String templateId,
            NotificationTemplateVersionStatus status
    );
}
