/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaDigitalTwinReadinessAssessmentRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for DigitalTwinReadinessAssessment.
 *
 */
package dz.sh.hidra.modules.analytics.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.analytics.application.port.out.DigitalTwinReadinessAssessmentRepositoryPort;
import dz.sh.hidra.modules.analytics.domain.model.DigitalTwinReadinessAssessment;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.mapper.AnalyticsPersistenceMapper;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.repository.DigitalTwinReadinessAssessmentJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for DigitalTwinReadinessAssessment.
 */
@Component
public class JpaDigitalTwinReadinessAssessmentRepositoryAdapter implements DigitalTwinReadinessAssessmentRepositoryPort {

    private final DigitalTwinReadinessAssessmentJpaRepository repository;

    public JpaDigitalTwinReadinessAssessmentRepositoryAdapter(DigitalTwinReadinessAssessmentJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "DigitalTwinReadinessAssessmentJpaRepository must not be null.");
    }

    @Override
    public DigitalTwinReadinessAssessment save(DigitalTwinReadinessAssessment model) {
        return AnalyticsPersistenceMapper.toDomain(repository.save(AnalyticsPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<DigitalTwinReadinessAssessment> findById(String id) {
        return repository.findById(id).map(AnalyticsPersistenceMapper::toDomain);
    }
}
