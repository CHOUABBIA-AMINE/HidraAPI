/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PermissionRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.port.out
 *
 * @Description : Outbound repository port for identity permissions.
 *
 */
package dz.sh.hidra.modules.identity.application.port.out;

import dz.sh.hidra.modules.identity.domain.model.Permission;
import dz.sh.hidra.modules.identity.domain.value.PermissionCode;
import dz.sh.hidra.modules.identity.domain.value.PermissionId;

import java.util.List;
import java.util.Optional;

/**
 * Outbound repository port for identity permissions.
 *
 * <p>Business role: provides application services with permission catalog lookup and
 * persistence operations.</p>
 *
 * <p>Architecture role: application outbound port implemented later by infrastructure
 * persistence adapters. It does not depend on Spring Data, JPA, SQL, or platform
 * security plumbing.</p>
 *
 * <p>Validation responsibility: callers and implementations should use validated
 * permission identifiers and permission codes.</p>
 *
 * <p>Usage: depend on this port from permission and role application services.</p>
 */
public interface PermissionRepository {

    Permission save(Permission permission);

    Optional<Permission> findById(PermissionId permissionId);

    Optional<Permission> findByCode(PermissionCode permissionCode);

    List<Permission> findAll();

    boolean existsByCode(PermissionCode permissionCode);
}
