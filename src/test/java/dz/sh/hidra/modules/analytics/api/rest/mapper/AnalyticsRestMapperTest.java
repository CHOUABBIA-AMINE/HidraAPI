/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsRestMapperTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Test
 * @Layer       : API Test
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.api.rest.mapper
 *
 * @Description : Verifies generated exact analytics boundary mappings preserve every component.
 *
 */
package dz.sh.hidra.modules.analytics.api.rest.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dz.sh.hidra.modules.analytics.api.rest.request.CreateAnalyticsDatasetRequest;
import dz.sh.hidra.modules.analytics.api.rest.request.CreateAnalyticsInsightRequest;
import dz.sh.hidra.modules.analytics.api.rest.request.RunMetricEvaluationRequest;
import dz.sh.hidra.modules.analytics.api.rest.request.RunProjectionRequest;
import dz.sh.hidra.modules.analytics.api.rest.response.AnalyticsDatasetResponse;
import dz.sh.hidra.modules.analytics.api.rest.response.AnalyticsInsightResponse;
import dz.sh.hidra.modules.analytics.api.rest.response.AnalyticsProjectionRunResponse;
import dz.sh.hidra.modules.analytics.api.rest.response.MetricEvaluationRunResponse;
import dz.sh.hidra.modules.analytics.application.command.CreateAnalyticsDatasetCommand;
import dz.sh.hidra.modules.analytics.application.command.CreateAnalyticsInsightCommand;
import dz.sh.hidra.modules.analytics.application.command.RunMetricEvaluationCommand;
import dz.sh.hidra.modules.analytics.application.command.RunProjectionCommand;
import dz.sh.hidra.modules.analytics.application.dto.AnalyticsDatasetSummaryDto;
import dz.sh.hidra.modules.analytics.application.dto.AnalyticsInsightSummaryDto;
import dz.sh.hidra.modules.analytics.application.dto.AnalyticsProjectionRunSummaryDto;
import dz.sh.hidra.modules.analytics.application.dto.MetricEvaluationRunSummaryDto;
import dz.sh.hidra.modules.analytics.domain.value.AnalyticsDatasetType;
import dz.sh.hidra.modules.analytics.domain.value.AnalyticsInsightStatus;
import dz.sh.hidra.modules.analytics.domain.value.AnalyticsLineageStatus;
import dz.sh.hidra.modules.analytics.domain.value.AnalyticsQualityStatus;
import dz.sh.hidra.modules.analytics.domain.value.AnalyticsRefreshMode;
import dz.sh.hidra.modules.analytics.domain.value.AnalyticsRunMode;
import dz.sh.hidra.modules.analytics.domain.value.AnalyticsRunStatus;
import java.math.BigDecimal;
import java.time.Instant;
import org.junit.jupiter.api.Test;

class AnalyticsRestMapperTest {

    @Test
    void generatedRequestMappingsPreserveExactContracts() {
        Instant periodStart = Instant.parse("2026-09-28T00:00:00Z");
        Instant periodEnd = Instant.parse("2026-09-28T12:00:00Z");

        CreateAnalyticsDatasetRequest datasetRequest = new CreateAnalyticsDatasetRequest(
                "DS-001",
                "مجموعة بيانات",
                "Jeu de données",
                "Dataset",
                "subject-1",
                AnalyticsDatasetType.TIME_SERIES,
                AnalyticsRefreshMode.INCREMENTAL,
                "v1",
                "telemetry"
        );
        assertEquals(
                new CreateAnalyticsDatasetCommand(
                        "DS-001",
                        "مجموعة بيانات",
                        "Jeu de données",
                        "Dataset",
                        "subject-1",
                        AnalyticsDatasetType.TIME_SERIES,
                        AnalyticsRefreshMode.INCREMENTAL,
                        "v1",
                        "telemetry"
                ),
                AnalyticsRestMapper.toCommand(datasetRequest)
        );

        CreateAnalyticsInsightRequest insightRequest = new CreateAnalyticsInsightRequest(
                "TREND",
                "subject-1",
                "PIPELINE",
                "pipe-1",
                "Pressure trend",
                "Pressure is trending upward.",
                "severity-2",
                new BigDecimal("0.87"),
                "projection-1",
                "trend-1",
                "model-run-1"
        );
        assertEquals(
                new CreateAnalyticsInsightCommand(
                        "TREND",
                        "subject-1",
                        "PIPELINE",
                        "pipe-1",
                        "Pressure trend",
                        "Pressure is trending upward.",
                        "severity-2",
                        new BigDecimal("0.87"),
                        "projection-1",
                        "trend-1",
                        "model-run-1"
                ),
                AnalyticsRestMapper.toCommand(insightRequest)
        );

        RunProjectionRequest projectionRequest = new RunProjectionRequest(
                "projection-definition-1",
                AnalyticsRunMode.BACKFILL,
                periodStart,
                periodEnd,
                "corr-1"
        );
        assertEquals(
                new RunProjectionCommand(
                        "projection-definition-1",
                        AnalyticsRunMode.BACKFILL,
                        periodStart,
                        periodEnd,
                        "corr-1"
                ),
                AnalyticsRestMapper.toCommand(projectionRequest)
        );

        RunMetricEvaluationRequest metricRequest = new RunMetricEvaluationRequest(
                "metric-version-1",
                periodStart,
                periodEnd,
                "FACILITY",
                "facility-1",
                "corr-2"
        );
        assertEquals(
                new RunMetricEvaluationCommand(
                        "metric-version-1",
                        periodStart,
                        periodEnd,
                        "FACILITY",
                        "facility-1",
                        "corr-2"
                ),
                AnalyticsRestMapper.toCommand(metricRequest)
        );
    }

    @Test
    void generatedResponseMappingsPreserveExactContracts() {
        Instant periodStart = Instant.parse("2026-09-28T00:00:00Z");
        Instant periodEnd = Instant.parse("2026-09-28T12:00:00Z");

        AnalyticsDatasetSummaryDto datasetDto = new AnalyticsDatasetSummaryDto(
                "dataset-1",
                "DS-001",
                "Jeu de données",
                "subject-1",
                AnalyticsDatasetType.TIME_SERIES,
                AnalyticsRefreshMode.INCREMENTAL,
                AnalyticsLineageStatus.COMPLETE,
                AnalyticsQualityStatus.READY
        );
        assertEquals(
                new AnalyticsDatasetResponse(
                        "dataset-1",
                        "DS-001",
                        "Jeu de données",
                        "subject-1",
                        AnalyticsDatasetType.TIME_SERIES,
                        AnalyticsRefreshMode.INCREMENTAL,
                        AnalyticsLineageStatus.COMPLETE,
                        AnalyticsQualityStatus.READY
                ),
                AnalyticsRestMapper.toResponse(datasetDto)
        );

        AnalyticsInsightSummaryDto insightDto = new AnalyticsInsightSummaryDto(
                "insight-1",
                "TREND",
                "subject-1",
                "PIPELINE",
                "pipe-1",
                "Pressure trend",
                "severity-2",
                new BigDecimal("0.87"),
                AnalyticsInsightStatus.OPEN
        );
        assertEquals(
                new AnalyticsInsightResponse(
                        "insight-1",
                        "TREND",
                        "subject-1",
                        "PIPELINE",
                        "pipe-1",
                        "Pressure trend",
                        "severity-2",
                        new BigDecimal("0.87"),
                        AnalyticsInsightStatus.OPEN
                ),
                AnalyticsRestMapper.toResponse(insightDto)
        );

        AnalyticsProjectionRunSummaryDto projectionDto = new AnalyticsProjectionRunSummaryDto(
                "projection-run-1",
                "projection-definition-1",
                AnalyticsRunStatus.COMPLETED,
                AnalyticsRunMode.BACKFILL,
                periodStart,
                periodEnd,
                "corr-1"
        );
        assertEquals(
                new AnalyticsProjectionRunResponse(
                        "projection-run-1",
                        "projection-definition-1",
                        AnalyticsRunStatus.COMPLETED,
                        AnalyticsRunMode.BACKFILL,
                        periodStart,
                        periodEnd,
                        "corr-1"
                ),
                AnalyticsRestMapper.toResponse(projectionDto)
        );

        MetricEvaluationRunSummaryDto metricDto = new MetricEvaluationRunSummaryDto(
                "metric-run-1",
                "metric-version-1",
                AnalyticsRunStatus.COMPLETED_WITH_WARNINGS,
                periodStart,
                periodEnd,
                "FACILITY",
                "facility-1",
                "corr-2"
        );
        assertEquals(
                new MetricEvaluationRunResponse(
                        "metric-run-1",
                        "metric-version-1",
                        AnalyticsRunStatus.COMPLETED_WITH_WARNINGS,
                        periodStart,
                        periodEnd,
                        "FACILITY",
                        "facility-1",
                        "corr-2"
                ),
                AnalyticsRestMapper.toResponse(metricDto)
        );
    }
}
