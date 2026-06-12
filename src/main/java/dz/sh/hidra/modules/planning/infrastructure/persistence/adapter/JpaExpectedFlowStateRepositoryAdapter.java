/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaExpectedFlowStateRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for ExpectedFlowState.
 *
 */
package dz.sh.hidra.modules.planning.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.planning.application.port.out.ExpectedFlowStateRepositoryPort;
import dz.sh.hidra.modules.planning.domain.model.ExpectedFlowState;
import dz.sh.hidra.modules.planning.infrastructure.persistence.mapper.PlanningPersistenceMapper;
import dz.sh.hidra.modules.planning.infrastructure.persistence.repository.ExpectedFlowStateJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for ExpectedFlowState.
 */
@Component
public class JpaExpectedFlowStateRepositoryAdapter implements ExpectedFlowStateRepositoryPort {

    private final ExpectedFlowStateJpaRepository repository;

    public JpaExpectedFlowStateRepositoryAdapter(ExpectedFlowStateJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "ExpectedFlowStateJpaRepository must not be null.");
    }

    @Override
    public ExpectedFlowState save(ExpectedFlowState model) {
        return PlanningPersistenceMapper.toDomain(repository.save(PlanningPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<ExpectedFlowState> findById(String id) {
        return repository.findById(id).map(PlanningPersistenceMapper::toDomain);
    }
}
