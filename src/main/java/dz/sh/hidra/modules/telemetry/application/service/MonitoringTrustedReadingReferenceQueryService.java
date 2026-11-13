/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringTrustedReadingReferenceQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.service
 *
 * @Description : Resolves governed trusted readings without adding new trust eligibility rules.
 *
 */
package dz.sh.hidra.modules.telemetry.application.service;

import dz.sh.hidra.modules.telemetry.application.contract.monitoring.MonitoringTrustedReadingReferenceContract;
import dz.sh.hidra.modules.telemetry.application.port.out.TrustedTelemetryReadingRepositoryPort;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryTrustedReadPort;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryQualityAssessmentPort;
import dz.sh.hidra.modules.telemetry.domain.value.AssessmentStatus;
import dz.sh.hidra.modules.telemetry.domain.value.TrustLevel;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public final class MonitoringTrustedReadingReferenceQueryService implements MonitoringTrustedReadingReferenceContract {
    private final TrustedTelemetryReadingRepositoryPort readings;
    private final TelemetryTrustedReadPort published;
    private final TelemetryQualityAssessmentPort assessments;

    public MonitoringTrustedReadingReferenceQueryService(TrustedTelemetryReadingRepositoryPort readings) {
        this(readings, null, null);
    }

    @Autowired
    public MonitoringTrustedReadingReferenceQueryService(TrustedTelemetryReadingRepositoryPort readings,
            TelemetryTrustedReadPort published, TelemetryQualityAssessmentPort assessments) {
        this.readings = Objects.requireNonNull(readings);
        this.published = published;
        this.assessments = assessments;
    }

    @Override
    public Optional<EvaluationReading> forEvaluation(String id, String point, String assetType, String assetId) {
        if (published == null) return Optional.empty();
        return published.byId(point, id)
                .filter(result -> assetType.equals(result.reading().topologyAssetTypeCode())
                        && assetId.equals(result.reading().topologyAssetId()))
                .map(result -> {
                    var reading = result.reading();
                    var assessment = assessments.find(point, reading.readingId());
                    boolean qualified = assessment.status() == AssessmentStatus.PASSED
                            && assessment.id().equals(reading.qualityAssessmentId())
                            && assessment.trustLevel() == reading.trustLevel()
                            && Objects.equals(assessment.resolvedQualityCodeId(), reading.qualityCodeId())
                            && reading.bindingId() != null && reading.qualityAssessmentId() != null
                            && result.receivedAt() != null && result.assessedAt() != null
                            && !result.assessedAt().isAfter(reading.trustedAt())
                            && reading.trustLevel().compareTo(TrustLevel.MEDIUM) >= 0;
                    return new EvaluationReading(reading.id(), reading.readingId(), reading.qualityAssessmentId(),
                            reading.bindingId(), reading.unitId(), reading.qualityCodeId(), reading.trustLevel().name(),
                            reading.sourceTimestamp(), reading.trustedAt(), reading.numericValue(), qualified);
                });
    }

    @Override
    public Optional<Reading> resolve(String id) {
        if (id == null || id.isBlank()) return Optional.empty();
        String key = id.trim();
        return readings.findById(key).filter(r -> key.equals(r.id()))
                .map(r -> new Reading(r.id(), r.pointId(), r.trustLevel().name()));
    }
}
