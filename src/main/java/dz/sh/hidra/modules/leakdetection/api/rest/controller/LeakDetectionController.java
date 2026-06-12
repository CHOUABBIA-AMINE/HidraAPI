/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakDetectionController
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : API
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.api.rest.controller
 *
 * @Description : Framework-neutral leak detection controller contract.
 *
 */
package dz.sh.hidra.modules.leakdetection.api.rest.controller;

import dz.sh.hidra.modules.leakdetection.api.rest.request.CreateLeakCandidateRequest;
import dz.sh.hidra.modules.leakdetection.api.rest.request.OpenLeakCaseRequest;
import dz.sh.hidra.modules.leakdetection.api.rest.response.LeakCandidateResponse;
import dz.sh.hidra.modules.leakdetection.api.rest.response.LeakCaseResponse;

/**
 * Framework-neutral leak detection controller contract.
 */
public interface LeakDetectionController {

    LeakCandidateResponse createLeakCandidate(CreateLeakCandidateRequest request);

    LeakCaseResponse openLeakCase(OpenLeakCaseRequest request);
}
