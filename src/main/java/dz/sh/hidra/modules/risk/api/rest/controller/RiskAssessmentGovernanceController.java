/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskAssessmentGovernanceController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.api.rest.controller
 *
 * @Description : Enforces governed Risk owner boundaries.
 *
 */
package dz.sh.hidra.modules.risk.api.rest.controller;

import dz.sh.hidra.modules.risk.application.command.*;
import dz.sh.hidra.modules.risk.application.dto.RiskAssessmentSummaryDto;
import dz.sh.hidra.modules.risk.application.port.in.RiskAssessmentGovernanceUseCase;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/risk/assessments")
public class RiskAssessmentGovernanceController {
    private final RiskAssessmentGovernanceUseCase governance;
    public RiskAssessmentGovernanceController(RiskAssessmentGovernanceUseCase governance) { this.governance=governance; }
    @io.swagger.v3.oas.annotations.Operation(operationId="scoreRiskAssessment")
    @PostMapping("/{id}/score")
    public RiskAssessmentSummaryDto score(@PathVariable String id, @RequestBody ScoreRiskAssessmentCommand request) {
        if (!id.equals(request.assessmentId())) throw new IllegalArgumentException("Assessment identity mismatch.");
        return governance.score(request);
    }
    @io.swagger.v3.oas.annotations.Operation(operationId="submitRiskAssessment")
    @PostMapping("/{id}/submit")
    public RiskAssessmentSummaryDto submit(@PathVariable String id) { return governance.submit(id); }
    @io.swagger.v3.oas.annotations.Operation(operationId="approveRiskAssessment")
    @PostMapping("/{id}/approve")
    public RiskAssessmentSummaryDto approve(@PathVariable String id, @RequestBody ApproveRiskAssessmentCommand request) {
        if (!id.equals(request.assessmentId())) throw new IllegalArgumentException("Assessment identity mismatch.");
        return governance.approve(request);
    }
}
