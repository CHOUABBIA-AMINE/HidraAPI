/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
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

import org.springframework.stereotype.Service;

import dz.sh.hidra.modules.analytics.application.command.CreateAnalyticsDatasetCommand;
import dz.sh.hidra.modules.analytics.application.command.CreateAnalyticsInsightCommand;
import dz.sh.hidra.modules.analytics.application.command.FinalizeMetricEvaluationRunCommand;
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
import dz.sh.hidra.modules.analytics.application.port.out.MetricEvaluationScopeResolverPort;
import dz.sh.hidra.modules.analytics.domain.model.AnalyticsDataset;
import dz.sh.hidra.modules.analytics.domain.model.AnalyticsInsight;
import dz.sh.hidra.modules.analytics.domain.model.AnalyticsProjectionRun;
import dz.sh.hidra.modules.analytics.domain.exception.InvalidAnalyticsValueException;
import dz.sh.hidra.modules.analytics.domain.model.MetricEvaluationRun;
import dz.sh.hidra.modules.analytics.domain.value.AnalyticsId;
import dz.sh.hidra.modules.analytics.domain.value.AnalyticsInsightStatus;
import dz.sh.hidra.modules.analytics.domain.value.AnalyticsLineageStatus;
import dz.sh.hidra.modules.analytics.domain.value.AnalyticsQualityStatus;
import dz.sh.hidra.modules.analytics.domain.value.AnalyticsRunStatus;

import java.time.Instant;
import java.util.Locale;
import java.util.Objects;

/**
 * Application service for analytics datasets, projections, metrics, and insights.
 */
@Service
public final class AnalyticsApplicationService implements AnalyticsDatasetUseCase, AnalyticsProjectionUseCase, MetricEvaluationUseCase, AnalyticsInsightUseCase {

    private final AnalyticsDatasetRepositoryPort datasetRepositoryPort;
    private final AnalyticsProjectionRunRepositoryPort projectionRunRepositoryPort;
    private final MetricEvaluationRunRepositoryPort metricEvaluationRunRepositoryPort;
    private final MetricEvaluationScopeResolverPort metricEvaluationScopeResolverPort;
    private final AnalyticsInsightRepositoryPort insightRepositoryPort;

    public AnalyticsApplicationService(
            AnalyticsDatasetRepositoryPort datasetRepositoryPort,
            AnalyticsProjectionRunRepositoryPort projectionRunRepositoryPort,
            MetricEvaluationRunRepositoryPort metricEvaluationRunRepositoryPort,
            MetricEvaluationScopeResolverPort metricEvaluationScopeResolverPort,
            AnalyticsInsightRepositoryPort insightRepositoryPort
    ) {
        this.datasetRepositoryPort = Objects.requireNonNull(datasetRepositoryPort, "Analytics dataset repository port must not be null.");
        this.projectionRunRepositoryPort = Objects.requireNonNull(projectionRunRepositoryPort, "Analytics projection run repository port must not be null.");
        this.metricEvaluationRunRepositoryPort = Objects.requireNonNull(metricEvaluationRunRepositoryPort, "Metric evaluation run repository port must not be null.");
        this.metricEvaluationScopeResolverPort = Objects.requireNonNull(
                metricEvaluationScopeResolverPort,
                "Metric evaluation scope resolver port must not be null."
        );
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

        String scopeType = normalizeScopeType(command.scopeType());
        String scopeId = normalize(command.scopeId());
        var resolution = metricEvaluationScopeResolverPort.resolve(scopeType, scopeId);
        if (!resolution.supported()) {
            throw new InvalidAnalyticsValueException(
                    "Unsupported MetricEvaluationRun scope type: " + scopeType + "."
            );
        }
        if (resolution.identifierRequired() && scopeId == null) {
            throw new InvalidAnalyticsValueException(
                    "MetricEvaluationRun scope id is required for scope type " + scopeType + "."
            );
        }
        if (!resolution.exists()) {
            throw new InvalidAnalyticsValueException(
                    "MetricEvaluationRun scope target does not exist: " + scopeType + "/" + scopeId + "."
            );
        }

        Instant now = Instant.now();
        MetricEvaluationRun run = new MetricEvaluationRun(
                AnalyticsId.newId().value(),
                command.metricDefinitionVersionId(),
                AnalyticsRunStatus.RUNNING,
                command.periodStart(),
                command.periodEnd(),
                scopeType,
                scopeId,
                now,
                null,
                0L,
                0L,
                null,
                null,
                normalize(command.correlationId()),
                now
        );
        return AnalyticsApplicationMapper.toSummary(metricEvaluationRunRepositoryPort.save(run));
    }

    @Override
    public MetricEvaluationRunSummaryDto finalizeMetricEvaluationRun(
            FinalizeMetricEvaluationRunCommand command
    ) {
        Objects.requireNonNull(command, "Finalize metric evaluation run command must not be null.");

        MetricEvaluationRun existing = metricEvaluationRunRepositoryPort.findById(command.runId())
                .orElseThrow(() -> new InvalidAnalyticsValueException(
                        "MetricEvaluationRun does not exist: " + command.runId() + "."
                ));
        if (existing.terminalStatus()) {
            throw new InvalidAnalyticsValueException(
                    "Terminal MetricEvaluationRun cannot be finalized again."
            );
        }

        Instant completedAt = Instant.now();
        MetricEvaluationRun finalized = new MetricEvaluationRun(
                existing.id(),
                existing.metricDefinitionVersionId(),
                command.terminalStatus(),
                existing.periodStart(),
                existing.periodEnd(),
                existing.scopeType(),
                existing.scopeId(),
                existing.startedAt(),
                completedAt,
                command.recordsRead() == null ? existing.recordsRead() : command.recordsRead(),
                command.recordsProduced() == null ? existing.recordsProduced() : command.recordsProduced(),
                command.errorCode(),
                command.errorMessage(),
                command.correlationId() == null ? existing.correlationId() : command.correlationId(),
                existing.createdAt()
        );
        finalized.validateTransitionFrom(existing.runStatus());
        return AnalyticsApplicationMapper.toSummary(metricEvaluationRunRepositoryPort.save(finalized));
    }

    private static String normalizeScopeType(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidAnalyticsValueException("MetricEvaluationRun scope type must not be blank.");
        }
        return value.trim().toUpperCase(Locale.ROOT);
    }

    private static String normalize(String value) {
        return value == null || value.isBlank() ? null : value.trim();
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
