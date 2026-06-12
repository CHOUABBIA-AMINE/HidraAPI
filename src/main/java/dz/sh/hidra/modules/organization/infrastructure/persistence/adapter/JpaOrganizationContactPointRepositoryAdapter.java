/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaOrganizationContactPointRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for OrganizationContactPoint.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.organization.application.port.out.OrganizationContactPointRepositoryPort;
import dz.sh.hidra.modules.organization.domain.model.OrganizationContactPoint;
import dz.sh.hidra.modules.organization.infrastructure.persistence.mapper.OrganizationPersistenceMapper;
import dz.sh.hidra.modules.organization.infrastructure.persistence.repository.OrganizationContactPointJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for OrganizationContactPoint.
 */
@Component
public class JpaOrganizationContactPointRepositoryAdapter implements OrganizationContactPointRepositoryPort {

    private final OrganizationContactPointJpaRepository repository;

    public JpaOrganizationContactPointRepositoryAdapter(OrganizationContactPointJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "OrganizationContactPointJpaRepository must not be null.");
    }

    @Override
    public OrganizationContactPoint save(OrganizationContactPoint model) {
        return OrganizationPersistenceMapper.toDomain(
                repository.save(OrganizationPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<OrganizationContactPoint> findById(String id) {
        return repository.findById(id).map(OrganizationPersistenceMapper::toDomain);
    }
}
