/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : ReadGovernedRiskAssessmentUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.application.port.in
 * @Description : Authenticated scoped reads of approved Risk evidence.
 */
package dz.sh.hidra.modules.risk.application.port.in;

import dz.sh.hidra.modules.risk.domain.model.GovernedRiskAssessment;
import java.util.List;
public interface ReadGovernedRiskAssessmentUseCase {
    GovernedRiskAssessment read(String assessmentId);
    List<GovernedRiskAssessment.Decision> decisionHistory(String assessmentId);
}
