/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConnectorInstance
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.domain.model
 *
 * @Description : Configured Hidra connector implementation attached to an endpoint.
 *
 */
package dz.sh.hidra.modules.integration.domain.model;

import dz.sh.hidra.modules.integration.domain.value.*;
import java.time.Instant;

    /**
     * Configured Hidra connector implementation attached to an endpoint.
     *
         * @param id id
     * @param externalSystemId externalSystemId
     * @param endpointId endpointId
     * @param code code
     * @param connectorTypeId connectorTypeId
     * @param connectorImplementation connectorImplementation
     * @param direction direction
     * @param configurationJson configurationJson
     * @param maxConcurrency maxConcurrency
     * @param active active
     * @param healthStatus healthStatus
     * @param lastHealthCheckAt lastHealthCheckAt
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record ConnectorInstance(
            String id,
        String externalSystemId,
        String endpointId,
        String code,
        String connectorTypeId,
        String connectorImplementation,
        IntegrationDirection direction,
        String configurationJson,
        Integer maxConcurrency,
        boolean active,
        ConnectorHealthStatus healthStatus,
        Instant lastHealthCheckAt,
        Instant createdAt,
        Instant updatedAt
    ) {

        public ConnectorInstance {
        id = normalize(id);
        externalSystemId = normalize(externalSystemId);
        endpointId = normalize(endpointId);
        code = normalize(code);
        connectorTypeId = normalize(connectorTypeId);
        connectorImplementation = normalize(connectorImplementation);
        configurationJson = normalize(configurationJson);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
