/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : StartIntegrationJobRunRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.api.rest.request
 *
 * @Description : REST request to start integration job run.
 *
 */
package dz.sh.hidra.modules.integration.api.rest.request;

import dz.sh.hidra.modules.integration.domain.value.JobTriggerType;

/**
 * REST request to start integration job run.
 */
public record StartIntegrationJobRunRequest(
        String jobDefinitionId,
        long runNumber,
        JobTriggerType triggerType,
        String triggeredByActorId,
        String correlationId
) {
}
