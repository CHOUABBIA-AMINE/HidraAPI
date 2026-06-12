/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsRestMapper
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
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
import dz.sh.hidra.modules.analytics.api.rest.response.AnalyticsDatasetResponse;
import dz.sh.hidra.modules.analytics.api.rest.response.AnalyticsInsightResponse;
import dz.sh.hidra.modules.analytics.application.command.CreateAnalyticsDatasetCommand;
import dz.sh.hidra.modules.analytics.application.command.CreateAnalyticsInsightCommand;
import dz.sh.hidra.modules.analytics.application.dto.AnalyticsDatasetSummaryDto;
import dz.sh.hidra.modules.analytics.application.dto.AnalyticsInsightSummaryDto;

/**
 * Maps analytics REST models to application models.
 */
public final class AnalyticsRestMapper {

    private AnalyticsRestMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static CreateAnalyticsDatasetCommand toCommand(CreateAnalyticsDatasetRequest request) {
        return new CreateAnalyticsDatasetCommand(request.code(), request.nameAr(), request.nameFr(), request.nameEn(), request.subjectAreaId(), request.datasetType(), request.refreshMode(), request.schemaVersion(), request.createdFrom());
    }

    public static CreateAnalyticsInsightCommand toCommand(CreateAnalyticsInsightRequest request) {
        return new CreateAnalyticsInsightCommand(request.insightType(), request.subjectAreaId(), request.scopeType(), request.scopeId(), request.title(), request.summary(), request.severityId(), request.confidenceScore(), request.sourceProjectionSnapshotId(), request.sourceTrendAnalysisId(), request.sourceModelRunId());
    }

    public static AnalyticsDatasetResponse toResponse(AnalyticsDatasetSummaryDto dto) {
        return new AnalyticsDatasetResponse(dto.id(), dto.code(), dto.nameFr(), dto.subjectAreaId(), dto.datasetType(), dto.refreshMode(), dto.lineageStatus(), dto.qualityStatus());
    }

    public static AnalyticsInsightResponse toResponse(AnalyticsInsightSummaryDto dto) {
        return new AnalyticsInsightResponse(dto.id(), dto.insightType(), dto.subjectAreaId(), dto.scopeType(), dto.scopeId(), dto.title(), dto.severityId(), dto.confidenceScore(), dto.status());
    }
}
