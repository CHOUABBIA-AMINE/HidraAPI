/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CoatingConditionObservation
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.domain.model
 *
 * @Description : Coating observation.
 *
 */
package dz.sh.hidra.modules.integrity.domain.model;

import dz.sh.hidra.modules.integrity.domain.value.*;
import java.time.Instant;
import java.math.BigDecimal;

    /**
     * Coating observation.
     *
         * @param id id
     * @param inspectionRunId inspectionRunId
     * @param topologyAssetTypeCode topologyAssetTypeCode
     * @param topologyAssetId topologyAssetId
     * @param coatingConditionId coatingConditionId
     * @param kilometerPoint kilometerPoint
     * @param description description
     * @param severity severity
     * @param observedAt observedAt
     * @param observedByActorId observedByActorId
     */
    public record CoatingConditionObservation(
            String id,
        String inspectionRunId,
        String topologyAssetTypeCode,
        String topologyAssetId,
        String coatingConditionId,
        BigDecimal kilometerPoint,
        String description,
        FindingSeverity severity,
        Instant observedAt,
        String observedByActorId
    ) {

        public CoatingConditionObservation {
        id = normalize(id);
        inspectionRunId = normalize(inspectionRunId);
        topologyAssetTypeCode = normalize(topologyAssetTypeCode);
        topologyAssetId = normalize(topologyAssetId);
        coatingConditionId = normalize(coatingConditionId);
        description = normalize(description);
        observedByActorId = normalize(observedByActorId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
