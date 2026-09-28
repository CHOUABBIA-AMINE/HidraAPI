/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseCaseStatusHistory
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.hse.domain.exception.InvalidHseValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidHseValueException("HseCaseStatusHistory id must not be blank.");
        }
        // HRA-051 required: hseCaseId
        if (hseCaseId == null || hseCaseId.isBlank()) {
            throw new InvalidHseValueException("HseCaseStatusHistory hse case id must not be blank.");
        }
        // HRA-051 required: newStatus
        if (newStatus == null) {
            throw new InvalidHseValueException("HseCaseStatusHistory new status must not be null.");
        }
        // HRA-051 required: changedAt
        if (changedAt == null) {
            throw new InvalidHseValueException("HseCaseStatusHistory changed at must not be null.");
        }

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
