/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseCaseStatusHistory
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.domain.model
 *
 * @Description : Append-only HSE case status history.
 *
 */
package dz.sh.hidra.modules.hse.domain.model;

import dz.sh.hidra.modules.hse.domain.value.*;
import java.time.Instant;

    /**
     * Append-only HSE case status history.
     *
         * @param id id
     * @param hseCaseId hseCaseId
     * @param oldStatus oldStatus
     * @param newStatus newStatus
     * @param reasonId reasonId
     * @param reasonText reasonText
     * @param changedByActorId changedByActorId
     * @param changedByDisplayNameSnapshot changedByDisplayNameSnapshot
     * @param changedAt changedAt
     * @param correlationId correlationId
     */
    public record HseCaseStatusHistory(
            String id,
        String hseCaseId,
        HseCaseStatus oldStatus,
        HseCaseStatus newStatus,
        String reasonId,
        String reasonText,
        String changedByActorId,
        String changedByDisplayNameSnapshot,
        Instant changedAt,
        String correlationId
    ) {

        public HseCaseStatusHistory {
        id = normalize(id);
        hseCaseId = normalize(hseCaseId);
        reasonId = normalize(reasonId);
        reasonText = normalize(reasonText);
        changedByActorId = normalize(changedByActorId);
        changedByDisplayNameSnapshot = normalize(changedByDisplayNameSnapshot);
        correlationId = normalize(correlationId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
