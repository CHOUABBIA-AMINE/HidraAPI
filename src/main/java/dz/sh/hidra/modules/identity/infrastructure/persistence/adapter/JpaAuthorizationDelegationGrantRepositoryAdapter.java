/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAuthorizationDelegationGrantRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.adapter
 *
 * @Description : Database-backed repository adapter for AuthorizationDelegationGrant.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.identity.application.port.out.AuthorizationDelegationGrantRepositoryPort;
import dz.sh.hidra.modules.identity.domain.model.AuthorizationDelegationGrant;
import dz.sh.hidra.modules.identity.infrastructure.persistence.mapper.IdentityPersistenceMapper;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.AuthorizationDelegationGrantJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AuthorizationDelegationGrant.
 */
@Component
public class JpaAuthorizationDelegationGrantRepositoryAdapter implements AuthorizationDelegationGrantRepositoryPort {

    private final AuthorizationDelegationGrantJpaRepository repository;

    public JpaAuthorizationDelegationGrantRepositoryAdapter(AuthorizationDelegationGrantJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AuthorizationDelegationGrantJpaRepository must not be null.");
    }

    @Override
    public AuthorizationDelegationGrant save(AuthorizationDelegationGrant model) {
        return IdentityPersistenceMapper.toDomain(repository.save(IdentityPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AuthorizationDelegationGrant> findById(String id) {
        return repository.findById(id).map(IdentityPersistenceMapper::toDomain);
    }
}
