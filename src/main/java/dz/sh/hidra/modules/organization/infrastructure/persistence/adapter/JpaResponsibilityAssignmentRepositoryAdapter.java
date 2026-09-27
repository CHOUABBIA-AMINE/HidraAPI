/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaResponsibilityAssignmentRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
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
import dz.sh.hidra.modules.organization.domain.value.AssignmentStatus;
import dz.sh.hidra.modules.organization.domain.value.ResponsibilityType;
import dz.sh.hidra.modules.organization.domain.value.ResponsibilityAssigneeType;
import dz.sh.hidra.modules.organization.infrastructure.persistence.mapper.ResponsibilityAssignmentPersistenceMapper;
import dz.sh.hidra.modules.organization.infrastructure.persistence.repository.ResponsibilityAssignmentJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for ResponsibilityAssignment.
 */
@Component
public class JpaResponsibilityAssignmentRepositoryAdapter implements ResponsibilityAssignmentRepositoryPort {

    private final ResponsibilityAssignmentJpaRepository repository;

    public JpaResponsibilityAssignmentRepositoryAdapter(ResponsibilityAssignmentJpaRepository repository) {
        this.repository = Objects.requireNonNull(
                repository,
                "ResponsibilityAssignmentJpaRepository must not be null."
        );
    }

    @Override
    public ResponsibilityAssignment save(ResponsibilityAssignment model) {
        return ResponsibilityAssignmentPersistenceMapper.toDomain(
                repository.save(ResponsibilityAssignmentPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<ResponsibilityAssignment> findById(String id) {
        return repository.findById(id).map(ResponsibilityAssignmentPersistenceMapper::toDomain);
    }

    @Override
    public List<ResponsibilityAssignment> findAll() {
        return repository.findAll().stream()
                .map(ResponsibilityAssignmentPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public List<ResponsibilityAssignment> findByScopeId(Long scopeId) {
        return repository.findByScopeId(scopeId).stream().map(ResponsibilityAssignmentPersistenceMapper::toDomain).toList();
    }

    @Override
    public List<ResponsibilityAssignment> findByAssignee(
            ResponsibilityAssigneeType assigneeType,
            String assigneeId
    ) {
        return repository.findByAssigneeTypeAndAssigneeId(assigneeType, assigneeId).stream().map(ResponsibilityAssignmentPersistenceMapper::toDomain).toList();
    }

    @Override
    public List<ResponsibilityAssignment> findActiveByAssigneeAndResponsibilityAndScope(
            ResponsibilityAssigneeType assigneeType,
            String assigneeId,
            ResponsibilityType responsibilityType,
            Long scopeId
    ) {
        return repository
                .findByAssigneeTypeAndAssigneeIdAndResponsibilityTypeAndScopeIdAndStatus(
                        assigneeType,
                        assigneeId,
                        responsibilityType,
                        scopeId,
                        AssignmentStatus.ACTIVE
                )
                .stream()
                .map(ResponsibilityAssignmentPersistenceMapper::toDomain)
                .toList();
    }
}
