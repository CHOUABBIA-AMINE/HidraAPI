/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaPartyRoleAssignmentRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for PartyRoleAssignment.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.party.application.port.out.PartyRoleAssignmentRepositoryPort;
import dz.sh.hidra.modules.party.domain.model.PartyRoleAssignment;
import dz.sh.hidra.modules.party.infrastructure.persistence.mapper.PartyPersistenceMapper;
import dz.sh.hidra.modules.party.infrastructure.persistence.repository.PartyRoleAssignmentJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for PartyRoleAssignment.
 */
@Component
public class JpaPartyRoleAssignmentRepositoryAdapter implements PartyRoleAssignmentRepositoryPort {

    private final PartyRoleAssignmentJpaRepository repository;

    public JpaPartyRoleAssignmentRepositoryAdapter(PartyRoleAssignmentJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "PartyRoleAssignmentJpaRepository must not be null.");
    }

    @Override
    public PartyRoleAssignment save(PartyRoleAssignment model) {
        return PartyPersistenceMapper.toDomain(
                repository.save(PartyPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<PartyRoleAssignment> findById(String id) {
        return repository.findById(id).map(PartyPersistenceMapper::toDomain);
    }
}
