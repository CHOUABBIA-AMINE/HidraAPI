/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : Party
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.domain.model
 *
 * @Description : Master record for external legal entity or recognized business actor.
 *
 */
package dz.sh.hidra.modules.party.domain.model;

import dz.sh.hidra.modules.party.domain.value.*;
import java.time.Instant;

    /**
     * Master record for external legal entity or recognized business actor.
     *
         * @param id id
     * @param code code
     * @param partyTypeId partyTypeId
     * @param legalName legalName
     * @param tradeName tradeName
     * @param shortName shortName
     * @param countryCode countryCode
     * @param jurisdictionCode jurisdictionCode
     * @param status status
     * @param primaryRoleCodeSnapshot primaryRoleCodeSnapshot
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record Party(
            String id,
        String code,
        String partyTypeId,
        String legalName,
        String tradeName,
        String shortName,
        String countryCode,
        String jurisdictionCode,
        PartyStatus status,
        String primaryRoleCodeSnapshot,
        Instant createdAt,
        Instant updatedAt
    ) {

        public Party {
        id = normalize(id);
        code = normalize(code);
        partyTypeId = normalize(partyTypeId);
        legalName = normalize(legalName);
        tradeName = normalize(tradeName);
        shortName = normalize(shortName);
        countryCode = normalize(countryCode);
        jurisdictionCode = normalize(jurisdictionCode);
        primaryRoleCodeSnapshot = normalize(primaryRoleCodeSnapshot);
        }
        public boolean selectableForNewReference() {
            return status == PartyStatus.ACTIVE;
        }
        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
