/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanActualReviewSnapshotRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.application.port.out
 *
 * @Description : Repository port for PlanActualReviewSnapshot.
 *
 */
package dz.sh.hidra.modules.planning.application.port.out;

import dz.sh.hidra.modules.planning.domain.model.PlanActualReviewSnapshot;

import java.util.Optional;

/**
 * Repository port for PlanActualReviewSnapshot.
 */
public interface PlanActualReviewSnapshotRepositoryPort {

    PlanActualReviewSnapshot save(PlanActualReviewSnapshot model);

    Optional<PlanActualReviewSnapshot> findById(String id);
}
