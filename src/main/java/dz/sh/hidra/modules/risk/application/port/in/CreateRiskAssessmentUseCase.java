/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateRiskAssessmentUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.application.port.in
 *
 * @Description : Use case for creating risk assessments.
 *
 */
package dz.sh.hidra.modules.risk.application.port.in;

import dz.sh.hidra.modules.risk.application.command.CreateRiskAssessmentCommand;
import dz.sh.hidra.modules.risk.application.dto.RiskAssessmentSummaryDto;

/**
 * Use case for creating risk assessments.
 */
public interface CreateRiskAssessmentUseCase {

    RiskAssessmentSummaryDto createRiskAssessment(CreateRiskAssessmentCommand command);
}
