/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyOwnershipLink
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.domain.model
 *
 * @Description : Ownership relation between parties or external business objects by neutral reference.
 *
 */
package dz.sh.hidra.modules.party.domain.model;

import dz.sh.hidra.modules.party.domain.exception.InvalidPartyValueException;
import dz.sh.hidra.modules.party.domain.value.*;
import java.time.Instant;
import java.math.BigDecimal;

    /**
     * Ownership relation between parties or external business objects by neutral reference.
     *
         * @param id id
     * @param ownerPartyId ownerPartyId
     * @param targetType targetType
     * @param targetId targetId
     * @param targetCodeSnapshot targetCodeSnapshot
     * @param targetNameSnapshot targetNameSnapshot
     * @param ownershipPercentage ownershipPercentage
     * @param validFrom validFrom
     * @param validTo validTo
     * @param status status
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record PartyOwnershipLink(
            String id,
        String ownerPartyId,
        OwnershipTargetType targetType,
        String targetId,
        String targetCodeSnapshot,
        String targetNameSnapshot,
        BigDecimal ownershipPercentage,
        Instant validFrom,
        Instant validTo,
        PartyCatalogStatus status,
        Instant createdAt,
        Instant updatedAt
    ) {

        public PartyOwnershipLink {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidPartyValueException("PartyOwnershipLink id must not be blank.");
        }
        // HRA-051 required: ownerPartyId
        if (ownerPartyId == null || ownerPartyId.isBlank()) {
            throw new InvalidPartyValueException("PartyOwnershipLink owner party id must not be blank.");
        }
        // HRA-051 required: targetType
        if (targetType == null) {
            throw new InvalidPartyValueException("PartyOwnershipLink target type must not be null.");
        }
        // HRA-051 required: targetId
        if (targetId == null || targetId.isBlank()) {
            throw new InvalidPartyValueException("PartyOwnershipLink target id must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidPartyValueException("PartyOwnershipLink status must not be null.");
        }
        // HRA-051 order: validFrom <= validTo
        if (validFrom != null && validTo != null && validTo.isBefore(validFrom)) {
            throw new InvalidPartyValueException("PartyOwnershipLink valid to must not be before valid from.");
        }

        id = normalize(id);
        ownerPartyId = normalize(ownerPartyId);
        targetId = normalize(targetId);
        targetCodeSnapshot = normalize(targetCodeSnapshot);
        targetNameSnapshot = normalize(targetNameSnapshot);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
