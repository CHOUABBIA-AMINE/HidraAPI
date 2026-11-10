/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningApplicationMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.application.mapper
 *
 * @Description : Maps planning domain models to DTOs.
 *
 */
package dz.sh.hidra.modules.planning.application.mapper;

import dz.sh.hidra.modules.planning.application.dto.OperationalPlanSummaryDto;
import dz.sh.hidra.modules.planning.application.dto.PlanningPeriodSummaryDto;
import dz.sh.hidra.modules.planning.domain.model.OperationalPlan;
import dz.sh.hidra.modules.planning.domain.model.PlanningPeriod;
import dz.sh.hidra.modules.planning.domain.model.PlanRevision;
import dz.sh.hidra.modules.planning.domain.model.PlanTarget;
import dz.sh.hidra.modules.planning.application.port.in.PlanningQueryUseCase.PlanRevisionView;
import dz.sh.hidra.modules.planning.application.port.in.PlanningQueryUseCase.PlanTargetView;

/**
 * Maps planning domain models to DTOs.
 */
public final class PlanningApplicationMapper {

    private PlanningApplicationMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static PlanningPeriodSummaryDto toSummary(PlanningPeriod period) {
        return new PlanningPeriodSummaryDto(period.id(), period.code(), period.nameFr(), period.periodStart(), period.periodEnd(), period.timeZone(), period.status(), period.companyPartyId());
    }

    public static OperationalPlanSummaryDto toSummary(OperationalPlan plan) {
        return new OperationalPlanSummaryDto(plan.id(), plan.periodId(), plan.code(), plan.nameFr(), plan.topologyScopeType(), plan.topologyScopeId(), plan.status(), plan.approvedRevisionId());
    }
    public static PlanRevisionView toRevisionView(PlanRevision revision) {
        return new PlanRevisionView(
                revision.id(), revision.planId(), revision.revisionNumber(), revision.revisionCode(), name(revision.status()),
                revision.changeReasonCodeId(), revision.changeReasonText(), revision.baseRevisionId(),
                revision.submittedByActorId(), revision.submittedAt(), revision.approvedByActorId(), revision.approvedAt(),
                revision.workflowInstanceId(), revision.createdAt(), revision.updatedAt()
        );
    }

    public static PlanTargetView toTargetView(PlanTarget target) {
        return new PlanTargetView(
                target.id(), target.revisionId(), target.scenarioId(), target.nominationId(), target.targetTypeId(),
                target.topologyAssetType(), target.topologyAssetId(), target.topologyAssetCode(), target.topologyAssetNameSnapshot(),
                target.telemetryPointId(), target.telemetryPointCodeSnapshot(), target.targetValue(), target.targetTextValue(), target.unitId(),
                target.toleranceLow(), target.toleranceHigh(), target.validFrom(), target.validTo(), target.priority(), name(target.status()),
                target.createdAt(), target.updatedAt()
        );
    }

    private static String name(Enum<?> value) {
        return value == null ? null : value.name();
    }
}
