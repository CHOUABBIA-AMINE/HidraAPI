/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InMemoryRoleRepository
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.repository
 *
 * @Description : In-memory repository adapter for Role.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.repository;

import dz.sh.hidra.modules.identity.application.port.out.RoleRepositoryPort;
import dz.sh.hidra.modules.identity.domain.model.Role;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * In-memory repository adapter for Role.
 */
public class InMemoryRoleRepository implements RoleRepositoryPort {

    private final Map<String, Role> store = new LinkedHashMap<>();

    @Override
    public Role save(Role model) {
        store.put(model.id(), model);
        return model;
    }

    @Override
    public Optional<Role> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }
}
