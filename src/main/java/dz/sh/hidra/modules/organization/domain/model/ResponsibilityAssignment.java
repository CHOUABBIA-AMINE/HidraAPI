/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ResponsibilityAssignment
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.model
 *
 * @Description : Responsibility over operational scopes by neutral reference.
 *
 */
package dz.sh.hidra.modules.organization.domain.model;

import dz.sh.hidra.modules.organization.domain.value.*;
import java.time.Instant;

    /**
     * Responsibility over operational scopes by neutral reference.
     *
         * @param id id
     * @param responsibilityType responsibilityType
     * @param assigneeType assigneeType
     * @param assigneeId assigneeId
     * @param operationalScopeType operationalScopeType
     * @param operationalScopeId operationalScopeId
     * @param operationalScopeCode operationalScopeCode
     * @param operationalScopeName operationalScopeName
     * @param description description
     * @param validFrom validFrom
     * @param validTo validTo
     * @param status status
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record ResponsibilityAssignment(
            String id,
        ResponsibilityType responsibilityType,
        String assigneeType,
        String assigneeId,
        String operationalScopeType,
        String operationalScopeId,
        String operationalScopeCode,
        String operationalScopeName,
        String description,
        Instant validFrom,
        Instant validTo,
        AssignmentStatus status,
        Instant createdAt,
        Instant updatedAt
    ) {

        public ResponsibilityAssignment {
        id = normalize(id);
        assigneeType = normalize(assigneeType);
        assigneeId = normalize(assigneeId);
        operationalScopeType = normalize(operationalScopeType);
        operationalScopeId = normalize(operationalScopeId);
        operationalScopeCode = normalize(operationalScopeCode);
        operationalScopeName = normalize(operationalScopeName);
        description = normalize(description);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
