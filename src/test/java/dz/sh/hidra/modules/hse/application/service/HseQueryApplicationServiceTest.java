/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseQueryApplicationServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Test
 * @Layer       : Application
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.application.service
 *
 * @Description : Verifies HWEB-009 HSE case/CAPA reads.
 *
 */
package dz.sh.hidra.modules.hse.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dz.sh.hidra.modules.hse.application.port.out.HseCaseRepositoryPort;
import dz.sh.hidra.modules.hse.application.port.out.HseCorrectivePreventiveActionRepositoryPort;
import dz.sh.hidra.modules.hse.domain.model.HseCase;
import dz.sh.hidra.modules.hse.domain.model.HseCorrectivePreventiveAction;
import dz.sh.hidra.modules.hse.domain.value.CapaStatus;
import dz.sh.hidra.modules.hse.domain.value.HseCaseSourceType;
import dz.sh.hidra.modules.hse.domain.value.HseCaseStatus;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HseQueryApplicationServiceTest {

    private HseCaseRepositoryPort caseRepository;
    private HseCorrectivePreventiveActionRepositoryPort capaRepository;
    private HseQueryApplicationService service;

    @BeforeEach
    void setUp() {
        caseRepository = mock(HseCaseRepositoryPort.class);
        capaRepository = mock(HseCorrectivePreventiveActionRepositoryPort.class);
        service = new HseQueryApplicationService(caseRepository, capaRepository);
    }

    @Test
    void listsAndLoadsCases() {
        HseCase hseCase = caseModel();
        when(caseRepository.findAll(0, 50)).thenReturn(List.of(hseCase));
        when(caseRepository.count()).thenReturn(1L);
        when(caseRepository.findById("hse-1")).thenReturn(Optional.of(hseCase));

        var page = service.cases(0, 50);
        var detail = service.hseCase(" hse-1 ");

        assertEquals(1, page.content().size());
        assertEquals("OPEN", page.content().getFirst().status());
        assertEquals("INCIDENT_REFERENCE", detail.sourceType());
        assertEquals("INC-001", detail.incidentCodeSnapshot());
    }

    @Test
    void listsAndLoadsCapas() {
        HseCorrectivePreventiveAction capa = capaModel();
        when(capaRepository.findAll(0, 50)).thenReturn(List.of(capa));
        when(capaRepository.count()).thenReturn(1L);
        when(capaRepository.findById("capa-1")).thenReturn(Optional.of(capa));

        var page = service.capas(0, 50);
        var detail = service.capa("capa-1");

        assertEquals(1, page.content().size());
        assertEquals("IN_PROGRESS", page.content().getFirst().status());
        assertEquals("work-1", detail.linkedWorkOrderId());
        assertEquals("task-1", detail.workflowTaskId());
    }

    @Test
    void rejectsInvalidPagingAndUnknownIds() {
        assertThrows(IllegalArgumentException.class, () -> service.cases(-1, 50));
        assertThrows(IllegalArgumentException.class, () -> service.capas(0, 201));
        when(caseRepository.findById("missing")).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> service.hseCase("missing"));
    }

    private HseCase caseModel() {
        Instant now = Instant.parse("2026-09-11T10:00:00Z");
        return new HseCase(
                "hse-1", "HSE-001", "Gas release", "Under investigation", "SAFETY", "HIGH", "P1",
                HseCaseStatus.OPEN, HseCaseSourceType.INCIDENT_REFERENCE,
                "inc-1", "INC-001", "Pipeline incident", "topology", "PIPELINE", "pipe-1", "PL-001", "Pipeline Nord",
                now, now, "actor-1", "Operator One", "org-1", "Operations", "wf-1", "audit-1",
                null, null, null, now, now
        );
    }

    private HseCorrectivePreventiveAction capaModel() {
        Instant now = Instant.parse("2026-09-11T10:00:00Z");
        return new HseCorrectivePreventiveAction(
                "capa-1", "hse-1", "CAPA-001", "CORRECTIVE", "Inspect valve", "Inspect and repair",
                "actor-2", "Engineer One", "org-2", "Maintenance", now.plusSeconds(86400), null,
                true, null, null, CapaStatus.IN_PROGRESS, "work-1", "task-1", now, now
        );
    }
}
