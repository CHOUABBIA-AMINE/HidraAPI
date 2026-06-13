/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
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
import dz.sh.hidra.modules.integration.api.rest.request.*;
import dz.sh.hidra.modules.integration.api.rest.response.*;

/**
 * Framework-neutral integration controller contract.
 */
public interface IntegrationController {
    IntegrationExchangeMessageResponse recordExchangeMessage(RecordExchangeMessageRequest request);
    ExternalSystemResponse registerExternalSystem(RegisterExternalSystemRequest request);
    IntegrationJobRunResponse startIntegrationJobRun(StartIntegrationJobRunRequest request);
}
