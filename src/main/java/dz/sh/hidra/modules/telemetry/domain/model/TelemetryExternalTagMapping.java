/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryExternalTagMapping
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.model
 *
 * @Description : Maps an external tag identity to a canonical telemetry point.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.model;

import dz.sh.hidra.modules.telemetry.domain.exception.InvalidTelemetryValueException;
import dz.sh.hidra.modules.telemetry.domain.value.*;
import java.time.Instant;

    /**
     * Maps an external tag identity to a canonical telemetry point.
     *
         * @param id id
     * @param sourceId sourceId
     * @param deviceId deviceId
     * @param pointId pointId
     * @param externalTagName externalTagName
     * @param externalTagId externalTagId
     * @param externalNamespace externalNamespace
     * @param externalDataType externalDataType
     * @param mappingMode mappingMode
     * @param transformationExpression transformationExpression
     * @param active active
     * @param validFrom validFrom
     * @param validTo validTo
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record TelemetryExternalTagMapping(
            String id,
        String sourceId,
        String deviceId,
        String pointId,
        String externalTagName,
        String externalTagId,
        String externalNamespace,
        String externalDataType,
        ExternalTagMappingMode mappingMode,
        String transformationExpression,
        boolean active,
        Instant validFrom,
        Instant validTo,
        Instant createdAt,
        Instant updatedAt
    ) {

        public TelemetryExternalTagMapping {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidTelemetryValueException("TelemetryExternalTagMapping id must not be blank.");
        }
        // HRA-051 required: sourceId
        if (sourceId == null || sourceId.isBlank()) {
            throw new InvalidTelemetryValueException("TelemetryExternalTagMapping source id must not be blank.");
        }
        // HRA-051 required: pointId
        if (pointId == null || pointId.isBlank()) {
            throw new InvalidTelemetryValueException("TelemetryExternalTagMapping point id must not be blank.");
        }
        // HRA-051 required: mappingMode
        if (mappingMode == null) {
            throw new InvalidTelemetryValueException("TelemetryExternalTagMapping mapping mode must not be null.");
        }
        // HRA-051 required: validFrom
        if (validFrom == null) {
            throw new InvalidTelemetryValueException("TelemetryExternalTagMapping valid from must not be null.");
        }
        // HRA-051 order: validFrom <= validTo
        if (validFrom != null && validTo != null && validTo.isBefore(validFrom)) {
            throw new InvalidTelemetryValueException("TelemetryExternalTagMapping valid to must not be before valid from.");
        }

        id = normalize(id);
        sourceId = normalize(sourceId);
        deviceId = normalize(deviceId);
        pointId = normalize(pointId);
        externalTagName = normalize(externalTagName);
        externalTagId = normalize(externalTagId);
        externalNamespace = normalize(externalNamespace);
        externalDataType = normalize(externalDataType);
        transformationExpression = normalize(transformationExpression);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
