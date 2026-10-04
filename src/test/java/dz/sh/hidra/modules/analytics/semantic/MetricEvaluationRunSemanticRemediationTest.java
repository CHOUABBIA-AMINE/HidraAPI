/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MetricEvaluationRunSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Analytics Test
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.semantic
 *
 * @Description : Verifies HMR-016 MetricEvaluationRun semantic remediation.
 *
 */
package dz.sh.hidra.modules.analytics.semantic;

import dz.sh.hidra.modules.analytics.application.command.FinalizeMetricEvaluationRunCommand;
import dz.sh.hidra.modules.analytics.application.command.RunMetricEvaluationCommand;
import dz.sh.hidra.modules.analytics.application.port.out.AnalyticsDatasetRepositoryPort;
import dz.sh.hidra.modules.analytics.application.port.out.AnalyticsInsightRepositoryPort;
import dz.sh.hidra.modules.analytics.application.port.out.AnalyticsProjectionRunRepositoryPort;
import dz.sh.hidra.modules.analytics.application.port.out.MetricEvaluationRunRepositoryPort;
import dz.sh.hidra.modules.analytics.application.port.out.MetricEvaluationScopeResolverPort;
import dz.sh.hidra.modules.analytics.application.service.AnalyticsApplicationService;
import dz.sh.hidra.modules.analytics.domain.exception.InvalidAnalyticsValueException;
import dz.sh.hidra.modules.analytics.domain.model.MetricEvaluationRun;
import dz.sh.hidra.modules.analytics.domain.value.AnalyticsRunStatus;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.adapter.JpaMetricEvaluationRunRepositoryAdapter;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.entity.MetricDefinitionVersionJpaEntity;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.repository.MetricDefinitionVersionJpaRepository;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.repository.MetricEvaluationRunJpaRepository;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MetricEvaluationRunSemanticRemediationTest {

    private static final Instant START = Instant.parse("2026-10-04T08:00:00Z");
    private static final Instant END = Instant.parse("2026-10-04T09:00:00Z");

    @Test
    void domainRequiresScopeTypeTerminalCompletionAndFailedEvidence() {
        assertThatThrownBy(() -> run(" ", "scope-1", AnalyticsRunStatus.RUNNING, null, null, null, "corr"))
                .isInstanceOf(InvalidAnalyticsValueException.class)
                .hasMessageContaining("scope type");

        assertThatThrownBy(() -> run("PIPELINE", "scope-1", AnalyticsRunStatus.COMPLETED, null, null, null, "corr"))
                .isInstanceOf(InvalidAnalyticsValueException.class)
                .hasMessageContaining("completedAt");

        assertThatThrownBy(() -> run("PIPELINE", "scope-1", AnalyticsRunStatus.FAILED, END, null, null, null))
                .isInstanceOf(InvalidAnalyticsValueException.class)
                .hasMessageContaining("diagnostic or correlation");
    }

    @Test
    void applicationFailsClosedForUnsupportedAndMissingOwnerScopes() {
        var runRepository = mock(MetricEvaluationRunRepositoryPort.class);
        var scopeResolver = mock(MetricEvaluationScopeResolverPort.class);
        var service = service(runRepository, scopeResolver);
        var command = new RunMetricEvaluationCommand(
                "version-1", START, END, "PRODUCT", "product-1", "corr"
        );

        when(scopeResolver.resolve("PRODUCT", "product-1"))
                .thenReturn(MetricEvaluationScopeResolverPort.Resolution.unsupported());

        assertThatThrownBy(() -> service.runMetricEvaluation(command))
                .isInstanceOf(InvalidAnalyticsValueException.class)
                .hasMessageContaining("Unsupported");
        verify(runRepository, never()).save(any());

        var pipeline = new RunMetricEvaluationCommand(
                "version-1", START, END, "pipeline", "missing", "corr"
        );
        when(scopeResolver.resolve("PIPELINE", "missing"))
                .thenReturn(MetricEvaluationScopeResolverPort.Resolution.ownerBacked(false));

        assertThatThrownBy(() -> service.runMetricEvaluation(pipeline))
                .isInstanceOf(InvalidAnalyticsValueException.class)
                .hasMessageContaining("does not exist");
    }

    @Test
    void persistenceRejectsMetricVersionOutsideRequestedPeriod() {
        var runJpaRepository = mock(MetricEvaluationRunJpaRepository.class);
        var versionRepository = mock(MetricDefinitionVersionJpaRepository.class);
        var adapter = new JpaMetricEvaluationRunRepositoryAdapter(
                runJpaRepository,
                versionRepository
        );
        when(runJpaRepository.findById("run-1")).thenReturn(Optional.empty());
        when(versionRepository.findById("version-1")).thenReturn(Optional.of(
                new MetricDefinitionVersionJpaEntity(
                        "version-1",
                        "definition-1",
                        1,
                        "formula",
                        null,
                        START.plusSeconds(60),
                        null,
                        null,
                        START
                )
        ));

        assertThatThrownBy(() -> adapter.save(
                run("PIPELINE", "pipeline-1", AnalyticsRunStatus.RUNNING, null, 0L, 0L, "corr")
        )).isInstanceOf(InvalidAnalyticsValueException.class)
                .hasMessageContaining("starts before");

        verify(runJpaRepository, never()).save(any());
    }

    @Test
    void governedFinalizationPreservesEvidenceAndRejectsRepeatTerminalFinalization() {
        var runRepository = mock(MetricEvaluationRunRepositoryPort.class);
        var scopeResolver = mock(MetricEvaluationScopeResolverPort.class);
        var service = service(runRepository, scopeResolver);
        var existing = run(
                "PIPELINE",
                "pipeline-1",
                AnalyticsRunStatus.RUNNING,
                null,
                0L,
                0L,
                "corr-start"
        );
        when(runRepository.findById("run-1")).thenReturn(Optional.of(existing));
        when(runRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        var result = service.finalizeMetricEvaluationRun(
                new FinalizeMetricEvaluationRunCommand(
                        "run-1",
                        AnalyticsRunStatus.FAILED,
                        10L,
                        2L,
                        "E42",
                        "Calculation failed",
                        null
                )
        );

        assertThat(result.runStatus()).isEqualTo(AnalyticsRunStatus.FAILED);

        var terminal = run(
                "PIPELINE",
                "pipeline-1",
                AnalyticsRunStatus.COMPLETED,
                END,
                10L,
                10L,
                "corr"
        );
        when(runRepository.findById("run-1")).thenReturn(Optional.of(terminal));

        assertThatThrownBy(() -> service.finalizeMetricEvaluationRun(
                new FinalizeMetricEvaluationRunCommand(
                        "run-1",
                        AnalyticsRunStatus.CANCELLED,
                        null,
                        null,
                        null,
                        null,
                        "corr"
                )
        )).isInstanceOf(InvalidAnalyticsValueException.class)
                .hasMessageContaining("cannot be finalized again");
    }

    @Test
    void migrationEnforcesPeriodTerminalAuditAndNoCrossModuleScopeForeignKey() throws Exception {
        String sql = Files.readString(Path.of(
                "src/main/resources/db/migration/V20261004_016__hmr_016_analytics_metric_evaluation_run.sql"
        ));

        assertThat(sql).contains("ck_hmr016_metric_run_scope_type");
        assertThat(sql).contains("ck_hmr016_metric_run_scope_id");
        assertThat(sql).contains("hmr016_validate_metric_version_period");
        assertThat(sql).contains("COMPLETED_WITH_WARNINGS");
        assertThat(sql).contains("ck_hmr016_metric_run_failed_evidence");
        assertThat(sql).contains("Terminal MetricEvaluationRun outcome cannot be changed or reopened");
        assertThat(sql).doesNotContain("REFERENCES hidra_topology_");
        assertThat(sql).doesNotContain("REFERENCES hidra_org_");
    }

    private static AnalyticsApplicationService service(
            MetricEvaluationRunRepositoryPort runRepository,
            MetricEvaluationScopeResolverPort scopeResolver
    ) {
        return new AnalyticsApplicationService(
                mock(AnalyticsDatasetRepositoryPort.class),
                mock(AnalyticsProjectionRunRepositoryPort.class),
                runRepository,
                scopeResolver,
                mock(AnalyticsInsightRepositoryPort.class)
        );
    }

    private static MetricEvaluationRun run(
            String scopeType,
            String scopeId,
            AnalyticsRunStatus status,
            Instant completedAt,
            Long recordsRead,
            Long recordsProduced,
            String correlationId
    ) {
        return new MetricEvaluationRun(
                "run-1",
                "version-1",
                status,
                START,
                END,
                scopeType,
                scopeId,
                START,
                completedAt,
                recordsRead,
                recordsProduced,
                null,
                null,
                correlationId,
                START
        );
    }
}
