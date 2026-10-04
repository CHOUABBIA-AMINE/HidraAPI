/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakCandidateSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Leak Detection Test
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.semantic
 *
 * @Description : Verifies HMR-015 LeakCandidate semantic remediation.
 *
 */
package dz.sh.hidra.modules.leakdetection.semantic;

import dz.sh.hidra.modules.leakdetection.application.command.CreateLeakCandidateCommand;
import dz.sh.hidra.modules.leakdetection.application.port.out.LeakCandidateRepositoryPort;
import dz.sh.hidra.modules.leakdetection.application.port.out.LeakDetectionCaseRepositoryPort;
import dz.sh.hidra.modules.leakdetection.application.port.out.LeakEscalationReferenceRepositoryPort;
import dz.sh.hidra.modules.leakdetection.application.service.LeakDetectionApplicationService;
import dz.sh.hidra.modules.leakdetection.domain.exception.InvalidLeakDetectionValueException;
import dz.sh.hidra.modules.leakdetection.domain.model.LeakCandidate;
import dz.sh.hidra.modules.leakdetection.domain.service.LeakConfidenceClassifier;
import dz.sh.hidra.modules.leakdetection.domain.value.LeakCandidateStatus;
import dz.sh.hidra.modules.leakdetection.domain.value.LeakDetectionProfileStatus;
import dz.sh.hidra.modules.leakdetection.domain.value.LeakDetectionRunStatus;
import dz.sh.hidra.modules.leakdetection.domain.value.LeakSeverityLevel;
import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.adapter.JpaLeakCandidateRepositoryAdapter;
import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.entity.LeakDetectionProfileJpaEntity;
import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.entity.LeakDetectionRunJpaEntity;
import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.repository.LeakCandidateJpaRepository;
import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.repository.LeakDetectionProfileJpaRepository;
import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.repository.LeakDetectionRunJpaRepository;
import dz.sh.hidra.modules.topology.application.contract.leakdetection.LeakDetectionTopologyAssetContract;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LeakCandidateSemanticRemediationTest {

    private static final Instant NOW = Instant.parse("2026-10-04T00:00:00Z");

    @Test
    void domainRequiresTopologyTypeAndAuditTimestamps() {
        assertThatThrownBy(() -> candidate(null, null, " ", BigDecimal.valueOf(0.80), LeakSeverityLevel.HIGH, NOW, NOW))
                .isInstanceOf(InvalidLeakDetectionValueException.class)
                .hasMessageContaining("topology asset type");

        assertThatThrownBy(() -> candidate(null, null, "PIPELINE", BigDecimal.valueOf(0.80), LeakSeverityLevel.HIGH, null, NOW))
                .isInstanceOf(InvalidLeakDetectionValueException.class)
                .hasMessageContaining("createdAt");
    }

    @Test
    void repositoryRejectsContradictoryPersistedSeverity() {
        var candidateRepository = mock(LeakCandidateJpaRepository.class);
        var profileRepository = mock(LeakDetectionProfileJpaRepository.class);
        var runRepository = mock(LeakDetectionRunJpaRepository.class);
        var adapter = new JpaLeakCandidateRepositoryAdapter(
                candidateRepository,
                profileRepository,
                runRepository
        );

        assertThatThrownBy(() -> adapter.save(candidate(
                null,
                null,
                "PIPELINE",
                BigDecimal.valueOf(0.91),
                LeakSeverityLevel.HIGH,
                NOW,
                NOW
        ))).isInstanceOf(InvalidLeakDetectionValueException.class)
                .hasMessageContaining("persisted severity");

        verify(candidateRepository, never()).save(any());
    }

    @Test
    void repositoryAllowsRunlessCandidateOnlyWithActiveProfile() {
        var candidateRepository = mock(LeakCandidateJpaRepository.class);
        var profileRepository = mock(LeakDetectionProfileJpaRepository.class);
        var runRepository = mock(LeakDetectionRunJpaRepository.class);
        var adapter = new JpaLeakCandidateRepositoryAdapter(
                candidateRepository,
                profileRepository,
                runRepository
        );
        var profile = mock(LeakDetectionProfileJpaEntity.class);
        when(profile.status()).thenReturn(LeakDetectionProfileStatus.ACTIVE);
        when(profileRepository.findById("profile-1")).thenReturn(Optional.of(profile));
        when(candidateRepository.existsById("candidate-1")).thenReturn(false);
        when(candidateRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        LeakCandidate saved = adapter.save(candidate(
                null,
                null,
                "PIPELINE",
                BigDecimal.valueOf(0.80),
                LeakSeverityLevel.HIGH,
                NOW,
                NOW
        ));

        assertThat(saved.runId()).isNull();
        verify(runRepository, never()).findById(any());
    }

    @Test
    void repositoryRejectsRunProfileMismatchAndIneligibleRun() {
        var candidateRepository = mock(LeakCandidateJpaRepository.class);
        var profileRepository = mock(LeakDetectionProfileJpaRepository.class);
        var runRepository = mock(LeakDetectionRunJpaRepository.class);
        var adapter = new JpaLeakCandidateRepositoryAdapter(
                candidateRepository,
                profileRepository,
                runRepository
        );
        var profile = mock(LeakDetectionProfileJpaEntity.class);
        var run = mock(LeakDetectionRunJpaEntity.class);
        when(profile.status()).thenReturn(LeakDetectionProfileStatus.ACTIVE);
        when(profileRepository.findById("profile-1")).thenReturn(Optional.of(profile));
        when(candidateRepository.existsById("candidate-1")).thenReturn(false);
        when(runRepository.findById("run-1")).thenReturn(Optional.of(run));

        when(run.profileId()).thenReturn("other-profile");
        when(run.status()).thenReturn(LeakDetectionRunStatus.RUNNING);
        assertThatThrownBy(() -> adapter.save(candidate(
                "run-1",
                null,
                "PIPELINE",
                BigDecimal.valueOf(0.80),
                LeakSeverityLevel.HIGH,
                NOW,
                NOW
        ))).isInstanceOf(InvalidLeakDetectionValueException.class)
                .hasMessageContaining("provenance must match");

        when(run.profileId()).thenReturn("profile-1");
        when(run.status()).thenReturn(LeakDetectionRunStatus.FAILED);
        assertThatThrownBy(() -> adapter.save(candidate(
                "run-1",
                null,
                "PIPELINE",
                BigDecimal.valueOf(0.80),
                LeakSeverityLevel.HIGH,
                NOW,
                NOW
        ))).isInstanceOf(InvalidLeakDetectionValueException.class)
                .hasMessageContaining("RUNNING or COMPLETED");
    }

    @Test
    void applicationUsesTopologyOwnerSnapshotsAndRejectsCallerCodeMismatch() {
        LeakCandidateRepositoryPort candidateRepository = mock(LeakCandidateRepositoryPort.class);
        LeakDetectionCaseRepositoryPort caseRepository = mock(LeakDetectionCaseRepositoryPort.class);
        LeakEscalationReferenceRepositoryPort escalationRepository = mock(LeakEscalationReferenceRepositoryPort.class);
        LeakDetectionTopologyAssetContract topologyContract = mock(LeakDetectionTopologyAssetContract.class);
        var service = new LeakDetectionApplicationService(
                candidateRepository,
                caseRepository,
                escalationRepository,
                new LeakConfidenceClassifier(),
                topologyContract
        );
        when(topologyContract.resolve("PIPELINE", "pipeline-1")).thenReturn(
                LeakDetectionTopologyAssetContract.AssetResolution.resolved(
                        "pipeline-1",
                        "GZ1",
                        "Gazoduc 1"
                )
        );
        when(candidateRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        service.createLeakCandidate(command(null));

        var captor = ArgumentCaptor.forClass(LeakCandidate.class);
        verify(candidateRepository).save(captor.capture());
        assertThat(captor.getValue().topologyAssetCode()).isEqualTo("GZ1");
        assertThat(captor.getValue().topologyAssetNameSnapshot()).isEqualTo("Gazoduc 1");

        assertThatThrownBy(() -> service.createLeakCandidate(command("WRONG")))
                .isInstanceOf(InvalidLeakDetectionValueException.class)
                .hasMessageContaining("does not match");
    }

    @Test
    void migrationEnforcesRunIntegrityProvenanceSeverityAndKeepsTopologyReferenceCrossModule() throws Exception {
        String sql = Files.readString(Path.of(
                "src/main/resources/db/migration/V20261004_015__hmr_015_leakdetection_leak_candidate.sql"
        ));

        assertThat(sql).contains("fk_hmr015_leak_candidate_run");
        assertThat(sql).contains("REFERENCES hidra_leak_detection_run (id)");
        assertThat(sql).contains("trg_hmr015_leak_candidate_provenance");
        assertThat(sql).contains("profile_status <> 'ACTIVE'");
        assertThat(sql).contains("run_status NOT IN ('RUNNING', 'COMPLETED')");
        assertThat(sql).contains("ck_hmr015_leak_candidate_derived_severity");
        assertThat(sql).doesNotContain("REFERENCES hidra_topology_");
    }

    @Test
    void dddKeepsExternalComputeIdempotencyBehindItsDeferredContractGate() throws Exception {
        String ddd = Files.readString(Path.of("docs/data definition/LeakDetection.md"));

        assertThat(ddd).contains("does not establish whether `candidateNumber` is globally unique");
        assertThat(ddd).contains("No CPM/gRPC adapter may create candidates until that contract defines");
    }

    private static CreateLeakCandidateCommand command(String topologyCode) {
        return new CreateLeakCandidateCommand(
                null,
                "profile-1",
                "candidate-number-1",
                "pipeline",
                "pipeline-1",
                topologyCode,
                "caller-name",
                NOW,
                null,
                BigDecimal.valueOf(0.80),
                "Suspected leak",
                "corr-1"
        );
    }

    private static LeakCandidate candidate(
            String runId,
            String candidateNumber,
            String topologyAssetType,
            BigDecimal confidence,
            LeakSeverityLevel severity,
            Instant createdAt,
            Instant updatedAt
    ) {
        return new LeakCandidate(
                "candidate-1",
                runId,
                "profile-1",
                candidateNumber == null ? "candidate-number-1" : candidateNumber,
                topologyAssetType,
                "pipeline-1",
                "GZ1",
                "Gazoduc 1",
                NOW,
                null,
                confidence,
                severity,
                LeakCandidateStatus.NEW,
                null,
                "corr-1",
                createdAt,
                updatedAt
        );
    }
}
