/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaPlannedOperationWindowRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for PlannedOperationWindow.
 *
 */
package dz.sh.hidra.modules.planning.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.planning.application.port.out.PlannedOperationWindowRepositoryPort;
import dz.sh.hidra.modules.planning.domain.model.PlannedOperationWindow;
import dz.sh.hidra.modules.planning.infrastructure.persistence.mapper.PlanningPersistenceMapper;
import dz.sh.hidra.modules.planning.infrastructure.persistence.repository.PlannedOperationWindowJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for PlannedOperationWindow.
 */
@Component
public class JpaPlannedOperationWindowRepositoryAdapter implements PlannedOperationWindowRepositoryPort {

    private final PlannedOperationWindowJpaRepository repository;

    public JpaPlannedOperationWindowRepositoryAdapter(PlannedOperationWindowJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "PlannedOperationWindowJpaRepository must not be null.");
    }

    @Override
    public PlannedOperationWindow save(PlannedOperationWindow model) {
        return PlanningPersistenceMapper.toDomain(repository.save(PlanningPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<PlannedOperationWindow> findById(String id) {
        return repository.findById(id).map(PlanningPersistenceMapper::toDomain);
    }
}
