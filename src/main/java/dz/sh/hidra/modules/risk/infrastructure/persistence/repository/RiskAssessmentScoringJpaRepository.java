/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskAssessmentScoringJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.persistence.repository
 *
 * @Description : Enforces governed Risk owner boundaries.
 *
 */
package dz.sh.hidra.modules.risk.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import dz.sh.hidra.modules.risk.infrastructure.persistence.entity.RiskAssessmentScoringJpaEntity;
public interface RiskAssessmentScoringJpaRepository extends JpaRepository<RiskAssessmentScoringJpaEntity,String> {}
