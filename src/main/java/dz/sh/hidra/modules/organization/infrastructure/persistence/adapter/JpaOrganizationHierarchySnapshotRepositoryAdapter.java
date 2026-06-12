/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaOrganizationHierarchySnapshotRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for OrganizationHierarchySnapshot.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.organization.application.port.out.OrganizationHierarchySnapshotRepositoryPort;
import dz.sh.hidra.modules.organization.domain.model.OrganizationHierarchySnapshot;
import dz.sh.hidra.modules.organization.infrastructure.persistence.mapper.OrganizationPersistenceMapper;
import dz.sh.hidra.modules.organization.infrastructure.persistence.repository.OrganizationHierarchySnapshotJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for OrganizationHierarchySnapshot.
 */
@Component
public class JpaOrganizationHierarchySnapshotRepositoryAdapter implements OrganizationHierarchySnapshotRepositoryPort {

    private final OrganizationHierarchySnapshotJpaRepository repository;

    public JpaOrganizationHierarchySnapshotRepositoryAdapter(OrganizationHierarchySnapshotJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "OrganizationHierarchySnapshotJpaRepository must not be null.");
    }

    @Override
    public OrganizationHierarchySnapshot save(OrganizationHierarchySnapshot model) {
        return OrganizationPersistenceMapper.toDomain(
                repository.save(OrganizationPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<OrganizationHierarchySnapshot> findById(String id) {
        return repository.findById(id).map(OrganizationPersistenceMapper::toDomain);
    }
}
