/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InMemoryIdentityProviderRepository
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.repository
 *
 * @Description : In-memory repository adapter for IdentityProvider.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.repository;

import dz.sh.hidra.modules.identity.application.port.out.IdentityProviderRepositoryPort;
import dz.sh.hidra.modules.identity.domain.model.IdentityProvider;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * In-memory repository adapter for IdentityProvider.
 */
public class InMemoryIdentityProviderRepository implements IdentityProviderRepositoryPort {

    private final Map<String, IdentityProvider> store = new LinkedHashMap<>();

    @Override
    public IdentityProvider save(IdentityProvider model) {
        store.put(model.id(), model);
        return model;
    }

    @Override
    public Optional<IdentityProvider> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }
}
