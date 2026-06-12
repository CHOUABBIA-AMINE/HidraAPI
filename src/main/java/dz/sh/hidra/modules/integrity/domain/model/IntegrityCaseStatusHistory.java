/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityCaseStatusHistory
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.domain.model
 *
 * @Description : Append-only status history.
 *
 */
package dz.sh.hidra.modules.integrity.domain.model;

import dz.sh.hidra.modules.integrity.domain.value.*;
import java.time.Instant;

    /**
     * Append-only status history.
     *
         * @param id id
     * @param integrityCaseId integrityCaseId
     * @param oldStatus oldStatus
     * @param newStatus newStatus
     * @param reasonId reasonId
     * @param reasonText reasonText
     * @param changedByActorId changedByActorId
     * @param changedAt changedAt
     * @param correlationId correlationId
     */
    public record IntegrityCaseStatusHistory(
            String id,
        String integrityCaseId,
        IntegrityCaseStatus oldStatus,
        IntegrityCaseStatus newStatus,
        String reasonId,
        String reasonText,
        String changedByActorId,
        Instant changedAt,
        String correlationId
    ) {

        public IntegrityCaseStatusHistory {
        id = normalize(id);
        integrityCaseId = normalize(integrityCaseId);
        reasonId = normalize(reasonId);
        reasonText = normalize(reasonText);
        changedByActorId = normalize(changedByActorId);
        correlationId = normalize(correlationId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
