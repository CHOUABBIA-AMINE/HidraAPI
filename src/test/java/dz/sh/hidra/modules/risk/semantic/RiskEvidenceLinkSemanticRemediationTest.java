/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskEvidenceLinkSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Risk Test
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.semantic
 *
 * @Description : Enforces governed Risk owner boundaries.
 *
 */
package dz.sh.hidra.modules.risk.semantic;

import dz.sh.hidra.modules.risk.application.contract.evidence.RiskOwnedEvidenceLookup;
import dz.sh.hidra.modules.risk.application.service.RiskEvidenceRegistry;
import dz.sh.hidra.modules.risk.domain.model.RiskEvidenceLink;
import dz.sh.hidra.modules.risk.domain.exception.InvalidRiskValueException;
import java.time.Instant;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class RiskEvidenceLinkSemanticRemediationTest {
    private static final Instant NOW = Instant.parse("2026-10-08T00:00:00Z");
    private static RiskEvidenceLink link(String module, String type, String id) {
        return new RiskEvidenceLink("link", "assessment", module, type, id,
                "caller-code", null, null, null, null, NOW);
    }
    @Test void incompleteIdentityIsRejectedBeforePersistence() {
        for (String blank : new String[]{null, "", "  ", "\t"}) {
            assertThatThrownBy(() -> link(blank, "Alarm", "id")).isInstanceOf(InvalidRiskValueException.class);
            assertThatThrownBy(() -> link("alarm", blank, "id")).isInstanceOf(InvalidRiskValueException.class);
            assertThatThrownBy(() -> link("alarm", "Alarm", blank)).isInstanceOf(InvalidRiskValueException.class);
        }
    }
    @Test void missingAmbiguousAndUnsupportedOwnersFailClosed() {
        var provider = provider("alarm", "Alarm", true);
        assertThatThrownBy(() -> new RiskEvidenceRegistry(List.of()).validate(link("alarm", "Alarm", "id")))
                .isInstanceOf(InvalidRiskValueException.class);
        assertThatThrownBy(() -> new RiskEvidenceRegistry(List.of(provider, provider)).validate(link("alarm", "Alarm", "id")))
                .isInstanceOf(InvalidRiskValueException.class);
        assertThatThrownBy(() -> new RiskEvidenceRegistry(List.of(provider)).validate(link("alarm", "Incident", "id")))
                .isInstanceOf(InvalidRiskValueException.class);
        assertThatThrownBy(() -> new RiskEvidenceRegistry(List.of(provider("alarm", "Alarm", false)))
                .validate(link("alarm", "Alarm", "id"))).isInstanceOf(InvalidRiskValueException.class);
        assertThat(new dz.sh.hidra.modules.risk.infrastructure.integration.NoopRiskExternalEvidenceResolver()
                .evidenceExists("alarm", "Alarm", "id")).isFalse();
    }
    @Test void canonicalAvailableSnapshotsReplaceCallerValuesWithoutRequiringOthers() {
        var result = new RiskEvidenceRegistry(List.of(provider("alarm", "Alarm", true)))
                .validate(link("alarm", "Alarm", "id"));
        assertThat(result.evidenceCodeSnapshot()).isEqualTo("owner-code");
        assertThat(result.evidenceLabelSnapshot()).isNull();
    }
    private static RiskOwnedEvidenceLookup provider(String module, String type, boolean exists) {
        return new RiskOwnedEvidenceLookup() {
            public String module() { return module; }
            public Set<String> evidenceTypes() { return Set.of(type); }
            public Optional<Evidence> resolve(String t, String id) {
                return exists ? Optional.of(new Evidence(id, "owner-code", null, null, null)) : Optional.empty();
            }
        };
    }
    @Test void monitoringProviderUsesOnlyItsExactOwnedRepositories() {
        var r0 = mock(dz.sh.hidra.modules.monitoring.infrastructure.persistence.repository.MonitoringEvaluationJpaRepository.class);
        var r1 = mock(dz.sh.hidra.modules.monitoring.infrastructure.persistence.repository.RiskSignalJpaRepository.class);
        var adapter = new dz.sh.hidra.modules.monitoring.infrastructure.integration.RiskEvidenceQueryAdapter(r0, r1);
        assertThat(adapter.module()).isEqualTo("monitoring");
        assertThat(adapter.resolve("MonitoringEvaluation", "missing")).isEmpty();
        verify(r0).findById("missing");
        var source0 = mock(dz.sh.hidra.modules.monitoring.infrastructure.persistence.entity.MonitoringEvaluationJpaEntity.class);
        when(source0.id()).thenReturn("source");
        when(r0.findById("source")).thenReturn(Optional.of(source0));
        assertThat(adapter.resolve("MonitoringEvaluation", "source")).isPresent();
        assertThat(adapter.resolve("RiskSignal", "missing")).isEmpty();
        verify(r1).findById("missing");
        var source1 = mock(dz.sh.hidra.modules.monitoring.infrastructure.persistence.entity.RiskSignalJpaEntity.class);
        when(source1.id()).thenReturn("source");
        when(r1.findById("source")).thenReturn(Optional.of(source1));
        assertThat(adapter.resolve("RiskSignal", "source")).isPresent();
        assertThat(adapter.resolve("Unknown", "id")).isEmpty();
    }
    @Test void alarmProviderUsesOnlyItsExactOwnedRepositories() {
        var r0 = mock(dz.sh.hidra.modules.alarm.infrastructure.persistence.repository.AlarmJpaRepository.class);
        var adapter = new dz.sh.hidra.modules.alarm.infrastructure.integration.RiskEvidenceQueryAdapter(r0);
        assertThat(adapter.module()).isEqualTo("alarm");
        assertThat(adapter.resolve("Alarm", "missing")).isEmpty();
        verify(r0).findById("missing");
        var source0 = mock(dz.sh.hidra.modules.alarm.infrastructure.persistence.entity.AlarmJpaEntity.class);
        when(source0.id()).thenReturn("source");
        when(r0.findById("source")).thenReturn(Optional.of(source0));
        assertThat(adapter.resolve("Alarm", "source")).isPresent();
        assertThat(adapter.resolve("Unknown", "id")).isEmpty();
    }
    @Test void incidentProviderUsesOnlyItsExactOwnedRepositories() {
        var r0 = mock(dz.sh.hidra.modules.incident.infrastructure.persistence.repository.IncidentJpaRepository.class);
        var adapter = new dz.sh.hidra.modules.incident.infrastructure.integration.RiskEvidenceQueryAdapter(r0);
        assertThat(adapter.module()).isEqualTo("incident");
        assertThat(adapter.resolve("Incident", "missing")).isEmpty();
        verify(r0).findById("missing");
        var source0 = mock(dz.sh.hidra.modules.incident.infrastructure.persistence.entity.IncidentJpaEntity.class);
        when(source0.id()).thenReturn("source");
        when(r0.findById("source")).thenReturn(Optional.of(source0));
        assertThat(adapter.resolve("Incident", "source")).isPresent();
        assertThat(adapter.resolve("Unknown", "id")).isEmpty();
    }
    @Test void hseProviderUsesOnlyItsExactOwnedRepositories() {
        var r0 = mock(dz.sh.hidra.modules.hse.infrastructure.persistence.repository.HseCaseJpaRepository.class);
        var adapter = new dz.sh.hidra.modules.hse.infrastructure.integration.RiskEvidenceQueryAdapter(r0);
        assertThat(adapter.module()).isEqualTo("hse");
        assertThat(adapter.resolve("HseCase", "missing")).isEmpty();
        verify(r0).findById("missing");
        var source0 = mock(dz.sh.hidra.modules.hse.infrastructure.persistence.entity.HseCaseJpaEntity.class);
        when(source0.id()).thenReturn("source");
        when(r0.findById("source")).thenReturn(Optional.of(source0));
        assertThat(adapter.resolve("HseCase", "source")).isPresent();
        assertThat(adapter.resolve("Unknown", "id")).isEmpty();
    }
    @Test void integrityProviderUsesOnlyItsExactOwnedRepositories() {
        var r0 = mock(dz.sh.hidra.modules.integrity.infrastructure.persistence.repository.IntegrityCaseJpaRepository.class);
        var adapter = new dz.sh.hidra.modules.integrity.infrastructure.integration.RiskEvidenceQueryAdapter(r0);
        assertThat(adapter.module()).isEqualTo("integrity");
        assertThat(adapter.resolve("IntegrityCase", "missing")).isEmpty();
        verify(r0).findById("missing");
        var source0 = mock(dz.sh.hidra.modules.integrity.infrastructure.persistence.entity.IntegrityCaseJpaEntity.class);
        when(source0.id()).thenReturn("source");
        when(r0.findById("source")).thenReturn(Optional.of(source0));
        assertThat(adapter.resolve("IntegrityCase", "source")).isPresent();
        assertThat(adapter.resolve("Unknown", "id")).isEmpty();
    }
    @Test void assetsProviderUsesOnlyItsExactOwnedRepositories() {
        var r0 = mock(dz.sh.hidra.modules.assets.infrastructure.persistence.repository.MaintenanceWorkOrderJpaRepository.class);
        var adapter = new dz.sh.hidra.modules.assets.infrastructure.integration.RiskEvidenceQueryAdapter(r0);
        assertThat(adapter.module()).isEqualTo("assets");
        assertThat(adapter.resolve("MaintenanceWorkOrder", "missing")).isEmpty();
        verify(r0).findById("missing");
        var source0 = mock(dz.sh.hidra.modules.assets.infrastructure.persistence.entity.MaintenanceWorkOrderJpaEntity.class);
        when(source0.id()).thenReturn("source");
        when(r0.findById("source")).thenReturn(Optional.of(source0));
        assertThat(adapter.resolve("MaintenanceWorkOrder", "source")).isPresent();
        assertThat(adapter.resolve("Unknown", "id")).isEmpty();
    }
    @Test void simulationProviderUsesOnlyItsExactOwnedRepositories() {
        var r0 = mock(dz.sh.hidra.modules.simulation.infrastructure.persistence.repository.SimulationRunJpaRepository.class);
        var adapter = new dz.sh.hidra.modules.simulation.infrastructure.integration.RiskEvidenceQueryAdapter(r0);
        assertThat(adapter.module()).isEqualTo("simulation");
        assertThat(adapter.resolve("SimulationRun", "missing")).isEmpty();
        verify(r0).findById("missing");
        var source0 = mock(dz.sh.hidra.modules.simulation.infrastructure.persistence.entity.SimulationRunJpaEntity.class);
        when(source0.id()).thenReturn("source");
        when(r0.findById("source")).thenReturn(Optional.of(source0));
        assertThat(adapter.resolve("SimulationRun", "source")).isPresent();
        assertThat(adapter.resolve("Unknown", "id")).isEmpty();
    }
    @Test void telemetryProviderUsesOnlyItsExactOwnedRepositories() {
        var r0 = mock(dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository.TelemetryReadingJpaRepository.class);
        var adapter = new dz.sh.hidra.modules.telemetry.infrastructure.integration.RiskEvidenceQueryAdapter(r0);
        assertThat(adapter.module()).isEqualTo("telemetry");
        assertThat(adapter.resolve("TelemetryReading", "missing")).isEmpty();
        verify(r0).findById("missing");
        var source0 = mock(dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.TelemetryReadingJpaEntity.class);
        when(source0.id()).thenReturn("source");
        when(r0.findById("source")).thenReturn(Optional.of(source0));
        assertThat(adapter.resolve("TelemetryReading", "source")).isPresent();
        assertThat(adapter.resolve("Unknown", "id")).isEmpty();
    }
    @Test void custodyProviderUsesOnlyItsExactOwnedRepositories() {
        var r0 = mock(dz.sh.hidra.modules.custody.infrastructure.persistence.repository.CustodyTransferTicketJpaRepository.class);
        var adapter = new dz.sh.hidra.modules.custody.infrastructure.integration.RiskEvidenceQueryAdapter(r0);
        assertThat(adapter.module()).isEqualTo("custody");
        assertThat(adapter.resolve("CustodyTransferTicket", "missing")).isEmpty();
        verify(r0).findById("missing");
        var source0 = mock(dz.sh.hidra.modules.custody.infrastructure.persistence.entity.CustodyTransferTicketJpaEntity.class);
        when(source0.id()).thenReturn("source");
        when(r0.findById("source")).thenReturn(Optional.of(source0));
        assertThat(adapter.resolve("CustodyTransferTicket", "source")).isPresent();
        assertThat(adapter.resolve("Unknown", "id")).isEmpty();
    }
    @Test void documentsProviderUsesOnlyItsExactOwnedRepositories() {
        var r0 = mock(dz.sh.hidra.modules.documents.infrastructure.persistence.repository.DocumentJpaRepository.class);
        var adapter = new dz.sh.hidra.modules.documents.infrastructure.integration.RiskEvidenceQueryAdapter(r0);
        assertThat(adapter.module()).isEqualTo("documents");
        assertThat(adapter.resolve("Document", "missing")).isEmpty();
        verify(r0).findById("missing");
        var source0 = mock(dz.sh.hidra.modules.documents.infrastructure.persistence.entity.DocumentJpaEntity.class);
        when(source0.id()).thenReturn("source");
        when(r0.findById("source")).thenReturn(Optional.of(source0));
        assertThat(adapter.resolve("Document", "source")).isPresent();
        assertThat(adapter.resolve("Unknown", "id")).isEmpty();
    }
    @Test void auditProviderUsesOnlyItsExactOwnedRepositories() {
        var r0 = mock(dz.sh.hidra.modules.audit.infrastructure.persistence.repository.AuditEventJpaRepository.class);
        var adapter = new dz.sh.hidra.modules.audit.infrastructure.integration.RiskEvidenceQueryAdapter(r0);
        assertThat(adapter.module()).isEqualTo("audit");
        assertThat(adapter.resolve("AuditEvent", "missing")).isEmpty();
        verify(r0).findById("missing");
        var source0 = mock(dz.sh.hidra.modules.audit.infrastructure.persistence.entity.AuditEventJpaEntity.class);
        when(source0.id()).thenReturn("source");
        when(r0.findById("source")).thenReturn(Optional.of(source0));
        assertThat(adapter.resolve("AuditEvent", "source")).isPresent();
        assertThat(adapter.resolve("Unknown", "id")).isEmpty();
    }
}
