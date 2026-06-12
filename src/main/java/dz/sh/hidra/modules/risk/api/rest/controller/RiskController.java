/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskController
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : API
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.api.rest.controller
 *
 * @Description : Framework-neutral risk controller contract.
 *
 */
package dz.sh.hidra.modules.risk.api.rest.controller;

import dz.sh.hidra.modules.risk.api.rest.request.CreateRiskAssessmentRequest;
import dz.sh.hidra.modules.risk.api.rest.request.CreateRiskRegisterRequest;
import dz.sh.hidra.modules.risk.api.rest.response.RiskAssessmentResponse;
import dz.sh.hidra.modules.risk.api.rest.response.RiskRegisterResponse;

/**
 * Framework-neutral risk controller contract.
 */
public interface RiskController {

    RiskRegisterResponse createRiskRegister(CreateRiskRegisterRequest request);

    RiskAssessmentResponse createRiskAssessment(CreateRiskAssessmentRequest request);
}
