/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationScheduleJpaRepository
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Repository
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for NotificationSchedule.
 *
 */
package dz.sh.hidra.modules.notification.infrastructure.persistence.repository;

import dz.sh.hidra.modules.notification.infrastructure.persistence.entity.NotificationScheduleJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for NotificationSchedule.
 */
@Repository
public interface NotificationScheduleJpaRepository extends JpaRepository<NotificationScheduleJpaEntity, String> {
}
