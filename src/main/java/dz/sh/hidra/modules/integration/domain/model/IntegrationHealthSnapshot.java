/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationHealthSnapshot
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.domain.model
 *
 * @Description : Periodic health snapshot.
 *
 */
package dz.sh.hidra.modules.integration.domain.model;

import dz.sh.hidra.modules.integration.domain.exception.InvalidIntegrationValueException;
import dz.sh.hidra.modules.integration.domain.value.*;
import java.time.Instant;

    /**
     * Periodic health snapshot.
     *
         * @param id id
     * @param externalSystemId externalSystemId
     * @param endpointId endpointId
     * @param connectorInstanceId connectorInstanceId
     * @param jobDefinitionId jobDefinitionId
     * @param healthStatus healthStatus
     * @param latencyMs latencyMs
     * @param lastSuccessAt lastSuccessAt
     * @param lastFailureAt lastFailureAt
     * @param errorCode errorCode
     * @param errorMessage errorMessage
     * @param capturedAt capturedAt
     */
    public record IntegrationHealthSnapshot(
            String id,
        String externalSystemId,
        String endpointId,
        String connectorInstanceId,
        String jobDefinitionId,
        ConnectorHealthStatus healthStatus,
        Long latencyMs,
        Instant lastSuccessAt,
        Instant lastFailureAt,
        String errorCode,
        String errorMessage,
        Instant capturedAt
    ) {

        public IntegrationHealthSnapshot {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidIntegrationValueException("IntegrationHealthSnapshot id must not be blank.");
        }
        // HRA-051 required: externalSystemId
        if (externalSystemId == null || externalSystemId.isBlank()) {
            throw new InvalidIntegrationValueException("IntegrationHealthSnapshot external system id must not be blank.");
        }
        // HRA-051 required: healthStatus
        if (healthStatus == null) {
            throw new InvalidIntegrationValueException("IntegrationHealthSnapshot health status must not be null.");
        }
        // HRA-051 required: capturedAt
        if (capturedAt == null) {
            throw new InvalidIntegrationValueException("IntegrationHealthSnapshot captured at must not be null.");
        }

        id = normalize(id);
        externalSystemId = normalize(externalSystemId);
        endpointId = normalize(endpointId);
        connectorInstanceId = normalize(connectorInstanceId);
        jobDefinitionId = normalize(jobDefinitionId);
        errorCode = normalize(errorCode);
        errorMessage = normalize(errorMessage);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
