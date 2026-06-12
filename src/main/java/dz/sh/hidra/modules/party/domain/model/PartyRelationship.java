/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyRelationship
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.domain.model
 *
 * @Description : Relationship between parties, such as parent/subsidiary/affiliate.
 *
 */
package dz.sh.hidra.modules.party.domain.model;

import dz.sh.hidra.modules.party.domain.value.*;
import java.time.Instant;

    /**
     * Relationship between parties, such as parent/subsidiary/affiliate.
     *
         * @param id id
     * @param sourcePartyId sourcePartyId
     * @param targetPartyId targetPartyId
     * @param relationshipType relationshipType
     * @param validFrom validFrom
     * @param validTo validTo
     * @param status status
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record PartyRelationship(
            String id,
        String sourcePartyId,
        String targetPartyId,
        PartyRelationshipType relationshipType,
        Instant validFrom,
        Instant validTo,
        PartyCatalogStatus status,
        Instant createdAt,
        Instant updatedAt
    ) {

        public PartyRelationship {
        id = normalize(id);
        sourcePartyId = normalize(sourcePartyId);
        targetPartyId = normalize(targetPartyId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
