/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningQueryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.application.port.out
 *
 * @Description : Read-only persistence port for planning query projections.
 *
 */
package dz.sh.hidra.modules.planning.application.port.out;

import dz.sh.hidra.modules.planning.domain.model.Nomination;
import dz.sh.hidra.modules.planning.domain.model.OperationalPlan;
import dz.sh.hidra.modules.planning.domain.model.PlanRevision;
import dz.sh.hidra.modules.planning.domain.model.PlanTarget;
import dz.sh.hidra.modules.planning.domain.model.PlanningPeriod;
import java.util.List;
import java.util.Optional;

public interface PlanningQueryPort {

    Slice<PlanningPeriod> findPeriods(int page, int size);

    Optional<PlanningPeriod> findPeriodById(String id);

    Slice<OperationalPlan> findOperationalPlans(int page, int size);

    Optional<OperationalPlan> findOperationalPlanById(String id);

    Slice<PlanRevision> findRevisionsByPlanId(String planId, int page, int size);

    Optional<PlanRevision> findRevisionById(String id);

    Slice<Nomination> findNominationsByRevisionId(String revisionId, int page, int size);

    Optional<Nomination> findNominationById(String id);

    Slice<PlanTarget> findTargetsByRevisionId(String revisionId, int page, int size);

    Optional<PlanTarget> findTargetById(String id);

    record Slice<T>(List<T> content, long totalElements) { }
}
