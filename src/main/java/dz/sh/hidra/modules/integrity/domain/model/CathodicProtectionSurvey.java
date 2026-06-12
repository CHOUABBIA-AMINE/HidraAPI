/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CathodicProtectionSurvey
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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
