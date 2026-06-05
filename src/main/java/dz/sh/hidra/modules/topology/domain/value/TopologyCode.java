/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyCode
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.value
 *
 * @Description : Validated topology business code value object.
 *
 */
package dz.sh.hidra.modules.topology.domain.value;

import java.util.Locale;
import java.util.regex.Pattern;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Represents a stable business code for a topology asset.
 *
 * <p>Business role:
 * This value object captures business-readable codes for pipeline systems, pipelines, facilities,
 * nodes, pipeline segments, appurtenances, connections, and equipment.
 *
 * <p>Architecture role:
 * This is a pure topology domain value object and must not depend on Spring, JPA, REST, identity,
 * organization, platform, measurement, flow, risk, workflow, or infrastructure code.
 *
 * <p>Validation:
 * Codes are trimmed, upper-cased, must be 2 to 80 characters long, and may contain uppercase
 * letters, digits, underscores, hyphens, or dots. Codes must start with a letter or digit.
 *
 * <p>Usage:
 * Use this type instead of raw strings whenever topology domain logic requires a business code.
 *
 * @param value validated code, for example <code>GZ1-CS-EAST-01</code>
 */
public record TopologyCode(String value) implements ValueObject {

    private static final Pattern ALLOWED_PATTERN = Pattern.compile("^[A-Z0-9][A-Z0-9_.-]*$");

    public TopologyCode {
        value = normalize(value);
    }

    /**
     * Creates a topology code from raw input.
     *
     * @param value raw code
     * @return validated topology code
     */
    public static TopologyCode of(String value) {
        return new TopologyCode(value);
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException("TopologyCode must not be null or blank.");
        }

        String normalized = value.trim().toUpperCase(Locale.ROOT);

        if (normalized.length() < 2 || normalized.length() > 80) {
            throw new InvalidValueObjectException("TopologyCode length must be between 2 and 80 characters.");
        }

        if (!ALLOWED_PATTERN.matcher(normalized).matches()) {
            throw new InvalidValueObjectException("TopologyCode must contain only uppercase letters, digits, underscores, hyphens, or dots and must start with a letter or digit.");
        }

        return normalized;
    }
}
