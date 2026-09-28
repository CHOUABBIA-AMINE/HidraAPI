/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringGeneratedRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Interface
 * @Layer       : API
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.api.rest.mapper
 *
 * @Description : Generates exact monitoring API/application boundary mappings at compile time.
 *
 */
package dz.sh.hidra.modules.monitoring.api.rest.mapper;

import dz.sh.hidra.modules.monitoring.api.rest.request.CreateMonitoringRuleRequest;
import dz.sh.hidra.modules.monitoring.api.rest.request.RecordDeviationRequest;
import dz.sh.hidra.modules.monitoring.api.rest.response.DeviationResponse;
import dz.sh.hidra.modules.monitoring.api.rest.response.MonitoringRuleResponse;
import dz.sh.hidra.modules.monitoring.application.command.CreateMonitoringRuleCommand;
import dz.sh.hidra.modules.monitoring.application.command.RecordDeviationCommand;
import dz.sh.hidra.modules.monitoring.application.dto.DeviationSummaryDto;
import dz.sh.hidra.modules.monitoring.application.dto.MonitoringRuleSummaryDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * Generates only the HRA-070-approved exact monitoring boundary mappings.
 */
@Mapper(
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface MonitoringGeneratedRestMapper {

    MonitoringGeneratedRestMapper INSTANCE = Mappers.getMapper(MonitoringGeneratedRestMapper.class);

    CreateMonitoringRuleCommand toCommand(CreateMonitoringRuleRequest request);

    RecordDeviationCommand toCommand(RecordDeviationRequest request);

    DeviationResponse toResponse(DeviationSummaryDto dto);

    MonitoringRuleResponse toResponse(MonitoringRuleSummaryDto dto);
}
