/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaVendorQualificationRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for VendorQualification.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.party.application.port.out.VendorQualificationRepositoryPort;
import dz.sh.hidra.modules.party.domain.model.VendorQualification;
import dz.sh.hidra.modules.party.infrastructure.persistence.mapper.PartyPersistenceMapper;
import dz.sh.hidra.modules.party.infrastructure.persistence.repository.VendorQualificationJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for VendorQualification.
 */
@Component
public class JpaVendorQualificationRepositoryAdapter implements VendorQualificationRepositoryPort {

    private final VendorQualificationJpaRepository repository;

    public JpaVendorQualificationRepositoryAdapter(VendorQualificationJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "VendorQualificationJpaRepository must not be null.");
    }

    @Override
    public VendorQualification save(VendorQualification model) {
        return PartyPersistenceMapper.toDomain(
                repository.save(PartyPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<VendorQualification> findById(String id) {
        return repository.findById(id).map(PartyPersistenceMapper::toDomain);
    }
}
