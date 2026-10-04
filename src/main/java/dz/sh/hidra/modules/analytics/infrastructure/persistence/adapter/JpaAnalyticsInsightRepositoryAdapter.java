/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAnalyticsInsightRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AnalyticsInsight with catalog and lineage validation.
 *
 */
package dz.sh.hidra.modules.analytics.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.analytics.application.port.out.AnalyticsInsightRepositoryPort;
import dz.sh.hidra.modules.analytics.domain.exception.InvalidAnalyticsValueException;
import dz.sh.hidra.modules.analytics.domain.model.AnalyticsInsight;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.mapper.AnalyticsPersistenceMapper;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.repository.AnalyticsInsightJpaRepository;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class JpaAnalyticsInsightRepositoryAdapter implements AnalyticsInsightRepositoryPort {

    private static final String INSIGHT_TYPE_CATALOG = "INSIGHT_TYPE";
    private static final String ANALYTICS_SEVERITY_CATALOG = "ANALYTICS_SEVERITY";

    private final AnalyticsInsightJpaRepository repository;

    public JpaAnalyticsInsightRepositoryAdapter(AnalyticsInsightJpaRepository repository) {
        this.repository = Objects.requireNonNull(
                repository,
                "AnalyticsInsightJpaRepository must not be null."
        );
    }

    @Override
    public AnalyticsInsight save(AnalyticsInsight model) {
        validateGovernedReferences(model);
        return AnalyticsPersistenceMapper.toDomain(
                repository.save(AnalyticsPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<AnalyticsInsight> findById(String id) {
        return repository.findById(id).map(AnalyticsPersistenceMapper::toDomain);
    }

    private void validateGovernedReferences(AnalyticsInsight model) {
        if (!repository.existsActiveCatalogCode(INSIGHT_TYPE_CATALOG, model.insightType())) {
            throw new InvalidAnalyticsValueException(
                    "AnalyticsInsight insight type must resolve to an active INSIGHT_TYPE catalog entry: "
                            + model.insightType()
            );
        }

        if (model.severityId() != null
                && !repository.existsActiveCatalogEntryId(
                        ANALYTICS_SEVERITY_CATALOG,
                        model.severityId()
                )) {
            throw new InvalidAnalyticsValueException(
                    "AnalyticsInsight severity must resolve to an active ANALYTICS_SEVERITY catalog entry: "
                            + model.severityId()
            );
        }

        validateSource(
                "projection snapshot",
                model.sourceProjectionSnapshotId(),
                model.sourceProjectionSnapshotId() == null
                        || repository.existsProjectionSnapshot(model.sourceProjectionSnapshotId())
        );
        validateSource(
                "trend analysis",
                model.sourceTrendAnalysisId(),
                model.sourceTrendAnalysisId() == null
                        || repository.existsTrendAnalysis(model.sourceTrendAnalysisId())
        );
        validateSource(
                "model run",
                model.sourceModelRunId(),
                model.sourceModelRunId() == null
                        || repository.existsModelRun(model.sourceModelRunId())
        );
    }

    private static void validateSource(String sourceName, String sourceId, boolean exists) {
        if (sourceId != null && !exists) {
            throw new InvalidAnalyticsValueException(
                    "AnalyticsInsight source " + sourceName + " does not exist: " + sourceId
            );
        }
    }
}
