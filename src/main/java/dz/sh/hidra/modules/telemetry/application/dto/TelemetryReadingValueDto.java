/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryReadingValueDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.dto
 *
 * @Description : Application DTO for telemetry reading values.
 *
 */
package dz.sh.hidra.modules.telemetry.application.dto;

import java.math.BigDecimal;

/**
 * Application DTO for telemetry reading values.
 *
 * <p>Architecture role:
 * Application-layer telemetry data transfer projection. It is technology-neutral and must not depend
 * on REST, persistence, Spring, JPA, topology implementation classes, flow, risk, analytics,
 * workflow, reporting, or notification modules.
 */
public record TelemetryReadingValueDto(
        BigDecimal numericValue,
        String textValue,
        Boolean booleanValue) {

    public TelemetryReadingValueDto {
    }
}
