/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyAgreementParty
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.custody.domain.exception.InvalidCustodyValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidCustodyValueException("CustodyAgreementParty id must not be blank.");
        }
        // HRA-051 required: agreementId
        if (agreementId == null || agreementId.isBlank()) {
            throw new InvalidCustodyValueException("CustodyAgreementParty agreement id must not be blank.");
        }
        // HRA-051 required: partyRoleId
        if (partyRoleId == null || partyRoleId.isBlank()) {
            throw new InvalidCustodyValueException("CustodyAgreementParty party role id must not be blank.");
        }
        // HRA-051 required: partyId
        if (partyId == null || partyId.isBlank()) {
            throw new InvalidCustodyValueException("CustodyAgreementParty party id must not be blank.");
        }
        // HRA-051 order: effectiveFrom <= effectiveTo
        if (effectiveFrom != null && effectiveTo != null && effectiveTo.isBefore(effectiveFrom)) {
            throw new InvalidCustodyValueException("CustodyAgreementParty effective to must not be before effective from.");
        }

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
