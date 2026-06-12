/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyRiskSnapshot
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.domain.model
 *
 * @Description : Snapshot of party risk classification from approved source.
 *
 */
package dz.sh.hidra.modules.party.domain.model;

import dz.sh.hidra.modules.party.domain.value.*;
import java.time.Instant;

    /**
     * Snapshot of party risk classification from approved source.
     *
         * @param id id
     * @param partyId partyId
     * @param riskLevel riskLevel
     * @param riskSourceModule riskSourceModule
     * @param riskSourceReferenceId riskSourceReferenceId
     * @param riskReason riskReason
     * @param assessedAt assessedAt
     * @param validUntil validUntil
     * @param createdAt createdAt
     */
    public record PartyRiskSnapshot(
            String id,
        String partyId,
        RiskLevel riskLevel,
        String riskSourceModule,
        String riskSourceReferenceId,
        String riskReason,
        Instant assessedAt,
        Instant validUntil,
        Instant createdAt
    ) {

        public PartyRiskSnapshot {
        id = normalize(id);
        partyId = normalize(partyId);
        riskSourceModule = normalize(riskSourceModule);
        riskSourceReferenceId = normalize(riskSourceReferenceId);
        riskReason = normalize(riskReason);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
