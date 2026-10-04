/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsInsightSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Analytics Test
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.semantic
 *
 * @Description : Verifies HMR-023 AnalyticsInsight catalog, scope and lineage semantics.
 *
 */
package dz.sh.hidra.modules.analytics.semantic;

import dz.sh.hidra.modules.analytics.application.command.CreateAnalyticsInsightCommand;
import dz.sh.hidra.modules.analytics.domain.exception.InvalidAnalyticsValueException;
import dz.sh.hidra.modules.analytics.domain.model.AnalyticsInsight;
import dz.sh.hidra.modules.analytics.domain.value.AnalyticsInsightStatus;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.adapter.JpaAnalyticsInsightRepositoryAdapter;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.repository.AnalyticsInsightJpaRepository;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AnalyticsInsightSemanticRemediationTest {

    private static final Instant NOW = Instant.parse("2026-10-04T00:00:00Z");

    @Test
    void domainRequiresGovernedInsightTypeAndScopeType() {
        assertThatThrownBy(() -> insight(" ", "PIPELINE", null, null, null, null))
                .isInstanceOf(InvalidAnalyticsValueException.class)
                .hasMessageContaining("insight type must not be blank");

        assertThatThrownBy(() -> insight("PERFORMANCE_DEGRADATION", " ", null, null, null, null))
                .isInstanceOf(InvalidAnalyticsValueException.class)
                .hasMessageContaining("scope type must not be blank");
    }

    @Test
    void commandFailsClosedBeforeTheServiceCanInventMissingClassificationOrScope() {
        assertThatThrownBy(() -> command(null, "PIPELINE"))
                .isInstanceOf(InvalidAnalyticsValueException.class)
                .hasMessageContaining("insight type must not be blank");

        assertThatThrownBy(() -> command("PERFORMANCE_DEGRADATION", null))
                .isInstanceOf(InvalidAnalyticsValueException.class)
                .hasMessageContaining("scope type must not be blank");

        CreateAnalyticsInsightCommand command =
                command(" PERFORMANCE_DEGRADATION ", " PIPELINE ");
        assertThat(command.insightType()).isEqualTo("PERFORMANCE_DEGRADATION");
        assertThat(command.scopeType()).isEqualTo("PIPELINE");
    }

    @Test
    void repositoryAdapterRejectsUngovernedTypeWrongSeverityFamilyAndDanglingSources() {
        AnalyticsInsightJpaRepository repository = mock(AnalyticsInsightJpaRepository.class);
        JpaAnalyticsInsightRepositoryAdapter adapter =
                new JpaAnalyticsInsightRepositoryAdapter(repository);

        AnalyticsInsight base = insight(
                "PERFORMANCE_DEGRADATION",
                "PIPELINE",
                null,
                null,
                null,
                null
        );

        when(repository.existsActiveCatalogCode("INSIGHT_TYPE", "PERFORMANCE_DEGRADATION"))
                .thenReturn(false);
        assertThatThrownBy(() -> adapter.save(base))
                .isInstanceOf(InvalidAnalyticsValueException.class)
                .hasMessageContaining("active INSIGHT_TYPE");

        when(repository.existsActiveCatalogCode("INSIGHT_TYPE", "PERFORMANCE_DEGRADATION"))
                .thenReturn(true);
        AnalyticsInsight badSeverity = insight(
                "PERFORMANCE_DEGRADATION",
                "PIPELINE",
                "severity-1",
                null,
                null,
                null
        );
        when(repository.existsActiveCatalogEntryId("ANALYTICS_SEVERITY", "severity-1"))
                .thenReturn(false);
        assertThatThrownBy(() -> adapter.save(badSeverity))
                .isInstanceOf(InvalidAnalyticsValueException.class)
                .hasMessageContaining("ANALYTICS_SEVERITY");

        AnalyticsInsight danglingSnapshot = insight(
                "PERFORMANCE_DEGRADATION",
                "PIPELINE",
                null,
                "snapshot-1",
                null,
                null
        );
        when(repository.existsProjectionSnapshot("snapshot-1")).thenReturn(false);
        assertThatThrownBy(() -> adapter.save(danglingSnapshot))
                .isInstanceOf(InvalidAnalyticsValueException.class)
                .hasMessageContaining("projection snapshot does not exist");
    }

    @Test
    void migrationProtectsCatalogFamiliesAndAllDirectSourceLineage() throws Exception {
        String sql = Files.readString(Path.of(
                "src/main/resources/db/migration/V20261004_023__hmr_023_analytics_analytics_insight.sql"
        ));

        assertThat(sql).contains("ck_hmr023_analytics_insight_type_nonblank");
        assertThat(sql).contains("ck_hmr023_analytics_insight_scope_type_nonblank");
        assertThat(sql).contains("catalog_name = 'INSIGHT_TYPE'");
        assertThat(sql).contains("catalog_name = 'ANALYTICS_SEVERITY'");
        assertThat(sql).contains("fk_hmr023_analytics_insight_projection_snapshot");
        assertThat(sql).contains("fk_hmr023_analytics_insight_trend_analysis");
        assertThat(sql).contains("fk_hmr023_analytics_insight_model_run");
        assertThat(sql).contains("trg_hmr023_analytics_insight_catalogs");
    }

    private static CreateAnalyticsInsightCommand command(String insightType, String scopeType) {
        return new CreateAnalyticsInsightCommand(
                insightType,
                "subject-area-1",
                scopeType,
                null,
                "Title",
                "Summary",
                null,
                null,
                null,
                null,
                null
        );
    }

    private static AnalyticsInsight insight(
            String insightType,
            String scopeType,
            String severityId,
            String sourceProjectionSnapshotId,
            String sourceTrendAnalysisId,
            String sourceModelRunId
    ) {
        return new AnalyticsInsight(
                "insight-1",
                insightType,
                "subject-area-1",
                scopeType,
                null,
                "Title",
                "Summary",
                severityId,
                null,
                sourceProjectionSnapshotId,
                sourceTrendAnalysisId,
                sourceModelRunId,
                AnalyticsInsightStatus.OPEN,
                NOW,
                NOW
        );
    }
}
