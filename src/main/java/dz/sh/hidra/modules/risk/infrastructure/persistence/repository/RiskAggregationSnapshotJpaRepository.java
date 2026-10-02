/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskAggregationSnapshotJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for RiskAggregationSnapshot.
 *
 */
package dz.sh.hidra.modules.risk.infrastructure.persistence.repository;

import dz.sh.hidra.modules.risk.infrastructure.persistence.entity.RiskAggregationSnapshotJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for RiskAggregationSnapshot.
 */
@Repository
public interface RiskAggregationSnapshotJpaRepository extends JpaRepository<RiskAggregationSnapshotJpaEntity, String> {
}
