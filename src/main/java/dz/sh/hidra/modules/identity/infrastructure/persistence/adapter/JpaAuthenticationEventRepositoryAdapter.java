/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAuthenticationEventRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.adapter
 *
 * @Description : Database-backed repository adapter for AuthenticationEvent.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.identity.application.port.out.AuthenticationEventRepositoryPort;
import dz.sh.hidra.modules.identity.domain.model.AuthenticationEvent;
import dz.sh.hidra.modules.identity.infrastructure.persistence.mapper.IdentityPersistenceMapper;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.AuthenticationEventJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AuthenticationEvent.
 */
@Component
public class JpaAuthenticationEventRepositoryAdapter implements AuthenticationEventRepositoryPort {

    private final AuthenticationEventJpaRepository repository;

    public JpaAuthenticationEventRepositoryAdapter(AuthenticationEventJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AuthenticationEventJpaRepository must not be null.");
    }

    @Override
    public AuthenticationEvent save(AuthenticationEvent model) {
        return IdentityPersistenceMapper.toDomain(repository.save(IdentityPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AuthenticationEvent> findById(String id) {
        return repository.findById(id).map(IdentityPersistenceMapper::toDomain);
    }
}
