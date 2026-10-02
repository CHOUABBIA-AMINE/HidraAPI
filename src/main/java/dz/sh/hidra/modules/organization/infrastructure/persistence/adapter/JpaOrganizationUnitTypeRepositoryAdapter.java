/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaOrganizationUnitTypeRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for OrganizationUnitType.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.organization.application.port.out.OrganizationUnitTypeRepositoryPort;
import dz.sh.hidra.modules.organization.domain.model.OrganizationUnitType;
import dz.sh.hidra.modules.organization.infrastructure.persistence.mapper.OrganizationPersistenceMapper;
import dz.sh.hidra.modules.organization.infrastructure.persistence.repository.OrganizationUnitTypeJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for OrganizationUnitType.
 */
@Component
public class JpaOrganizationUnitTypeRepositoryAdapter implements OrganizationUnitTypeRepositoryPort {

    private final OrganizationUnitTypeJpaRepository repository;

    public JpaOrganizationUnitTypeRepositoryAdapter(OrganizationUnitTypeJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "OrganizationUnitTypeJpaRepository must not be null.");
    }

    @Override
    public OrganizationUnitType save(OrganizationUnitType model) {
        return OrganizationPersistenceMapper.toDomain(
                repository.save(OrganizationPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<OrganizationUnitType> findById(String id) {
        return repository.findById(id).map(OrganizationPersistenceMapper::toDomain);
    }
}
