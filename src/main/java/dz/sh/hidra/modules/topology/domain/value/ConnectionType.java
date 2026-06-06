/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConnectionType
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.value
 *
 * @Description : Deprecated compatibility constant class for the connection type catalog.
 *
 */
package dz.sh.hidra.modules.topology.domain.value;

import java.util.Arrays;
import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

@Deprecated(forRemoval = false)
public final class ConnectionType implements ValueObject {

    public static final ConnectionType PIPELINE_SEGMENT = new ConnectionType("PIPELINE_SEGMENT");
    public static final ConnectionType FACILITY_INTERNAL = new ConnectionType("FACILITY_INTERNAL");
    public static final ConnectionType VALVE_CONNECTION = new ConnectionType("VALVE_CONNECTION");
    public static final ConnectionType METERING_CONNECTION = new ConnectionType("METERING_CONNECTION");
    public static final ConnectionType JUNCTION_CONNECTION = new ConnectionType("JUNCTION_CONNECTION");
    public static final ConnectionType APPURTENANCE_CONNECTION = new ConnectionType("APPURTENANCE_CONNECTION");
    public static final ConnectionType OTHER = new ConnectionType("OTHER");

    private static final ConnectionType[] VALUES = {PIPELINE_SEGMENT, FACILITY_INTERNAL, VALVE_CONNECTION,
            METERING_CONNECTION, JUNCTION_CONNECTION, APPURTENANCE_CONNECTION, OTHER};

    private final String name;

    private ConnectionType(String name) {
        this.name = requireName(name);
    }

    public static ConnectionType valueOf(String name) {
        String normalizedName = requireName(name);
        return Arrays.stream(VALUES)
                .filter(value -> value.name.equals(normalizedName))
                .findFirst()
                .orElseThrow(() -> new InvalidValueObjectException("ConnectionType is not supported: " + name));
    }

    public static ConnectionType[] values() {
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
        if (!(other instanceof ConnectionType that)) {
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
            throw new InvalidValueObjectException("ConnectionType name must not be null or blank.");
        }
        return value.trim().toUpperCase();
    }
}
