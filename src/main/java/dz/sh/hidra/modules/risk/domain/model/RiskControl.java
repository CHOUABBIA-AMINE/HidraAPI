/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskControl
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.domain.model
 *
 * @Description : Preventive, detective, corrective, or compensating control.
 *
 */
package dz.sh.hidra.modules.risk.domain.model;

import java.time.Instant;

    /**
     * Preventive, detective, corrective, or compensating control.
     *
         * @param id id
     * @param riskAssessmentId riskAssessmentId
     * @param controlCode controlCode
     * @param controlName controlName
     * @param controlTypeId controlTypeId
     * @param controlOwnerOrganizationUnitId controlOwnerOrganizationUnitId
     * @param controlOwnerNameSnapshot controlOwnerNameSnapshot
     * @param effectivenessLevelId effectivenessLevelId
     * @param effectivenessJustification effectivenessJustification
     * @param verified verified
     * @param verifiedByActorId verifiedByActorId
     * @param verifiedAt verifiedAt
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record RiskControl(
            String id,
        String riskAssessmentId,
        String controlCode,
        String controlName,
        String controlTypeId,
        String controlOwnerOrganizationUnitId,
        String controlOwnerNameSnapshot,
        String effectivenessLevelId,
        String effectivenessJustification,
        boolean verified,
        String verifiedByActorId,
        Instant verifiedAt,
        Instant createdAt,
        Instant updatedAt
    ) {

        public RiskControl {
        id = normalize(id);
        riskAssessmentId = normalize(riskAssessmentId);
        controlCode = normalize(controlCode);
        controlName = normalize(controlName);
        controlTypeId = normalize(controlTypeId);
        controlOwnerOrganizationUnitId = normalize(controlOwnerOrganizationUnitId);
        controlOwnerNameSnapshot = normalize(controlOwnerNameSnapshot);
        effectivenessLevelId = normalize(effectivenessLevelId);
        effectivenessJustification = normalize(effectivenessJustification);
        verifiedByActorId = normalize(verifiedByActorId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
