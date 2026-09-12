/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanRevisionUpdateApplicationServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.application.service
 *
 * @Description : Verifies authoritative current-revision concurrency semantics.
 *
 */
package dz.sh.hidra.modules.planning.application.service;

import dz.sh.hidra.modules.planning.application.port.in.UpdatePlanRevisionUseCase;
import dz.sh.hidra.modules.planning.application.port.out.OperationalPlanRepositoryPort;
import dz.sh.hidra.modules.planning.application.port.out.PlanRevisionRepositoryPort;
import dz.sh.hidra.modules.planning.domain.exception.PlanningRevisionConflictException;
import dz.sh.hidra.modules.planning.domain.model.OperationalPlan;
import dz.sh.hidra.modules.planning.domain.model.PlanRevision;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PlanRevisionUpdateApplicationServiceTest {

    private static final Instant TOKEN = Instant.parse("2026-09-12T10:00:00Z");

    private PlanRevisionRepositoryPort revisionRepository;
    private OperationalPlanRepositoryPort planRepository;
    private PlanRevisionUpdateApplicationService service;

    @BeforeEach
    void setUp() {
        revisionRepository = mock(PlanRevisionRepositoryPort.class);
        planRepository = mock(OperationalPlanRepositoryPort.class);
        service = new PlanRevisionUpdateApplicationService(revisionRepository, planRepository);
    }

    @Test
    void updatesCurrentRevisionWhenExpectedTokenMatchesAndReturnsRefreshedToken() {
        PlanRevision revision = revision("REV-2", TOKEN);
        when(revisionRepository.findByIdForUpdate("REV-2")).thenReturn(Optional.of(revision));
        when(planRepository.findById("PLAN-1")).thenReturn(Optional.of(plan("REV-2")));
        when(revisionRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        PlanRevision result = service.update("REV-2", new UpdatePlanRevisionUseCase.Command(
                TOKEN, "OPS_CHANGE", "Updated throughput assumptions"
        ));

        assertEquals("OPS_CHANGE", result.changeReasonCodeId());
        assertEquals("Updated throughput assumptions", result.changeReasonText());
        assertTrue(result.updatedAt().isAfter(TOKEN));
        assertEquals("DRAFT", result.status().name());
        verify(revisionRepository).findByIdForUpdate("REV-2");
        verify(revisionRepository).save(any(PlanRevision.class));
    }

    @Test
    void rejectsStaleExpectedTokenWithoutSaving() {
        PlanRevision revision = revision("REV-2", TOKEN);
        when(revisionRepository.findByIdForUpdate("REV-2")).thenReturn(Optional.of(revision));
        when(planRepository.findById("PLAN-1")).thenReturn(Optional.of(plan("REV-2")));

        PlanningRevisionConflictException error = assertThrows(
                PlanningRevisionConflictException.class,
                () -> service.update("REV-2", new UpdatePlanRevisionUseCase.Command(
                        TOKEN.minusSeconds(1), "OPS_CHANGE", "stale"
                ))
        );

        assertTrue(error.getMessage().contains("Refetch the revision"));
        verify(revisionRepository, never()).save(any());
    }

    @Test
    void rejectsRevisionThatIsNoLongerCurrentWithoutSaving() {
        PlanRevision revision = revision("REV-1", TOKEN);
        when(revisionRepository.findByIdForUpdate("REV-1")).thenReturn(Optional.of(revision));
        when(planRepository.findById("PLAN-1")).thenReturn(Optional.of(plan("REV-2")));

        assertThrows(
                PlanningRevisionConflictException.class,
                () -> service.update("REV-1", new UpdatePlanRevisionUseCase.Command(TOKEN, null, "edit"))
        );

        verify(revisionRepository, never()).save(any());
    }

    private static PlanRevision revision(String id, Instant updatedAt) {
        return new PlanRevision(
                id, "PLAN-1", 2, "R02",
                dz.sh.hidra.modules.planning.domain.value.PlanRevisionStatus.DRAFT,
                null, null, "REV-1", null, null, null, null, null,
                Instant.parse("2026-09-12T09:00:00Z"), updatedAt
        );
    }

    private static OperationalPlan plan(String currentRevisionId) {
        return new OperationalPlan(
                "PLAN-1", "PERIOD-1", "PLAN", null, "Plan", null,
                null, null, "PIPELINE", "PIPE-1", "P-1", "Pipeline", "ORG-1",
                dz.sh.hidra.modules.planning.domain.value.OperationalPlanStatus.DRAFT,
                currentRevisionId, null, "ACTOR-1",
                Instant.parse("2026-09-12T08:00:00Z"), Instant.parse("2026-09-12T09:00:00Z")
        );
    }
}
