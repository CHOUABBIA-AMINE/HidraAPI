/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InMemoryGroupRepository
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.repository
 *
 * @Description : In-memory repository adapter for Group.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.repository;

import dz.sh.hidra.modules.identity.application.port.out.GroupRepositoryPort;
import dz.sh.hidra.modules.identity.domain.model.Group;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * In-memory repository adapter for Group.
 */
public class InMemoryGroupRepository implements GroupRepositoryPort {

    private final Map<String, Group> store = new LinkedHashMap<>();

    @Override
    public Group save(Group model) {
        store.put(model.id(), model);
        return model;
    }

    @Override
    public Optional<Group> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }
}
