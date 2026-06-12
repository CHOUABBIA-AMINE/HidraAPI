/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationJobRunResponse
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.api.rest.response
 *
 * @Description : REST response for integration job run.
 *
 */
package dz.sh.hidra.modules.integration.api.rest.response;

import dz.sh.hidra.modules.integration.domain.value.JobRunStatus;
import dz.sh.hidra.modules.integration.domain.value.JobTriggerType;

import java.time.Instant;

/**
 * REST response for integration job run.
 */
public record IntegrationJobRunResponse(
        String id,
        String jobDefinitionId,
        long runNumber,
        JobTriggerType triggerType,
        JobRunStatus status,
        String correlationId,
        Instant startedAt,
        Instant completedAt
) {
}
