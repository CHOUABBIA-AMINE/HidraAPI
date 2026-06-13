/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
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
import dz.sh.hidra.modules.risk.api.rest.request.*;
import dz.sh.hidra.modules.risk.api.rest.response.*;

/**
 * Framework-neutral risk controller contract.
 */
public interface RiskController {
    String addRiskEvidence(AddRiskEvidenceRequest request);
    RiskAssessmentResponse createRiskAssessment(CreateRiskAssessmentRequest request);
    RiskRegisterResponse createRiskRegister(CreateRiskRegisterRequest request);
}
