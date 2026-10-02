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
import org.junit.jupiter.api.Test;

class PlanningQueryApplicationServiceTest {

    private final PlanningQueryApplicationService service = new PlanningQueryApplicationService(new EmptyPlanningQueryPort());

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
        public Slice<PlanningPeriod> findPeriods(int page, int size) {
            return new Slice<>(List.of(), 0);
        }

        @Override
        public Optional<PlanningPeriod> findPeriodById(String id) {
            return Optional.empty();
        }

        @Override
        public Slice<OperationalPlan> findOperationalPlans(int page, int size) {
            return new Slice<>(List.of(), 0);
        }

        @Override
        public Optional<OperationalPlan> findOperationalPlanById(String id) {
            return Optional.empty();
        }

        @Override
        public Slice<PlanRevision> findRevisionsByPlanId(String planId, int page, int size) {
            return new Slice<>(List.of(), 0);
        }

        @Override
        public Optional<PlanRevision> findRevisionById(String id) {
            return Optional.empty();
        }

        @Override
        public Slice<Nomination> findNominationsByRevisionId(String revisionId, int page, int size) {
            return new Slice<>(List.of(), 0);
        }

        @Override
        public Optional<Nomination> findNominationById(String id) {
            return Optional.empty();
        }

        @Override
        public Slice<PlanTarget> findTargetsByRevisionId(String revisionId, int page, int size) {
            return new Slice<>(List.of(), 0);
        }

        @Override
        public Optional<PlanTarget> findTargetById(String id) {
            return Optional.empty();
        }
    }
}
