/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanTargetReferenceValidation
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.infrastructure.persistence.adapter
 *
 * @Description : Validates locked local parents and owner evidence while retaining historical snapshots.
 *
 */
package dz.sh.hidra.modules.planning.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.planning.domain.model.PlanTarget;
import dz.sh.hidra.modules.planning.infrastructure.configuration.PlanningTargetValuePolicy;
import dz.sh.hidra.modules.planning.infrastructure.persistence.repository.*;
import dz.sh.hidra.modules.topology.application.contract.planning.PlanningTargetTopologyReferenceContract;
import dz.sh.hidra.modules.telemetry.application.contract.planning.PlanningTelemetryPointReferenceContract;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class PlanTargetReferenceValidation {
    private final PlanRevisionJpaRepository revisions;
    private final NominationJpaRepository nominations;
    private final PlanScenarioJpaRepository scenarios;
    private final PlanningCatalogEntryJpaRepository catalogs;
    private final PlanningTargetValuePolicy policies;
    private final PlanningTargetTopologyReferenceContract topology;
    private final PlanningTelemetryPointReferenceContract points;

    public PlanTargetReferenceValidation(PlanRevisionJpaRepository revisions, NominationJpaRepository nominations,
            PlanScenarioJpaRepository scenarios, PlanningCatalogEntryJpaRepository catalogs,
            PlanningTargetValuePolicy policies, PlanningTargetTopologyReferenceContract topology,
            PlanningTelemetryPointReferenceContract points) {
        this.revisions = Objects.requireNonNull(revisions);
        this.nominations = Objects.requireNonNull(nominations);
        this.scenarios = Objects.requireNonNull(scenarios);
        this.catalogs = Objects.requireNonNull(catalogs);
        this.policies = Objects.requireNonNull(policies);
        this.topology = Objects.requireNonNull(topology);
        this.points = Objects.requireNonNull(points);
    }

    public PlanTarget validate(PlanTarget target, PlanTarget old) {
        Objects.requireNonNull(target);
        var revision = revisions.findByIdForShare(target.revisionId())
                .orElseThrow(() -> invalid("Unknown revision"));
        require(target.revisionId().equals(revision.id()), "Revision identity mismatch");
        if (target.nominationId() != null) {
            var nomination = nominations.findByIdForShare(target.nominationId())
                    .orElseThrow(() -> invalid("Unknown nomination"));
            require(target.nominationId().equals(nomination.id()) && target.revisionId().equals(nomination.revisionId()),
                    "Nomination revision mismatch");
        }
        if (target.scenarioId() != null) {
            var scenario = scenarios.findByIdForShare(target.scenarioId())
                    .orElseThrow(() -> invalid("Unknown scenario"));
            require(target.scenarioId().equals(scenario.id()) && target.revisionId().equals(scenario.revisionId()),
                    "Scenario revision mismatch");
        }
        boolean sameValue = old != null && Objects.equals(old.targetTypeId(), target.targetTypeId())
                && sameNumber(old.targetValue(), target.targetValue())
                && Objects.equals(old.targetTextValue(), target.targetTextValue())
                && Objects.equals(old.unitId(), target.unitId());
        var catalog = catalogs.findByIdForShare(target.targetTypeId())
                .orElseThrow(() -> invalid("Unknown target type"));
        require(target.targetTypeId().equals(catalog.id()) && "TARGET_TYPE".equals(catalog.catalogName()),
                "Exact TARGET_TYPE membership required");
        var policy = policies.findByIdForShare(target.targetTypeId())
                .orElseThrow(() -> invalid("Owner-approved target value policy required"));
        require(target.targetTypeId().equals(policy.targetTypeId()), "Policy identity mismatch");
        require(sameValue || (policy.active() && catalog.active()), "Active fresh target policy and catalog required");
        switch (policy.representation()) {
            case NUMERIC -> require(target.targetValue() != null && nonblank(target.unitId()), "Numeric value and unit required");
            case TEXT -> require(nonblank(target.targetTextValue()), "Text value required");
        }

        boolean sameAsset = old != null && Objects.equals(old.topologyAssetType(), target.topologyAssetType())
                && Objects.equals(old.topologyAssetId(), target.topologyAssetId());
        String code;
        String name;
        if (sameAsset) {
            code = old.topologyAssetCode();
            name = old.topologyAssetNameSnapshot();
        } else {
            var asset = topology.resolve(target.topologyAssetType(), target.topologyAssetId())
                    .orElseThrow(() -> invalid("Unknown typed topology asset"));
            require(target.topologyAssetId().equals(asset.id()) && nonblank(asset.code()), "Topology identity/code mismatch");
            code = asset.code();
            name = asset.name();
        }
        String pointCode = null;
        if (target.telemetryPointId() != null) {
            if (old != null && Objects.equals(old.telemetryPointId(), target.telemetryPointId())) {
                pointCode = old.telemetryPointCodeSnapshot();
            } else {
                var point = points.resolve(target.telemetryPointId()).orElseThrow(() -> invalid("Unknown Telemetry point"));
                require(target.telemetryPointId().equals(point.id()) && nonblank(point.code()), "Telemetry identity/code mismatch");
                pointCode = point.code();
            }
        } else if (old != null && old.telemetryPointId() == null) {
            pointCode = old.telemetryPointCodeSnapshot();
        }
        return new PlanTarget(target.id(), target.revisionId(), target.scenarioId(), target.nominationId(),
                target.targetTypeId(), target.topologyAssetType(), target.topologyAssetId(), code, name,
                target.telemetryPointId(), pointCode, target.targetValue(), target.targetTextValue(), target.unitId(),
                target.toleranceLow(), target.toleranceHigh(), target.validFrom(), target.validTo(), target.priority(),
                target.status(), target.createdAt(), target.updatedAt());
    }

    private static boolean sameNumber(java.math.BigDecimal left, java.math.BigDecimal right) {
        return left == null ? right == null : right != null && left.compareTo(right) == 0;
    }
    private static boolean nonblank(String value) { return value != null && !value.isBlank(); }
    private static IllegalArgumentException invalid(String message) { return new IllegalArgumentException(message); }
    private static void require(boolean condition, String message) { if (!condition) throw invalid(message); }
}
