/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaOrganizationUnitRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for OrganizationUnit.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.organization.application.port.out.OrganizationUnitRepositoryPort;
import dz.sh.hidra.modules.organization.domain.model.OrganizationUnit;
import dz.sh.hidra.modules.organization.infrastructure.persistence.mapper.OrganizationPersistenceMapper;
import dz.sh.hidra.modules.organization.infrastructure.persistence.repository.OrganizationUnitJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for OrganizationUnit.
 */
@Component
public class JpaOrganizationUnitRepositoryAdapter implements OrganizationUnitRepositoryPort {

    private final OrganizationUnitJpaRepository repository;

    public JpaOrganizationUnitRepositoryAdapter(OrganizationUnitJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "OrganizationUnitJpaRepository must not be null.");
    }

    @Override
    public OrganizationUnit save(OrganizationUnit model) {
        return OrganizationPersistenceMapper.toDomain(
                repository.save(OrganizationPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<OrganizationUnit> findById(String id) {
        return repository.findById(id).map(OrganizationPersistenceMapper::toDomain);
    }
}
