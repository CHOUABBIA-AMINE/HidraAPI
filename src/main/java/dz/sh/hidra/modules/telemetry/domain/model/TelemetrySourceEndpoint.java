/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetrySourceEndpoint
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.model
 *
 * @Description : Connection profile or endpoint for a telemetry source.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.model;

import dz.sh.hidra.modules.telemetry.domain.value.*;
import java.time.Instant;

    /**
     * Connection profile or endpoint for a telemetry source.
     *
         * @param id id
     * @param sourceId sourceId
     * @param code code
     * @param endpointRole endpointRole
     * @param protocolId protocolId
     * @param endpointUri endpointUri
     * @param host host
     * @param port port
     * @param pathOrTopic pathOrTopic
     * @param pollingIntervalSeconds pollingIntervalSeconds
     * @param timeoutSeconds timeoutSeconds
     * @param credentialReference credentialReference
     * @param connectionOptionsJson connectionOptionsJson
     * @param active active
     * @param validFrom validFrom
     * @param validTo validTo
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record TelemetrySourceEndpoint(
            String id,
        String sourceId,
        String code,
        EndpointRole endpointRole,
        String protocolId,
        String endpointUri,
        String host,
        Integer port,
        String pathOrTopic,
        Integer pollingIntervalSeconds,
        Integer timeoutSeconds,
        String credentialReference,
        String connectionOptionsJson,
        boolean active,
        Instant validFrom,
        Instant validTo,
        Instant createdAt,
        Instant updatedAt
    ) {

        public TelemetrySourceEndpoint {
        id = normalize(id);
        sourceId = normalize(sourceId);
        code = normalize(code);
        protocolId = normalize(protocolId);
        endpointUri = normalize(endpointUri);
        host = normalize(host);
        pathOrTopic = normalize(pathOrTopic);
        credentialReference = normalize(credentialReference);
        connectionOptionsJson = normalize(connectionOptionsJson);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
