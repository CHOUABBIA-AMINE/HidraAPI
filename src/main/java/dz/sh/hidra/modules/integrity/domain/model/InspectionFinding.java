/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InspectionFinding
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.integrity.domain.exception.InvalidIntegrityValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidIntegrityValueException("InspectionFinding id must not be blank.");
        }
        // HRA-051 required: inspectionRunId
        if (inspectionRunId == null || inspectionRunId.isBlank()) {
            throw new InvalidIntegrityValueException("InspectionFinding inspection run id must not be blank.");
        }
        // HRA-051 required: findingNumber
        if (findingNumber == null || findingNumber.isBlank()) {
            throw new InvalidIntegrityValueException("InspectionFinding finding number must not be blank.");
        }
        // HRA-051 required: findingTypeId
        if (findingTypeId == null || findingTypeId.isBlank()) {
            throw new InvalidIntegrityValueException("InspectionFinding finding type id must not be blank.");
        }
        // HRA-051 required: severity
        if (severity == null) {
            throw new InvalidIntegrityValueException("InspectionFinding severity must not be null.");
        }
        // HRA-051 required: observedAt
        if (observedAt == null) {
            throw new InvalidIntegrityValueException("InspectionFinding observed at must not be null.");
        }

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
