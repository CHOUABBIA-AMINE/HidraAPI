/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OwnerProfile
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.domain.model
 *
 * @Description : Ownership role profile for assets, products, custody, or JV contexts.
 *
 */
package dz.sh.hidra.modules.party.domain.model;

import dz.sh.hidra.modules.party.domain.value.*;
import java.time.Instant;

    /**
     * Ownership role profile for assets, products, custody, or JV contexts.
     *
         * @param id id
     * @param partyId partyId
     * @param ownerProfileType ownerProfileType
     * @param ownershipContextCode ownershipContextCode
     * @param riskLevelSnapshot riskLevelSnapshot
     * @param status status
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record OwnerProfile(
            String id,
        String partyId,
        OwnerProfileType ownerProfileType,
        String ownershipContextCode,
        RiskLevel riskLevelSnapshot,
        PartyCatalogStatus status,
        Instant createdAt,
        Instant updatedAt
    ) {

        public OwnerProfile {
        id = normalize(id);
        partyId = normalize(partyId);
        ownershipContextCode = normalize(ownershipContextCode);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
