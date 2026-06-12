/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaContractorQualificationRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for ContractorQualification.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.party.application.port.out.ContractorQualificationRepositoryPort;
import dz.sh.hidra.modules.party.domain.model.ContractorQualification;
import dz.sh.hidra.modules.party.infrastructure.persistence.mapper.PartyPersistenceMapper;
import dz.sh.hidra.modules.party.infrastructure.persistence.repository.ContractorQualificationJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for ContractorQualification.
 */
@Component
public class JpaContractorQualificationRepositoryAdapter implements ContractorQualificationRepositoryPort {

    private final ContractorQualificationJpaRepository repository;

    public JpaContractorQualificationRepositoryAdapter(ContractorQualificationJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "ContractorQualificationJpaRepository must not be null.");
    }

    @Override
    public ContractorQualification save(ContractorQualification model) {
        return PartyPersistenceMapper.toDomain(
                repository.save(PartyPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<ContractorQualification> findById(String id) {
        return repository.findById(id).map(PartyPersistenceMapper::toDomain);
    }
}
