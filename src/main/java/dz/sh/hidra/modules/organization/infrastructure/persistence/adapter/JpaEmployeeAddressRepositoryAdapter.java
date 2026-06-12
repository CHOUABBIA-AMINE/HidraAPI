/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaEmployeeAddressRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for EmployeeAddress.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.organization.application.port.out.EmployeeAddressRepositoryPort;
import dz.sh.hidra.modules.organization.domain.model.EmployeeAddress;
import dz.sh.hidra.modules.organization.infrastructure.persistence.mapper.OrganizationPersistenceMapper;
import dz.sh.hidra.modules.organization.infrastructure.persistence.repository.EmployeeAddressJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for EmployeeAddress.
 */
@Component
public class JpaEmployeeAddressRepositoryAdapter implements EmployeeAddressRepositoryPort {

    private final EmployeeAddressJpaRepository repository;

    public JpaEmployeeAddressRepositoryAdapter(EmployeeAddressJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "EmployeeAddressJpaRepository must not be null.");
    }

    @Override
    public EmployeeAddress save(EmployeeAddress model) {
        return OrganizationPersistenceMapper.toDomain(
                repository.save(OrganizationPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<EmployeeAddress> findById(String id) {
        return repository.findById(id).map(OrganizationPersistenceMapper::toDomain);
    }
}
