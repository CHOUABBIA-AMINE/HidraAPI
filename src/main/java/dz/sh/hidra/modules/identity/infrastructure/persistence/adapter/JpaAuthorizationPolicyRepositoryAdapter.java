/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAuthorizationPolicyRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.adapter
 *
 * @Description : Database-backed repository adapter for AuthorizationPolicy.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.identity.application.port.out.AuthorizationPolicyRepositoryPort;
import dz.sh.hidra.modules.identity.domain.model.AuthorizationPolicy;
import dz.sh.hidra.modules.identity.infrastructure.persistence.mapper.IdentityPersistenceMapper;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.AuthorizationPolicyJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AuthorizationPolicy.
 */
@Component
public class JpaAuthorizationPolicyRepositoryAdapter implements AuthorizationPolicyRepositoryPort {

    private final AuthorizationPolicyJpaRepository repository;

    public JpaAuthorizationPolicyRepositoryAdapter(AuthorizationPolicyJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AuthorizationPolicyJpaRepository must not be null.");
    }

    @Override
    public AuthorizationPolicy save(AuthorizationPolicy model) {
        return IdentityPersistenceMapper.toDomain(repository.save(IdentityPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AuthorizationPolicy> findById(String id) {
        return repository.findById(id).map(IdentityPersistenceMapper::toDomain);
    }
}
