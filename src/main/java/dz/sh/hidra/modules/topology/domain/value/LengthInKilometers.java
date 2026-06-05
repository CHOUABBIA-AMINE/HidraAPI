/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LengthInKilometers
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.value
 *
 * @Description : Validated positive length value object expressed in kilometers.
 *
 */
package dz.sh.hidra.modules.topology.domain.value;

import java.math.BigDecimal;
import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Represents a physical length in kilometers.
 *
 * <p>Business role:
 * This value object stores a physical length in kilometers for topology domain assets.
 *
 * <p>Architecture role:
 * This is a pure topology domain value object and must not depend on Spring, JPA, REST, identity,
 * organization, platform, measurement, flow, risk, workflow, or infrastructure code.
 *
 * <p>Validation:
 * The value must be greater than zero.
 *
 * <p>Usage:
 * Use this type instead of raw numeric values when topology logic requires this measurement.
 *
 * @param value numeric value in kilometers
 */
public record LengthInKilometers(BigDecimal value) implements ValueObject {

    public LengthInKilometers {
        value = normalize(value);
    }

    /**
     * Creates the value object from a BigDecimal input.
     *
     * @param value raw value
     * @return validated value object
     */
    public static LengthInKilometers of(BigDecimal value) {
        return new LengthInKilometers(value);
    }

    /**
     * Creates the value object from a double input.
     *
     * @param value raw value
     * @return validated value object
     */
    public static LengthInKilometers of(double value) {
        return new LengthInKilometers(BigDecimal.valueOf(value));
    }

    private static BigDecimal normalize(BigDecimal value) {
        Objects.requireNonNull(value, "LengthInKilometers value must not be null.");

        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidValueObjectException("LengthInKilometers must be greater than zero.");
        }

        return value.stripTrailingZeros();
    }
}
