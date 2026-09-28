/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsGeneratedRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Interface
 * @Layer       : API
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.api.rest.mapper
 *
 * @Description : Generates exact analytics API/application boundary mappings at compile time.
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
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * Generates only the HRA-070-approved exact analytics boundary mappings.
 */
@Mapper(
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface AnalyticsGeneratedRestMapper {

    AnalyticsGeneratedRestMapper INSTANCE = Mappers.getMapper(AnalyticsGeneratedRestMapper.class);

    CreateAnalyticsDatasetCommand toCommand(CreateAnalyticsDatasetRequest request);

    CreateAnalyticsInsightCommand toCommand(CreateAnalyticsInsightRequest request);

    RunProjectionCommand toCommand(RunProjectionRequest request);

    RunMetricEvaluationCommand toCommand(RunMetricEvaluationRequest request);

    AnalyticsDatasetResponse toResponse(AnalyticsDatasetSummaryDto dto);

    AnalyticsInsightResponse toResponse(AnalyticsInsightSummaryDto dto);

    AnalyticsProjectionRunResponse toResponse(AnalyticsProjectionRunSummaryDto dto);

    MetricEvaluationRunResponse toResponse(MetricEvaluationRunSummaryDto dto);
}
