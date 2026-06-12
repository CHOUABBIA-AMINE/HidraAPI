/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaNominationScheduleLineRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for NominationScheduleLine.
 *
 */
package dz.sh.hidra.modules.planning.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.planning.application.port.out.NominationScheduleLineRepositoryPort;
import dz.sh.hidra.modules.planning.domain.model.NominationScheduleLine;
import dz.sh.hidra.modules.planning.infrastructure.persistence.mapper.PlanningPersistenceMapper;
import dz.sh.hidra.modules.planning.infrastructure.persistence.repository.NominationScheduleLineJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for NominationScheduleLine.
 */
@Component
public class JpaNominationScheduleLineRepositoryAdapter implements NominationScheduleLineRepositoryPort {

    private final NominationScheduleLineJpaRepository repository;

    public JpaNominationScheduleLineRepositoryAdapter(NominationScheduleLineJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "NominationScheduleLineJpaRepository must not be null.");
    }

    @Override
    public NominationScheduleLine save(NominationScheduleLine model) {
        return PlanningPersistenceMapper.toDomain(repository.save(PlanningPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<NominationScheduleLine> findById(String id) {
        return repository.findById(id).map(PlanningPersistenceMapper::toDomain);
    }
}
