/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperatorProfile
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.domain.model
 *
 * @Description : Operator role profile for operational counterparties.
 *
 */
package dz.sh.hidra.modules.party.domain.model;

import dz.sh.hidra.modules.party.domain.exception.InvalidPartyValueException;
import dz.sh.hidra.modules.party.domain.value.*;
import java.time.Instant;

    /**
     * Operator role profile for operational counterparties.
     *
         * @param id id
     * @param partyId partyId
     * @param capabilityType capabilityType
     * @param operatorCode operatorCode
     * @param qualificationStatus qualificationStatus
     * @param status status
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record OperatorProfile(
            String id,
        String partyId,
        OperatorCapabilityType capabilityType,
        String operatorCode,
        QualificationStatus qualificationStatus,
        PartyCatalogStatus status,
        Instant createdAt,
        Instant updatedAt
    ) {

        public OperatorProfile {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidPartyValueException("OperatorProfile id must not be blank.");
        }
        // HRA-051 required: partyId
        if (partyId == null || partyId.isBlank()) {
            throw new InvalidPartyValueException("OperatorProfile party id must not be blank.");
        }
        // HRA-051 required: capabilityType
        if (capabilityType == null) {
            throw new InvalidPartyValueException("OperatorProfile capability type must not be null.");
        }
        // HRA-051 required: qualificationStatus
        if (qualificationStatus == null) {
            throw new InvalidPartyValueException("OperatorProfile qualification status must not be null.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidPartyValueException("OperatorProfile status must not be null.");
        }

        id = normalize(id);
        partyId = normalize(partyId);
        operatorCode = normalize(operatorCode);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
