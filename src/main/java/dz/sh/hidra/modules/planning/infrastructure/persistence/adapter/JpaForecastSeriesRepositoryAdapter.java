/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaForecastSeriesRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for ForecastSeries.
 *
 */
package dz.sh.hidra.modules.planning.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.planning.application.port.out.ForecastSeriesRepositoryPort;
import dz.sh.hidra.modules.planning.domain.model.ForecastSeries;
import dz.sh.hidra.modules.planning.infrastructure.persistence.mapper.PlanningPersistenceMapper;
import dz.sh.hidra.modules.planning.infrastructure.persistence.repository.ForecastSeriesJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for ForecastSeries.
 */
@Component
public class JpaForecastSeriesRepositoryAdapter implements ForecastSeriesRepositoryPort {

    private final ForecastSeriesJpaRepository repository;

    public JpaForecastSeriesRepositoryAdapter(ForecastSeriesJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "ForecastSeriesJpaRepository must not be null.");
    }

    @Override
    public ForecastSeries save(ForecastSeries model) {
        return PlanningPersistenceMapper.toDomain(repository.save(PlanningPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<ForecastSeries> findById(String id) {
        return repository.findById(id).map(PlanningPersistenceMapper::toDomain);
    }
}
