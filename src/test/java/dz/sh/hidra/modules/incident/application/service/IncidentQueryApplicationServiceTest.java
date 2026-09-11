package dz.sh.hidra.modules.incident.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import dz.sh.hidra.modules.incident.application.port.out.IncidentRepositoryPort;
import dz.sh.hidra.modules.incident.domain.model.Incident;
import dz.sh.hidra.modules.incident.domain.value.IncidentSourceType;
import dz.sh.hidra.modules.incident.domain.value.IncidentStatus;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class IncidentQueryApplicationServiceTest {

    @Mock
    private IncidentRepositoryPort repository;

    @Test
    void returnsPagedIncidentViews() {
        Incident incident = incident("inc-1");
        when(repository.findAll(0, 50)).thenReturn(List.of(incident));
        when(repository.count()).thenReturn(1L);

        var result = new IncidentQueryApplicationService(repository).incidents(0, 50);

        assertThat(result.content()).hasSize(1);
        assertThat(result.content().getFirst().id()).isEqualTo("inc-1");
        assertThat(result.content().getFirst().status()).isEqualTo("OPEN");
        assertThat(result.totalElements()).isEqualTo(1);
        assertThat(result.hasNext()).isFalse();
    }

    @Test
    void returnsIncidentDetailAndRejectsUnknownId() {
        when(repository.findById("inc-1")).thenReturn(Optional.of(incident("inc-1")));
        when(repository.findById("missing")).thenReturn(Optional.empty());
        var service = new IncidentQueryApplicationService(repository);

        assertThat(service.incident("inc-1").incidentNumber()).isEqualTo("INC-001");
        assertThatThrownBy(() -> service.incident("missing"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unknown incident");
    }

    private Incident incident(String id) {
        Instant now = Instant.parse("2026-09-11T18:00:00Z");
        return new Incident(
                id, "INC-001", "Pressure loss", "Observed pressure loss",
                "CLASS-1", "SEV-2", "PRI-1", IncidentStatus.OPEN, IncidentSourceType.ALARM,
                "alarm-1", "ALM-001", now, now, now,
                "PIPELINE", "pipe-1", "PL-01", "Pipeline 01",
                null, "KP 120", null, null,
                "org-1", "OPS", "Operations", "actor-1", "Operator",
                "wf-1", 0, null, null, null, null,
                "actor-1", "Operator", now, now
        );
    }
}
