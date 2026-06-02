/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationApplicationMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.mapper
 *
 * @Description : Maps organization domain objects to application DTOs.
 *
 */
package dz.sh.hidra.modules.organization.application.mapper;

import java.util.List;
import java.util.Objects;

import dz.sh.hidra.modules.organization.application.dto.EmployeeAssignmentDto;
import dz.sh.hidra.modules.organization.application.dto.EmployeeDto;
import dz.sh.hidra.modules.organization.application.dto.OrganizationUnitDto;
import dz.sh.hidra.modules.organization.application.dto.PositionDto;
import dz.sh.hidra.modules.organization.application.dto.ReportingLineDto;
import dz.sh.hidra.modules.organization.domain.model.Employee;
import dz.sh.hidra.modules.organization.domain.model.EmployeeAssignment;
import dz.sh.hidra.modules.organization.domain.model.OperationalScopeReference;
import dz.sh.hidra.modules.organization.domain.model.OrganizationUnit;
import dz.sh.hidra.modules.organization.domain.model.Position;
import dz.sh.hidra.modules.organization.domain.model.ReportingLine;

/**
 * Maps organization domain objects to application DTOs.
 *
 * <p>Business role:
 * This mapper converts employees, organization units, positions, assignments, and reporting lines
 * into stable application outputs without leaking domain aggregates to outer layers.
 *
 * <p>Architecture role:
 * This is an application-layer mapper. It must not depend on REST request/response DTOs, JPA
 * entities, Spring components, identity domain classes, topology domain classes, or infrastructure
 * code.
 *
 * <p>Validation:
 * Input domain objects are required. Null optional domain fields are mapped to null DTO fields.
 *
 * <p>Usage:
 * Application services should use this mapper before returning DTOs through inbound ports.
 */
public final class OrganizationApplicationMapper {

    /**
     * Maps an employee aggregate to an application DTO.
     *
     * @param employee employee aggregate
     * @return employee DTO
     */
    public EmployeeDto toDto(Employee employee) {
        Objects.requireNonNull(employee, "Employee must not be null.");

        List<EmployeeAssignmentDto> assignmentDtos = employee.assignments().stream()
                .map(this::toDto)
                .toList();

        List<ReportingLineDto> reportingLineDtos = employee.reportingLines().stream()
                .map(this::toDto)
                .toList();

        return new EmployeeDto(
                employee.id().value(),
                employee.employeeNumber().value(),
                employee.fullName().value(),
                employee.email().map(email -> email.value()).orElse(null),
                employee.status().name(),
                employee.identityUserReference().map(reference -> reference.value()).orElse(null),
                assignmentDtos,
                reportingLineDtos,
                employee.createdAt(),
                employee.activatedAt().orElse(null),
                employee.suspendedAt().orElse(null),
                employee.disabledAt().orElse(null),
                employee.updatedAt());
    }

    /**
     * Maps an employee assignment to an application DTO.
     *
     * @param assignment employee assignment
     * @return employee assignment DTO
     */
    public EmployeeAssignmentDto toDto(EmployeeAssignment assignment) {
        Objects.requireNonNull(assignment, "Employee assignment must not be null.");

        OperationalScopeReference scope = assignment.optionalOperationalScopeReference().orElse(null);

        return new EmployeeAssignmentDto(
                assignment.id().value(),
                assignment.employeeId().value(),
                assignment.organizationUnitId().value(),
                assignment.positionId().value(),
                scopeType(scope),
                scopeId(scope),
                scopeCode(scope),
                scopeName(scope),
                assignment.effectiveFrom(),
                assignment.effectiveTo());
    }

    /**
     * Maps a reporting line to an application DTO.
     *
     * @param reportingLine reporting line
     * @return reporting line DTO
     */
    public ReportingLineDto toDto(ReportingLine reportingLine) {
        Objects.requireNonNull(reportingLine, "Reporting line must not be null.");

        return new ReportingLineDto(
                reportingLine.id().value(),
                reportingLine.employeeId().value(),
                reportingLine.managerEmployeeId().value(),
                reportingLine.type().name(),
                reportingLine.primaryLine(),
                reportingLine.effectiveFrom(),
                reportingLine.effectiveTo(),
                reportingLine.optionalDescription().orElse(null));
    }

    /**
     * Maps an organization unit to an application DTO.
     *
     * @param organizationUnit organization unit
     * @return organization unit DTO
     */
    public OrganizationUnitDto toDto(OrganizationUnit organizationUnit) {
        Objects.requireNonNull(organizationUnit, "Organization unit must not be null.");

        OperationalScopeReference scope = organizationUnit.operationalScopeReference().orElse(null);

        return new OrganizationUnitDto(
                organizationUnit.id().value(),
                organizationUnit.code().value(),
                organizationUnit.name().value(),
                organizationUnit.status().name(),
                organizationUnit.type().name(),
                organizationUnit.parentId().map(parentId -> parentId.value()).orElse(null),
                scopeType(scope),
                scopeId(scope),
                scopeCode(scope),
                scopeName(scope),
                organizationUnit.createdAt(),
                organizationUnit.updatedAt());
    }

    /**
     * Maps a position to an application DTO.
     *
     * @param position position
     * @return position DTO
     */
    public PositionDto toDto(Position position) {
        Objects.requireNonNull(position, "Position must not be null.");

        return new PositionDto(
                position.id().value(),
                position.code().value(),
                position.title().value(),
                position.description(),
                position.active(),
                position.createdAt(),
                position.updatedAt());
    }

    private static String scopeType(OperationalScopeReference scope) {
        return scope == null ? null : scope.scopeType().name();
    }

    private static String scopeId(OperationalScopeReference scope) {
        return scope == null ? null : scope.scopeId();
    }

    private static String scopeCode(OperationalScopeReference scope) {
        return scope == null ? null : scope.scopeCode();
    }

    private static String scopeName(OperationalScopeReference scope) {
        return scope == null ? null : scope.scopeName();
    }
}
