/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ValveType
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.value
 *
 * @Description : Deprecated compatibility constant class for the valve type catalog.
 *
 */
package dz.sh.hidra.modules.topology.domain.value;

import java.util.Arrays;
import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

@Deprecated(forRemoval = false)
public final class ValveType implements ValueObject {

    public static final ValveType BLOCK_VALVE = new ValveType("BLOCK_VALVE");
    public static final ValveType SECTIONALIZING_VALVE = new ValveType("SECTIONALIZING_VALVE");
    public static final ValveType ISOLATION_VALVE = new ValveType("ISOLATION_VALVE");
    public static final ValveType SHUTDOWN_VALVE = new ValveType("SHUTDOWN_VALVE");
    public static final ValveType CONTROL_VALVE = new ValveType("CONTROL_VALVE");
    public static final ValveType CHECK_VALVE = new ValveType("CHECK_VALVE");
    public static final ValveType RELIEF_VALVE = new ValveType("RELIEF_VALVE");
    public static final ValveType PRESSURE_REGULATING_VALVE = new ValveType("PRESSURE_REGULATING_VALVE");
    public static final ValveType BYPASS_VALVE = new ValveType("BYPASS_VALVE");
    public static final ValveType DRAIN_VALVE = new ValveType("DRAIN_VALVE");
    public static final ValveType VENT_VALVE = new ValveType("VENT_VALVE");
    public static final ValveType ESD_VALVE = new ValveType("ESD_VALVE");
    public static final ValveType MANUAL_VALVE = new ValveType("MANUAL_VALVE");
    public static final ValveType MOTORIZED_VALVE = new ValveType("MOTORIZED_VALVE");
    public static final ValveType OTHER = new ValveType("OTHER");

    private static final ValveType[] VALUES = {BLOCK_VALVE, SECTIONALIZING_VALVE, ISOLATION_VALVE,
            SHUTDOWN_VALVE, CONTROL_VALVE, CHECK_VALVE, RELIEF_VALVE, PRESSURE_REGULATING_VALVE,
            BYPASS_VALVE, DRAIN_VALVE, VENT_VALVE, ESD_VALVE, MANUAL_VALVE, MOTORIZED_VALVE, OTHER};

    private final String name;

    private ValveType(String name) {
        this.name = requireName(name);
    }

    public static ValveType valueOf(String name) {
        String normalizedName = requireName(name);
        return Arrays.stream(VALUES)
                .filter(value -> value.name.equals(normalizedName))
                .findFirst()
                .orElseThrow(() -> new InvalidValueObjectException("ValveType is not supported: " + name));
    }

    public static ValveType[] values() {
        return VALUES.clone();
    }

    public String name() {
        return name;
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
        if (!(other instanceof ValveType that)) {
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
            throw new InvalidValueObjectException("ValveType name must not be null or blank.");
        }
        return value.trim().toUpperCase();
    }
}
