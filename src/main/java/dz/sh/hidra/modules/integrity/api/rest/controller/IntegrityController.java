/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Interface
 * @Layer       : API
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.api.rest.controller
 *
 * @Description : Framework-neutral integrity controller contract.
 *
 */
package dz.sh.hidra.modules.integrity.api.rest.controller;
import dz.sh.hidra.modules.integrity.api.rest.request.*;
import dz.sh.hidra.modules.integrity.api.rest.response.*;

/**
 * Framework-neutral integrity controller contract.
 */
public interface IntegrityController {
    IntegrityAssessmentResponse createIntegrityAssessment(CreateIntegrityAssessmentRequest request);
    IntegrityProgramResponse createIntegrityProgram(CreateIntegrityProgramRequest request);
    IntegrityCaseResponse openIntegrityCase(OpenIntegrityCaseRequest request);
}
