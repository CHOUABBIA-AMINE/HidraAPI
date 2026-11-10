/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthorPlanningDraftUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-10
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.application.port.in
 *
 * @Description : Authors scoped draft revisions and targets with explicit concurrency tokens.
 *
 */
package dz.sh.hidra.modules.planning.application.port.in;

import dz.sh.hidra.modules.planning.application.port.in.PlanningQueryUseCase.PlanRevisionView;
import dz.sh.hidra.modules.planning.application.port.in.PlanningQueryUseCase.PlanTargetView;
import java.math.BigDecimal;
import java.time.Instant;

public interface AuthorPlanningDraftUseCase {

    PlanRevisionView createRevision(String planId, RevisionCommand command);

    TargetResult createTarget(String revisionId, TargetCommand command);

    TargetResult replaceTarget(String targetId, TargetCommand command);

    record RevisionCommand(Instant expectedPlanUpdatedAt, String changeReasonCodeId, String changeReasonText) { }

    record TargetCommand(Instant expectedRevisionUpdatedAt, Instant expectedTargetUpdatedAt,
            String scenarioId, String nominationId, String targetTypeId, String topologyAssetType,
            String topologyAssetId, String telemetryPointId, BigDecimal targetValue, String targetTextValue,
            String unitId, BigDecimal toleranceLow, BigDecimal toleranceHigh,
            Instant validFrom, Instant validTo, Integer priority) { }

    record TargetResult(PlanTargetView target, Instant revisionUpdatedAt) { }
}
