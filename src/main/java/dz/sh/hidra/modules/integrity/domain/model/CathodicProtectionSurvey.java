/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CathodicProtectionSurvey
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.domain.model
 *
 * @Description : CP survey.
 *
 */
package dz.sh.hidra.modules.integrity.domain.model;

import dz.sh.hidra.modules.integrity.domain.exception.InvalidIntegrityValueException;
import dz.sh.hidra.modules.integrity.domain.value.*;
import java.time.Instant;

    /**
     * CP survey.
     *
         * @param id id
     * @param surveyNumber surveyNumber
     * @param surveyTypeId surveyTypeId
     * @param topologyAssetTypeCode topologyAssetTypeCode
     * @param topologyAssetId topologyAssetId
     * @param topologyAssetCodeSnapshot topologyAssetCodeSnapshot
     * @param status status
     * @param surveyStartAt surveyStartAt
     * @param surveyEndAt surveyEndAt
     * @param performedByPartyId performedByPartyId
     * @param summary summary
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record CathodicProtectionSurvey(
            String id,
        String surveyNumber,
        String surveyTypeId,
        String topologyAssetTypeCode,
        String topologyAssetId,
        String topologyAssetCodeSnapshot,
        InspectionCampaignStatus status,
        Instant surveyStartAt,
        Instant surveyEndAt,
        String performedByPartyId,
        String summary,
        Instant createdAt,
        Instant updatedAt
    ) {

        public CathodicProtectionSurvey {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidIntegrityValueException("CathodicProtectionSurvey id must not be blank.");
        }
        // HRA-051 required: surveyNumber
        if (surveyNumber == null || surveyNumber.isBlank()) {
            throw new InvalidIntegrityValueException("CathodicProtectionSurvey survey number must not be blank.");
        }
        // HRA-051 required: surveyTypeId
        if (surveyTypeId == null || surveyTypeId.isBlank()) {
            throw new InvalidIntegrityValueException("CathodicProtectionSurvey survey type id must not be blank.");
        }
        // HRA-051 required: topologyAssetTypeCode
        if (topologyAssetTypeCode == null || topologyAssetTypeCode.isBlank()) {
            throw new InvalidIntegrityValueException("CathodicProtectionSurvey topology asset type code must not be blank.");
        }
        // HRA-051 required: topologyAssetId
        if (topologyAssetId == null || topologyAssetId.isBlank()) {
            throw new InvalidIntegrityValueException("CathodicProtectionSurvey topology asset id must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidIntegrityValueException("CathodicProtectionSurvey status must not be null.");
        }

        id = normalize(id);
        surveyNumber = normalize(surveyNumber);
        surveyTypeId = normalize(surveyTypeId);
        topologyAssetTypeCode = normalize(topologyAssetTypeCode);
        topologyAssetId = normalize(topologyAssetId);
        topologyAssetCodeSnapshot = normalize(topologyAssetCodeSnapshot);
        performedByPartyId = normalize(performedByPartyId);
        summary = normalize(summary);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
