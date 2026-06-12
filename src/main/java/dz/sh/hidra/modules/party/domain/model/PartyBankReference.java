/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyBankReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.domain.model
 *
 * @Description : Bank reference metadata only; no payment execution.
 *
 */
package dz.sh.hidra.modules.party.domain.model;

import dz.sh.hidra.modules.party.domain.value.*;
import java.time.Instant;

    /**
     * Bank reference metadata only; no payment execution.
     *
         * @param id id
     * @param partyId partyId
     * @param bankName bankName
     * @param accountReferenceMasked accountReferenceMasked
     * @param ibanMasked ibanMasked
     * @param swiftCode swiftCode
     * @param countryCode countryCode
     * @param status status
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record PartyBankReference(
            String id,
        String partyId,
        String bankName,
        String accountReferenceMasked,
        String ibanMasked,
        String swiftCode,
        String countryCode,
        BankReferenceStatus status,
        Instant createdAt,
        Instant updatedAt
    ) {

        public PartyBankReference {
        id = normalize(id);
        partyId = normalize(partyId);
        bankName = normalize(bankName);
        accountReferenceMasked = normalize(accountReferenceMasked);
        ibanMasked = normalize(ibanMasked);
        swiftCode = normalize(swiftCode);
        countryCode = normalize(countryCode);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
