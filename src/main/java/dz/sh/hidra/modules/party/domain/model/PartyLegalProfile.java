/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyLegalProfile
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.domain.model
 *
 * @Description : Legal name, trade name, jurisdiction, legal form, and registration summary.
 *
 */
package dz.sh.hidra.modules.party.domain.model;

import dz.sh.hidra.modules.party.domain.exception.InvalidPartyValueException;
import java.time.Instant;

    /**
     * Legal name, trade name, jurisdiction, legal form, and registration summary.
     *
         * @param id id
     * @param partyId partyId
     * @param legalName legalName
     * @param tradeName tradeName
     * @param legalFormCode legalFormCode
     * @param registrationSummary registrationSummary
     * @param jurisdictionCode jurisdictionCode
     * @param countryCode countryCode
     * @param effectiveFrom effectiveFrom
     * @param effectiveTo effectiveTo
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record PartyLegalProfile(
            String id,
        String partyId,
        String legalName,
        String tradeName,
        String legalFormCode,
        String registrationSummary,
        String jurisdictionCode,
        String countryCode,
        Instant effectiveFrom,
        Instant effectiveTo,
        Instant createdAt,
        Instant updatedAt
    ) {

        public PartyLegalProfile {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidPartyValueException("PartyLegalProfile id must not be blank.");
        }
        // HRA-051 required: partyId
        if (partyId == null || partyId.isBlank()) {
            throw new InvalidPartyValueException("PartyLegalProfile party id must not be blank.");
        }
        // HRA-051 required: countryCode
        if (countryCode == null || countryCode.isBlank()) {
            throw new InvalidPartyValueException("PartyLegalProfile country code must not be blank.");
        }
        // HRA-051 order: effectiveFrom <= effectiveTo
        if (effectiveFrom != null && effectiveTo != null && effectiveTo.isBefore(effectiveFrom)) {
            throw new InvalidPartyValueException("PartyLegalProfile effective to must not be before effective from.");
        }

        id = normalize(id);
        partyId = normalize(partyId);
        legalName = normalize(legalName);
        tradeName = normalize(tradeName);
        legalFormCode = normalize(legalFormCode);
        registrationSummary = normalize(registrationSummary);
        jurisdictionCode = normalize(jurisdictionCode);
        countryCode = normalize(countryCode);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
