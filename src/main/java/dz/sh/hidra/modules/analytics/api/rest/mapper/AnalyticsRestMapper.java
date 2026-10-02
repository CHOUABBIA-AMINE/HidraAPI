/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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
import java.util.Objects;

/**
 * Maps analytics REST models to application models.
 */
public final class AnalyticsRestMapper {

    private static final AnalyticsGeneratedRestMapper GENERATED = AnalyticsGeneratedRestMapper.INSTANCE;

    private AnalyticsRestMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static CreateAnalyticsDatasetCommand toCommand(CreateAnalyticsDatasetRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "CreateAnalyticsDatasetRequest must not be null."));
    }

    public static CreateAnalyticsInsightCommand toCommand(CreateAnalyticsInsightRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "CreateAnalyticsInsightRequest must not be null."));
    }

    public static RunProjectionCommand toCommand(RunProjectionRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "RunProjectionRequest must not be null."));
    }

    public static RunMetricEvaluationCommand toCommand(RunMetricEvaluationRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "RunMetricEvaluationRequest must not be null."));
    }

    public static AnalyticsDatasetResponse toResponse(AnalyticsDatasetSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "AnalyticsDatasetSummaryDto must not be null."));
    }

    public static AnalyticsInsightResponse toResponse(AnalyticsInsightSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "AnalyticsInsightSummaryDto must not be null."));
    }

    public static AnalyticsProjectionRunResponse toResponse(AnalyticsProjectionRunSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "AnalyticsProjectionRunSummaryDto must not be null."));
    }

    public static MetricEvaluationRunResponse toResponse(MetricEvaluationRunSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "MetricEvaluationRunSummaryDto must not be null."));
    }
}
