/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ResponsibilityAssignment
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-29
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.model
 *
 * @Description : Effective-dated responsibility over one registered operational scope.
 *
 */
package dz.sh.hidra.modules.organization.domain.model;

import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;
import dz.sh.hidra.modules.organization.domain.value.AssignmentStatus;
import dz.sh.hidra.modules.organization.domain.value.ResponsibilityType;
import dz.sh.hidra.modules.organization.domain.value.ResponsibilityAssigneeType;

import java.time.Instant;

/**
 * Effective-dated responsibility over one canonical operational-scope registry entry.
 *
 * <p>The assignment stores only {@code scopeId}. Scope type, target owner ID and
 * current display attributes are resolved through the OperationalScope registry
 * and the target owner's public read contract.</p>
 *
 * <p>{@code scopeId} remains nullable only until ORG-032 finalizes the greenfield
 * schema constraint. Canonical assignment use cases already require a positive
 * registered scope ID before persistence.</p>
 *
 * @param id assignment identifier
 * @param responsibilityType responsibility role
 * @param assigneeType assignee discriminator
 * @param assigneeId assignee identifier
 * @param scopeId generated OperationalScope registry identifier
 * @param description optional description
 * @param validFrom inclusive effective start
 * @param validTo exclusive effective end, nullable for open-ended assignment
 * @param status assignment status
 * @param createdAt creation timestamp
 * @param updatedAt update timestamp
 */
public record ResponsibilityAssignment(
        String id,
        ResponsibilityType responsibilityType,
        ResponsibilityAssigneeType assigneeType,
        String assigneeId,
        Long scopeId,
        String description,
        Instant validFrom,
        Instant validTo,
        AssignmentStatus status,
        Instant createdAt,
        Instant updatedAt
) {

    public ResponsibilityAssignment {
        id = requireText(id, "Responsibility assignment ID is required.");
        if (responsibilityType == null) {
            throw new InvalidOrganizationValueException("Responsibility type is required.");
        }
        if (assigneeType == null) {
            throw new InvalidOrganizationValueException("Responsibility assignee type must not be null.");
        }
        assigneeId = requireText(assigneeId, "Responsibility assignee ID is required.");
        description = normalize(description);

        if (scopeId != null && scopeId <= 0) {
            throw new InvalidOrganizationValueException("Operational scope registry ID must be positive when present.");
        }
        if (status == null) {
            throw new InvalidOrganizationValueException("Responsibility assignment status is required.");
        }
        if (validFrom == null) {
            throw new InvalidOrganizationValueException("Responsibility assignment validFrom is required.");
        }
        if (validTo != null && !validTo.isAfter(validFrom)) {
            throw new InvalidOrganizationValueException("Responsibility assignment validTo must be after validFrom.");
        }
    }

    /**
     * Transitional compatibility constructor for callers that still provide a textual assignee type.
     */
    @Deprecated(forRemoval = true)
    public ResponsibilityAssignment(
            String id,
            ResponsibilityType responsibilityType,
            String assigneeType,
            String assigneeId,
            Long scopeId,
            String description,
            Instant validFrom,
            Instant validTo,
            AssignmentStatus status,
            Instant createdAt,
            Instant updatedAt
    ) {
        this(
                id,
                responsibilityType,
                ResponsibilityAssigneeType.from(assigneeType),
                assigneeId,
                scopeId,
                description,
                validFrom,
                validTo,
                status,
                createdAt,
                updatedAt
        );
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
