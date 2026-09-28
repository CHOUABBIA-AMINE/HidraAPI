/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetrySourceEndpoint
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.telemetry.domain.exception.InvalidTelemetryValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidTelemetryValueException("TelemetrySourceEndpoint id must not be blank.");
        }
        // HRA-051 required: sourceId
        if (sourceId == null || sourceId.isBlank()) {
            throw new InvalidTelemetryValueException("TelemetrySourceEndpoint source id must not be blank.");
        }
        // HRA-051 required: code
        if (code == null || code.isBlank()) {
            throw new InvalidTelemetryValueException("TelemetrySourceEndpoint code must not be blank.");
        }
        // HRA-051 required: endpointRole
        if (endpointRole == null) {
            throw new InvalidTelemetryValueException("TelemetrySourceEndpoint endpoint role must not be null.");
        }
        // HRA-051 required: protocolId
        if (protocolId == null || protocolId.isBlank()) {
            throw new InvalidTelemetryValueException("TelemetrySourceEndpoint protocol id must not be blank.");
        }
        // HRA-051 required: validFrom
        if (validFrom == null) {
            throw new InvalidTelemetryValueException("TelemetrySourceEndpoint valid from must not be null.");
        }
        // HRA-051 order: validFrom <= validTo
        if (validFrom != null && validTo != null && validTo.isBefore(validFrom)) {
            throw new InvalidTelemetryValueException("TelemetrySourceEndpoint valid to must not be before valid from.");
        }

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
