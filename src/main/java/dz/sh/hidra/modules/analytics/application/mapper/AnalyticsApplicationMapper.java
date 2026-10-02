/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsApplicationMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.application.mapper
 *
 * @Description : Maps analytics domain models to DTOs.
 *
 */
package dz.sh.hidra.modules.analytics.application.mapper;

import dz.sh.hidra.modules.analytics.application.dto.AnalyticsDatasetSummaryDto;
import dz.sh.hidra.modules.analytics.application.dto.AnalyticsInsightSummaryDto;
import dz.sh.hidra.modules.analytics.application.dto.AnalyticsProjectionRunSummaryDto;
import dz.sh.hidra.modules.analytics.application.dto.MetricEvaluationRunSummaryDto;
import dz.sh.hidra.modules.analytics.domain.model.AnalyticsDataset;
import dz.sh.hidra.modules.analytics.domain.model.AnalyticsInsight;
import dz.sh.hidra.modules.analytics.domain.model.AnalyticsProjectionRun;
import dz.sh.hidra.modules.analytics.domain.model.MetricEvaluationRun;

/**
 * Maps analytics domain models to DTOs.
 */
public final class AnalyticsApplicationMapper {

    private AnalyticsApplicationMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static AnalyticsDatasetSummaryDto toSummary(AnalyticsDataset dataset) {
        return new AnalyticsDatasetSummaryDto(dataset.id(), dataset.code(), dataset.nameFr(), dataset.subjectAreaId(), dataset.datasetType(), dataset.refreshMode(), dataset.lineageStatus(), dataset.qualityStatus());
    }

    public static AnalyticsProjectionRunSummaryDto toSummary(AnalyticsProjectionRun run) {
        return new AnalyticsProjectionRunSummaryDto(run.id(), run.projectionDefinitionId(), run.runStatus(), run.runMode(), run.periodStart(), run.periodEnd(), run.correlationId());
    }

    public static MetricEvaluationRunSummaryDto toSummary(MetricEvaluationRun run) {
        return new MetricEvaluationRunSummaryDto(run.id(), run.metricDefinitionVersionId(), run.runStatus(), run.periodStart(), run.periodEnd(), run.scopeType(), run.scopeId(), run.correlationId());
    }

    public static AnalyticsInsightSummaryDto toSummary(AnalyticsInsight insight) {
        return new AnalyticsInsightSummaryDto(insight.id(), insight.insightType(), insight.subjectAreaId(), insight.scopeType(), insight.scopeId(), insight.title(), insight.severityId(), insight.confidenceScore(), insight.status());
    }
}
