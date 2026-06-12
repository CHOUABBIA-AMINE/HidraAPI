/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityController
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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

import dz.sh.hidra.modules.integrity.api.rest.request.CreateIntegrityAssessmentRequest;
import dz.sh.hidra.modules.integrity.api.rest.request.CreateIntegrityProgramRequest;
import dz.sh.hidra.modules.integrity.api.rest.request.OpenIntegrityCaseRequest;
import dz.sh.hidra.modules.integrity.api.rest.response.IntegrityAssessmentResponse;
import dz.sh.hidra.modules.integrity.api.rest.response.IntegrityCaseResponse;
import dz.sh.hidra.modules.integrity.api.rest.response.IntegrityProgramResponse;

/**
 * Framework-neutral integrity controller contract.
 */
public interface IntegrityController {

    IntegrityProgramResponse createIntegrityProgram(CreateIntegrityProgramRequest request);

    IntegrityAssessmentResponse createIntegrityAssessment(CreateIntegrityAssessmentRequest request);

    IntegrityCaseResponse openIntegrityCase(OpenIntegrityCaseRequest request);
}
