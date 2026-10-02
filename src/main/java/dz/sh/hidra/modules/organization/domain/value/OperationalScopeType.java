/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalScopeType
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.value
 *
 * @Description : Defines governed operational-scope target categories and their identity semantics.
 *
 */
package dz.sh.hidra.modules.organization.domain.value;

/**
 * Defines the canonical categories that may own an operational scope target.
 *
 * <p>Business role: the type determines which owner namespace interprets a target
 * identifier. GLOBAL is the sole target-less scope. CUSTOM remains intentionally
 * ungoverned until a dedicated owner namespace and contract are approved.</p>
 *
 * <p>Architecture role: this enum is domain-only metadata. It does not resolve
 * foreign objects and does not import another module.</p>
 *
 * <p>Validation: callers use {@link #requiresTargetId()}, {@link #isGlobal()} and
 * {@link #isGoverned()} to enforce local reference shape before application-level
 * owner resolution.</p>
 *
 * <p>Usage: use these values only as typed identity categories; never infer a
 * registry ID, code or display name from an enum value.</p>
 */
public enum OperationalScopeType {

    GLOBAL,
    ORGANIZATION_UNIT,
    PIPELINE_SYSTEM,
    PIPELINE,
    FACILITY,
    EQUIPMENT,
    CUSTOM;

    /**
     * Whether this type represents the unique target-less global scope.
     *
     * @return true only for GLOBAL
     */
    public boolean isGlobal() {
        return this == GLOBAL;
    }

    /**
     * Whether a canonical owner-native target ID is required.
     *
     * @return false only for GLOBAL
     */
    public boolean requiresTargetId() {
        return !isGlobal();
    }

    /**
     * Whether the type currently has an approved canonical ownership model.
     *
     * @return false for CUSTOM until a governed namespace contract is approved
     */
    public boolean isGoverned() {
        return this != CUSTOM;
    }
}
