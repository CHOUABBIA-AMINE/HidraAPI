/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskLikelihoodJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for RiskLikelihood.
 *
 */
package dz.sh.hidra.modules.risk.infrastructure.persistence.repository;

import dz.sh.hidra.modules.risk.infrastructure.persistence.entity.RiskLikelihoodJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for RiskLikelihood.
 */
@Repository
public interface RiskLikelihoodJpaRepository extends JpaRepository<RiskLikelihoodJpaEntity, String> {
}
