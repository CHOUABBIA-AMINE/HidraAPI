/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaGroupRoleGrantRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.adapter
 *
 * @Description : Database-backed repository adapter for GroupRoleGrant.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.identity.application.port.out.GroupRoleGrantRepositoryPort;
import dz.sh.hidra.modules.identity.domain.model.GroupRoleGrant;
import dz.sh.hidra.modules.identity.infrastructure.persistence.mapper.IdentityPersistenceMapper;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.GroupRoleGrantJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for GroupRoleGrant.
 */
@Component
public class JpaGroupRoleGrantRepositoryAdapter implements GroupRoleGrantRepositoryPort {

    private final GroupRoleGrantJpaRepository repository;

    public JpaGroupRoleGrantRepositoryAdapter(GroupRoleGrantJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "GroupRoleGrantJpaRepository must not be null.");
    }

    @Override
    public GroupRoleGrant save(GroupRoleGrant model) {
        return IdentityPersistenceMapper.toDomain(repository.save(IdentityPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<GroupRoleGrant> findById(String id) {
        return repository.findById(id).map(IdentityPersistenceMapper::toDomain);
    }
}
