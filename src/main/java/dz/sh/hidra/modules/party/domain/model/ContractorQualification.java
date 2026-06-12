/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ContractorQualification
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.domain.model
 *
 * @Description : Qualification specific to contractor role.
 *
 */
package dz.sh.hidra.modules.party.domain.model;

import dz.sh.hidra.modules.party.domain.value.*;
import java.time.Instant;

    /**
     * Qualification specific to contractor role.
     *
         * @param id id
     * @param partyId partyId
     * @param contractorCategoryCode contractorCategoryCode
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
    public record ContractorQualification(
            String id,
        String partyId,
        String contractorCategoryCode,
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

        public ContractorQualification {
        id = normalize(id);
        partyId = normalize(partyId);
        contractorCategoryCode = normalize(contractorCategoryCode);
        approvalReferenceId = normalize(approvalReferenceId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
