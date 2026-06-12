/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeOrganizationProjection
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.projection
 *
 * @Description : Employee organization read projection.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.projection;

import dz.sh.hidra.modules.organization.domain.value.EmployeeStatus;

/**
 * Employee organization read projection.
 */
public record EmployeeOrganizationProjection(
        String employeeId,
        String employeeNumber,
        String displayNameAr,
        String displayNameLt,
        String organizationUnitId,
        String positionId,
        EmployeeStatus status
) {
}
