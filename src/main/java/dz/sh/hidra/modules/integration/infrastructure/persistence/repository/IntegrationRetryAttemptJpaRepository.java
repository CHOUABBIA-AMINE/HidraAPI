/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationRetryAttemptJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for IntegrationRetryAttempt.
 *
 */
package dz.sh.hidra.modules.integration.infrastructure.persistence.repository;

import dz.sh.hidra.modules.integration.infrastructure.persistence.entity.IntegrationRetryAttemptJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for IntegrationRetryAttempt.
 */
@Repository
public interface IntegrationRetryAttemptJpaRepository extends JpaRepository<IntegrationRetryAttemptJpaEntity, String> {
}
