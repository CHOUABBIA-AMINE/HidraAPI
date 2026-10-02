/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaIdentityProviderRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for IdentityProvider.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.identity.application.port.out.IdentityProviderRepositoryPort;
import dz.sh.hidra.modules.identity.domain.model.IdentityProvider;
import dz.sh.hidra.modules.identity.infrastructure.persistence.mapper.IdentityPersistenceMapper;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.IdentityProviderJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for IdentityProvider.
 */
@Component
public class JpaIdentityProviderRepositoryAdapter implements IdentityProviderRepositoryPort {

    private final IdentityProviderJpaRepository repository;

    public JpaIdentityProviderRepositoryAdapter(IdentityProviderJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "IdentityProviderJpaRepository must not be null.");
    }

    @Override
    public IdentityProvider save(IdentityProvider model) {
        return IdentityPersistenceMapper.toDomain(
                repository.save(IdentityPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<IdentityProvider> findById(String id) {
        return repository.findById(id).map(IdentityPersistenceMapper::toDomain);
    }
}
