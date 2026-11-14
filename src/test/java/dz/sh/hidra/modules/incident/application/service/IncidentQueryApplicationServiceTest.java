package dz.sh.hidra.modules.incident.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import dz.sh.hidra.modules.incident.application.port.out.IncidentRepositoryPort;
import dz.sh.hidra.modules.incident.application.port.out.IncidentEscalationAuthorizationPort;
import dz.sh.hidra.modules.incident.application.port.out.IncidentEscalationStorePort;
import dz.sh.hidra.modules.incident.application.port.out.IncidentEscalationSourcePort;
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
    @Mock
    private IncidentEscalationAuthorizationPort scope;
    @Mock private IncidentEscalationStorePort links;
    @Mock private IncidentEscalationSourcePort sources;

    @Test
    void returnsPagedIncidentViews() {
        Incident incident = incident("inc-1");
        when(scope.permittedAssets("incident:incidents:read"))
            .thenReturn(List.of(new IncidentEscalationAuthorizationPort.AssetRef("PIPELINE","pipe-1")));
        when(repository.findAuthorized(org.mockito.ArgumentMatchers.anyList(),
                org.mockito.ArgumentMatchers.eq(List.of()),
                org.mockito.ArgumentMatchers.eq(0),org.mockito.ArgumentMatchers.eq(50)))
            .thenReturn(new IncidentRepositoryPort.AuthorizedPage(List.of(incident),1));

        when(links.correlationsForAssets(org.mockito.ArgumentMatchers.anyList())).thenReturn(List.of());
        var result = new IncidentQueryApplicationService(repository,scope,sources,links).incidents(0, 50);

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
        when(links.byIncident(org.mockito.ArgumentMatchers.anyString())).thenReturn(Optional.empty());
        var service = new IncidentQueryApplicationService(repository, scope, sources, links);

        assertThat(service.incident("inc-1").incidentNumber()).isEqualTo("INC-001");
        verify(scope).requireIncident("incident:incidents:read", "inc-1");
        assertThatThrownBy(() -> service.incident("missing"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unknown incident");
        verify(scope).requireIncident("incident:incidents:read", "missing");
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
