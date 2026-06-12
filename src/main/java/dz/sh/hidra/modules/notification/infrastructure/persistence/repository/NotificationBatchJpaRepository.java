/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationBatchJpaRepository
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Repository
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for NotificationBatch.
 *
 */
package dz.sh.hidra.modules.notification.infrastructure.persistence.repository;

import dz.sh.hidra.modules.notification.infrastructure.persistence.entity.NotificationBatchJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for NotificationBatch.
 */
@Repository
public interface NotificationBatchJpaRepository extends JpaRepository<NotificationBatchJpaEntity, String> {
}
