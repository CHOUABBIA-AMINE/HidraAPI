/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportParameterDefinitionJpaRepository
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Repository
 * @Layer       : Infrastructure
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for ReportParameterDefinition.
 *
 */
package dz.sh.hidra.modules.reporting.infrastructure.persistence.repository;

import dz.sh.hidra.modules.reporting.infrastructure.persistence.entity.ReportParameterDefinitionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for ReportParameterDefinition.
 */
@Repository
public interface ReportParameterDefinitionJpaRepository extends JpaRepository<ReportParameterDefinitionJpaEntity, String> {
}
