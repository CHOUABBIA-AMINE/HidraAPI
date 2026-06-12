/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyOwnershipLink
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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
