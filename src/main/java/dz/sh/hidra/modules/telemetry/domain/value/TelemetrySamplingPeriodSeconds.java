/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetrySamplingPeriodSeconds
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.value
 *
 * @Description : Validated telemetry sampling period value object.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.value;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Sampling period in seconds for a telemetry point.
 *
 * @param value period in seconds
 */
public record TelemetrySamplingPeriodSeconds(Integer value) implements ValueObject {

    public TelemetrySamplingPeriodSeconds {
        if (value == null) {
            throw new InvalidValueObjectException("TelemetrySamplingPeriodSeconds value must not be null.");
        }

        if (value < 1 || value > 86_400) {
            throw new InvalidValueObjectException("TelemetrySamplingPeriodSeconds must be between 1 and 86400 seconds.");
        }
    }

    public static TelemetrySamplingPeriodSeconds of(Integer value) {
        return new TelemetrySamplingPeriodSeconds(value);
    }
}
