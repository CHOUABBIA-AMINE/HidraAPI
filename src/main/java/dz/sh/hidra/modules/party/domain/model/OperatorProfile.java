/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperatorProfile
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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
