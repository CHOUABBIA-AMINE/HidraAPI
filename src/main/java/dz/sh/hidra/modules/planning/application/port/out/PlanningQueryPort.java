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
import java.util.Set;

public interface PlanningQueryPort {

    Slice<PlanningPeriod> findPeriods(Set<String> companies, int page, int size);

    Optional<PlanningPeriod> findPeriodById(Set<String> companies, String id);

    Slice<OperationalPlan> findOperationalPlans(Set<AssetScope> assets, int page, int size);

    Optional<OperationalPlan> findOperationalPlanById(Set<AssetScope> assets, String id);

    Slice<PlanRevision> findRevisionsByPlanId(Set<AssetScope> assets, String planId, int page, int size);

    Optional<PlanRevision> findRevisionById(Set<AssetScope> assets, String id);

    Slice<Nomination> findNominationsByRevisionId(Set<AssetScope> assets, String revisionId, int page, int size);

    Optional<Nomination> findNominationById(Set<AssetScope> assets, String id);

    Slice<PlanTarget> findTargetsByRevisionId(Set<AssetScope> assets, String revisionId, int page, int size);

    Optional<PlanTarget> findTargetById(Set<AssetScope> assets, String id);

    record AssetScope(String companyPartyId, String type, String assetId) { }

    record Slice<T>(List<T> content, long totalElements) { }
}
