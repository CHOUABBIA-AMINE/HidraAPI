/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryAggregationMethodReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.value
 *
 * @Description : Telemetry aggregation method catalog reference.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.value;

import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Telemetry aggregation method catalog reference.
 *
 * <p>Business rule:
 * Business taxonomy values are configurable catalog references, not Java enums. Localized labels are
 * resolved by catalog services and are not embedded in this reference.
 *
 * @param id catalog entry identifier
 * @param code language-neutral catalog code
 */
public record TelemetryAggregationMethodReference(String id, TelemetryCode code) implements ValueObject {

    public TelemetryAggregationMethodReference {
        id = requireText(id, "TelemetryAggregationMethodReference id");
        code = Objects.requireNonNull(code, "TelemetryAggregationMethodReference code must not be null.");
    }

    public static TelemetryAggregationMethodReference of(String id, TelemetryCode code) {
        return new TelemetryAggregationMethodReference(id, code);
    }

    public static TelemetryAggregationMethodReference of(String id, String code) {
        return new TelemetryAggregationMethodReference(id, TelemetryCode.of(code));
    }

    public String name() {
        return code.value();
    }

    public boolean is(String expectedCode) {
        return code.value().equals(TelemetryCode.of(expectedCode).value());
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException(fieldName + " must not be null or blank.");
        }

        String normalized = value.trim();

        if (normalized.length() > 80) {
            throw new InvalidValueObjectException(fieldName + " length must not exceed 80 characters.");
        }

        return normalized;
    }
}
