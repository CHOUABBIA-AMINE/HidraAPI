/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityCaseStatusHistory
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.integrity.domain.exception.InvalidIntegrityValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidIntegrityValueException("IntegrityCaseStatusHistory id must not be blank.");
        }
        // HRA-051 required: integrityCaseId
        if (integrityCaseId == null || integrityCaseId.isBlank()) {
            throw new InvalidIntegrityValueException("IntegrityCaseStatusHistory integrity case id must not be blank.");
        }
        // HRA-051 required: newStatus
        if (newStatus == null) {
            throw new InvalidIntegrityValueException("IntegrityCaseStatusHistory new status must not be null.");
        }
        // HRA-051 required: changedAt
        if (changedAt == null) {
            throw new InvalidIntegrityValueException("IntegrityCaseStatusHistory changed at must not be null.");
        }

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
