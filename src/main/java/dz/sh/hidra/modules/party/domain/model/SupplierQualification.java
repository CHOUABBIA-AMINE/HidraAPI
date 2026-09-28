/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SupplierQualification
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.domain.model
 *
 * @Description : Qualification specific to supplier role.
 *
 */
package dz.sh.hidra.modules.party.domain.model;

import dz.sh.hidra.modules.party.domain.exception.InvalidPartyValueException;
import dz.sh.hidra.modules.party.domain.value.*;
import java.time.Instant;

    /**
     * Qualification specific to supplier role.
     *
         * @param id id
     * @param partyId partyId
     * @param supplierCategoryCode supplierCategoryCode
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
    public record SupplierQualification(
            String id,
        String partyId,
        String supplierCategoryCode,
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

        public SupplierQualification {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidPartyValueException("SupplierQualification id must not be blank.");
        }
        // HRA-051 required: partyId
        if (partyId == null || partyId.isBlank()) {
            throw new InvalidPartyValueException("SupplierQualification party id must not be blank.");
        }
        // HRA-051 required: supplierCategoryCode
        if (supplierCategoryCode == null || supplierCategoryCode.isBlank()) {
            throw new InvalidPartyValueException("SupplierQualification supplier category code must not be blank.");
        }
        // HRA-051 required: qualificationStatus
        if (qualificationStatus == null) {
            throw new InvalidPartyValueException("SupplierQualification qualification status must not be null.");
        }

        id = normalize(id);
        partyId = normalize(partyId);
        supplierCategoryCode = normalize(supplierCategoryCode);
        approvalReferenceId = normalize(approvalReferenceId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
