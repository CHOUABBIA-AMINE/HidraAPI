/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmCatalogEntryJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for AlarmCatalogEntry.
 *
 */
package dz.sh.hidra.modules.alarm.infrastructure.persistence.repository;

import dz.sh.hidra.modules.alarm.infrastructure.persistence.entity.AlarmCatalogEntryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for AlarmCatalogEntry.
 */
@Repository
public interface AlarmCatalogEntryJpaRepository extends JpaRepository<AlarmCatalogEntryJpaEntity, String> {
}
