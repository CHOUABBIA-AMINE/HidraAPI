/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaGroupRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for Group.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.identity.application.port.out.GroupRepositoryPort;
import dz.sh.hidra.modules.identity.domain.model.Group;
import dz.sh.hidra.modules.identity.infrastructure.persistence.mapper.IdentityPersistenceMapper;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.GroupJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for Group.
 */
@Component
public class JpaGroupRepositoryAdapter implements GroupRepositoryPort {

    private final GroupJpaRepository repository;

    public JpaGroupRepositoryAdapter(GroupJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "GroupJpaRepository must not be null.");
    }

    @Override
    public Group save(Group model) {
        return IdentityPersistenceMapper.toDomain(
                repository.save(IdentityPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<Group> findById(String id) {
        return repository.findById(id).map(IdentityPersistenceMapper::toDomain);
    }
}
