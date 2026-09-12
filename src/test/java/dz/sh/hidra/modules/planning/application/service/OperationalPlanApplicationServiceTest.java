/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalPlanApplicationServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Class
 * @Layer       : Application Test
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.application.service
 *
 * @Description : Verifies operational-plan optimistic concurrency and atomic compare-and-set behavior.
 *
 */
package dz.sh.hidra.modules.planning.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import dz.sh.hidra.modules.planning.application.command.UpdateOperationalPlanCommand;
import dz.sh.hidra.modules.planning.application.port.out.OperationalPlanRepositoryPort;
import dz.sh.hidra.modules.planning.domain.model.OperationalPlan;
import dz.sh.hidra.modules.planning.domain.value.OperationalPlanStatus;
import java.time.Instant;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class OperationalPlanApplicationServiceTest {

    private static final Instant CURRENT_TOKEN = Instant.parse("2026-09-12T08:00:00Z");

    @Test
    void updatesMetadataWithCurrentTokenAndReturnsRefreshedToken() {
        InMemoryOperationalPlanRepository repository = new InMemoryOperationalPlanRepository(plan());
        OperationalPlanApplicationService service = new OperationalPlanApplicationService(repository);

        var result = service.updateOperationalPlan(command(CURRENT_TOKEN));

        assertThat(result.id()).isEqualTo("PLAN-1");
        assertThat(result.nameFr()).isEqualTo("Plan Nord actualisé");
        assertThat(result.updatedAt()).isAfter(CURRENT_TOKEN);
        assertThat(repository.value.nameFr()).isEqualTo("Plan Nord actualisé");
        assertThat(repository.value.updatedAt()).isEqualTo(result.updatedAt());
    }

    @Test
    void rejectsStaleTokenWithoutWriting() {
        InMemoryOperationalPlanRepository repository = new InMemoryOperationalPlanRepository(plan());
        OperationalPlanApplicationService service = new OperationalPlanApplicationService(repository);

        assertThatThrownBy(() -> service.updateOperationalPlan(command(CURRENT_TOKEN.minusSeconds(1))))
                .isInstanceOf(ConcurrentModificationException.class)
                .hasMessageContaining("refetch before retrying");
        assertThat(repository.updateAttempts).isZero();
        assertThat(repository.value.nameFr()).isEqualTo("Plan Nord");
    }

    @Test
    void rejectsLostRaceWhenAtomicCompareAndSetDoesNotMatch() {
        InMemoryOperationalPlanRepository repository = new InMemoryOperationalPlanRepository(plan());
        repository.forceCompareAndSetMiss = true;
        OperationalPlanApplicationService service = new OperationalPlanApplicationService(repository);

        assertThatThrownBy(() -> service.updateOperationalPlan(command(CURRENT_TOKEN)))
                .isInstanceOf(ConcurrentModificationException.class);
        assertThat(repository.value.nameFr()).isEqualTo("Plan Nord");
    }

    @Test
    void rejectsMissingTokenAsInvalidRequest() {
        OperationalPlanApplicationService service = new OperationalPlanApplicationService(
                new InMemoryOperationalPlanRepository(plan())
        );

        assertThatThrownBy(() -> service.updateOperationalPlan(command(null)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("expectedUpdatedAt");
    }

    @Test
    void rejectsUnknownOperationalPlan() {
        OperationalPlanApplicationService service = new OperationalPlanApplicationService(
                new InMemoryOperationalPlanRepository(null)
        );

        assertThatThrownBy(() -> service.updateOperationalPlan(command(CURRENT_TOKEN)))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Unknown operational plan");
    }

    private static UpdateOperationalPlanCommand command(Instant token) {
        return new UpdateOperationalPlanCommand(
                "PLAN-1", token, "الخطة الشمالية", "Plan Nord actualisé", "Updated North Plan", "ORG-2"
        );
    }

    private static OperationalPlan plan() {
        return new OperationalPlan(
                "PLAN-1", "PERIOD-1", "OP-NORTH", null, "Plan Nord", "North Plan", "TYPE-1", "PRODUCT-1",
                "PIPELINE", "PIPE-1", "PL-NORTH", "Pipeline Nord", "ORG-1", OperationalPlanStatus.DRAFT,
                "REV-2", "REV-1", "ACTOR-1", Instant.parse("2026-09-12T07:00:00Z"), CURRENT_TOKEN
        );
    }

    private static final class InMemoryOperationalPlanRepository implements OperationalPlanRepositoryPort {
        private OperationalPlan value;
        private int updateAttempts;
        private boolean forceCompareAndSetMiss;

        private InMemoryOperationalPlanRepository(OperationalPlan value) {
            this.value = value;
        }

        @Override
        public OperationalPlan save(OperationalPlan model) {
            value = model;
            return model;
        }

        @Override
        public Optional<OperationalPlan> findById(String id) {
            return Optional.ofNullable(value).filter(plan -> plan.id().equals(id));
        }

        @Override
        public boolean updateMetadataIfUpdatedAtMatches(
                String id,
                Instant expectedUpdatedAt,
                String nameAr,
                String nameFr,
                String nameEn,
                String responsibleOrganizationUnitId,
                Instant newUpdatedAt
        ) {
            updateAttempts++;
            if (forceCompareAndSetMiss || value == null || !value.id().equals(id) || !value.updatedAt().equals(expectedUpdatedAt)) {
                return false;
            }
            value = new OperationalPlan(
                    value.id(), value.periodId(), value.code(), nameAr, nameFr, nameEn, value.planTypeId(), value.productTypeId(),
                    value.topologyScopeType(), value.topologyScopeId(), value.topologyScopeCode(), value.topologyScopeNameSnapshot(),
                    responsibleOrganizationUnitId, value.status(), value.currentRevisionId(), value.approvedRevisionId(),
                    value.createdByActorId(), value.createdAt(), newUpdatedAt
            );
            return true;
        }
    }
}
