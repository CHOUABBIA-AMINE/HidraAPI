/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : VendorQualification
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.domain.model
 *
 * @Description : Qualification specific to vendor role.
 *
 */
package dz.sh.hidra.modules.party.domain.model;

import dz.sh.hidra.modules.party.domain.exception.InvalidPartyValueException;
import dz.sh.hidra.modules.party.domain.value.*;
import java.time.Instant;

    /**
     * Qualification specific to vendor role.
     *
         * @param id id
     * @param partyId partyId
     * @param vendorCategoryCode vendorCategoryCode
     * @param qualificationStatus qualificationStatus
     * @param approvedFrom approvedFrom
     * @param approvedTo approvedTo
     * @param approvalReferenceId approvalReferenceId
     * @param riskLevelSnapshot riskLevelSnapshot
     * @param lastReviewDate lastReviewDate
     * @param nextReviewDate nextReviewDate
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record VendorQualification(
            String id,
        String partyId,
        String vendorCategoryCode,
        QualificationStatus qualificationStatus,
        Instant approvedFrom,
        Instant approvedTo,
        String approvalReferenceId,
        RiskLevel riskLevelSnapshot,
        Instant lastReviewDate,
        Instant nextReviewDate,
        Instant createdAt,
        Instant updatedAt
    ) {

        public VendorQualification {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidPartyValueException("VendorQualification id must not be blank.");
        }
        // HRA-051 required: partyId
        if (partyId == null || partyId.isBlank()) {
            throw new InvalidPartyValueException("VendorQualification party id must not be blank.");
        }
        // HRA-051 required: vendorCategoryCode
        if (vendorCategoryCode == null || vendorCategoryCode.isBlank()) {
            throw new InvalidPartyValueException("VendorQualification vendor category code must not be blank.");
        }
        // HRA-051 required: qualificationStatus
        if (qualificationStatus == null) {
            throw new InvalidPartyValueException("VendorQualification qualification status must not be null.");
        }

        id = normalize(id);
        partyId = normalize(partyId);
        vendorCategoryCode = normalize(vendorCategoryCode);
        approvalReferenceId = normalize(approvalReferenceId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
