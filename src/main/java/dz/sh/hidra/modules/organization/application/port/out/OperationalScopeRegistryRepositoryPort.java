/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalScopeRegistryRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.port.out
 *
 * @Description : Persistence contract for canonical operational-scope registry identities.
 *
 */
package dz.sh.hidra.modules.organization.application.port.out;

import dz.sh.hidra.modules.organization.domain.model.OperationalScope;
import dz.sh.hidra.modules.organization.domain.value.OperationalScopeReference;
import dz.sh.hidra.modules.organization.domain.value.OperationalScopeType;

import java.util.Optional;

/**
 * Persists and resolves canonical operational-scope registry identities without
 * exposing JPA or another module's internals.
 *
 * <p>Registration receives an already normalized and owner-validated
 * {@link OperationalScopeReference}. The repository creates or reuses the independent,
 * database-generated registry identity.</p>
 */
public interface OperationalScopeRegistryRepositoryPort {

    /**
     * Idempotently registers a canonical typed owner reference.
     *
     * <p>The returned {@link OperationalScope#id()} is the registry identity used by
     * responsibilities; {@link OperationalScopeReference#targetId()} remains the
     * native identifier in the owner's namespace.</p>
     *
     * <p>This default bridge preserves legacy repository implementations temporarily.
     * New implementations should override this method directly.</p>
     *
     * @param reference owner-validated canonical reference
     * @return existing or newly persisted registry record
     */
    default OperationalScope register(OperationalScopeReference reference) {
        if (reference == null) {
            throw new IllegalArgumentException(
                    "Operational scope reference must not be null."
            );
        }
        return register(reference.type(), reference.targetId());
    }

    /**
     * Transitional registration bridge for legacy implementations.
     *
     * @param type canonical scope type
     * @param targetId canonical target-owner ID, null only for GLOBAL
     * @return existing or newly persisted registry record
     */
    @Deprecated(forRemoval = true)
    default OperationalScope register(OperationalScopeType type, String targetId) {
        throw new UnsupportedOperationException(
                "Repository must implement register(OperationalScopeReference)."
        );
    }

    /**
     * Find a registered scope by its database-generated identity.
     *
     * @param scopeId positive registry identifier, not an underlying target ID
     * @return matching scope, or empty when absent
     */
    Optional<OperationalScope> findById(Long scopeId);

    /**
     * Find an existing entity-backed scope by owner type and owner-native target ID.
     *
     * @param type supported entity-backed operational scope type
     * @param targetId canonical target ID as defined by the owning module
     * @return existing registry record, or empty if no registration exists
     */
    Optional<OperationalScope> findByTypeAndTargetId(
            OperationalScopeType type,
            String targetId
    );

    /**
     * Resolve the singleton GLOBAL registry row, when it has been registered.
     *
     * @return existing GLOBAL scope, or empty before its creation
     */
    Optional<OperationalScope> findGlobal();
}
