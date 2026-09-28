/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningGeneratedRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Interface
 * @Layer       : API
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.api.rest.mapper
 *
 * @Description : Generates exact planning API/application boundary mappings at compile time.
 *
 */
package dz.sh.hidra.modules.planning.api.rest.mapper;

import dz.sh.hidra.modules.planning.api.rest.request.CreateOperationalPlanRequest;
import dz.sh.hidra.modules.planning.api.rest.request.CreatePlanningPeriodRequest;
import dz.sh.hidra.modules.planning.api.rest.response.OperationalPlanResponse;
import dz.sh.hidra.modules.planning.api.rest.response.PlanningPeriodResponse;
import dz.sh.hidra.modules.planning.application.command.CreateOperationalPlanCommand;
import dz.sh.hidra.modules.planning.application.command.CreatePlanningPeriodCommand;
import dz.sh.hidra.modules.planning.application.dto.OperationalPlanSummaryDto;
import dz.sh.hidra.modules.planning.application.dto.PlanningPeriodSummaryDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * Generates only the HRA-070-approved exact planning boundary mappings.
 */
@Mapper(
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface PlanningGeneratedRestMapper {

    PlanningGeneratedRestMapper INSTANCE = Mappers.getMapper(PlanningGeneratedRestMapper.class);

    CreateOperationalPlanCommand toCommand(CreateOperationalPlanRequest request);

    CreatePlanningPeriodCommand toCommand(CreatePlanningPeriodRequest request);

    OperationalPlanResponse toResponse(OperationalPlanSummaryDto dto);

    PlanningPeriodResponse toResponse(PlanningPeriodSummaryDto dto);
}
