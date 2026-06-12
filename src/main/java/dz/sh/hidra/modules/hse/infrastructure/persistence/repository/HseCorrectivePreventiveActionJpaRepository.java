/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseCorrectivePreventiveActionJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for HseCorrectivePreventiveAction.
 *
 */
package dz.sh.hidra.modules.hse.infrastructure.persistence.repository;

import dz.sh.hidra.modules.hse.infrastructure.persistence.entity.HseCorrectivePreventiveActionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for HseCorrectivePreventiveAction.
 */
@Repository
public interface HseCorrectivePreventiveActionJpaRepository extends JpaRepository<HseCorrectivePreventiveActionJpaEntity, String> {
}
