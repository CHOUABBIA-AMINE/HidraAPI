/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationRequestJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for NotificationRequest.
 *
 */
package dz.sh.hidra.modules.notification.infrastructure.persistence.repository;

import dz.sh.hidra.modules.notification.infrastructure.persistence.entity.NotificationRequestJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for NotificationRequest.
 */
@Repository
public interface NotificationRequestJpaRepository extends JpaRepository<NotificationRequestJpaEntity, String> {

    @Query(value = """
            SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END
            FROM hidra_notification_catalog_entry
            WHERE id = :entryId
              AND catalog_name = :catalogName
            """, nativeQuery = true)
    boolean isCatalogEntryInFamily(
            @Param("entryId") String entryId,
            @Param("catalogName") String catalogName
    );

    @Query(value = """
            SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END
            FROM hidra_notification_policy
            WHERE id = :policyId
            """, nativeQuery = true)
    boolean policyExists(@Param("policyId") String policyId);

    @Query(value = """
            SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END
            FROM hidra_notification_template
            WHERE id = :templateId
            """, nativeQuery = true)
    boolean templateExists(@Param("templateId") String templateId);

    @Query(value = """
            SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END
            FROM hidra_notification_template_version
            WHERE id = :templateVersionId
            """, nativeQuery = true)
    boolean templateVersionExists(@Param("templateVersionId") String templateVersionId);
}
