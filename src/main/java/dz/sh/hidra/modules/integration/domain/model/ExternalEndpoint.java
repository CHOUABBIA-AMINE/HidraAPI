/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ExternalEndpoint
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.domain.model
 *
 * @Description : External endpoint, topic, API, file path, or channel.
 *
 */
package dz.sh.hidra.modules.integration.domain.model;

import dz.sh.hidra.modules.integration.domain.value.*;
import java.time.Instant;

    /**
     * External endpoint, topic, API, file path, or channel.
     *
         * @param id id
     * @param externalSystemId externalSystemId
     * @param code code
     * @param endpointTypeId endpointTypeId
     * @param direction direction
     * @param endpointUri endpointUri
     * @param host host
     * @param port port
     * @param pathOrTopic pathOrTopic
     * @param protocolId protocolId
     * @param pollingIntervalSeconds pollingIntervalSeconds
     * @param timeoutSeconds timeoutSeconds
     * @param credentialReference credentialReference
     * @param tlsRequired tlsRequired
     * @param active active
     * @param validFrom validFrom
     * @param validTo validTo
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record ExternalEndpoint(
            String id,
        String externalSystemId,
        String code,
        String endpointTypeId,
        IntegrationDirection direction,
        String endpointUri,
        String host,
        Integer port,
        String pathOrTopic,
        String protocolId,
        Integer pollingIntervalSeconds,
        Integer timeoutSeconds,
        String credentialReference,
        boolean tlsRequired,
        boolean active,
        Instant validFrom,
        Instant validTo,
        Instant createdAt,
        Instant updatedAt
    ) {

        public ExternalEndpoint {
        id = normalize(id);
        externalSystemId = normalize(externalSystemId);
        code = normalize(code);
        endpointTypeId = normalize(endpointTypeId);
        endpointUri = normalize(endpointUri);
        host = normalize(host);
        pathOrTopic = normalize(pathOrTopic);
        protocolId = normalize(protocolId);
        credentialReference = normalize(credentialReference);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
