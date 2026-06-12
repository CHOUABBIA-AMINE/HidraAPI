/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryUnit
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.model
 *
 * @Description : Telemetry unit of measure.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.model;

import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Telemetry unit of measure.
     *
         * @param id id
     * @param code code
     * @param symbol symbol
     * @param dimension dimension
     * @param baseUnitId baseUnitId
     * @param toBaseFactor toBaseFactor
     * @param toBaseOffset toBaseOffset
     * @param displayPrecision displayPrecision
     * @param active active
     * @param systemDefined systemDefined
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record TelemetryUnit(
            String id,
        String code,
        String symbol,
        String dimension,
        String baseUnitId,
        BigDecimal toBaseFactor,
        BigDecimal toBaseOffset,
        Integer displayPrecision,
        boolean active,
        boolean systemDefined,
        Instant createdAt,
        Instant updatedAt
    ) {

        public TelemetryUnit {
        id = normalize(id);
        code = normalize(code);
        symbol = normalize(symbol);
        dimension = normalize(dimension);
        baseUnitId = normalize(baseUnitId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
