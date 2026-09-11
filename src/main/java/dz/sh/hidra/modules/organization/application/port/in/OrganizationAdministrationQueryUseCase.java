/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationAdministrationQueryUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.port.in
 *
 * @Description : Provides dedicated organization hierarchy, employee directory, and assignment read models.
 *
 */
package dz.sh.hidra.modules.organization.application.port.in;

import java.time.Instant;
import java.util.List;

public interface OrganizationAdministrationQueryUseCase {

    Page<OrganizationUnitView> units(String query, int page, int size);

    OrganizationUnitView unit(String id);

    List<OrganizationUnitView> children(String parentUnitId);

    List<OrganizationNodeView> hierarchy();

    Page<EmployeeView> employees(String query, int page, int size);

    EmployeeView employee(String id);

    Page<EmployeeAssignmentView> assignments(
            String employeeId,
            String organizationUnitId,
            String status,
            int page,
            int size
    );

    List<EmployeeAssignmentView> employeeAssignments(String employeeId);

    record Page<T>(
            List<T> content,
            int page,
            int size,
            long totalElements,
            int totalPages,
            boolean hasNext
    ) { }

    record OrganizationUnitView(
            String id,
            String code,
            String nameAr,
            String nameFr,
            String nameEn,
            String unitTypeId,
            String parentUnitId,
            String status,
            String operationalScopeType,
            String operationalScopeId,
            String operationalScopeCode,
            String operationalScopeName,
            Instant validFrom,
            Instant validTo
    ) { }

    record OrganizationNodeView(
            OrganizationUnitView unit,
            List<OrganizationNodeView> children
    ) { }

    record EmployeeView(
            String id,
            String employeeNumber,
            String displayNameAr,
            String displayNameLt,
            String emailAddress,
            String mobileNumber,
            String employeeType,
            String status,
            String identityUserReference,
            Instant hiredAt,
            Instant terminatedAt
    ) { }

    record EmployeeAssignmentView(
            String id,
            String employeeId,
            String organizationUnitId,
            String positionId,
            String assignmentType,
            String operationalScopeType,
            String operationalScopeId,
            String operationalScopeCode,
            String operationalScopeName,
            Instant validFrom,
            Instant validTo,
            String status
    ) { }
}
