/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalScopeRegistrationValidator
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-24
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.service
 *
 * @Description : Validates scope registration requests against an authoritative owner resolver.
 *
 */
package dz.sh.hidra.modules.organization.application.service;

import dz.sh.hidra.modules.organization.application.port.out.OperationalScopeTargetResolverPort;
import dz.sh.hidra.modules.organization.application.port.out.OperationalScopeTargetResolverPort.ResolvedTarget;
import dz.sh.hidra.modules.organization.domain.value.OperationalScopeType;

import java.util.Objects;

/**
 * Performs read-only prerequisite validation before any operational-scope registration.
 * This class does not authorize the caller, reserve a registry ID, write data or
 * guarantee that the owner will remain assignable until a later transaction commits.
 */
public final class OperationalScopeRegistrationValidator {

    private final OperationalScopeTargetResolverPort targetResolver;

    public OperationalScopeRegistrationValidator(OperationalScopeTargetResolverPort targetResolver) {
        this.targetResolver = Objects.requireNonNull(targetResolver, "Target resolver must not be null.");
    }

    /**
     * Validates the requested category and exact owner identifier. GLOBAL has no
     * external target. CUSTOM remains disabled until a governed owner namespace
     * and resolver have been approved. Unavailable owner services must raise an
     * error rather than returning an empty target.
     *
     * @return normalized target reference suitable for a subsequent authorized,
     *         concurrency-safe registry registration; never a persisted registry ID
     */
    public ValidatedTarget validate(OperationalScopeType type, String targetId) {
        Objects.requireNonNull(type, "Operational scope type must not be null.");
        String normalizedId = targetId == null || targetId.isBlank() ? null : targetId.trim();
        if (type == OperationalScopeType.GLOBAL) {
            if (normalizedId != null) {
                throw new IllegalArgumentException("GLOBAL must not reference a target object.");
            }
            return new ValidatedTarget(type, null);
        }
        if (type == OperationalScopeType.CUSTOM) {
            throw new IllegalArgumentException("CUSTOM requires an approved owner namespace and contract.");
        }
        if (normalizedId == null) {
            throw new IllegalArgumentException("Entity-backed scope requires a target ID.");
        }
        if (!targetResolver.supports(type)) {
            throw new IllegalStateException("No approved owner resolver for requested scope type.");
        }
        ResolvedTarget resolved = targetResolver.resolve(type, normalizedId)
                .orElseThrow(() -> new IllegalArgumentException("Operational scope target not found."));
        if (resolved.type() != type || !resolved.targetId().equals(normalizedId)) {
            throw new IllegalStateException("Owner resolver returned a mismatched scope reference.");
        }
        if (!resolved.assignable()) {
            throw new IllegalStateException("Operational scope target is not assignable.");
        }
        return new ValidatedTarget(type, normalizedId);
    }

    /** Owner-validated typed reference, not a registry entity or a permission grant. */
    public record ValidatedTarget(OperationalScopeType type, String targetId) {
        public ValidatedTarget {
            Objects.requireNonNull(type, "Scope type must not be null.");
            if (type == OperationalScopeType.CUSTOM) {
                throw new IllegalArgumentException("Unregistered CUSTOM scope is not supported.");
            }
            if (type == OperationalScopeType.GLOBAL) {
                if (targetId != null) {
                    throw new IllegalArgumentException("GLOBAL has no target ID.");
                }
            } else if (targetId == null || targetId.isBlank() || !targetId.equals(targetId.trim())) {
                throw new IllegalArgumentException("Entity-backed scope requires a canonical target ID.");
            }
        }
    }
}
