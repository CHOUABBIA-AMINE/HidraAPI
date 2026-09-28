/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyStatusHistory
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.domain.model
 *
 * @Description : Append-only party lifecycle status history.
 *
 */
package dz.sh.hidra.modules.party.domain.model;

import dz.sh.hidra.modules.party.domain.exception.InvalidPartyValueException;
import dz.sh.hidra.modules.party.domain.value.*;
import java.time.Instant;

    /**
     * Append-only party lifecycle status history.
     *
         * @param id id
     * @param partyId partyId
     * @param oldStatus oldStatus
     * @param newStatus newStatus
     * @param reason reason
     * @param reasonMessage reasonMessage
     * @param changedByActorId changedByActorId
     * @param changedAt changedAt
     * @param correlationId correlationId
     */
    public record PartyStatusHistory(
            String id,
        String partyId,
        PartyStatus oldStatus,
        PartyStatus newStatus,
        StatusChangeReason reason,
        String reasonMessage,
        String changedByActorId,
        Instant changedAt,
        String correlationId
    ) {

        public PartyStatusHistory {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidPartyValueException("PartyStatusHistory id must not be blank.");
        }
        // HRA-051 required: partyId
        if (partyId == null || partyId.isBlank()) {
            throw new InvalidPartyValueException("PartyStatusHistory party id must not be blank.");
        }
        // HRA-051 required: newStatus
        if (newStatus == null) {
            throw new InvalidPartyValueException("PartyStatusHistory new status must not be null.");
        }
        // HRA-051 required: reason
        if (reason == null) {
            throw new InvalidPartyValueException("PartyStatusHistory reason must not be null.");
        }
        // HRA-051 required: changedAt
        if (changedAt == null) {
            throw new InvalidPartyValueException("PartyStatusHistory changed at must not be null.");
        }

        id = normalize(id);
        partyId = normalize(partyId);
        reasonMessage = normalize(reasonMessage);
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
