/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingLineType
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.value
 *
 * @Description : Deprecated compatibility constant class for reporting line types.
 *
 */
package dz.sh.hidra.modules.organization.domain.value;

import java.util.Arrays;
import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

@Deprecated(forRemoval = false)
public final class ReportingLineType implements ValueObject {

    public static final ReportingLineType LINE = new ReportingLineType("LINE");
    public static final ReportingLineType OPERATIONAL = new ReportingLineType("OPERATIONAL");
    public static final ReportingLineType FUNCTIONAL = new ReportingLineType("FUNCTIONAL");
    public static final ReportingLineType ADMINISTRATIVE = new ReportingLineType("ADMINISTRATIVE");
    public static final ReportingLineType TECHNICAL = new ReportingLineType("TECHNICAL");
    public static final ReportingLineType DOTTED_LINE = new ReportingLineType("DOTTED_LINE");

    private static final ReportingLineType[] VALUES = {LINE, OPERATIONAL, FUNCTIONAL, ADMINISTRATIVE, TECHNICAL, DOTTED_LINE};

    private final String name;

    private ReportingLineType(String name) {
        this.name = requireName(name);
    }

    public static ReportingLineType valueOf(String name) {
        String normalizedName = requireName(name);
        return Arrays.stream(VALUES)
                .filter(value -> value.name.equals(normalizedName))
                .findFirst()
                .orElseThrow(() -> new InvalidValueObjectException("ReportingLineType is not supported: " + name));
    }

    public static ReportingLineType[] values() {
        return VALUES.clone();
    }

    public String name() {
        return name;
    }

    public boolean allowsMultipleActiveLines() {
        return this == FUNCTIONAL || this == ADMINISTRATIVE || this == TECHNICAL || this == DOTTED_LINE;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ReportingLineType that)) {
            return false;
        }
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    private static String requireName(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException("ReportingLineType name must not be null or blank.");
        }
        return value.trim().toUpperCase();
    }
}
