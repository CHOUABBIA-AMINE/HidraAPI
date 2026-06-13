/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.api.rest.mapper
 *
 * @Description : Maps analytics REST models to application models.
 *
 */
package dz.sh.hidra.modules.analytics.api.rest.mapper;
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

/**
 * Maps analytics REST models to application models.
 */
public final class AnalyticsRestMapper {

    private AnalyticsRestMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static CreateAnalyticsDatasetCommand toCommand(CreateAnalyticsDatasetRequest request) {
        return new CreateAnalyticsDatasetCommand(
                request.code(),
                request.nameAr(),
                request.nameFr(),
                request.nameEn(),
                request.subjectAreaId(),
                request.datasetType(),
                request.refreshMode(),
                request.schemaVersion(),
                request.createdFrom()
        );
    }

    public static CreateAnalyticsInsightCommand toCommand(CreateAnalyticsInsightRequest request) {
        return new CreateAnalyticsInsightCommand(
                request.insightType(),
                request.subjectAreaId(),
                request.scopeType(),
                request.scopeId(),
                request.title(),
                request.summary(),
                request.severityId(),
                request.confidenceScore(),
                request.sourceProjectionSnapshotId(),
                request.sourceTrendAnalysisId(),
                request.sourceModelRunId()
        );
    }

    public static RunProjectionCommand toCommand(RunProjectionRequest request) {
        return new RunProjectionCommand(
                request.projectionDefinitionId(),
                request.runMode(),
                request.periodStart(),
                request.periodEnd(),
                request.correlationId()
        );
    }

    public static RunMetricEvaluationCommand toCommand(RunMetricEvaluationRequest request) {
        return new RunMetricEvaluationCommand(
                request.metricDefinitionVersionId(),
                request.periodStart(),
                request.periodEnd(),
                request.scopeType(),
                request.scopeId(),
                request.correlationId()
        );
    }

    public static AnalyticsDatasetResponse toResponse(AnalyticsDatasetSummaryDto dto) {
        return new AnalyticsDatasetResponse(
                dto.id(),
                dto.code(),
                dto.nameFr(),
                dto.subjectAreaId(),
                dto.datasetType(),
                dto.refreshMode(),
                dto.lineageStatus(),
                dto.qualityStatus()
        );
    }

    public static AnalyticsInsightResponse toResponse(AnalyticsInsightSummaryDto dto) {
        return new AnalyticsInsightResponse(
                dto.id(),
                dto.insightType(),
                dto.subjectAreaId(),
                dto.scopeType(),
                dto.scopeId(),
                dto.title(),
                dto.severityId(),
                dto.confidenceScore(),
                dto.status()
        );
    }

    public static AnalyticsProjectionRunResponse toResponse(AnalyticsProjectionRunSummaryDto dto) {
        return new AnalyticsProjectionRunResponse(
                dto.id(),
                dto.projectionDefinitionId(),
                dto.runStatus(),
                dto.runMode(),
                dto.periodStart(),
                dto.periodEnd(),
                dto.correlationId()
        );
    }

    public static MetricEvaluationRunResponse toResponse(MetricEvaluationRunSummaryDto dto) {
        return new MetricEvaluationRunResponse(
                dto.id(),
                dto.metricDefinitionVersionId(),
                dto.runStatus(),
                dto.periodStart(),
                dto.periodEnd(),
                dto.scopeType(),
                dto.scopeId(),
                dto.correlationId()
        );
    }
}
