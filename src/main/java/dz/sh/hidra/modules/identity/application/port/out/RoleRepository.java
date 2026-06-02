/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RoleRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.port.out
 *
 * @Description : Outbound repository port for identity roles.
 *
 */
package dz.sh.hidra.modules.identity.application.port.out;

import dz.sh.hidra.modules.identity.domain.model.Role;
import dz.sh.hidra.modules.identity.domain.value.RoleCode;
import dz.sh.hidra.modules.identity.domain.value.RoleId;

import java.util.List;
import java.util.Optional;

/**
 * Outbound repository port for identity roles.
 *
 * <p>Business role: provides application services with role lookup and persistence
 * operations for role administration and assignment use cases.</p>
 *
 * <p>Architecture role: application outbound port implemented later by infrastructure
 * persistence adapters. It does not depend on Spring Data, JPA, SQL, or platform
 * security plumbing.</p>
 *
 * <p>Validation responsibility: callers and implementations should use validated role
 * identifiers and role codes.</p>
 *
 * <p>Usage: depend on this port from role application services.</p>
 */
public interface RoleRepository {

    Role save(Role role);

    Optional<Role> findById(RoleId roleId);

    Optional<Role> findByCode(RoleCode roleCode);

    List<Role> findAll();

    boolean existsByCode(RoleCode roleCode);
}
