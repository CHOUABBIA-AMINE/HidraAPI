/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PermissionCatalog
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.repository
 *
 * @Description : Domain contract for identity permission catalog lookup operations.
 *
 */
package dz.sh.hidra.modules.identity.domain.repository;

import dz.sh.hidra.modules.identity.domain.model.Permission;
import dz.sh.hidra.modules.identity.domain.value.PermissionCode;
import dz.sh.hidra.modules.identity.domain.value.PermissionId;

import java.util.List;
import java.util.Optional;

/**
 * Domain contract for permission catalog lookup operations.
 *
 * <p>Business role: exposes the identity permission catalog so roles and access
 * policies can find the permissions they grant or evaluate.</p>
 *
 * <p>Architecture role: domain-facing repository contract. Infrastructure adapters may
 * implement this interface later, but this contract remains free of Spring Data, JPA,
 * SQL, and platform security details.</p>
 *
 * <p>Validation responsibility: callers must provide validated {@link PermissionId} and
 * {@link PermissionCode} values; implementations should reject missing or unknown
 * permissions according to their use-case requirements.</p>
 *
 * <p>Usage: inject or depend on this contract from identity domain/application logic
 * when a permission catalog lookup is required.</p>
 */
public interface PermissionCatalog {

    Optional<Permission> findById(PermissionId permissionId);

    Optional<Permission> findByCode(PermissionCode permissionCode);

    List<Permission> findAll();

    boolean existsByCode(PermissionCode permissionCode);
}
