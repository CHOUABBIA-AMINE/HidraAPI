/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryPoint
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
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
