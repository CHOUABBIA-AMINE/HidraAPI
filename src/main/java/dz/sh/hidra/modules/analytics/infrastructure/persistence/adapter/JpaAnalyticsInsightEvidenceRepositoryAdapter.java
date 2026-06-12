/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAnalyticsInsightEvidenceRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AnalyticsInsightEvidence.
 *
 */
package dz.sh.hidra.modules.analytics.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.analytics.application.port.out.AnalyticsInsightEvidenceRepositoryPort;
import dz.sh.hidra.modules.analytics.domain.model.AnalyticsInsightEvidence;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.mapper.AnalyticsPersistenceMapper;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.repository.AnalyticsInsightEvidenceJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AnalyticsInsightEvidence.
 */
@Component
public class JpaAnalyticsInsightEvidenceRepositoryAdapter implements AnalyticsInsightEvidenceRepositoryPort {

    private final AnalyticsInsightEvidenceJpaRepository repository;

    public JpaAnalyticsInsightEvidenceRepositoryAdapter(AnalyticsInsightEvidenceJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AnalyticsInsightEvidenceJpaRepository must not be null.");
    }

    @Override
    public AnalyticsInsightEvidence save(AnalyticsInsightEvidence model) {
        return AnalyticsPersistenceMapper.toDomain(repository.save(AnalyticsPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AnalyticsInsightEvidence> findById(String id) {
        return repository.findById(id).map(AnalyticsPersistenceMapper::toDomain);
    }
}
