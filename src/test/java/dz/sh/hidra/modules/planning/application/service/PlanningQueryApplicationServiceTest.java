/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningQueryApplicationServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Class
 * @Layer       : Application Test
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.application.service
 *
 * @Description : Verifies planning query validation and missing-resource behavior.
 *
 */
package dz.sh.hidra.modules.planning.application.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import dz.sh.hidra.modules.planning.application.port.out.PlanningQueryPort;
import dz.sh.hidra.modules.planning.domain.model.Nomination;
import dz.sh.hidra.modules.planning.domain.model.OperationalPlan;
import dz.sh.hidra.modules.planning.domain.model.PlanRevision;
import dz.sh.hidra.modules.planning.domain.model.PlanTarget;
import dz.sh.hidra.modules.planning.domain.model.PlanningPeriod;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PlanningQueryApplicationServiceTest {

    private final PlanningQueryApplicationService service = new PlanningQueryApplicationService(
            new EmptyPlanningQueryPort(), org.mockito.Mockito.mock(PlanningAccessService.class));

    @Test
    void rejectsInvalidPaging() {
        assertThrows(IllegalArgumentException.class, () -> service.periods(-1, 50));
        assertThrows(IllegalArgumentException.class, () -> service.periods(0, 0));
        assertThrows(IllegalArgumentException.class, () -> service.periods(0, 201));
    }

    @Test
    void rejectsBlankRelationshipIdentifiers() {
        assertThrows(IllegalArgumentException.class, () -> service.revisions(" ", 0, 50));
        assertThrows(IllegalArgumentException.class, () -> service.nominations(null, 0, 50));
        assertThrows(IllegalArgumentException.class, () -> service.targets("", 0, 50));
    }

    @Test
    void reportsUnknownDetailsAsMissingResources() {
        assertThrows(NoSuchElementException.class, () -> service.period("missing"));
        assertThrows(NoSuchElementException.class, () -> service.operationalPlan("missing"));
        assertThrows(NoSuchElementException.class, () -> service.revision("missing"));
        assertThrows(NoSuchElementException.class, () -> service.nomination("missing"));
        assertThrows(NoSuchElementException.class, () -> service.target("missing"));
    }

    private static final class EmptyPlanningQueryPort implements PlanningQueryPort {

        @Override
        public Slice<PlanningPeriod> findPeriods(Set<String> companies, int page, int size) {
            return new Slice<>(List.of(), 0);
        }

        @Override
        public Optional<PlanningPeriod> findPeriodById(Set<String> companies, String id) {
            return Optional.empty();
        }

        @Override
        public Slice<OperationalPlan> findOperationalPlans(Set<AssetScope> assets, int page, int size) {
            return new Slice<>(List.of(), 0);
        }

        @Override
        public Optional<OperationalPlan> findOperationalPlanById(Set<AssetScope> assets, String id) {
            return Optional.empty();
        }

        @Override
        public Slice<PlanRevision> findRevisionsByPlanId(Set<AssetScope> assets, String planId, int page, int size) {
            return new Slice<>(List.of(), 0);
        }

        @Override
        public Optional<PlanRevision> findRevisionById(Set<AssetScope> assets, String id) {
            return Optional.empty();
        }

        @Override
        public Slice<Nomination> findNominationsByRevisionId(Set<AssetScope> assets, String revisionId, int page, int size) {
            return new Slice<>(List.of(), 0);
        }

        @Override
        public Optional<Nomination> findNominationById(Set<AssetScope> assets, String id) {
            return Optional.empty();
        }

        @Override
        public Slice<PlanTarget> findTargetsByRevisionId(Set<AssetScope> assets, String revisionId, int page, int size) {
            return new Slice<>(List.of(), 0);
        }

        @Override
        public Optional<PlanTarget> findTargetById(Set<AssetScope> assets, String id) {
            return Optional.empty();
        }
    }
}
