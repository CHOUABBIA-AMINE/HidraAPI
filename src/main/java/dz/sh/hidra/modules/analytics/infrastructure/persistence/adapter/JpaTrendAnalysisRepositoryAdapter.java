/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaTrendAnalysisRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for TrendAnalysis.
 *
 */
package dz.sh.hidra.modules.analytics.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.analytics.application.port.out.TrendAnalysisRepositoryPort;
import dz.sh.hidra.modules.analytics.domain.model.TrendAnalysis;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.mapper.AnalyticsPersistenceMapper;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.repository.TrendAnalysisJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for TrendAnalysis.
 */
@Component
public class JpaTrendAnalysisRepositoryAdapter implements TrendAnalysisRepositoryPort {

    private final TrendAnalysisJpaRepository repository;

    public JpaTrendAnalysisRepositoryAdapter(TrendAnalysisJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "TrendAnalysisJpaRepository must not be null.");
    }

    @Override
    public TrendAnalysis save(TrendAnalysis model) {
        return AnalyticsPersistenceMapper.toDomain(repository.save(AnalyticsPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<TrendAnalysis> findById(String id) {
        return repository.findById(id).map(AnalyticsPersistenceMapper::toDomain);
    }
}
