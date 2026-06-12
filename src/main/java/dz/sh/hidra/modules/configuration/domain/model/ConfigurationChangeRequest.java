/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationChangeRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.domain.model
 *
 * @Description : Governance request for configuration change.
 *
 */
package dz.sh.hidra.modules.configuration.domain.model;

import dz.sh.hidra.modules.configuration.domain.value.*;
import java.time.Instant;

    /**
     * Governance request for configuration change.
     *
         * @param id id
     * @param requestNumber requestNumber
     * @param definitionId definitionId
     * @param profileId profileId
     * @param featureFlagId featureFlagId
     * @param requestedValue requestedValue
     * @param requestedJsonValue requestedJsonValue
     * @param status status
     * @param reason reason
     * @param requestedByActorId requestedByActorId
     * @param requestedAt requestedAt
     * @param workflowInstanceId workflowInstanceId
     * @param approvedByActorId approvedByActorId
     * @param approvedAt approvedAt
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record ConfigurationChangeRequest(
            String id,
        String requestNumber,
        String definitionId,
        String profileId,
        String featureFlagId,
        String requestedValue,
        String requestedJsonValue,
        ChangeRequestStatus status,
        String reason,
        String requestedByActorId,
        Instant requestedAt,
        String workflowInstanceId,
        String approvedByActorId,
        Instant approvedAt,
        Instant createdAt,
        Instant updatedAt
    ) {

        public ConfigurationChangeRequest {
        id = normalize(id);
        requestNumber = normalize(requestNumber);
        definitionId = normalize(definitionId);
        profileId = normalize(profileId);
        featureFlagId = normalize(featureFlagId);
        requestedValue = normalize(requestedValue);
        requestedJsonValue = normalize(requestedJsonValue);
        reason = normalize(reason);
        requestedByActorId = normalize(requestedByActorId);
        workflowInstanceId = normalize(workflowInstanceId);
        approvedByActorId = normalize(approvedByActorId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
