/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskAggregationSnapshotRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.application.port.out
 *
 * @Description : Repository port for RiskAggregationSnapshot.
 *
 */
package dz.sh.hidra.modules.risk.application.port.out;

import dz.sh.hidra.modules.risk.domain.model.RiskAggregationSnapshot;

import java.util.Optional;

/**
 * Repository port for RiskAggregationSnapshot.
 */
public interface RiskAggregationSnapshotRepositoryPort {

    RiskAggregationSnapshot save(RiskAggregationSnapshot model);

    Optional<RiskAggregationSnapshot> findById(String id);
}
