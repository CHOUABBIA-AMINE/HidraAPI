/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaMetricEvaluationRunRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for MetricEvaluationRun.
 *
 */
package dz.sh.hidra.modules.analytics.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.analytics.application.port.out.MetricEvaluationRunRepositoryPort;
import dz.sh.hidra.modules.analytics.domain.exception.InvalidAnalyticsValueException;
import dz.sh.hidra.modules.analytics.domain.model.MetricEvaluationRun;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.mapper.AnalyticsPersistenceMapper;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.repository.MetricDefinitionVersionJpaRepository;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.repository.MetricEvaluationRunJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for MetricEvaluationRun.
 */
@Component
public class JpaMetricEvaluationRunRepositoryAdapter implements MetricEvaluationRunRepositoryPort {

    private final MetricEvaluationRunJpaRepository repository;
    private final MetricDefinitionVersionJpaRepository versionRepository;

    public JpaMetricEvaluationRunRepositoryAdapter(
            MetricEvaluationRunJpaRepository repository,
            MetricDefinitionVersionJpaRepository versionRepository
    ) {
        this.repository = Objects.requireNonNull(repository, "MetricEvaluationRunJpaRepository must not be null.");
        this.versionRepository = Objects.requireNonNull(
                versionRepository,
                "MetricDefinitionVersionJpaRepository must not be null."
        );
    }

    @Override
    public MetricEvaluationRun save(MetricEvaluationRun model) {
        Objects.requireNonNull(model, "MetricEvaluationRun must not be null.");

        var existing = repository.findById(model.id());
        if (existing.isPresent()) {
            MetricEvaluationRun previous = AnalyticsPersistenceMapper.toDomain(existing.orElseThrow());
            model.validateTransitionFrom(previous.runStatus());
        } else {
            validateMetricVersionEligibility(model);
        }

        return AnalyticsPersistenceMapper.toDomain(
                repository.save(AnalyticsPersistenceMapper.toEntity(model))
        );
    }

    private void validateMetricVersionEligibility(MetricEvaluationRun model) {
        var version = versionRepository.findById(model.metricDefinitionVersionId())
                .orElseThrow(() -> new InvalidAnalyticsValueException(
                        "MetricEvaluationRun metric definition version must exist."
                ));
        if (version.validFrom() != null && model.periodStart().isBefore(version.validFrom())) {
            throw new InvalidAnalyticsValueException(
                    "MetricEvaluationRun period starts before the metric definition version is valid."
            );
        }
        if (version.validTo() != null && model.periodEnd().isAfter(version.validTo())) {
            throw new InvalidAnalyticsValueException(
                    "MetricEvaluationRun period ends after the metric definition version is valid."
            );
        }
    }

    @Override
    public Optional<MetricEvaluationRun> findById(String id) {
        return repository.findById(id).map(AnalyticsPersistenceMapper::toDomain);
    }
}
