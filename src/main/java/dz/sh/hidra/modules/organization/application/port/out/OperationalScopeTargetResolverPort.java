/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalScopeTargetResolverPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-24
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.port.out
 *
 * @Description : Read-only public-owner resolution contract for an operational scope target.
 *
 */
package dz.sh.hidra.modules.organization.application.port.out;

import dz.sh.hidra.modules.organization.domain.value.OperationalScopeType;

import java.util.Objects;
import java.util.Optional;

/**
 * Resolves the CURRENT authoritative identity, labels and assignability of a target.
 *
 * <p>Implementations call the target owner's approved public read interface, never
 * directly access another module's JPA repositories or domain internals. This
 * contract does not grant permission to assign a scope: callers must authorize
 * that action separately and recheck lifecycle/concurrency at the write boundary.
 *
 * <p>The owning-module ID format is preserved: a registry Long ID is not a
 * topology target ID. GLOBAL has no underlying target and is handled by the
 * scope registry itself. Unregistered CUSTOM must fail closed.
 */
public interface OperationalScopeTargetResolverPort {

    /**
     * Whether this implementation has an approved owner read contract for a type.
     * GLOBAL and unregistered CUSTOM are not external targets.
     *
     * @param type non-null entity-backed scope type
     * @return true only for a type this resolver can authoritatively resolve
     */
    boolean supports(OperationalScopeType type);

    /**
     * Look up a typed target by its owning-module ID.
     *
     * <p>Empty means the requested target cannot be resolved; it must not be
     * interpreted as assignable. Infrastructure/owner unavailability must be
     * reported as an error, not disguised as an absent target. The caller must
     * verify the result's type and target ID match the request before proceeding.
     *
     * @param type an entity-backed type supported by this resolver
     * @param targetId canonical nonblank ID in the target owner's ID namespace
     * @return authoritative current target data, or empty when the target does not exist
     */
    Optional<ResolvedTarget> resolve(OperationalScopeType type, String targetId);

    /**
     * Read-only target details. Display attributes are not registry identity.
     *
     * @param type type in the authoritative target owner
     * @param targetId target-owner ID (not OperationalScope.id)
     * @param code current code, nullable when unavailable
     * @param name current display name, nullable when unavailable
     * @param assignable whether the owner currently permits new assignments
     */
    record ResolvedTarget(OperationalScopeType type, String targetId,
                          String code, String name, boolean assignable) {

        public ResolvedTarget {
            Objects.requireNonNull(type, "Resolved target type is required.");
            if (type == OperationalScopeType.GLOBAL || type == OperationalScopeType.CUSTOM) {
                throw new IllegalArgumentException("Unregistered or targetless scope has no external resolver target.");
            }
            if (targetId == null || targetId.isBlank()) {
                throw new IllegalArgumentException("Resolved target ID must not be blank.");
            }
            targetId = targetId.trim();
        }
    }
}
