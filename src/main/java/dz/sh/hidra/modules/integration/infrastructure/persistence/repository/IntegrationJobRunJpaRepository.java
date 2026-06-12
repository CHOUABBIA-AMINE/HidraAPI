/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationJobRunJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for IntegrationJobRun.
 *
 */
package dz.sh.hidra.modules.integration.infrastructure.persistence.repository;

import dz.sh.hidra.modules.integration.infrastructure.persistence.entity.IntegrationJobRunJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for IntegrationJobRun.
 */
@Repository
public interface IntegrationJobRunJpaRepository extends JpaRepository<IntegrationJobRunJpaEntity, String> {
}
