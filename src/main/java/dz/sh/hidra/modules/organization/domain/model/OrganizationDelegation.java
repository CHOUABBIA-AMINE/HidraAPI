/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationDelegation
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;
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
        // HRA-051 required: validTo
        if (validTo == null) {
            throw new InvalidOrganizationValueException("OrganizationDelegation valid to must not be null.");
        }

        id = requireText(id, "Organization delegation ID is required.");
        delegatorEmployeeId = requireText(delegatorEmployeeId, "Delegator employee ID is required.");
        delegateEmployeeId = requireText(delegateEmployeeId, "Delegate employee ID is required.");
        responsibilityAssignmentId = requireText(
                responsibilityAssignmentId,
                "Responsibility assignment ID is required for delegation."
        );
        if (delegatorEmployeeId.equals(delegateEmployeeId)) {
            throw new InvalidOrganizationValueException("Delegator and delegate employees must be different.");
        }
        if (status == null) {
            throw new InvalidOrganizationValueException("Delegation status is required.");
        }
        if (validFrom == null) {
            throw new InvalidOrganizationValueException("Organization delegation validFrom is required.");
        }
        if (validTo != null && !validTo.isAfter(validFrom)) {
            throw new InvalidOrganizationValueException("Organization delegation validTo must be after validFrom.");
        }
        if (revokedAt != null && revokedAt.isBefore(validFrom)) {
            throw new InvalidOrganizationValueException("Delegation revokedAt must not precede validFrom.");
        }
        reason = normalize(reason);
        }
    private static String requireText(String value, String message) {
        String normalized = normalize(value);
        if (normalized == null) {
            throw new InvalidOrganizationValueException(message);
        }
        return normalized;
    }


        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
