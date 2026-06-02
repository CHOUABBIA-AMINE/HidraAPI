/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalScopeReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.model
 *
 * @Description : Neutral reference from organization responsibility to a future operational or topology scope.
 *
 */
package dz.sh.hidra.modules.organization.domain.model;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;
import dz.sh.hidra.modules.organization.domain.value.OperationalScopeType;

/**
 * Represents a neutral operational scope reference.
 *
 * <p>Business role:
 * This reference links an organization unit or employee assignment to an operational scope such as
 * a future topology station, compression station, pumping station, delivery station, pipeline,
 * region, or facility. It allows organization to express responsibility without owning topology.
 *
 * <p>Architecture role:
 * This is a domain value-style model inside the organization bounded context. It intentionally
 * stores neutral scope identifiers and codes instead of importing topology aggregate classes.
 *
 * <p>Validation:
 * The scope type and scope code are required. Scope id and scope name are optional. All string
 * values are trimmed. Scope code must not exceed 120 characters.
 *
 * <p>Usage:
 * Use this type when an organization unit represents people and responsibility attached to a
 * physical or operational asset that is or will be modeled in another bounded context.
 *
 * @param scopeType type of referenced operational scope
 * @param scopeId optional stable scope identifier from the owner bounded context
 * @param scopeCode required business scope code, for example <code>CS-EAST-01</code>
 * @param scopeName optional human-readable scope name
 */
public record OperationalScopeReference(
        OperationalScopeType scopeType,
        String scopeId,
        String scopeCode,
        String scopeName) implements ValueObject {

    private static final int SCOPE_ID_MAX_LENGTH = 120;
    private static final int SCOPE_CODE_MAX_LENGTH = 120;
    private static final int SCOPE_NAME_MAX_LENGTH = 160;

    public OperationalScopeReference {
        if (scopeType == null) {
            throw new InvalidValueObjectException("Operational scope type must not be null.");
        }

        scopeId = normalizeOptional(scopeId, SCOPE_ID_MAX_LENGTH, "Operational scope id");
        scopeCode = normalizeRequired(scopeCode, SCOPE_CODE_MAX_LENGTH, "Operational scope code");
        scopeName = normalizeOptional(scopeName, SCOPE_NAME_MAX_LENGTH, "Operational scope name");
    }

    /**
     * Creates a reference to an operational scope using a required scope type and code.
     *
     * @param scopeType scope type
     * @param scopeCode scope business code
     * @return operational scope reference
     */
    public static OperationalScopeReference of(OperationalScopeType scopeType, String scopeCode) {
        return new OperationalScopeReference(scopeType, null, scopeCode, null);
    }

    /**
     * Indicates whether this reference targets a station-like operational scope.
     *
     * @return true when the scope type is station-oriented
     */
    public boolean isStationScope() {
        return scopeType.isStationScope();
    }

    private static String normalizeRequired(String value, int maxLength, String label) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException(label + " must not be null or blank.");
        }

        String normalized = value.trim();
        if (normalized.length() > maxLength) {
            throw new InvalidValueObjectException(label + " must not exceed " + maxLength + " characters.");
        }
        return normalized;
    }

    private static String normalizeOptional(String value, int maxLength, String label) {
        if (value == null || value.isBlank()) {
            return null;
        }

        String normalized = value.trim();
        if (normalized.length() > maxLength) {
            throw new InvalidValueObjectException(label + " must not exceed " + maxLength + " characters.");
        }
        return normalized;
    }
}
