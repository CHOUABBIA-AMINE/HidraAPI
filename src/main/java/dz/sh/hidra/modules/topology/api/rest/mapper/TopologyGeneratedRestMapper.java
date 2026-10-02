/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyGeneratedRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Interface
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.mapper
 *
 * @Description : Generates exact topology API/application boundary mappings at compile time.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.mapper;

import dz.sh.hidra.modules.topology.api.rest.request.CreatePipelineSystemRequest;
import dz.sh.hidra.modules.topology.api.rest.request.RegisterFacilityRequest;
import dz.sh.hidra.modules.topology.api.rest.response.FacilityResponse;
import dz.sh.hidra.modules.topology.api.rest.response.PipelineSystemResponse;
import dz.sh.hidra.modules.topology.application.command.CreatePipelineSystemCommand;
import dz.sh.hidra.modules.topology.application.command.RegisterFacilityCommand;
import dz.sh.hidra.modules.topology.application.dto.FacilitySummaryDto;
import dz.sh.hidra.modules.topology.application.dto.PipelineSystemSummaryDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * Generates only the HRA-070-approved exact topology boundary mappings.
 */
@Mapper(
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface TopologyGeneratedRestMapper {

    TopologyGeneratedRestMapper INSTANCE = Mappers.getMapper(TopologyGeneratedRestMapper.class);

    CreatePipelineSystemCommand toCommand(CreatePipelineSystemRequest request);

    RegisterFacilityCommand toCommand(RegisterFacilityRequest request);

    FacilityResponse toResponse(FacilitySummaryDto dto);

    PipelineSystemResponse toResponse(PipelineSystemSummaryDto dto);
}
