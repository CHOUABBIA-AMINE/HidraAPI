/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingGeneratedRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Interface
 * @Layer       : API
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.api.rest.mapper
 *
 * @Description : Generates exact reporting API/application boundary mappings at compile time.
 *
 */
package dz.sh.hidra.modules.reporting.api.rest.mapper;

import dz.sh.hidra.modules.reporting.api.rest.request.CreateReportDefinitionRequest;
import dz.sh.hidra.modules.reporting.api.rest.request.GenerateReportArtifactRequest;
import dz.sh.hidra.modules.reporting.api.rest.request.QueueReportRunRequest;
import dz.sh.hidra.modules.reporting.api.rest.request.RequestReportRequest;
import dz.sh.hidra.modules.reporting.api.rest.response.ReportDefinitionResponse;
import dz.sh.hidra.modules.reporting.api.rest.response.ReportOutputArtifactResponse;
import dz.sh.hidra.modules.reporting.api.rest.response.ReportRequestResponse;
import dz.sh.hidra.modules.reporting.api.rest.response.ReportRunResponse;
import dz.sh.hidra.modules.reporting.application.command.CreateReportDefinitionCommand;
import dz.sh.hidra.modules.reporting.application.command.GenerateReportArtifactCommand;
import dz.sh.hidra.modules.reporting.application.command.QueueReportRunCommand;
import dz.sh.hidra.modules.reporting.application.command.RequestReportCommand;
import dz.sh.hidra.modules.reporting.application.dto.ReportDefinitionSummaryDto;
import dz.sh.hidra.modules.reporting.application.dto.ReportOutputArtifactSummaryDto;
import dz.sh.hidra.modules.reporting.application.dto.ReportRequestSummaryDto;
import dz.sh.hidra.modules.reporting.application.dto.ReportRunSummaryDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * Generates only the HRA-070-approved exact reporting boundary mappings.
 */
@Mapper(
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface ReportingGeneratedRestMapper {

    ReportingGeneratedRestMapper INSTANCE = Mappers.getMapper(ReportingGeneratedRestMapper.class);

    CreateReportDefinitionCommand toCommand(CreateReportDefinitionRequest request);

    GenerateReportArtifactCommand toCommand(GenerateReportArtifactRequest request);

    QueueReportRunCommand toCommand(QueueReportRunRequest request);

    RequestReportCommand toCommand(RequestReportRequest request);

    ReportDefinitionResponse toResponse(ReportDefinitionSummaryDto dto);

    ReportOutputArtifactResponse toResponse(ReportOutputArtifactSummaryDto dto);

    ReportRequestResponse toResponse(ReportRequestSummaryDto dto);

    ReportRunResponse toResponse(ReportRunSummaryDto dto);
}
