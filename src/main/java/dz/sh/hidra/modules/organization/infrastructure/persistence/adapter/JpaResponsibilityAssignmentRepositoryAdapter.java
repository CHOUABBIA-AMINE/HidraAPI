/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaResponsibilityAssignmentRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for ResponsibilityAssignment.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.organization.application.port.out.ResponsibilityAssignmentRepositoryPort;
import dz.sh.hidra.modules.organization.domain.model.ResponsibilityAssignment;
import dz.sh.hidra.modules.organization.infrastructure.persistence.mapper.OrganizationPersistenceMapper;
import dz.sh.hidra.modules.organization.infrastructure.persistence.repository.ResponsibilityAssignmentJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for ResponsibilityAssignment.
 */
@Component
public class JpaResponsibilityAssignmentRepositoryAdapter implements ResponsibilityAssignmentRepositoryPort {

    private final ResponsibilityAssignmentJpaRepository repository;

    public JpaResponsibilityAssignmentRepositoryAdapter(ResponsibilityAssignmentJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "ResponsibilityAssignmentJpaRepository must not be null.");
    }

    @Override
    public ResponsibilityAssignment save(ResponsibilityAssignment model) {
        return OrganizationPersistenceMapper.toDomain(
                repository.save(OrganizationPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<ResponsibilityAssignment> findById(String id) {
        return repository.findById(id).map(OrganizationPersistenceMapper::toDomain);
    }
}
