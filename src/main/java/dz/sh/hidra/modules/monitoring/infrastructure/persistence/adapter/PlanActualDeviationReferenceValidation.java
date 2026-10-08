/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanActualDeviationReferenceValidation
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.infrastructure.persistence.adapter
 *
 * @Description : Resolves target and Telemetry evidence and locks coherent Monitoring evaluation context.
 *
 */
package dz.sh.hidra.modules.monitoring.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.monitoring.application.service.DeviationReferenceValidation;
import dz.sh.hidra.modules.monitoring.domain.model.PlanActualDeviation;
import dz.sh.hidra.modules.monitoring.infrastructure.persistence.repository.MonitoringEvaluationJpaRepository;
import dz.sh.hidra.modules.planning.application.contract.monitoring.MonitoringPlanTargetReferenceContract;
import dz.sh.hidra.modules.telemetry.application.contract.monitoring.MonitoringTelemetryPointReferenceContract;
import dz.sh.hidra.modules.telemetry.application.contract.monitoring.MonitoringTrustedReadingReferenceContract;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class PlanActualDeviationReferenceValidation implements DeviationReferenceValidation {
    private final MonitoringPlanTargetReferenceContract targets;
    private final MonitoringTelemetryPointReferenceContract points;
    private final MonitoringTrustedReadingReferenceContract readings;
    private final MonitoringEvaluationJpaRepository evaluations;

    public PlanActualDeviationReferenceValidation(MonitoringPlanTargetReferenceContract targets,
            MonitoringTelemetryPointReferenceContract points, MonitoringTrustedReadingReferenceContract readings,
            MonitoringEvaluationJpaRepository evaluations) {
        this.targets = Objects.requireNonNull(targets);
        this.points = Objects.requireNonNull(points);
        this.readings = Objects.requireNonNull(readings);
        this.evaluations = Objects.requireNonNull(evaluations);
    }

    @Override
    public PlanActualDeviation validate(PlanActualDeviation requested, PlanActualDeviation stored) {
        Objects.requireNonNull(requested);
        var target = targets.resolve(requested.planTargetId()).orElseThrow(() -> invalid("Unknown Planning target"));
        require(requested.planTargetId().equals(target.id()), "Planning target identity mismatch");
        require(requested.topologyAssetId().equals(target.topologyAssetId()), "Target topology identity mismatch");
        if (requested.topologyAssetType() != null)
            require(requested.topologyAssetType().equals(target.topologyAssetType()), "Target topology namespace mismatch");

        boolean samePoint = stored != null && Objects.equals(stored.telemetryPointId(), requested.telemetryPointId());
        boolean sameReading = stored != null && Objects.equals(stored.trustedTelemetryReadingId(), requested.trustedTelemetryReadingId());
        boolean sameContext = stored != null && Objects.equals(stored.planTargetId(), requested.planTargetId())
                && Objects.equals(stored.evaluationId(), requested.evaluationId())
                && Objects.equals(stored.topologyAssetType(), requested.topologyAssetType())
                && Objects.equals(stored.topologyAssetId(), requested.topologyAssetId());
        if (requested.telemetryPointId() != null && !samePoint)
            require(points.exists(requested.telemetryPointId()), "Unknown Telemetry point");

        String readingPoint = null;
        if (requested.trustedTelemetryReadingId() != null && (!sameReading || !samePoint || !sameContext)) {
            var reading = readings.resolve(requested.trustedTelemetryReadingId())
                    .orElseThrow(() -> invalid("Unknown trusted Telemetry reading"));
            require(requested.trustedTelemetryReadingId().equals(reading.id()), "Trusted-reading identity mismatch");
            require(reading.pointId() != null && !reading.pointId().isBlank(), "Trusted reading lacks point provenance");
            readingPoint = reading.pointId();
            if (requested.telemetryPointId() != null)
                require(requested.telemetryPointId().equals(readingPoint), "Reading and point identities disagree");
        }
        if (requested.evaluationId() != null) {
            var evaluation = evaluations.findByIdForShare(requested.evaluationId())
                    .orElseThrow(() -> invalid("Unknown Monitoring evaluation"));
            require(requested.evaluationId().equals(evaluation.id()), "Evaluation identity mismatch");
            compatible(evaluation.planRevisionId(), target.revisionId(), "Evaluation revision mismatch");
            compatible(evaluation.topologyAssetType(), target.topologyAssetType(), "Evaluation topology namespace mismatch");
            compatible(evaluation.topologyAssetId(), target.topologyAssetId(), "Evaluation topology identity mismatch");
            compatible(evaluation.topologyAssetType(), requested.topologyAssetType(), "Evaluation/deviation namespace mismatch");
            compatible(evaluation.topologyAssetId(), requested.topologyAssetId(), "Evaluation/deviation topology mismatch");
            compatible(evaluation.telemetryPointId(), target.telemetryPointId(), "Evaluation/target point mismatch");
            compatible(evaluation.telemetryPointId(), requested.telemetryPointId(), "Evaluation/deviation point mismatch");
            compatible(evaluation.telemetryPointId(), readingPoint, "Evaluation/reading point mismatch");
            // An unrelated historical update retains unchanged owner evidence; it is not refreshed.
        }
        String code = stored != null && Objects.equals(stored.topologyAssetType(), requested.topologyAssetType())
                && Objects.equals(stored.topologyAssetId(), requested.topologyAssetId())
                ? stored.topologyAssetCode() : requested.topologyAssetCode();
        return new PlanActualDeviation(requested.id(),requested.evaluationId(),requested.planTargetId(),
                requested.expectedFlowStateId(),requested.trustedTelemetryReadingId(),requested.telemetryPointId(),
                requested.topologyAssetType(),requested.topologyAssetId(),code,requested.actualValue(),
                requested.expectedValue(),requested.differenceValue(),requested.differencePercent(),requested.unitId(),
                requested.severity(),requested.status(),requested.detectedAt(),requested.resolvedAt(),
                requested.reasonCode(),requested.reasonMessage());
    }
    private static void compatible(String supplied, String evidence, String message) {
        if (supplied != null && evidence != null) require(supplied.equals(evidence), message);
    }
    private static void require(boolean condition, String message) { if (!condition) throw invalid(message); }
    private static IllegalArgumentException invalid(String message) { return new IllegalArgumentException(message); }
}
