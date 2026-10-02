/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningQueryUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.application.port.in
 *
 * @Description : Read-only planning query contract for HWEB-010.
 *
 */
package dz.sh.hidra.modules.planning.application.port.in;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public interface PlanningQueryUseCase {

    Page<PlanningPeriodView> periods(int page, int size);

    PlanningPeriodView period(String id);

    Page<OperationalPlanView> operationalPlans(int page, int size);

    OperationalPlanView operationalPlan(String id);

    Page<PlanRevisionView> revisions(String planId, int page, int size);

    PlanRevisionView revision(String id);

    Page<NominationView> nominations(String revisionId, int page, int size);

    NominationView nomination(String id);

    Page<PlanTargetView> targets(String revisionId, int page, int size);

    PlanTargetView target(String id);

    record Page<T>(List<T> content, int page, int size, long totalElements, int totalPages, boolean hasNext) { }

    record PlanningPeriodView(
            String id,
            String code,
            String nameAr,
            String nameFr,
            String nameEn,
            String periodTypeId,
            Instant periodStart,
            Instant periodEnd,
            String timeZone,
            String status,
            String createdByActorId,
            Instant createdAt,
            Instant updatedAt
    ) { }

    record OperationalPlanView(
            String id,
            String periodId,
            String code,
            String nameAr,
            String nameFr,
            String nameEn,
            String planTypeId,
            String productTypeId,
            String topologyScopeType,
            String topologyScopeId,
            String topologyScopeCode,
            String topologyScopeNameSnapshot,
            String responsibleOrganizationUnitId,
            String status,
            String currentRevisionId,
            String approvedRevisionId,
            String createdByActorId,
            Instant createdAt,
            Instant updatedAt
    ) { }

    record PlanRevisionView(
            String id,
            String planId,
            int revisionNumber,
            String revisionCode,
            String status,
            String changeReasonCodeId,
            String changeReasonText,
            String baseRevisionId,
            String submittedByActorId,
            Instant submittedAt,
            String approvedByActorId,
            Instant approvedAt,
            String workflowInstanceId,
            Instant createdAt,
            Instant updatedAt
    ) { }

    record NominationView(
            String id,
            String revisionId,
            String scenarioId,
            String code,
            String nominationTypeId,
            String productTypeId,
            BigDecimal quantity,
            String quantityUnitId,
            BigDecimal rate,
            String rateUnitId,
            String sourceAssetType,
            String sourceAssetId,
            String sourceAssetCode,
            String destinationAssetType,
            String destinationAssetId,
            String destinationAssetCode,
            String shipperPartyId,
            String shipperPartyCodeSnapshot,
            String counterpartyId,
            String contractReferenceId,
            Integer priority,
            String status,
            Instant periodStart,
            Instant periodEnd,
            Instant createdAt,
            Instant updatedAt
    ) { }

    record PlanTargetView(
            String id,
            String revisionId,
            String scenarioId,
            String nominationId,
            String targetTypeId,
            String topologyAssetType,
            String topologyAssetId,
            String topologyAssetCode,
            String topologyAssetNameSnapshot,
            String telemetryPointId,
            String telemetryPointCodeSnapshot,
            BigDecimal targetValue,
            String targetTextValue,
            String unitId,
            BigDecimal toleranceLow,
            BigDecimal toleranceHigh,
            Instant validFrom,
            Instant validTo,
            Integer priority,
            String status,
            Instant createdAt,
            Instant updatedAt
    ) { }
}
