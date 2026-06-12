/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaSupplierQualificationRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for SupplierQualification.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.party.application.port.out.SupplierQualificationRepositoryPort;
import dz.sh.hidra.modules.party.domain.model.SupplierQualification;
import dz.sh.hidra.modules.party.infrastructure.persistence.mapper.PartyPersistenceMapper;
import dz.sh.hidra.modules.party.infrastructure.persistence.repository.SupplierQualificationJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for SupplierQualification.
 */
@Component
public class JpaSupplierQualificationRepositoryAdapter implements SupplierQualificationRepositoryPort {

    private final SupplierQualificationJpaRepository repository;

    public JpaSupplierQualificationRepositoryAdapter(SupplierQualificationJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "SupplierQualificationJpaRepository must not be null.");
    }

    @Override
    public SupplierQualification save(SupplierQualification model) {
        return PartyPersistenceMapper.toDomain(
                repository.save(PartyPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<SupplierQualification> findById(String id) {
        return repository.findById(id).map(PartyPersistenceMapper::toDomain);
    }
}
