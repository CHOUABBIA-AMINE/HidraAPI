/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakdetectionController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Interface
 * @Layer       : API
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.api.rest.controller
 *
 * @Description : Framework-neutral leakdetection controller contract.
 *
 */
package dz.sh.hidra.modules.leakdetection.api.rest.controller;
import dz.sh.hidra.modules.leakdetection.api.rest.request.*;
import dz.sh.hidra.modules.leakdetection.api.rest.response.*;

/**
 * Framework-neutral leakdetection controller contract.
 */
public interface LeakDetectionController {
    LeakCandidateResponse createLeakCandidate(CreateLeakCandidateRequest request);
    String escalateLeakCase(EscalateLeakCaseRequest request);
    LeakCaseResponse openLeakCase(OpenLeakCaseRequest request);
}
