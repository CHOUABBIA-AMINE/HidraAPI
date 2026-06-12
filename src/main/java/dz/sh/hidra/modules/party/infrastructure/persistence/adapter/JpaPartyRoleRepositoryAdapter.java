/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaPartyRoleRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for PartyRole.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.party.application.port.out.PartyRoleRepositoryPort;
import dz.sh.hidra.modules.party.domain.model.PartyRole;
import dz.sh.hidra.modules.party.infrastructure.persistence.mapper.PartyPersistenceMapper;
import dz.sh.hidra.modules.party.infrastructure.persistence.repository.PartyRoleJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for PartyRole.
 */
@Component
public class JpaPartyRoleRepositoryAdapter implements PartyRoleRepositoryPort {

    private final PartyRoleJpaRepository repository;

    public JpaPartyRoleRepositoryAdapter(PartyRoleJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "PartyRoleJpaRepository must not be null.");
    }

    @Override
    public PartyRole save(PartyRole model) {
        return PartyPersistenceMapper.toDomain(
                repository.save(PartyPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<PartyRole> findById(String id) {
        return repository.findById(id).map(PartyPersistenceMapper::toDomain);
    }
}
