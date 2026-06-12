/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyQualification
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.domain.model
 *
 * @Description : General qualification status for operational or commercial use.
 *
 */
package dz.sh.hidra.modules.party.domain.model;

import dz.sh.hidra.modules.party.domain.value.*;
import java.time.Instant;

    /**
     * General qualification status for operational or commercial use.
     *
         * @param id id
     * @param partyId partyId
     * @param qualificationTypeCode qualificationTypeCode
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
    public record PartyQualification(
            String id,
        String partyId,
        String qualificationTypeCode,
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

        public PartyQualification {
        id = normalize(id);
        partyId = normalize(partyId);
        qualificationTypeCode = normalize(qualificationTypeCode);
        approvalReferenceId = normalize(approvalReferenceId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
