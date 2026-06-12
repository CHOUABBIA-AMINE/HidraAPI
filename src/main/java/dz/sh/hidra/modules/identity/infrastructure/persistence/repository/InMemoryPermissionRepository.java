/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InMemoryPermissionRepository
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.repository
 *
 * @Description : In-memory repository adapter for Permission.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.repository;

import dz.sh.hidra.modules.identity.application.port.out.PermissionRepositoryPort;
import dz.sh.hidra.modules.identity.domain.model.Permission;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * In-memory repository adapter for Permission.
 */
public class InMemoryPermissionRepository implements PermissionRepositoryPort {

    private final Map<String, Permission> store = new LinkedHashMap<>();

    @Override
    public Permission save(Permission model) {
        store.put(model.id(), model);
        return model;
    }

    @Override
    public Optional<Permission> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }
}
