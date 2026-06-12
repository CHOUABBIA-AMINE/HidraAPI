/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineDefect
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.domain.model
 *
 * @Description : Pipeline defect record.
 *
 */
package dz.sh.hidra.modules.integrity.domain.model;

import dz.sh.hidra.modules.integrity.domain.value.*;
import java.time.Instant;
import java.math.BigDecimal;

    /**
     * Pipeline defect record.
     *
         * @param id id
     * @param defectNumber defectNumber
     * @param defectTypeId defectTypeId
     * @param threatType threatType
     * @param status status
     * @param severity severity
     * @param topologyAssetTypeCode topologyAssetTypeCode
     * @param topologyAssetId topologyAssetId
     * @param topologyAssetCodeSnapshot topologyAssetCodeSnapshot
     * @param kilometerPoint kilometerPoint
     * @param latitude latitude
     * @param longitude longitude
     * @param description description
     * @param detectedAt detectedAt
     * @param closedAt closedAt
     * @param sourceFindingId sourceFindingId
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record PipelineDefect(
            String id,
        String defectNumber,
        String defectTypeId,
        ThreatType threatType,
        DefectStatus status,
        FindingSeverity severity,
        String topologyAssetTypeCode,
        String topologyAssetId,
        String topologyAssetCodeSnapshot,
        BigDecimal kilometerPoint,
        BigDecimal latitude,
        BigDecimal longitude,
        String description,
        Instant detectedAt,
        Instant closedAt,
        String sourceFindingId,
        Instant createdAt,
        Instant updatedAt
    ) {

        public PipelineDefect {
        id = normalize(id);
        defectNumber = normalize(defectNumber);
        defectTypeId = normalize(defectTypeId);
        topologyAssetTypeCode = normalize(topologyAssetTypeCode);
        topologyAssetId = normalize(topologyAssetId);
        topologyAssetCodeSnapshot = normalize(topologyAssetCodeSnapshot);
        description = normalize(description);
        sourceFindingId = normalize(sourceFindingId);
        }
        public boolean openLifecycle() {
            return status != DefectStatus.CLOSED
                    && status != DefectStatus.DISMISSED;
        }
        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
