/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationJobRunSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Integration Test
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.semantic
 *
 * @Description : Verifies HMR-014 IntegrationJobRun semantic remediation.
 *
 */
package dz.sh.hidra.modules.integration.semantic;

import dz.sh.hidra.modules.integration.application.command.StartIntegrationJobRunCommand;
import dz.sh.hidra.modules.integration.domain.exception.InvalidIntegrationValueException;
import dz.sh.hidra.modules.integration.domain.model.IntegrationJobRun;
import dz.sh.hidra.modules.integration.domain.value.IntegrationDirection;
import dz.sh.hidra.modules.integration.domain.value.JobRunStatus;
import dz.sh.hidra.modules.integration.domain.value.JobTriggerType;
import dz.sh.hidra.modules.integration.infrastructure.persistence.adapter.JpaIntegrationJobRunRepositoryAdapter;
import dz.sh.hidra.modules.integration.infrastructure.persistence.entity.IntegrationCatalogEntryJpaEntity;
import dz.sh.hidra.modules.integration.infrastructure.persistence.entity.IntegrationJobDefinitionJpaEntity;
import dz.sh.hidra.modules.integration.infrastructure.persistence.repository.ConnectorInstanceJpaRepository;
import dz.sh.hidra.modules.integration.infrastructure.persistence.repository.IntegrationCatalogEntryJpaRepository;
import dz.sh.hidra.modules.integration.infrastructure.persistence.repository.IntegrationJobDefinitionJpaRepository;
import dz.sh.hidra.modules.integration.infrastructure.persistence.repository.IntegrationJobRunJpaRepository;
import dz.sh.hidra.modules.integration.infrastructure.persistence.repository.IntegrationMappingProfileJpaRepository;
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

class IntegrationJobRunSemanticRemediationTest {

    private static final Instant NOW = Instant.parse("2026-10-04T00:00:00Z");

    @Test
    void commandNoLongerCarriesCallerOwnedRunNumber() {
        var command = new StartIntegrationJobRunCommand(
                "job-1",
                JobTriggerType.EVENT,
                null,
                "correlation-1"
        );

        assertThat(command.runNumber()).isZero();
        assertThat(StartIntegrationJobRunCommand.class.getRecordComponents())
                .extracting(component -> component.getName())
                .doesNotContain("runNumber");
    }

    @Test
    void enforcesCountersCompletionOrderingAndManualActorProvenance() {
        assertThatThrownBy(() -> run(
                1, JobTriggerType.EVENT, null, JobRunStatus.RUNNING,
                null, -1, 0, 0, 0, 0, 0
        )).isInstanceOf(InvalidIntegrationValueException.class)
                .hasMessageContaining("counters");

        assertThatThrownBy(() -> run(
                1, JobTriggerType.EVENT, null, JobRunStatus.RUNNING,
                null, 1, 1, 1, 1, 0, 0
        )).isInstanceOf(InvalidIntegrationValueException.class)
                .hasMessageContaining("must not exceed");

        assertThatThrownBy(() -> new IntegrationJobRun(
                "run-1", "job-1", 1, JobTriggerType.EVENT, null,
                JobRunStatus.COMPLETED, null, NOW, NOW.minusSeconds(1),
                0, 0, 0, 0, 0, 0, null, NOW, NOW
        )).isInstanceOf(InvalidIntegrationValueException.class)
                .hasMessageContaining("must not precede");

        assertThatThrownBy(() -> run(
                1, JobTriggerType.MANUAL, null, JobRunStatus.RUNNING,
                null, 0, 0, 0, 0, 0, 0
        )).isInstanceOf(InvalidIntegrationValueException.class)
                .hasMessageContaining("triggeredByActorId");
    }

    @Test
    void terminalLifecycleCannotReopen() {
        IntegrationJobRun previous = run(
                3, JobTriggerType.EVENT, null, JobRunStatus.COMPLETED,
                NOW, 2, 2, 2, 0, 0, 0
        );
        IntegrationJobRun reopened = run(
                3, JobTriggerType.EVENT, null, JobRunStatus.RUNNING,
                null, 2, 2, 2, 0, 0, 0
        );

        assertThatThrownBy(() -> reopened.validateTransitionFrom(previous.status()))
                .isInstanceOf(InvalidIntegrationValueException.class)
                .hasMessageContaining("Terminal");
    }

    @Test
    void newRunIgnoresCallerNumberAndUsesDatabaseAllocation() {
        var runRepository = mock(IntegrationJobRunJpaRepository.class);
        var jobRepository = mock(IntegrationJobDefinitionJpaRepository.class);
        var connectorRepository = mock(ConnectorInstanceJpaRepository.class);
        var mappingRepository = mock(IntegrationMappingProfileJpaRepository.class);
        var catalogRepository = mock(IntegrationCatalogEntryJpaRepository.class);
        var adapter = new JpaIntegrationJobRunRepositoryAdapter(
                runRepository,
                jobRepository,
                connectorRepository,
                mappingRepository,
                catalogRepository
        );
        var model = run(
                999, JobTriggerType.MANUAL, "actor-1", JobRunStatus.RUNNING,
                null, 0, 0, 0, 0, 0, 0
        );

        when(jobRepository.findById("job-1")).thenReturn(Optional.of(job(true, true)));
        when(catalogRepository.findById("job-type-1")).thenReturn(Optional.of(
                new IntegrationCatalogEntryJpaEntity(
                        "job-type-1", "JOB_TYPE", "HEALTH_CHECK",
                        true, 1, true, NOW, NOW
                )
        ));
        when(runRepository.findAllocatedRunNumberById("run-1")).thenReturn(Optional.of(7L));

        IntegrationJobRun saved = adapter.save(model);

        var captor = ArgumentCaptor.forClass(
                dz.sh.hidra.modules.integration.infrastructure.persistence.entity.IntegrationJobRunJpaEntity.class
        );
        verify(runRepository).saveAndFlush(captor.capture());
        assertThat(captor.getValue().runNumber()).isZero();
        assertThat(saved.runNumber()).isEqualTo(7L);
    }

    @Test
    void inactiveJobAndDisallowedManualRunFailClosed() {
        var runRepository = mock(IntegrationJobRunJpaRepository.class);
        var jobRepository = mock(IntegrationJobDefinitionJpaRepository.class);
        var connectorRepository = mock(ConnectorInstanceJpaRepository.class);
        var mappingRepository = mock(IntegrationMappingProfileJpaRepository.class);
        var catalogRepository = mock(IntegrationCatalogEntryJpaRepository.class);
        var adapter = new JpaIntegrationJobRunRepositoryAdapter(
                runRepository,
                jobRepository,
                connectorRepository,
                mappingRepository,
                catalogRepository
        );
        var model = run(
                0, JobTriggerType.MANUAL, "actor-1", JobRunStatus.RUNNING,
                null, 0, 0, 0, 0, 0, 0
        );

        when(jobRepository.findById("job-1")).thenReturn(Optional.of(job(false, true)));
        assertThatThrownBy(() -> adapter.save(model))
                .isInstanceOf(InvalidIntegrationValueException.class)
                .hasMessageContaining("inactive");
        verify(runRepository, never()).saveAndFlush(any());

        when(jobRepository.findById("job-1")).thenReturn(Optional.of(job(true, false)));
        when(catalogRepository.findById("job-type-1")).thenReturn(Optional.of(
                new IntegrationCatalogEntryJpaEntity(
                        "job-type-1", "JOB_TYPE", "HEALTH_CHECK",
                        true, 1, true, NOW, NOW
                )
        ));
        assertThatThrownBy(() -> adapter.save(model))
                .isInstanceOf(InvalidIntegrationValueException.class)
                .hasMessageContaining("does not allow MANUAL");
    }

    @Test
    void migrationOwnsRunNumberCountersEligibilityAndLifecycle() throws Exception {
        String sql = Files.readString(Path.of(
                "src/main/resources/db/migration/V20261004_014__hmr_014_integration_integration_job_run.sql"
        ));

        assertThat(sql).contains("hidra_integration_job_run_sequence");
        assertThat(sql).contains("uk_hmr014_integration_job_run_number");
        assertThat(sql).contains("accepted_count::numeric");
        assertThat(sql).contains("completed_at IS NULL OR completed_at >= started_at");
        assertThat(sql).contains("MANUAL IntegrationJobRun requires triggered_by_actor_id");
        assertThat(sql).contains("Automated IntegrationJobRun requires an active ConnectorInstance");
        assertThat(sql).contains("IMPORT/SYNC requires a mapping profile");
        assertThat(sql).contains("Terminal IntegrationJobRun status is immutable");
    }

    private static IntegrationJobDefinitionJpaEntity job(boolean active, boolean manualAllowed) {
        return new IntegrationJobDefinitionJpaEntity(
                "job-1",
                "JOB",
                "Tâche",
                null,
                null,
                "connector-1",
                null,
                "job-type-1",
                IntegrationDirection.BIDIRECTIONAL,
                null,
                null,
                manualAllowed,
                null,
                active,
                NOW,
                NOW
        );
    }

    private static IntegrationJobRun run(
            long runNumber,
            JobTriggerType triggerType,
            String actorId,
            JobRunStatus status,
            Instant completedAt,
            long received,
            long mapped,
            long accepted,
            long rejected,
            long deadLetter,
            long retry
    ) {
        return new IntegrationJobRun(
                "run-1",
                "job-1",
                runNumber,
                triggerType,
                actorId,
                status,
                "correlation-1",
                NOW,
                completedAt,
                received,
                mapped,
                accepted,
                rejected,
                deadLetter,
                retry,
                null,
                NOW,
                NOW
        );
    }
}
