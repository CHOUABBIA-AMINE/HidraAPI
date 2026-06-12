/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportDefinitionJpaRepository
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Repository
 * @Layer       : Infrastructure
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for ReportDefinition.
 *
 */
package dz.sh.hidra.modules.reporting.infrastructure.persistence.repository;

import dz.sh.hidra.modules.reporting.infrastructure.persistence.entity.ReportDefinitionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for ReportDefinition.
 */
@Repository
public interface ReportDefinitionJpaRepository extends JpaRepository<ReportDefinitionJpaEntity, String> {
}
