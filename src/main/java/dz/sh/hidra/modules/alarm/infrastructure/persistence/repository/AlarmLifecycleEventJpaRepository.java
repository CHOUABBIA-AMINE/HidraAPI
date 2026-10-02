/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmLifecycleEventJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for AlarmLifecycleEvent.
 *
 */
package dz.sh.hidra.modules.alarm.infrastructure.persistence.repository;

import dz.sh.hidra.modules.alarm.infrastructure.persistence.entity.AlarmLifecycleEventJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for AlarmLifecycleEvent.
 */
@Repository
public interface AlarmLifecycleEventJpaRepository extends JpaRepository<AlarmLifecycleEventJpaEntity, String> {
}
