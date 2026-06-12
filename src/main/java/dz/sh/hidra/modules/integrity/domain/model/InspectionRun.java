/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InspectionRun
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.domain.model
 *
 * @Description : Inspection execution run.
 *
 */
package dz.sh.hidra.modules.integrity.domain.model;

import dz.sh.hidra.modules.integrity.domain.value.*;
import java.time.Instant;

    /**
     * Inspection execution run.
     *
         * @param id id
     * @param campaignId campaignId
     * @param runNumber runNumber
     * @param topologyAssetTypeCode topologyAssetTypeCode
     * @param topologyAssetId topologyAssetId
     * @param topologyAssetCodeSnapshot topologyAssetCodeSnapshot
     * @param status status
     * @param startedAt startedAt
     * @param completedAt completedAt
     * @param toolReference toolReference
     * @param operatorActorId operatorActorId
     * @param summary summary
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record InspectionRun(
            String id,
        String campaignId,
        String runNumber,
        String topologyAssetTypeCode,
        String topologyAssetId,
        String topologyAssetCodeSnapshot,
        InspectionRunStatus status,
        Instant startedAt,
        Instant completedAt,
        String toolReference,
        String operatorActorId,
        String summary,
        Instant createdAt,
        Instant updatedAt
    ) {

        public InspectionRun {
        id = normalize(id);
        campaignId = normalize(campaignId);
        runNumber = normalize(runNumber);
        topologyAssetTypeCode = normalize(topologyAssetTypeCode);
        topologyAssetId = normalize(topologyAssetId);
        topologyAssetCodeSnapshot = normalize(topologyAssetCodeSnapshot);
        toolReference = normalize(toolReference);
        operatorActorId = normalize(operatorActorId);
        summary = normalize(summary);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
