/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationGeneratedRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Interface
 * @Layer       : API
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.api.rest.mapper
 *
 * @Description : Generates exact organization API/application boundary mappings at compile time.
 *
 */
package dz.sh.hidra.modules.organization.api.rest.mapper;

import dz.sh.hidra.modules.organization.api.rest.request.AssignEmployeeRequest;
import dz.sh.hidra.modules.organization.api.rest.request.RegisterEmployeeRequest;
import dz.sh.hidra.modules.organization.api.rest.response.EmployeeResponse;
import dz.sh.hidra.modules.organization.api.rest.response.OrganizationUnitResponse;
import dz.sh.hidra.modules.organization.application.command.AssignEmployeeCommand;
import dz.sh.hidra.modules.organization.application.command.RegisterEmployeeCommand;
import dz.sh.hidra.modules.organization.application.dto.EmployeeSummaryDto;
import dz.sh.hidra.modules.organization.application.dto.OrganizationUnitSummaryDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * Generates only the HRA-070-approved exact organization boundary mappings.
 */
@Mapper(
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface OrganizationGeneratedRestMapper {

    OrganizationGeneratedRestMapper INSTANCE = Mappers.getMapper(OrganizationGeneratedRestMapper.class);

    AssignEmployeeCommand toCommand(AssignEmployeeRequest request);

    RegisterEmployeeCommand toCommand(RegisterEmployeeRequest request);

    EmployeeResponse toResponse(EmployeeSummaryDto dto);

    OrganizationUnitResponse toResponse(OrganizationUnitSummaryDto dto);
}
