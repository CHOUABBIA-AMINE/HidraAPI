/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RoleDomainRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.repository
 *
 * @Description : Domain repository contract for identity roles.
 *
 */
package dz.sh.hidra.modules.identity.domain.repository;

import dz.sh.hidra.modules.identity.domain.model.Role;
import dz.sh.hidra.modules.identity.domain.value.RoleCode;
import dz.sh.hidra.modules.identity.domain.value.RoleId;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository contract for roles.
 *
 * <p>Business role: provides role lookup and persistence boundaries required by identity
 * role administration and permission assignment use cases.</p>
 *
 * <p>Architecture role: domain-facing repository contract. Infrastructure adapters may
 * implement it later, but this interface remains free of Spring Data, JPA, SQL, and
 * platform security details.</p>
 *
 * <p>Validation responsibility: callers must pass validated {@link RoleId} and
 * {@link RoleCode} values. Implementations should preserve aggregate invariants when
 * storing and loading roles.</p>
 *
 * <p>Usage: depend on this contract from identity application services when roles must
 * be saved, loaded, or checked for uniqueness.</p>
 */
public interface RoleDomainRepository {

    Role save(Role role);

    Optional<Role> findById(RoleId roleId);

    Optional<Role> findByCode(RoleCode roleCode);

    List<Role> findAll();

    boolean existsByCode(RoleCode roleCode);
}
