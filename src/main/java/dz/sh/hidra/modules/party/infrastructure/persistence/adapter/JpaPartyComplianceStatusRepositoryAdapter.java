/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaPartyComplianceStatusRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for PartyComplianceStatus.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.party.application.port.out.PartyComplianceStatusRepositoryPort;
import dz.sh.hidra.modules.party.domain.model.PartyComplianceStatus;
import dz.sh.hidra.modules.party.infrastructure.persistence.mapper.PartyPersistenceMapper;
import dz.sh.hidra.modules.party.infrastructure.persistence.repository.PartyComplianceStatusJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for PartyComplianceStatus.
 */
@Component
public class JpaPartyComplianceStatusRepositoryAdapter implements PartyComplianceStatusRepositoryPort {

    private final PartyComplianceStatusJpaRepository repository;

    public JpaPartyComplianceStatusRepositoryAdapter(PartyComplianceStatusJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "PartyComplianceStatusJpaRepository must not be null.");
    }

    @Override
    public PartyComplianceStatus save(PartyComplianceStatus model) {
        return PartyPersistenceMapper.toDomain(
                repository.save(PartyPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<PartyComplianceStatus> findById(String id) {
        return repository.findById(id).map(PartyPersistenceMapper::toDomain);
    }
}
