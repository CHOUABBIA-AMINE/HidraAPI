/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationApplicationMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.mapper
 *
 * @Description : Maps organization domain models to DTOs.
 *
 */
package dz.sh.hidra.modules.organization.application.mapper;

import dz.sh.hidra.modules.organization.application.dto.EmployeeSummaryDto;
import dz.sh.hidra.modules.organization.application.dto.OrganizationUnitSummaryDto;
import dz.sh.hidra.modules.organization.domain.model.Employee;
import dz.sh.hidra.modules.organization.domain.model.OrganizationUnit;

/**
 * Maps organization domain models to application DTOs.
 */
public final class OrganizationApplicationMapper {

    private OrganizationApplicationMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static OrganizationUnitSummaryDto toSummary(OrganizationUnit model) {
        return new OrganizationUnitSummaryDto(
                model.id(),
                model.code(),
                model.nameAr(),
                model.nameFr(),
                model.nameEn(),
                model.unitTypeId(),
                model.parentUnitId(),
                model.status()
        );
    }

    public static EmployeeSummaryDto toSummary(Employee model) {
        return new EmployeeSummaryDto(
                model.id(),
                model.employeeNumber(),
                model.displayNameAr(),
                model.displayNameLt(),
                model.emailAddress(),
                model.employeeType(),
                model.status(),
                model.identityUserReference()
        );
    }
}
