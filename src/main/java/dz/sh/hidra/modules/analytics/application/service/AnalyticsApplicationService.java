/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.application.service
 *
 * @Description : Application service for analytics datasets, projections, metrics, and insights.
 *
 */
package dz.sh.hidra.modules.analytics.application.service;

import dz.sh.hidra.modules.analytics.application.command.CreateAnalyticsDatasetCommand;
import dz.sh.hidra.modules.analytics.application.command.CreateAnalyticsInsightCommand;
import dz.sh.hidra.modules.analytics.application.command.RunMetricEvaluationCommand;
import dz.sh.hidra.modules.analytics.application.command.RunProjectionCommand;
import dz.sh.hidra.modules.analytics.application.dto.AnalyticsDatasetSummaryDto;
import dz.sh.hidra.modules.analytics.application.dto.AnalyticsInsightSummaryDto;
import dz.sh.hidra.modules.analytics.application.dto.AnalyticsProjectionRunSummaryDto;
import dz.sh.hidra.modules.analytics.application.dto.MetricEvaluationRunSummaryDto;
import dz.sh.hidra.modules.analytics.application.mapper.AnalyticsApplicationMapper;
import dz.sh.hidra.modules.analytics.application.port.in.AnalyticsDatasetUseCase;
import dz.sh.hidra.modules.analytics.application.port.in.AnalyticsInsightUseCase;
import dz.sh.hidra.modules.analytics.application.port.in.AnalyticsProjectionUseCase;
import dz.sh.hidra.modules.analytics.application.port.in.MetricEvaluationUseCase;
import dz.sh.hidra.modules.analytics.application.port.out.AnalyticsDatasetRepositoryPort;
import dz.sh.hidra.modules.analytics.application.port.out.AnalyticsInsightRepositoryPort;
import dz.sh.hidra.modules.analytics.application.port.out.AnalyticsProjectionRunRepositoryPort;
import dz.sh.hidra.modules.analytics.application.port.out.MetricEvaluationRunRepositoryPort;
import dz.sh.hidra.modules.analytics.domain.model.AnalyticsDataset;
import dz.sh.hidra.modules.analytics.domain.model.AnalyticsInsight;
import dz.sh.hidra.modules.analytics.domain.model.AnalyticsProjectionRun;
import dz.sh.hidra.modules.analytics.domain.model.MetricEvaluationRun;
import dz.sh.hidra.modules.analytics.domain.value.AnalyticsId;
import dz.sh.hidra.modules.analytics.domain.value.AnalyticsInsightStatus;
import dz.sh.hidra.modules.analytics.domain.value.AnalyticsLineageStatus;
import dz.sh.hidra.modules.analytics.domain.value.AnalyticsQualityStatus;
import dz.sh.hidra.modules.analytics.domain.value.AnalyticsRunStatus;

import java.time.Instant;
import java.util.Objects;

/**
 * Application service for analytics datasets, projections, metrics, and insights.
 */
public final class AnalyticsApplicationService implements AnalyticsDatasetUseCase, AnalyticsProjectionUseCase, MetricEvaluationUseCase, AnalyticsInsightUseCase {

    private final AnalyticsDatasetRepositoryPort datasetRepositoryPort;
    private final AnalyticsProjectionRunRepositoryPort projectionRunRepositoryPort;
    private final MetricEvaluationRunRepositoryPort metricEvaluationRunRepositoryPort;
    private final AnalyticsInsightRepositoryPort insightRepositoryPort;

    public AnalyticsApplicationService(
            AnalyticsDatasetRepositoryPort datasetRepositoryPort,
            AnalyticsProjectionRunRepositoryPort projectionRunRepositoryPort,
            MetricEvaluationRunRepositoryPort metricEvaluationRunRepositoryPort,
            AnalyticsInsightRepositoryPort insightRepositoryPort
    ) {
        this.datasetRepositoryPort = Objects.requireNonNull(datasetRepositoryPort, "Analytics dataset repository port must not be null.");
        this.projectionRunRepositoryPort = Objects.requireNonNull(projectionRunRepositoryPort, "Analytics projection run repository port must not be null.");
        this.metricEvaluationRunRepositoryPort = Objects.requireNonNull(metricEvaluationRunRepositoryPort, "Metric evaluation run repository port must not be null.");
        this.insightRepositoryPort = Objects.requireNonNull(insightRepositoryPort, "Analytics insight repository port must not be null.");
    }

    @Override
    public AnalyticsDatasetSummaryDto createAnalyticsDataset(CreateAnalyticsDatasetCommand command) {
        Objects.requireNonNull(command, "Create analytics dataset command must not be null.");
        Instant now = Instant.now();
        AnalyticsDataset dataset = new AnalyticsDataset(
                AnalyticsId.newId().value(),
                command.code(),
                command.nameAr(),
                command.nameFr(),
                command.nameEn(),
                command.subjectAreaId(),
                command.datasetType(),
                command.refreshMode(),
                AnalyticsLineageStatus.DRAFT,
                AnalyticsQualityStatus.UNKNOWN,
                command.schemaVersion(),
                command.createdFrom(),
                null,
                null,
                now,
                now
        );
        return AnalyticsApplicationMapper.toSummary(datasetRepositoryPort.save(dataset));
    }

    @Override
    public AnalyticsProjectionRunSummaryDto runProjection(RunProjectionCommand command) {
        Objects.requireNonNull(command, "Run projection command must not be null.");
        Instant now = Instant.now();
        AnalyticsProjectionRun run = new AnalyticsProjectionRun(
                AnalyticsId.newId().value(),
                command.projectionDefinitionId(),
                AnalyticsRunStatus.RUNNING,
                command.runMode(),
                command.periodStart(),
                command.periodEnd(),
                now,
                null,
                null,
                0L,
                0L,
                null,
                null,
                command.correlationId(),
                now
        );
        return AnalyticsApplicationMapper.toSummary(projectionRunRepositoryPort.save(run));
    }

    @Override
    public MetricEvaluationRunSummaryDto runMetricEvaluation(RunMetricEvaluationCommand command) {
        Objects.requireNonNull(command, "Run metric evaluation command must not be null.");
        Instant now = Instant.now();
        MetricEvaluationRun run = new MetricEvaluationRun(
                AnalyticsId.newId().value(),
                command.metricDefinitionVersionId(),
                AnalyticsRunStatus.RUNNING,
                command.periodStart(),
                command.periodEnd(),
                command.scopeType(),
                command.scopeId(),
                now,
                null,
                0L,
                0L,
                null,
                null,
                command.correlationId(),
                now
        );
        return AnalyticsApplicationMapper.toSummary(metricEvaluationRunRepositoryPort.save(run));
    }

    @Override
    public AnalyticsInsightSummaryDto createAnalyticsInsight(CreateAnalyticsInsightCommand command) {
        Objects.requireNonNull(command, "Create analytics insight command must not be null.");
        Instant now = Instant.now();
        AnalyticsInsight insight = new AnalyticsInsight(
                AnalyticsId.newId().value(),
                command.insightType(),
                command.subjectAreaId(),
                command.scopeType(),
                command.scopeId(),
                command.title(),
                command.summary(),
                command.severityId(),
                command.confidenceScore(),
                command.sourceProjectionSnapshotId(),
                command.sourceTrendAnalysisId(),
                command.sourceModelRunId(),
                AnalyticsInsightStatus.OPEN,
                now,
                now
        );
        return AnalyticsApplicationMapper.toSummary(insightRepositoryPort.save(insight));
    }
}
