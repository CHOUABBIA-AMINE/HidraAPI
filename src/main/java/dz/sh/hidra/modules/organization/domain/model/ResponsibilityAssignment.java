/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ResponsibilityAssignment
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
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

import dz.sh.hidra.modules.organization.domain.value.AssignmentStatus;
import dz.sh.hidra.modules.organization.domain.value.ResponsibilityType;

import java.time.Instant;

/**
 * Effective-dated responsibility over one canonical operational-scope registry entry.
 *
 * <p>The assignment stores only {@code scopeId}. Scope type, target owner ID and
 * current display attributes are resolved through the OperationalScope registry
 * and the target owner's public read contract.</p>
 *
 * <p>During the migration window, {@code scopeId} may be null only for legacy rows
 * that have not yet been reconciled and backfilled. New assignment use cases must
 * require a positive registered scope ID before persistence.</p>
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

    public ResponsibilityAssignment {
        id = normalize(id);
        assigneeType = normalize(assigneeType);
        assigneeId = normalize(assigneeId);
        description = normalize(description);

        if (scopeId != null && scopeId <= 0) {
            throw new IllegalArgumentException("Operational scope registry ID must be positive when present.");
        }
    }

    /**
     * Transitional compatibility constructor for legacy generated mapping code.
     *
     * <p>The old {@code operationalScopeId} argument is interpreted only when it
     * already contains the numeric registry ID. Non-numeric legacy target IDs are
     * deliberately not guessed or converted and therefore produce a null scopeId.</p>
     */
    @Deprecated(forRemoval = true)
    public ResponsibilityAssignment(
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
        this(
                id,
                responsibilityType,
                assigneeType,
                assigneeId,
                parseRegistryId(operationalScopeId),
                description,
                validFrom,
                validTo,
                status,
                createdAt,
                updatedAt
        );
    }

    /**
     * Transitional compatibility only; scope type is no longer assignment state.
     */
    @Deprecated(forRemoval = true)
    public String operationalScopeType() {
        return null;
    }

    /**
     * Transitional compatibility only. This returns the registry ID as text and
     * must never be interpreted as the legacy owner target ID.
     */
    @Deprecated(forRemoval = true)
    public String operationalScopeId() {
        return scopeId == null ? null : scopeId.toString();
    }

    /**
     * Transitional compatibility only; current code belongs to the target owner.
     */
    @Deprecated(forRemoval = true)
    public String operationalScopeCode() {
        return null;
    }

    /**
     * Transitional compatibility only; current name belongs to the target owner.
     */
    @Deprecated(forRemoval = true)
    public String operationalScopeName() {
        return null;
    }

    private static Long parseRegistryId(String value) {
        String normalized = normalize(value);
        if (normalized == null) {
            return null;
        }
        try {
            long parsed = Long.parseLong(normalized);
            return parsed > 0 ? parsed : null;
        } catch (NumberFormatException ignored) {
            return null;
        }
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
