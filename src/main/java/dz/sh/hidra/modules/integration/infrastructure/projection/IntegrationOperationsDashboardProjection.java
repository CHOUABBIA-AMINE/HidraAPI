/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationOperationsDashboardProjection
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.projection
 *
 * @Description : Integration operations dashboard projection.
 *
 */
package dz.sh.hidra.modules.integration.infrastructure.projection;

import dz.sh.hidra.modules.integration.domain.value.ConnectorHealthStatus;
import dz.sh.hidra.modules.integration.domain.value.JobRunStatus;

import java.time.Instant;

/**
 * Integration operations dashboard projection.
 */
public record IntegrationOperationsDashboardProjection(
        String externalSystemId,
        String externalSystemCode,
        String connectorInstanceId,
        ConnectorHealthStatus healthStatus,
        String jobDefinitionId,
        JobRunStatus lastRunStatus,
        long deadLetterCount,
        Instant lastExchangeAt
) {
}
