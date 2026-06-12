/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryValueShape
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.value
 *
 * @Description : Represents a telemetry reading value shape.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.value;

import java.math.BigDecimal;

/**
 * Represents exactly one telemetry value shape.
 *
 * @param numericValue numeric value
 * @param textValue text value
 * @param booleanValue boolean value
 */
public record TelemetryValueShape(
        BigDecimal numericValue,
        String textValue,
        Boolean booleanValue
) {

    public boolean hasExactlyOneValue() {
        int count = 0;
        if (numericValue != null) {
            count++;
        }
        if (textValue != null && !textValue.isBlank()) {
            count++;
        }
        if (booleanValue != null) {
            count++;
        }
        return count == 1;
    }
}
