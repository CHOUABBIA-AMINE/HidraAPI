/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaUserRoleGrantRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.adapter
 *
 * @Description : Database-backed repository adapter for UserRoleGrant.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.identity.application.port.out.UserRoleGrantRepositoryPort;
import dz.sh.hidra.modules.identity.domain.model.UserRoleGrant;
import dz.sh.hidra.modules.identity.infrastructure.persistence.mapper.IdentityPersistenceMapper;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.UserRoleGrantJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for UserRoleGrant.
 */
@Component
public class JpaUserRoleGrantRepositoryAdapter implements UserRoleGrantRepositoryPort {

    private final UserRoleGrantJpaRepository repository;

    public JpaUserRoleGrantRepositoryAdapter(UserRoleGrantJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "UserRoleGrantJpaRepository must not be null.");
    }

    @Override
    public UserRoleGrant save(UserRoleGrant model) {
        return IdentityPersistenceMapper.toDomain(repository.save(IdentityPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<UserRoleGrant> findById(String id) {
        return repository.findById(id).map(IdentityPersistenceMapper::toDomain);
    }
}
