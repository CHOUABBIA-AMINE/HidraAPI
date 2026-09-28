/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryPointBinding
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.model
 *
 * @Description : Temporal binding between a telemetry point and a topology asset reference.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.model;

import dz.sh.hidra.modules.telemetry.domain.exception.InvalidTelemetryValueException;
import java.time.Instant;

    /**
     * Temporal binding between a telemetry point and a topology asset reference.
     *
         * @param id id
     * @param pointId pointId
     * @param topologyAssetTypeCode topologyAssetTypeCode
     * @param topologyAssetId topologyAssetId
     * @param topologyAssetCode topologyAssetCode
     * @param topologyAssetNameSnapshot topologyAssetNameSnapshot
     * @param topologySnapshotId topologySnapshotId
     * @param bindingRoleId bindingRoleId
     * @param active active
     * @param validFrom validFrom
     * @param validTo validTo
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record TelemetryPointBinding(
            String id,
        String pointId,
        String topologyAssetTypeCode,
        String topologyAssetId,
        String topologyAssetCode,
        String topologyAssetNameSnapshot,
        String topologySnapshotId,
        String bindingRoleId,
        boolean active,
        Instant validFrom,
        Instant validTo,
        Instant createdAt,
        Instant updatedAt
    ) {

        public TelemetryPointBinding {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidTelemetryValueException("TelemetryPointBinding id must not be blank.");
        }
        // HRA-051 required: pointId
        if (pointId == null || pointId.isBlank()) {
            throw new InvalidTelemetryValueException("TelemetryPointBinding point id must not be blank.");
        }
        // HRA-051 required: topologyAssetTypeCode
        if (topologyAssetTypeCode == null || topologyAssetTypeCode.isBlank()) {
            throw new InvalidTelemetryValueException("TelemetryPointBinding topology asset type code must not be blank.");
        }
        // HRA-051 required: topologyAssetId
        if (topologyAssetId == null || topologyAssetId.isBlank()) {
            throw new InvalidTelemetryValueException("TelemetryPointBinding topology asset id must not be blank.");
        }
        // HRA-051 required: topologyAssetCode
        if (topologyAssetCode == null || topologyAssetCode.isBlank()) {
            throw new InvalidTelemetryValueException("TelemetryPointBinding topology asset code must not be blank.");
        }
        // HRA-051 required: bindingRoleId
        if (bindingRoleId == null || bindingRoleId.isBlank()) {
            throw new InvalidTelemetryValueException("TelemetryPointBinding binding role id must not be blank.");
        }
        // HRA-051 required: validFrom
        if (validFrom == null) {
            throw new InvalidTelemetryValueException("TelemetryPointBinding valid from must not be null.");
        }
        // HRA-051 order: validFrom <= validTo
        if (validFrom != null && validTo != null && validTo.isBefore(validFrom)) {
            throw new InvalidTelemetryValueException("TelemetryPointBinding valid to must not be before valid from.");
        }

        id = normalize(id);
        pointId = normalize(pointId);
        topologyAssetTypeCode = normalize(topologyAssetTypeCode);
        topologyAssetId = normalize(topologyAssetId);
        topologyAssetCode = normalize(topologyAssetCode);
        topologyAssetNameSnapshot = normalize(topologyAssetNameSnapshot);
        topologySnapshotId = normalize(topologySnapshotId);
        bindingRoleId = normalize(bindingRoleId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
