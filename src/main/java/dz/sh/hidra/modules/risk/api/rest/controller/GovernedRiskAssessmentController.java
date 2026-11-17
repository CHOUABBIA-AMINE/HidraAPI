/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : GovernedRiskAssessmentController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 * @Type        : Class
 * @Layer       : API
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.api.rest.controller
 * @Description : Expose only Risk-authorized governed assessment and approved history projections.
 */
package dz.sh.hidra.modules.risk.api.rest.controller;

import dz.sh.hidra.modules.risk.api.rest.response.GovernedRiskAssessmentResponse;
import dz.sh.hidra.modules.risk.domain.model.GovernedRiskAssessment;
import dz.sh.hidra.modules.risk.application.port.in.ReadGovernedRiskAssessmentUseCase;
import io.swagger.v3.oas.annotations.Operation;
import dz.sh.hidra.platform.permissions.NativeProtectedOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/risk/assessments")
@Tag(name="Risk governed assessments")
public class GovernedRiskAssessmentController {
    private final ReadGovernedRiskAssessmentUseCase reads;
    public GovernedRiskAssessmentController(ReadGovernedRiskAssessmentUseCase reads){this.reads=reads;}
    @NativeProtectedOperation
    @Operation(operationId="getGovernedRiskAssessment")
    @GetMapping("/{id}")
    public GovernedRiskAssessmentResponse assessment(@PathVariable String id) {
        return map(reads.read(id));
    }
    @NativeProtectedOperation
    @Operation(operationId="getGovernedRiskAssessmentDecisionHistory")
    @GetMapping("/{id}/decision-history")
    public List<GovernedRiskAssessmentResponse.DecisionHistory> decisionHistory(@PathVariable String id) {
        return reads.decisionHistory(id).stream().map(GovernedRiskAssessmentController::mapDecision).toList();
    }
    private static GovernedRiskAssessmentResponse map(GovernedRiskAssessment assessment) {
        return new GovernedRiskAssessmentResponse(assessment.assessmentId(),
                assessment.companyPartyId(),assessment.facilityId(),assessment.status(),
                assessment.inherentScore(),assessment.inherentRatingId(),
                assessment.inherentCellId(),assessment.inherentMatrixId(),
                assessment.inherentMatrixVersion(),assessment.alarmId(),
                assessment.alarmCodeSnapshot(),assessment.alarmRaisedAt(),
                assessment.submissionFormat(),assessment.submissionDigest(),
                assessment.submittedAt(),assessment.decisions().stream()
                        .map(GovernedRiskAssessmentController::mapDecision).toList());
    }
    private static GovernedRiskAssessmentResponse.DecisionHistory mapDecision(
            GovernedRiskAssessment.Decision decision) {
        return new GovernedRiskAssessmentResponse.DecisionHistory(
                decision.workflowInstanceId(),decision.reviewTaskId(),
                decision.reviewActionId(),decision.reviewerId(),decision.reviewedAt(),
                decision.approvalTaskId(),decision.approvalActionId(),decision.approverId(),
                decision.approvedAt(),decision.auditReceiptId(),decision.submissionDigest());
    }

}
