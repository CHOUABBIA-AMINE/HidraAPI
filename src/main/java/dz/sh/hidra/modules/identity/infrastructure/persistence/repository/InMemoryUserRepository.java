/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InMemoryUserRepository
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.repository
 *
 * @Description : In-memory repository adapter for identity users.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.repository;

import dz.sh.hidra.modules.identity.application.port.out.UserRepositoryPort;
import dz.sh.hidra.modules.identity.domain.model.User;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * In-memory repository adapter for development/testing source alignment.
 */
public class InMemoryUserRepository implements UserRepositoryPort {

    private final Map<String, User> store = new LinkedHashMap<>();

    @Override
    public User save(User model) {
        store.put(model.id(), model);
        return model;
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }
}
