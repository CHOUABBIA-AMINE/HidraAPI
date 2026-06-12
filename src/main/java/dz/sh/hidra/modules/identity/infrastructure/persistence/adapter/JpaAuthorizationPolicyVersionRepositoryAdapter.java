/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAuthorizationPolicyVersionRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.adapter
 *
 * @Description : Database-backed repository adapter for AuthorizationPolicyVersion.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.identity.application.port.out.AuthorizationPolicyVersionRepositoryPort;
import dz.sh.hidra.modules.identity.domain.model.AuthorizationPolicyVersion;
import dz.sh.hidra.modules.identity.infrastructure.persistence.mapper.IdentityPersistenceMapper;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.AuthorizationPolicyVersionJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AuthorizationPolicyVersion.
 */
@Component
public class JpaAuthorizationPolicyVersionRepositoryAdapter implements AuthorizationPolicyVersionRepositoryPort {

    private final AuthorizationPolicyVersionJpaRepository repository;

    public JpaAuthorizationPolicyVersionRepositoryAdapter(AuthorizationPolicyVersionJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AuthorizationPolicyVersionJpaRepository must not be null.");
    }

    @Override
    public AuthorizationPolicyVersion save(AuthorizationPolicyVersion model) {
        return IdentityPersistenceMapper.toDomain(repository.save(IdentityPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AuthorizationPolicyVersion> findById(String id) {
        return repository.findById(id).map(IdentityPersistenceMapper::toDomain);
    }
}
