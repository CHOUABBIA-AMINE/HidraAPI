/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalScopeRegistrationValidator
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.service
 *
 * @Description : Validates canonical scope references against their authoritative owner resolver.
 *
 */
package dz.sh.hidra.modules.organization.application.service;

import dz.sh.hidra.modules.organization.application.port.out.OperationalScopeTargetResolverPort;
import dz.sh.hidra.modules.organization.application.port.out.OperationalScopeTargetResolverPort.ResolvedTarget;
import dz.sh.hidra.modules.organization.domain.value.OperationalScopeReference;
import dz.sh.hidra.modules.organization.domain.value.OperationalScopeType;

import java.util.Objects;

/**
 * Performs read-only prerequisite validation before operational-scope registration.
 *
 * <p>Business role: confirms that an entity-backed owner reference exists and is
 * currently assignable. GLOBAL is target-less and requires no owner lookup.</p>
 *
 * <p>Architecture role: validates the existing domain value object without replacing
 * it with a second application-specific type.</p>
 *
 * <p>Validation: the domain value object already enforces local type/target shape.
 * This validator verifies resolver support, exact owner identity, and assignability.
 * Owner unavailability propagates as an error and is never disguised as absence.</p>
 *
 * <p>Usage: call before registry persistence. A successful result is still an
 * owner-native reference, never a generated operational-scope registry ID.</p>
 */
public final class OperationalScopeRegistrationValidator {

    private final OperationalScopeTargetResolverPort targetResolver;

    public OperationalScopeRegistrationValidator(OperationalScopeTargetResolverPort targetResolver) {
        this.targetResolver = Objects.requireNonNull(
                targetResolver,
                "Target resolver must not be null."
        );
    }

    /**
     * Validates one canonical owner reference.
     *
     * @param reference governed owner-native reference
     * @return the same canonical reference after owner validation
     */
    public OperationalScopeReference validate(OperationalScopeReference reference) {
        Objects.requireNonNull(reference, "Operational scope reference must not be null.");

        if (reference.type().isGlobal()) {
            return reference;
        }

        if (!targetResolver.supports(reference.type())) {
            throw new IllegalStateException(
                    "No approved owner resolver for requested scope type."
            );
        }

        ResolvedTarget resolved = targetResolver
                .resolve(reference.type(), reference.targetId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Operational scope target not found."
                ));

        if (resolved.type() != reference.type()
                || !resolved.targetId().equals(reference.targetId())) {
            throw new IllegalStateException(
                    "Owner resolver returned a mismatched scope reference."
            );
        }

        if (!resolved.assignable()) {
            throw new IllegalStateException(
                    "Operational scope target is not assignable."
            );
        }

        return reference;
    }

    /**
     * Transitional compatibility boundary for callers that still provide separate values.
     *
     * @param type scope type
     * @param targetId owner-native target identifier
     * @return validated canonical reference
     */
    @Deprecated(forRemoval = true)
    public OperationalScopeReference validate(
            OperationalScopeType type,
            String targetId
    ) {
        return validate(new OperationalScopeReference(type, targetId));
    }
}
