/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaShiftAssignmentRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for ShiftAssignment.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.organization.application.port.out.ShiftAssignmentRepositoryPort;
import dz.sh.hidra.modules.organization.domain.model.ShiftAssignment;
import dz.sh.hidra.modules.organization.infrastructure.persistence.mapper.OrganizationPersistenceMapper;
import dz.sh.hidra.modules.organization.infrastructure.persistence.repository.ShiftAssignmentJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for ShiftAssignment.
 */
@Component
public class JpaShiftAssignmentRepositoryAdapter implements ShiftAssignmentRepositoryPort {

    private final ShiftAssignmentJpaRepository repository;

    public JpaShiftAssignmentRepositoryAdapter(ShiftAssignmentJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "ShiftAssignmentJpaRepository must not be null.");
    }

    @Override
    public ShiftAssignment save(ShiftAssignment model) {
        return OrganizationPersistenceMapper.toDomain(
                repository.save(OrganizationPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<ShiftAssignment> findById(String id) {
        return repository.findById(id).map(OrganizationPersistenceMapper::toDomain);
    }
}
