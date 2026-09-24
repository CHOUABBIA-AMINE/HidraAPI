/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalScopeRegistryRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-24
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.port.out
 *
 * @Description : Read-only application persistence contract for registered operational-scope identities.
 *
 */
package dz.sh.hidra.modules.organization.application.port.out;

import dz.sh.hidra.modules.organization.domain.model.OperationalScope;
import dz.sh.hidra.modules.organization.domain.value.OperationalScopeType;

import java.util.Optional;

/**
 * Looks up already registered scope identities without depending on JPA or topology internals.
 *
 * <p>This port does not create or authorize new scope registrations. Registration
 * must first validate the authoritative owning module through
 * {@link OperationalScopeTargetResolverPort}; a future persistence adapter must
 * enforce the database unique constraints when writing registry rows.
 *
 * <p>The registry ID is a generated Long. The target ID retains its owner's
 * native String representation; neither a scope code nor a scope name is a key.
 */
public interface OperationalScopeRegistryRepositoryPort {

    /**
     * Find a registered scope by its database-generated identity.
     *
     * @param scopeId positive registry identifier, not an underlying target ID
     * @return matching scope, or empty when absent
     */
    Optional<OperationalScope> findById(Long scopeId);

    /**
     * Find an existing entity-backed scope by the unique owner-type and target-ID pair.
     *
     * <p>For GLOBAL, use {@link #findGlobal()} instead. CUSTOM remains unsupported
     * until a separately approved namespace/resolver design is implemented.
     * Implementations must not perform fuzzy matching on target labels or codes.
     *
     * @param type supported entity-backed operational scope type
     * @param targetId canonical target ID as defined by the owning module
     * @return existing registry record, or empty if no registration exists
     */
    Optional<OperationalScope> findByTypeAndTargetId(OperationalScopeType type, String targetId);

    /**
     * Resolve the singleton GLOBAL registry row, when it has been registered.
     *
     * @return existing GLOBAL scope, or empty before its creation
     */
    Optional<OperationalScope> findGlobal();
}
