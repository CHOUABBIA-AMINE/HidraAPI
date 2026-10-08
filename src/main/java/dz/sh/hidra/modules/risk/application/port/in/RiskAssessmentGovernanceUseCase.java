/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskAssessmentGovernanceUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.application.port.in
 *
 * @Description : Enforces governed Risk owner boundaries.
 *
 */
package dz.sh.hidra.modules.risk.application.port.in;

import dz.sh.hidra.modules.risk.application.command.*;
import dz.sh.hidra.modules.risk.application.dto.RiskAssessmentSummaryDto;
public interface RiskAssessmentGovernanceUseCase {
    RiskAssessmentSummaryDto score(ScoreRiskAssessmentCommand command);
    RiskAssessmentSummaryDto submit(String assessmentId);
    RiskAssessmentSummaryDto approve(ApproveRiskAssessmentCommand command);
}
