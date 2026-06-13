/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
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

/**
 * Maps organization REST models to application models.
 */
public final class OrganizationRestMapper {

    private OrganizationRestMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static AssignEmployeeCommand toCommand(AssignEmployeeRequest request) {
        return new AssignEmployeeCommand(
                request.employeeId(),
                request.organizationUnitId(),
                request.positionId(),
                request.assignmentType(),
                request.operationalScopeType(),
                request.operationalScopeId(),
                request.operationalScopeCode(),
                request.operationalScopeName(),
                request.validFrom(),
                request.validTo()
        );
    }

    public static CreateOrganizationUnitCommand toCommand(CreateOrganizationUnitRequest request) {
        return new CreateOrganizationUnitCommand(
                request.code(),
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
        return new RegisterEmployeeCommand(
                request.employeeNumber(),
                request.firstNameAr(),
                request.lastNameAr(),
                request.firstNameLt(),
                request.lastNameLt(),
                request.displayNameAr(),
                request.displayNameLt(),
                request.emailAddress(),
                request.mobileNumber(),
                request.employeeType(),
                request.identityUserReference()
        );
    }

    public static OrganizationUnitResponse toResponse(OrganizationUnitSummaryDto dto) {
        return new OrganizationUnitResponse(
                dto.id(),
                dto.code(),
                dto.nameAr(),
                dto.nameFr(),
                dto.nameEn(),
                dto.unitTypeId(),
                dto.parentUnitId(),
                dto.status()
        );
    }

    public static EmployeeResponse toResponse(EmployeeSummaryDto dto) {
        return new EmployeeResponse(
                dto.id(),
                dto.employeeNumber(),
                dto.displayNameAr(),
                dto.displayNameLt(),
                dto.emailAddress(),
                dto.employeeType(),
                dto.status(),
                dto.identityUserReference()
        );
    }
}
