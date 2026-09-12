/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalPlanRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.application.port.out
 *
 * @Description : Repository port for OperationalPlan.
 *
 */
package dz.sh.hidra.modules.planning.application.port.out;

import dz.sh.hidra.modules.planning.domain.model.OperationalPlan;
import java.time.Instant;
import java.util.Optional;

/**
 * Repository port for OperationalPlan.
 */
public interface OperationalPlanRepositoryPort {

    OperationalPlan save(OperationalPlan model);

    Optional<OperationalPlan> findById(String id);

    boolean updateMetadataIfUpdatedAtMatches(
            String id,
            Instant expectedUpdatedAt,
            String nameAr,
            String nameFr,
            String nameEn,
            String responsibleOrganizationUnitId,
            Instant newUpdatedAt
    );
}
