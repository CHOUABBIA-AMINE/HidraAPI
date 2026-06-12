/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InspectionFinding
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.domain.model
 *
 * @Description : Finding from inspection.
 *
 */
package dz.sh.hidra.modules.integrity.domain.model;

import dz.sh.hidra.modules.integrity.domain.value.*;
import java.time.Instant;
import java.math.BigDecimal;

    /**
     * Finding from inspection.
     *
         * @param id id
     * @param inspectionRunId inspectionRunId
     * @param findingNumber findingNumber
     * @param findingTypeId findingTypeId
     * @param severity severity
     * @param description description
     * @param kilometerPoint kilometerPoint
     * @param topologyAssetTypeCode topologyAssetTypeCode
     * @param topologyAssetId topologyAssetId
     * @param topologyAssetCodeSnapshot topologyAssetCodeSnapshot
     * @param linkedDefectId linkedDefectId
     * @param observedAt observedAt
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record InspectionFinding(
            String id,
        String inspectionRunId,
        String findingNumber,
        String findingTypeId,
        FindingSeverity severity,
        String description,
        BigDecimal kilometerPoint,
        String topologyAssetTypeCode,
        String topologyAssetId,
        String topologyAssetCodeSnapshot,
        String linkedDefectId,
        Instant observedAt,
        Instant createdAt,
        Instant updatedAt
    ) {

        public InspectionFinding {
        id = normalize(id);
        inspectionRunId = normalize(inspectionRunId);
        findingNumber = normalize(findingNumber);
        findingTypeId = normalize(findingTypeId);
        description = normalize(description);
        topologyAssetTypeCode = normalize(topologyAssetTypeCode);
        topologyAssetId = normalize(topologyAssetId);
        topologyAssetCodeSnapshot = normalize(topologyAssetCodeSnapshot);
        linkedDefectId = normalize(linkedDefectId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
