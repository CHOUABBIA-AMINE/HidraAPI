/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeAssignment
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.model
 *
 * @Description : Assignment of an employee to an organization unit and position.
 *
 */
package dz.sh.hidra.modules.organization.domain.model;

import dz.sh.hidra.modules.organization.domain.value.AssignmentStatus;
import dz.sh.hidra.modules.organization.domain.value.AssignmentType;

import java.time.Instant;

/**
 * Assignment of an employee to a unit and position.
 *
 * <p>Operational responsibility is deliberately not embedded in membership.
 * Employee-specific operational responsibility belongs to ResponsibilityAssignment.</p>
 *
 * @param id id
 * @param employeeId employeeId
 * @param organizationUnitId organizationUnitId
 * @param positionId positionId
 * @param assignmentType assignmentType
 * @param validFrom validFrom
 * @param validTo validTo
 * @param status status
 * @param createdAt createdAt
 * @param updatedAt updatedAt
 */
public record EmployeeAssignment(
        String id,
        String employeeId,
        String organizationUnitId,
        String positionId,
        AssignmentType assignmentType,
        Instant validFrom,
        Instant validTo,
        AssignmentStatus status,
        Instant createdAt,
        Instant updatedAt
) {

    public EmployeeAssignment {
        id = normalize(id);
        employeeId = normalize(employeeId);
        organizationUnitId = normalize(organizationUnitId);
        positionId = normalize(positionId);
    }

    /**
     * Transitional constructor kept only so the still-legacy persistence mapper can
     * read rows containing historical scope columns while those columns are retired.
     * The supplied scope values are deliberately discarded.
     */
    @Deprecated(forRemoval = true)
    public EmployeeAssignment(
            String id,
            String employeeId,
            String organizationUnitId,
            String positionId,
            AssignmentType assignmentType,
            String operationalScopeType,
            String operationalScopeId,
            String operationalScopeCode,
            String operationalScopeName,
            Instant validFrom,
            Instant validTo,
            AssignmentStatus status,
            Instant createdAt,
            Instant updatedAt
    ) {
        this(
                id,
                employeeId,
                organizationUnitId,
                positionId,
                assignmentType,
                validFrom,
                validTo,
                status,
                createdAt,
                updatedAt
        );
    }

    /**
     * Transitional persistence compatibility only. Scope is no longer part of
     * EmployeeAssignment canonical state.
     */
    @Deprecated(forRemoval = true)
    public String operationalScopeType() {
        return null;
    }

    /**
     * Transitional persistence compatibility only.
     */
    @Deprecated(forRemoval = true)
    public String operationalScopeId() {
        return null;
    }

    /**
     * Transitional persistence compatibility only.
     */
    @Deprecated(forRemoval = true)
    public String operationalScopeCode() {
        return null;
    }

    /**
     * Transitional persistence compatibility only.
     */
    @Deprecated(forRemoval = true)
    public String operationalScopeName() {
        return null;
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
