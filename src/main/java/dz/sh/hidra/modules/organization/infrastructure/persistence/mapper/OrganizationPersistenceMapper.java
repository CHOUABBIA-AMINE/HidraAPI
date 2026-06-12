/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationPersistenceMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.mapper
 *
 * @Description : Maps organization domain models to JPA entities.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.mapper;

import dz.sh.hidra.modules.organization.domain.model.*;
import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.*;

/**
 * Maps organization domain models to JPA entities.
 */
public final class OrganizationPersistenceMapper {

    private OrganizationPersistenceMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }


        public static OrganizationUnitTypeJpaEntity toEntity(OrganizationUnitType model) {
            return new OrganizationUnitTypeJpaEntity(
                        model.id(),
                        model.code(),
                        model.kind(),
                        model.description(),
                        model.active(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static OrganizationUnitType toDomain(OrganizationUnitTypeJpaEntity entity) {
            return new OrganizationUnitType(
                        entity.id(),
                        entity.code(),
                        entity.kind(),
                        entity.description(),
                        entity.active(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static OrganizationUnitTypeTranslationJpaEntity toEntity(OrganizationUnitTypeTranslation model) {
            return new OrganizationUnitTypeTranslationJpaEntity(
                        model.id(),
                        model.unitTypeId(),
                        model.languageCode(),
                        model.label(),
                        model.description(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static OrganizationUnitTypeTranslation toDomain(OrganizationUnitTypeTranslationJpaEntity entity) {
            return new OrganizationUnitTypeTranslation(
                        entity.id(),
                        entity.unitTypeId(),
                        entity.languageCode(),
                        entity.label(),
                        entity.description(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static OrganizationUnitJpaEntity toEntity(OrganizationUnit model) {
            return new OrganizationUnitJpaEntity(
                        model.id(),
                        model.code(),
                        model.nameAr(),
                        model.nameFr(),
                        model.nameEn(),
                        model.unitTypeId(),
                        model.parentUnitId(),
                        model.status(),
                        model.operationalScopeType(),
                        model.operationalScopeId(),
                        model.operationalScopeCode(),
                        model.operationalScopeName(),
                        model.validFrom(),
                        model.validTo(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static OrganizationUnit toDomain(OrganizationUnitJpaEntity entity) {
            return new OrganizationUnit(
                        entity.id(),
                        entity.code(),
                        entity.nameAr(),
                        entity.nameFr(),
                        entity.nameEn(),
                        entity.unitTypeId(),
                        entity.parentUnitId(),
                        entity.status(),
                        entity.operationalScopeType(),
                        entity.operationalScopeId(),
                        entity.operationalScopeCode(),
                        entity.operationalScopeName(),
                        entity.validFrom(),
                        entity.validTo(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static PositionJpaEntity toEntity(Position model) {
            return new PositionJpaEntity(
                        model.id(),
                        model.code(),
                        model.titleAr(),
                        model.titleFr(),
                        model.titleEn(),
                        model.level(),
                        model.description(),
                        model.status(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static Position toDomain(PositionJpaEntity entity) {
            return new Position(
                        entity.id(),
                        entity.code(),
                        entity.titleAr(),
                        entity.titleFr(),
                        entity.titleEn(),
                        entity.level(),
                        entity.description(),
                        entity.status(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static EmployeeJpaEntity toEntity(Employee model) {
            return new EmployeeJpaEntity(
                        model.id(),
                        model.employeeNumber(),
                        model.firstNameAr(),
                        model.lastNameAr(),
                        model.firstNameLt(),
                        model.lastNameLt(),
                        model.displayNameAr(),
                        model.displayNameLt(),
                        model.emailAddress(),
                        model.mobileNumber(),
                        model.employeeType(),
                        model.status(),
                        model.identityUserReference(),
                        model.hiredAt(),
                        model.terminatedAt(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static Employee toDomain(EmployeeJpaEntity entity) {
            return new Employee(
                        entity.id(),
                        entity.employeeNumber(),
                        entity.firstNameAr(),
                        entity.lastNameAr(),
                        entity.firstNameLt(),
                        entity.lastNameLt(),
                        entity.displayNameAr(),
                        entity.displayNameLt(),
                        entity.emailAddress(),
                        entity.mobileNumber(),
                        entity.employeeType(),
                        entity.status(),
                        entity.identityUserReference(),
                        entity.hiredAt(),
                        entity.terminatedAt(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static AdministrativeStateJpaEntity toEntity(AdministrativeState model) {
            return new AdministrativeStateJpaEntity(
                        model.id(),
                        model.code(),
                        model.nameAr(),
                        model.nameFr(),
                        model.nameEn(),
                        model.active(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static AdministrativeState toDomain(AdministrativeStateJpaEntity entity) {
            return new AdministrativeState(
                        entity.id(),
                        entity.code(),
                        entity.nameAr(),
                        entity.nameFr(),
                        entity.nameEn(),
                        entity.active(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static AdministrativeDistrictJpaEntity toEntity(AdministrativeDistrict model) {
            return new AdministrativeDistrictJpaEntity(
                        model.id(),
                        model.stateId(),
                        model.code(),
                        model.nameAr(),
                        model.nameFr(),
                        model.nameEn(),
                        model.active(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static AdministrativeDistrict toDomain(AdministrativeDistrictJpaEntity entity) {
            return new AdministrativeDistrict(
                        entity.id(),
                        entity.stateId(),
                        entity.code(),
                        entity.nameAr(),
                        entity.nameFr(),
                        entity.nameEn(),
                        entity.active(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static AdministrativeLocalityJpaEntity toEntity(AdministrativeLocality model) {
            return new AdministrativeLocalityJpaEntity(
                        model.id(),
                        model.districtId(),
                        model.code(),
                        model.nameAr(),
                        model.nameFr(),
                        model.nameEn(),
                        model.postalCode(),
                        model.active(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static AdministrativeLocality toDomain(AdministrativeLocalityJpaEntity entity) {
            return new AdministrativeLocality(
                        entity.id(),
                        entity.districtId(),
                        entity.code(),
                        entity.nameAr(),
                        entity.nameFr(),
                        entity.nameEn(),
                        entity.postalCode(),
                        entity.active(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static EmployeeAddressJpaEntity toEntity(EmployeeAddress model) {
            return new EmployeeAddressJpaEntity(
                        model.id(),
                        model.employeeId(),
                        model.addressType(),
                        model.localityId(),
                        model.streetLine1(),
                        model.streetLine2(),
                        model.postalCodeSnapshot(),
                        model.primaryAddress(),
                        model.validFrom(),
                        model.validTo(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static EmployeeAddress toDomain(EmployeeAddressJpaEntity entity) {
            return new EmployeeAddress(
                        entity.id(),
                        entity.employeeId(),
                        entity.addressType(),
                        entity.localityId(),
                        entity.streetLine1(),
                        entity.streetLine2(),
                        entity.postalCodeSnapshot(),
                        entity.primaryAddress(),
                        entity.validFrom(),
                        entity.validTo(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static EmployeeAssignmentJpaEntity toEntity(EmployeeAssignment model) {
            return new EmployeeAssignmentJpaEntity(
                        model.id(),
                        model.employeeId(),
                        model.organizationUnitId(),
                        model.positionId(),
                        model.assignmentType(),
                        model.operationalScopeType(),
                        model.operationalScopeId(),
                        model.operationalScopeCode(),
                        model.operationalScopeName(),
                        model.validFrom(),
                        model.validTo(),
                        model.status(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static EmployeeAssignment toDomain(EmployeeAssignmentJpaEntity entity) {
            return new EmployeeAssignment(
                        entity.id(),
                        entity.employeeId(),
                        entity.organizationUnitId(),
                        entity.positionId(),
                        entity.assignmentType(),
                        entity.operationalScopeType(),
                        entity.operationalScopeId(),
                        entity.operationalScopeCode(),
                        entity.operationalScopeName(),
                        entity.validFrom(),
                        entity.validTo(),
                        entity.status(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static ReportingLineJpaEntity toEntity(ReportingLine model) {
            return new ReportingLineJpaEntity(
                        model.id(),
                        model.reportingLineType(),
                        model.sourceType(),
                        model.sourceId(),
                        model.targetType(),
                        model.targetId(),
                        model.validFrom(),
                        model.validTo(),
                        model.active(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static ReportingLine toDomain(ReportingLineJpaEntity entity) {
            return new ReportingLine(
                        entity.id(),
                        entity.reportingLineType(),
                        entity.sourceType(),
                        entity.sourceId(),
                        entity.targetType(),
                        entity.targetId(),
                        entity.validFrom(),
                        entity.validTo(),
                        entity.active(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static ResponsibilityAssignmentJpaEntity toEntity(ResponsibilityAssignment model) {
            return new ResponsibilityAssignmentJpaEntity(
                        model.id(),
                        model.responsibilityType(),
                        model.assigneeType(),
                        model.assigneeId(),
                        model.operationalScopeType(),
                        model.operationalScopeId(),
                        model.operationalScopeCode(),
                        model.operationalScopeName(),
                        model.description(),
                        model.validFrom(),
                        model.validTo(),
                        model.status(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static ResponsibilityAssignment toDomain(ResponsibilityAssignmentJpaEntity entity) {
            return new ResponsibilityAssignment(
                        entity.id(),
                        entity.responsibilityType(),
                        entity.assigneeType(),
                        entity.assigneeId(),
                        entity.operationalScopeType(),
                        entity.operationalScopeId(),
                        entity.operationalScopeCode(),
                        entity.operationalScopeName(),
                        entity.description(),
                        entity.validFrom(),
                        entity.validTo(),
                        entity.status(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static OrganizationDelegationJpaEntity toEntity(OrganizationDelegation model) {
            return new OrganizationDelegationJpaEntity(
                        model.id(),
                        model.delegatorEmployeeId(),
                        model.delegateEmployeeId(),
                        model.responsibilityAssignmentId(),
                        model.reason(),
                        model.validFrom(),
                        model.validTo(),
                        model.status(),
                        model.createdAt(),
                        model.revokedAt()
            );
        }

        public static OrganizationDelegation toDomain(OrganizationDelegationJpaEntity entity) {
            return new OrganizationDelegation(
                        entity.id(),
                        entity.delegatorEmployeeId(),
                        entity.delegateEmployeeId(),
                        entity.responsibilityAssignmentId(),
                        entity.reason(),
                        entity.validFrom(),
                        entity.validTo(),
                        entity.status(),
                        entity.createdAt(),
                        entity.revokedAt()
            );
        }

        public static ShiftJpaEntity toEntity(Shift model) {
            return new ShiftJpaEntity(
                        model.id(),
                        model.code(),
                        model.name(),
                        model.shiftType(),
                        model.startTime(),
                        model.endTime(),
                        model.timezone(),
                        model.active(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static Shift toDomain(ShiftJpaEntity entity) {
            return new Shift(
                        entity.id(),
                        entity.code(),
                        entity.name(),
                        entity.shiftType(),
                        entity.startTime(),
                        entity.endTime(),
                        entity.timezone(),
                        entity.active(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static ShiftAssignmentJpaEntity toEntity(ShiftAssignment model) {
            return new ShiftAssignmentJpaEntity(
                        model.id(),
                        model.employeeId(),
                        model.shiftId(),
                        model.organizationUnitId(),
                        model.validFrom(),
                        model.validTo(),
                        model.status(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static ShiftAssignment toDomain(ShiftAssignmentJpaEntity entity) {
            return new ShiftAssignment(
                        entity.id(),
                        entity.employeeId(),
                        entity.shiftId(),
                        entity.organizationUnitId(),
                        entity.validFrom(),
                        entity.validTo(),
                        entity.status(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static OrganizationContactPointJpaEntity toEntity(OrganizationContactPoint model) {
            return new OrganizationContactPointJpaEntity(
                        model.id(),
                        model.contactPointType(),
                        model.targetType(),
                        model.targetId(),
                        model.label(),
                        model.value(),
                        model.primaryContact(),
                        model.emergencyContact(),
                        model.active(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static OrganizationContactPoint toDomain(OrganizationContactPointJpaEntity entity) {
            return new OrganizationContactPoint(
                        entity.id(),
                        entity.contactPointType(),
                        entity.targetType(),
                        entity.targetId(),
                        entity.label(),
                        entity.value(),
                        entity.primaryContact(),
                        entity.emergencyContact(),
                        entity.active(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static OrganizationHierarchySnapshotJpaEntity toEntity(OrganizationHierarchySnapshot model) {
            return new OrganizationHierarchySnapshotJpaEntity(
                        model.id(),
                        model.snapshotCode(),
                        model.capturedAt(),
                        model.capturedByEmployeeId(),
                        model.status(),
                        model.snapshotPayload(),
                        model.description(),
                        model.createdAt()
            );
        }

        public static OrganizationHierarchySnapshot toDomain(OrganizationHierarchySnapshotJpaEntity entity) {
            return new OrganizationHierarchySnapshot(
                        entity.id(),
                        entity.snapshotCode(),
                        entity.capturedAt(),
                        entity.capturedByEmployeeId(),
                        entity.status(),
                        entity.snapshotPayload(),
                        entity.description(),
                        entity.createdAt()
            );
        }

}
