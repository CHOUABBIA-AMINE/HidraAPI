/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaTelemetryQualityAssessmentRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for TelemetryQualityAssessment.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryQualityAssessmentRepositoryPort;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryQualityAssessment;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.mapper.TelemetryPersistenceMapper;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository.TelemetryQualityAssessmentJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for TelemetryQualityAssessment.
 */
@Component
public class JpaTelemetryQualityAssessmentRepositoryAdapter implements TelemetryQualityAssessmentRepositoryPort {

    private final TelemetryQualityAssessmentJpaRepository repository;

    public JpaTelemetryQualityAssessmentRepositoryAdapter(TelemetryQualityAssessmentJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "TelemetryQualityAssessmentJpaRepository must not be null.");
    }

    @Override
    public TelemetryQualityAssessment save(TelemetryQualityAssessment model) {
        return TelemetryPersistenceMapper.toDomain(
                repository.save(TelemetryPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<TelemetryQualityAssessment> findById(String id) {
        return repository.findById(id).map(TelemetryPersistenceMapper::toDomain);
    }
}
