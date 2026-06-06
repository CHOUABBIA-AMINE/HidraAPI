/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationPersistenceMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-06
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.mapper
 *
 * @Description : Maps organization domain objects to persistence entities.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.mapper;

import java.util.List;
import java.util.Objects;

import dz.sh.hidra.modules.organization.domain.model.Employee;
import dz.sh.hidra.modules.organization.domain.model.EmployeeAssignment;
import dz.sh.hidra.modules.organization.domain.model.OperationalScopeReference;
import dz.sh.hidra.modules.organization.domain.model.OrganizationUnit;
import dz.sh.hidra.modules.organization.domain.model.Position;
import dz.sh.hidra.modules.organization.domain.model.ReportingLine;
import dz.sh.hidra.modules.organization.domain.value.AssignmentId;
import dz.sh.hidra.modules.organization.domain.value.EmployeeEmail;
import dz.sh.hidra.modules.organization.domain.value.EmployeeFullName;
import dz.sh.hidra.modules.organization.domain.value.EmployeeId;
import dz.sh.hidra.modules.organization.domain.value.EmployeeNumber;
import dz.sh.hidra.modules.organization.domain.value.EmploymentStatus;
import dz.sh.hidra.modules.organization.domain.value.IdentityUserReference;
import dz.sh.hidra.modules.organization.domain.value.OperationalScopeType;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitCode;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitId;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitName;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitStatus;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitTypeReference;
import dz.sh.hidra.modules.organization.domain.value.PositionCode;
import dz.sh.hidra.modules.organization.domain.value.PositionId;
import dz.sh.hidra.modules.organization.domain.value.PositionTitle;
import dz.sh.hidra.modules.organization.domain.value.ReportingLineId;
import dz.sh.hidra.modules.organization.domain.value.ReportingLineType;
import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.EmployeeAssignmentJpaEntity;
import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.EmployeeJpaEntity;
import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.OrganizationUnitJpaEntity;
import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.PositionJpaEntity;
import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.ReportingLineJpaEntity;

/**
 * Maps organization domain objects and persistence entities.
 */
public final class OrganizationPersistenceMapper {

    public EmployeeJpaEntity toEntity(Employee employee) {
        Objects.requireNonNull(employee, "Employee must not be null.");

        EmployeeJpaEntity entity = new EmployeeJpaEntity();
        entity.setId(employee.id().value());
        entity.setEmployeeNumber(employee.employeeNumber().value());
        entity.setFullName(employee.fullName().value());
        entity.setEmail(employee.email().map(EmployeeEmail::value).orElse(null));
        entity.setStatus(employee.status().name());
        entity.setIdentityUserReference(employee.identityUserReference().map(IdentityUserReference::value).orElse(null));
        entity.setCreatedAt(employee.createdAt());
        entity.setActivatedAt(employee.activatedAt().orElse(null));
        entity.setSuspendedAt(employee.suspendedAt().orElse(null));
        entity.setDisabledAt(employee.disabledAt().orElse(null));
        entity.setUpdatedAt(employee.updatedAt());
        entity.setAssignments(employee.assignments().stream().map(this::toEntity).toList());
        entity.setReportingLines(employee.reportingLines().stream().map(this::toEntity).toList());
        return entity;
    }

    public Employee toDomain(EmployeeJpaEntity entity) {
        Objects.requireNonNull(entity, "Employee entity must not be null.");

        List<EmployeeAssignment> assignments = entity.getAssignments().stream()
                .map(this::toDomain)
                .toList();

        List<ReportingLine> reportingLines = entity.getReportingLines().stream()
                .map(this::toDomain)
                .toList();

        return Employee.restore(
                EmployeeId.of(entity.getId()),
                EmployeeNumber.of(entity.getEmployeeNumber()),
                EmployeeFullName.of(entity.getFullName()),
                entity.getEmail() == null ? null : EmployeeEmail.of(entity.getEmail()),
                EmploymentStatus.valueOf(entity.getStatus()),
                entity.getIdentityUserReference() == null ? null : IdentityUserReference.of(entity.getIdentityUserReference()),
                assignments,
                reportingLines,
                entity.getCreatedAt(),
                entity.getActivatedAt(),
                entity.getSuspendedAt(),
                entity.getDisabledAt(),
                entity.getUpdatedAt());
    }

    public OrganizationUnitJpaEntity toEntity(OrganizationUnit organizationUnit) {
        Objects.requireNonNull(organizationUnit, "Organization unit must not be null.");

        OperationalScopeReference scope = organizationUnit.operationalScopeReference().orElse(null);

        OrganizationUnitJpaEntity entity = new OrganizationUnitJpaEntity();
        entity.setId(organizationUnit.id().value());
        entity.setCode(organizationUnit.code().value());
        entity.setNameAr(organizationUnit.name().nameAr());
        entity.setNameFr(organizationUnit.name().nameFr());
        entity.setNameEn(organizationUnit.name().nameEn());
        entity.setStatus(organizationUnit.status().name());
        entity.setTypeId(organizationUnit.type().id());
        entity.setParentId(organizationUnit.parentId().map(OrganizationUnitId::value).orElse(null));
        entity.setOperationalScopeType(scope == null ? null : scope.scopeType().name());
        entity.setOperationalScopeId(scope == null ? null : scope.scopeId());
        entity.setOperationalScopeCode(scope == null ? null : scope.scopeCode());
        entity.setOperationalScopeName(scope == null ? null : scope.scopeName());
        entity.setCreatedAt(organizationUnit.createdAt());
        entity.setUpdatedAt(organizationUnit.updatedAt());
        return entity;
    }

    public OrganizationUnit toDomain(OrganizationUnitJpaEntity entity) {
        Objects.requireNonNull(entity, "Organization unit entity must not be null.");

        OperationalScopeReference scope = toOperationalScopeReference(
                entity.getOperationalScopeType(),
                entity.getOperationalScopeId(),
                entity.getOperationalScopeCode(),
                entity.getOperationalScopeName());

        return OrganizationUnit.restore(
                OrganizationUnitId.of(entity.getId()),
                OrganizationUnitCode.of(entity.getCode()),
                OrganizationUnitName.of(entity.getNameAr(), entity.getNameFr(), entity.getNameEn()),
                OrganizationUnitStatus.valueOf(entity.getStatus()),
                OrganizationUnitTypeReference.ofId(entity.getTypeId()),
                entity.getParentId() == null ? null : OrganizationUnitId.of(entity.getParentId()),
                scope,
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    public PositionJpaEntity toEntity(Position position) {
        Objects.requireNonNull(position, "Position must not be null.");

        PositionJpaEntity entity = new PositionJpaEntity();
        entity.setId(position.id().value());
        entity.setCode(position.code().value());
        entity.setTitleAr(position.title().titleAr());
        entity.setTitleFr(position.title().titleFr());
        entity.setTitleEn(position.title().titleEn());
        entity.setDescriptionAr(position.description());
        entity.setDescriptionFr(position.description());
        entity.setDescriptionEn(position.description());
        entity.setActive(position.active());
        entity.setCreatedAt(position.createdAt());
        entity.setUpdatedAt(position.updatedAt());
        return entity;
    }

    public Position toDomain(PositionJpaEntity entity) {
        Objects.requireNonNull(entity, "Position entity must not be null.");

        return Position.restore(
                PositionId.of(entity.getId()),
                PositionCode.of(entity.getCode()),
                PositionTitle.of(entity.getTitleAr(), entity.getTitleFr(), entity.getTitleEn()),
                entity.getDescriptionEn(),
                entity.isActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    private EmployeeAssignmentJpaEntity toEntity(EmployeeAssignment assignment) {
        EmployeeAssignmentJpaEntity entity = new EmployeeAssignmentJpaEntity();
        OperationalScopeReference scope = assignment.optionalOperationalScopeReference().orElse(null);

        entity.setId(assignment.id().value());
        entity.setOrganizationUnitId(assignment.organizationUnitId().value());
        entity.setPositionId(assignment.positionId().value());
        entity.setOperationalScopeType(scope == null ? null : scope.scopeType().name());
        entity.setOperationalScopeId(scope == null ? null : scope.scopeId());
        entity.setOperationalScopeCode(scope == null ? null : scope.scopeCode());
        entity.setOperationalScopeName(scope == null ? null : scope.scopeName());
        entity.setEffectiveFrom(assignment.effectiveFrom());
        entity.setEffectiveTo(assignment.effectiveTo());
        return entity;
    }

    private EmployeeAssignment toDomain(EmployeeAssignmentJpaEntity entity) {
        return new EmployeeAssignment(
                AssignmentId.of(entity.getId()),
                EmployeeId.of(entity.getEmployeeId()),
                OrganizationUnitId.of(entity.getOrganizationUnitId()),
                PositionId.of(entity.getPositionId()),
                toOperationalScopeReference(
                        entity.getOperationalScopeType(),
                        entity.getOperationalScopeId(),
                        entity.getOperationalScopeCode(),
                        entity.getOperationalScopeName()),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo());
    }

    private ReportingLineJpaEntity toEntity(ReportingLine reportingLine) {
        ReportingLineJpaEntity entity = new ReportingLineJpaEntity();
        entity.setId(reportingLine.id().value());
        entity.setManagerEmployeeId(reportingLine.managerEmployeeId().value());
        entity.setReportingLineType(reportingLine.type().name());
        entity.setPrimaryLine(reportingLine.primaryLine());
        entity.setEffectiveFrom(reportingLine.effectiveFrom());
        entity.setEffectiveTo(reportingLine.effectiveTo());
        entity.setDescription(reportingLine.optionalDescription().orElse(null));
        return entity;
    }

    private ReportingLine toDomain(ReportingLineJpaEntity entity) {
        return new ReportingLine(
                ReportingLineId.of(entity.getId()),
                EmployeeId.of(entity.getEmployeeId()),
                EmployeeId.of(entity.getManagerEmployeeId()),
                ReportingLineType.valueOf(entity.getReportingLineType()),
                entity.isPrimaryLine(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo(),
                entity.getDescription());
    }

    private static OperationalScopeReference toOperationalScopeReference(
            String type,
            String id,
            String code,
            String name) {

        if (type == null || code == null) {
            return null;
        }

        return new OperationalScopeReference(OperationalScopeType.valueOf(type), id, code, name);
    }
}
