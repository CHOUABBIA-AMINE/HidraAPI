/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsProjectionRunSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Analytics Test
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.semantic
 *
 * @Description : Verifies HMR-024 projection-run terminal evidence and reproducibility semantics.
 *
 */
package dz.sh.hidra.modules.analytics.semantic;

import dz.sh.hidra.modules.analytics.domain.exception.InvalidAnalyticsValueException;
import dz.sh.hidra.modules.analytics.domain.model.AnalyticsProjectionRun;
import dz.sh.hidra.modules.analytics.domain.value.AnalyticsRunMode;
import dz.sh.hidra.modules.analytics.domain.value.AnalyticsRunStatus;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.adapter.JpaAnalyticsProjectionRunRepositoryAdapter;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.entity.AnalyticsProjectionRunJpaEntity;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.repository.AnalyticsProjectionRunJpaRepository;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AnalyticsProjectionRunSemanticRemediationTest {

    private static final Instant NOW = Instant.parse("2026-10-04T00:00:00Z");

    @Test
    void failedRunRequiresAtLeastOneDiagnostic() {
        assertThatThrownBy(() -> run(
                AnalyticsRunStatus.FAILED,
                null,
                null,
                null,
                "version-1"
        ))
                .isInstanceOf(InvalidAnalyticsValueException.class)
                .hasMessageContaining("error code or error message");

        assertThat(run(
                AnalyticsRunStatus.FAILED,
                null,
                "E-1",
                null,
                "version-1"
        ).errorCode()).isEqualTo("E-1");
    }

    @Test
    void completedStatusesRequireSourceWatermark() {
        assertThatThrownBy(() -> run(
                AnalyticsRunStatus.COMPLETED,
                " ",
                null,
                null,
                "version-1"
        ))
                .isInstanceOf(InvalidAnalyticsValueException.class)
                .hasMessageContaining("source watermark");

        assertThatThrownBy(() -> run(
                AnalyticsRunStatus.COMPLETED_WITH_WARNINGS,
                null,
                null,
                null,
                "version-1"
        ))
                .isInstanceOf(InvalidAnalyticsValueException.class)
                .hasMessageContaining("source watermark");

        assertThat(run(
                AnalyticsRunStatus.COMPLETED,
                "wm-42",
                null,
                null,
                "version-1"
        ).successfulStatus()).isTrue();
    }

    @Test
    void adapterCapturesDefinitionVersionOnFirstPersistence() {
        AnalyticsProjectionRunJpaRepository repository =
                mock(AnalyticsProjectionRunJpaRepository.class);
        JpaAnalyticsProjectionRunRepositoryAdapter adapter =
                new JpaAnalyticsProjectionRunRepositoryAdapter(repository);

        when(repository.findById("run-1")).thenReturn(Optional.empty());
        when(repository.resolveProjectionDefinitionVersion("definition-1"))
                .thenReturn(Optional.of("20261004000000.000000-abcdef"));
        when(repository.save(any(AnalyticsProjectionRunJpaEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        AnalyticsProjectionRun saved = adapter.save(run(
                AnalyticsRunStatus.RUNNING,
                null,
                null,
                null,
                null
        ));

        assertThat(saved.projectionDefinitionVersion())
                .isEqualTo("20261004000000.000000-abcdef");
    }

    @Test
    void adapterPreservesExistingCapturedDefinitionVersion() {
        AnalyticsProjectionRunJpaRepository repository =
                mock(AnalyticsProjectionRunJpaRepository.class);
        JpaAnalyticsProjectionRunRepositoryAdapter adapter =
                new JpaAnalyticsProjectionRunRepositoryAdapter(repository);

        AnalyticsProjectionRunJpaEntity existing = new AnalyticsProjectionRunJpaEntity(
                "run-1",
                "definition-1",
                "version-original",
                AnalyticsRunStatus.RUNNING,
                AnalyticsRunMode.INCREMENTAL,
                null,
                null,
                NOW,
                null,
                null,
                0L,
                0L,
                null,
                null,
                null,
                NOW
        );

        when(repository.findById("run-1")).thenReturn(Optional.of(existing));
        when(repository.save(any(AnalyticsProjectionRunJpaEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        AnalyticsProjectionRun saved = adapter.save(run(
                AnalyticsRunStatus.RUNNING,
                null,
                null,
                null,
                "version-forged"
        ));

        assertThat(saved.projectionDefinitionVersion()).isEqualTo("version-original");
    }

    @Test
    void migrationAddsVersionAndTerminalEvidenceChecks() throws Exception {
        String sql = Files.readString(Path.of(
                "src/main/resources/db/migration/V20261004_024__hmr_024_analytics_projection_run.sql"
        ));

        assertThat(sql).contains("projection_definition_version");
        assertThat(sql).contains("jsonb_build_array");
        assertThat(sql).contains("ck_hmr024_failed_run_diagnostics");
        assertThat(sql).contains("ck_hmr024_successful_run_watermark");
        assertThat(sql).contains("'COMPLETED', 'COMPLETED_WITH_WARNINGS'");
    }

    private static AnalyticsProjectionRun run(
            AnalyticsRunStatus status,
            String sourceWatermark,
            String errorCode,
            String errorMessage,
            String projectionDefinitionVersion
    ) {
        return new AnalyticsProjectionRun(
                "run-1",
                "definition-1",
                projectionDefinitionVersion,
                status,
                AnalyticsRunMode.INCREMENTAL,
                null,
                null,
                NOW,
                null,
                sourceWatermark,
                0L,
                0L,
                errorCode,
                errorMessage,
                null,
                NOW
        );
    }
}
