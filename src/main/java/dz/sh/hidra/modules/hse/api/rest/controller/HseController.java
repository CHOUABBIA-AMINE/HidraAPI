/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseController
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : API
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.api.rest.controller
 *
 * @Description : Framework-neutral HSE controller contract.
 *
 */
package dz.sh.hidra.modules.hse.api.rest.controller;

import dz.sh.hidra.modules.hse.api.rest.request.CreateHseCapaRequest;
import dz.sh.hidra.modules.hse.api.rest.request.OpenHseCaseRequest;
import dz.sh.hidra.modules.hse.api.rest.response.HseCapaResponse;
import dz.sh.hidra.modules.hse.api.rest.response.HseCaseResponse;

/**
 * Framework-neutral HSE controller contract.
 */
public interface HseController {

    HseCaseResponse openHseCase(OpenHseCaseRequest request);

    HseCapaResponse createHseCapa(CreateHseCapaRequest request);
}
