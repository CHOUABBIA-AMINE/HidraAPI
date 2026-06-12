/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationSyncCursor
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.domain.model
 *
 * @Description : Checkpoint state for incremental synchronization.
 *
 */
package dz.sh.hidra.modules.integration.domain.model;

import dz.sh.hidra.modules.integration.domain.value.*;
import java.time.Instant;

    /**
     * Checkpoint state for incremental synchronization.
     *
         * @param id id
     * @param jobDefinitionId jobDefinitionId
     * @param externalSystemId externalSystemId
     * @param cursorName cursorName
     * @param cursorValue cursorValue
     * @param cursorPayload cursorPayload
     * @param lastSuccessfulRunId lastSuccessfulRunId
     * @param lastSuccessfulAt lastSuccessfulAt
     * @param status status
     * @param updatedAt updatedAt
     */
    public record IntegrationSyncCursor(
            String id,
        String jobDefinitionId,
        String externalSystemId,
        String cursorName,
        String cursorValue,
        String cursorPayload,
        String lastSuccessfulRunId,
        Instant lastSuccessfulAt,
        SyncCursorStatus status,
        Instant updatedAt
    ) {

        public IntegrationSyncCursor {
        id = normalize(id);
        jobDefinitionId = normalize(jobDefinitionId);
        externalSystemId = normalize(externalSystemId);
        cursorName = normalize(cursorName);
        cursorValue = normalize(cursorValue);
        cursorPayload = normalize(cursorPayload);
        lastSuccessfulRunId = normalize(lastSuccessfulRunId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
