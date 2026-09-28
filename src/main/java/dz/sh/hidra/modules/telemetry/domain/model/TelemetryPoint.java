/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryPoint
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.model
 *
 * @Description : Canonical Hidra telemetry point/tag.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.model;

import dz.sh.hidra.modules.telemetry.domain.exception.InvalidTelemetryValueException;
import dz.sh.hidra.modules.telemetry.domain.value.*;
import java.time.Instant;
import java.math.BigDecimal;

    /**
     * Canonical Hidra telemetry point/tag.
     *
         * @param id id
     * @param deviceId deviceId
     * @param code code
     * @param nameAr nameAr
     * @param nameFr nameFr
     * @param nameEn nameEn
     * @param pointTypeId pointTypeId
     * @param signalTypeId signalTypeId
     * @param unitId unitId
     * @param defaultAggregationMethodId defaultAggregationMethodId
     * @param samplingPeriodSeconds samplingPeriodSeconds
     * @param externalReference externalReference
     * @param deadbandValue deadbandValue
     * @param minOperationalValue minOperationalValue
     * @param maxOperationalValue maxOperationalValue
     * @param status status
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record TelemetryPoint(
            String id,
        String deviceId,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String pointTypeId,
        String signalTypeId,
        String unitId,
        String defaultAggregationMethodId,
        Integer samplingPeriodSeconds,
        String externalReference,
        BigDecimal deadbandValue,
        BigDecimal minOperationalValue,
        BigDecimal maxOperationalValue,
        TelemetryLifecycleStatus status,
        Instant createdAt,
        Instant updatedAt
    ) {

        public TelemetryPoint {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidTelemetryValueException("TelemetryPoint id must not be blank.");
        }
        // HRA-051 required: deviceId
        if (deviceId == null || deviceId.isBlank()) {
            throw new InvalidTelemetryValueException("TelemetryPoint device id must not be blank.");
        }
        // HRA-051 required: code
        if (code == null || code.isBlank()) {
            throw new InvalidTelemetryValueException("TelemetryPoint code must not be blank.");
        }
        // HRA-051 required: pointTypeId
        if (pointTypeId == null || pointTypeId.isBlank()) {
            throw new InvalidTelemetryValueException("TelemetryPoint point type id must not be blank.");
        }
        // HRA-051 required: signalTypeId
        if (signalTypeId == null || signalTypeId.isBlank()) {
            throw new InvalidTelemetryValueException("TelemetryPoint signal type id must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidTelemetryValueException("TelemetryPoint status must not be null.");
        }

        id = normalize(id);
        deviceId = normalize(deviceId);
        code = normalize(code);
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
        pointTypeId = normalize(pointTypeId);
        signalTypeId = normalize(signalTypeId);
        unitId = normalize(unitId);
        defaultAggregationMethodId = normalize(defaultAggregationMethodId);
        externalReference = normalize(externalReference);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
