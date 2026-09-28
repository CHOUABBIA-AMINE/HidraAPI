/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationGeneratedRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Interface
 * @Layer       : API
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.api.rest.mapper
 *
 * @Description : Generates exact integration API/application boundary mappings at compile time.
 *
 */
package dz.sh.hidra.modules.integration.api.rest.mapper;

import dz.sh.hidra.modules.integration.api.rest.request.RecordExchangeMessageRequest;
import dz.sh.hidra.modules.integration.api.rest.request.RegisterExternalSystemRequest;
import dz.sh.hidra.modules.integration.api.rest.request.StartIntegrationJobRunRequest;
import dz.sh.hidra.modules.integration.api.rest.response.ExternalSystemResponse;
import dz.sh.hidra.modules.integration.api.rest.response.IntegrationExchangeMessageResponse;
import dz.sh.hidra.modules.integration.api.rest.response.IntegrationJobRunResponse;
import dz.sh.hidra.modules.integration.application.command.RecordExchangeMessageCommand;
import dz.sh.hidra.modules.integration.application.command.RegisterExternalSystemCommand;
import dz.sh.hidra.modules.integration.application.command.StartIntegrationJobRunCommand;
import dz.sh.hidra.modules.integration.application.dto.ExternalSystemSummaryDto;
import dz.sh.hidra.modules.integration.application.dto.IntegrationExchangeMessageSummaryDto;
import dz.sh.hidra.modules.integration.application.dto.IntegrationJobRunSummaryDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * Generates only the HRA-070-approved exact integration boundary mappings.
 */
@Mapper(
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface IntegrationGeneratedRestMapper {

    IntegrationGeneratedRestMapper INSTANCE = Mappers.getMapper(IntegrationGeneratedRestMapper.class);

    RecordExchangeMessageCommand toCommand(RecordExchangeMessageRequest request);

    RegisterExternalSystemCommand toCommand(RegisterExternalSystemRequest request);

    StartIntegrationJobRunCommand toCommand(StartIntegrationJobRunRequest request);

    ExternalSystemResponse toResponse(ExternalSystemSummaryDto dto);

    IntegrationExchangeMessageResponse toResponse(IntegrationExchangeMessageSummaryDto dto);

    IntegrationJobRunResponse toResponse(IntegrationJobRunSummaryDto dto);
}
