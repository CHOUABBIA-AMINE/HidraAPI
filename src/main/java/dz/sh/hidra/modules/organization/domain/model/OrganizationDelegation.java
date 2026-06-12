/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationDelegation
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.model
 *
 * @Description : Temporary delegation of responsibility.
 *
 */
package dz.sh.hidra.modules.organization.domain.model;

import dz.sh.hidra.modules.organization.domain.value.*;
import java.time.Instant;

    /**
     * Temporary delegation of responsibility.
     *
         * @param id id
     * @param delegatorEmployeeId delegatorEmployeeId
     * @param delegateEmployeeId delegateEmployeeId
     * @param responsibilityAssignmentId responsibilityAssignmentId
     * @param reason reason
     * @param validFrom validFrom
     * @param validTo validTo
     * @param status status
     * @param createdAt createdAt
     * @param revokedAt revokedAt
     */
    public record OrganizationDelegation(
            String id,
        String delegatorEmployeeId,
        String delegateEmployeeId,
        String responsibilityAssignmentId,
        String reason,
        Instant validFrom,
        Instant validTo,
        DelegationStatus status,
        Instant createdAt,
        Instant revokedAt
    ) {

        public OrganizationDelegation {
        id = normalize(id);
        delegatorEmployeeId = normalize(delegatorEmployeeId);
        delegateEmployeeId = normalize(delegateEmployeeId);
        responsibilityAssignmentId = normalize(responsibilityAssignmentId);
        reason = normalize(reason);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
