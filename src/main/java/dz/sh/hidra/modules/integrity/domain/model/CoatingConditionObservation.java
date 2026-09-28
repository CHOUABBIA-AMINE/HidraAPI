/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CoatingConditionObservation
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.integrity.domain.exception.InvalidIntegrityValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidIntegrityValueException("CoatingConditionObservation id must not be blank.");
        }
        // HRA-051 required: topologyAssetTypeCode
        if (topologyAssetTypeCode == null || topologyAssetTypeCode.isBlank()) {
            throw new InvalidIntegrityValueException("CoatingConditionObservation topology asset type code must not be blank.");
        }
        // HRA-051 required: topologyAssetId
        if (topologyAssetId == null || topologyAssetId.isBlank()) {
            throw new InvalidIntegrityValueException("CoatingConditionObservation topology asset id must not be blank.");
        }
        // HRA-051 required: coatingConditionId
        if (coatingConditionId == null || coatingConditionId.isBlank()) {
            throw new InvalidIntegrityValueException("CoatingConditionObservation coating condition id must not be blank.");
        }
        // HRA-051 required: observedAt
        if (observedAt == null) {
            throw new InvalidIntegrityValueException("CoatingConditionObservation observed at must not be null.");
        }

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
