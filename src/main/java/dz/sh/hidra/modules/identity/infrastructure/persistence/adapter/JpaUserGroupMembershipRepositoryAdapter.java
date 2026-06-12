/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaUserGroupMembershipRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.adapter
 *
 * @Description : Database-backed repository adapter for UserGroupMembership.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.identity.application.port.out.UserGroupMembershipRepositoryPort;
import dz.sh.hidra.modules.identity.domain.model.UserGroupMembership;
import dz.sh.hidra.modules.identity.infrastructure.persistence.mapper.IdentityPersistenceMapper;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.UserGroupMembershipJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for UserGroupMembership.
 */
@Component
public class JpaUserGroupMembershipRepositoryAdapter implements UserGroupMembershipRepositoryPort {

    private final UserGroupMembershipJpaRepository repository;

    public JpaUserGroupMembershipRepositoryAdapter(UserGroupMembershipJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "UserGroupMembershipJpaRepository must not be null.");
    }

    @Override
    public UserGroupMembership save(UserGroupMembership model) {
        return IdentityPersistenceMapper.toDomain(repository.save(IdentityPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<UserGroupMembership> findById(String id) {
        return repository.findById(id).map(IdentityPersistenceMapper::toDomain);
    }
}
