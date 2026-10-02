/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.api.rest.mapper
 *
 * @Description : Maps organization REST models to application models.
 *
 */
package dz.sh.hidra.modules.organization.api.rest.mapper;

import dz.sh.hidra.modules.organization.api.rest.request.AssignEmployeeRequest;
import dz.sh.hidra.modules.organization.api.rest.request.CreateOrganizationUnitRequest;
import dz.sh.hidra.modules.organization.api.rest.request.RegisterEmployeeRequest;
import dz.sh.hidra.modules.organization.api.rest.response.EmployeeResponse;
import dz.sh.hidra.modules.organization.api.rest.response.OrganizationUnitResponse;
import dz.sh.hidra.modules.organization.application.command.AssignEmployeeCommand;
import dz.sh.hidra.modules.organization.application.command.CreateOrganizationUnitCommand;
import dz.sh.hidra.modules.organization.application.command.RegisterEmployeeCommand;
import dz.sh.hidra.modules.organization.application.dto.EmployeeSummaryDto;
import dz.sh.hidra.modules.organization.application.dto.OrganizationUnitSummaryDto;
import dz.sh.hidra.modules.organization.domain.value.OrganizationCode;
import java.util.Objects;

/**
 * Maps organization REST models to application models.
 */
public final class OrganizationRestMapper {

    private static final OrganizationGeneratedRestMapper GENERATED = OrganizationGeneratedRestMapper.INSTANCE;

    private OrganizationRestMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static AssignEmployeeCommand toCommand(AssignEmployeeRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "AssignEmployeeRequest must not be null."));
    }

    public static CreateOrganizationUnitCommand toCommand(CreateOrganizationUnitRequest request) {
        return new CreateOrganizationUnitCommand(
                OrganizationCode.of(request.code()),
                request.nameAr(),
                request.nameFr(),
                request.nameEn(),
                request.unitTypeId(),
                request.parentUnitId(),
                request.status(),
                request.validFrom()
        );
    }

    public static RegisterEmployeeCommand toCommand(RegisterEmployeeRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "RegisterEmployeeRequest must not be null."));
    }

    public static OrganizationUnitResponse toResponse(OrganizationUnitSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "OrganizationUnitSummaryDto must not be null."));
    }

    public static EmployeeResponse toResponse(EmployeeSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "EmployeeSummaryDto must not be null."));
    }
}
