/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeAssignment
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.model
 *
 * @Description : Assignment of employee to unit, position, and optional operational scope.
 *
 */
package dz.sh.hidra.modules.organization.domain.model;

import dz.sh.hidra.modules.organization.domain.value.*;
import java.time.Instant;

    /**
     * Assignment of employee to unit, position, and optional operational scope.
     *
         * @param id id
     * @param employeeId employeeId
     * @param organizationUnitId organizationUnitId
     * @param positionId positionId
     * @param assignmentType assignmentType
     * @param operationalScopeType operationalScopeType
     * @param operationalScopeId operationalScopeId
     * @param operationalScopeCode operationalScopeCode
     * @param operationalScopeName operationalScopeName
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

        public EmployeeAssignment {
        id = normalize(id);
        employeeId = normalize(employeeId);
        organizationUnitId = normalize(organizationUnitId);
        positionId = normalize(positionId);
        operationalScopeType = normalize(operationalScopeType);
        operationalScopeId = normalize(operationalScopeId);
        operationalScopeCode = normalize(operationalScopeCode);
        operationalScopeName = normalize(operationalScopeName);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
