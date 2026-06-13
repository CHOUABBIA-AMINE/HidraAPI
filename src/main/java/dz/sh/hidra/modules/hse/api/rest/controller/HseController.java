/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Interface
 * @Layer       : API
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.api.rest.controller
 *
 * @Description : Framework-neutral hse controller contract.
 *
 */
package dz.sh.hidra.modules.hse.api.rest.controller;
import dz.sh.hidra.modules.hse.api.rest.request.*;
import dz.sh.hidra.modules.hse.api.rest.response.*;

/**
 * Framework-neutral hse controller contract.
 */
public interface HseController {
    String closeHseCase(CloseHseCaseRequest request);
    HseCapaResponse createHseCapa(CreateHseCapaRequest request);
    HseCaseResponse openHseCase(OpenHseCaseRequest request);
}
