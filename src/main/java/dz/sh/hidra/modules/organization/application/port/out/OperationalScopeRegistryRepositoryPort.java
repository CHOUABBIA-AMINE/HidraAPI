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
import dz.sh.hidra.modules.organization.domain.value.OperationalScopeType;

import java.util.Optional;

/**
 * Persists and resolves canonical operational-scope registry identities without
 * exposing JPA or another module's internals.
 *
 * <p>Registration receives an already normalized/owner-validated typed target.
 * Caller authorization and target-owner validation remain application concerns.
 * Database uniqueness is the final guard against duplicate concurrent registration.</p>
 */
public interface OperationalScopeRegistryRepositoryPort {

    /**
     * Idempotently registers a canonical typed target and returns its generated registry identity.
     *
     * <p>GLOBAL must use a null target ID. CUSTOM remains unsupported until an approved
     * namespace/owner contract exists.</p>
     *
     * @param type canonical scope type
     * @param targetId canonical target-owner ID, null only for GLOBAL
     * @return existing or newly persisted registry record
     */
    OperationalScope register(OperationalScopeType type, String targetId);

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
