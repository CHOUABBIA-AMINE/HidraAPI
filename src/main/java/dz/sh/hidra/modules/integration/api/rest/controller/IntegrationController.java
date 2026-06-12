/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationController
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : API
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.api.rest.controller
 *
 * @Description : Framework-neutral integration controller contract.
 *
 */
package dz.sh.hidra.modules.integration.api.rest.controller;

import dz.sh.hidra.modules.integration.api.rest.request.RegisterExternalSystemRequest;
import dz.sh.hidra.modules.integration.api.rest.request.StartIntegrationJobRunRequest;
import dz.sh.hidra.modules.integration.api.rest.response.ExternalSystemResponse;
import dz.sh.hidra.modules.integration.api.rest.response.IntegrationJobRunResponse;

/**
 * Framework-neutral integration controller contract.
 */
public interface IntegrationController {

    ExternalSystemResponse registerExternalSystem(RegisterExternalSystemRequest request);

    IntegrationJobRunResponse startIntegrationJobRun(StartIntegrationJobRunRequest request);
}
