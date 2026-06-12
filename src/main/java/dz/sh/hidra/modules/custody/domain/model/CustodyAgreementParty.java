/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyAgreementParty
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.domain.model
 *
 * @Description : Party reference in agreement.
 *
 */
package dz.sh.hidra.modules.custody.domain.model;

import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Party reference in agreement.
     *
         * @param id id
     * @param agreementId agreementId
     * @param partyRoleId partyRoleId
     * @param partyId partyId
     * @param partyCodeSnapshot partyCodeSnapshot
     * @param partyNameSnapshot partyNameSnapshot
     * @param partyRoleCodeSnapshot partyRoleCodeSnapshot
     * @param ownershipSharePercent ownershipSharePercent
     * @param effectiveFrom effectiveFrom
     * @param effectiveTo effectiveTo
     * @param createdAt createdAt
     */
    public record CustodyAgreementParty(
            String id,
        String agreementId,
        String partyRoleId,
        String partyId,
        String partyCodeSnapshot,
        String partyNameSnapshot,
        String partyRoleCodeSnapshot,
        BigDecimal ownershipSharePercent,
        Instant effectiveFrom,
        Instant effectiveTo,
        Instant createdAt
    ) {

        public CustodyAgreementParty {
        id = normalize(id);
        agreementId = normalize(agreementId);
        partyRoleId = normalize(partyRoleId);
        partyId = normalize(partyId);
        partyCodeSnapshot = normalize(partyCodeSnapshot);
        partyNameSnapshot = normalize(partyNameSnapshot);
        partyRoleCodeSnapshot = normalize(partyRoleCodeSnapshot);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
