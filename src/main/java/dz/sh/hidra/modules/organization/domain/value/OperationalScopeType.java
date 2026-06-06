/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalScopeType
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.value
 *
 * @Description : Deprecated compatibility constant class for operational scope types.
 *
 */
package dz.sh.hidra.modules.organization.domain.value;

import java.util.Arrays;
import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

@Deprecated(forRemoval = false)
public final class OperationalScopeType implements ValueObject {

    public static final OperationalScopeType TOPOLOGY_STATION = new OperationalScopeType("TOPOLOGY_STATION");
    public static final OperationalScopeType TOPOLOGY_COMPRESSION_STATION = new OperationalScopeType("TOPOLOGY_COMPRESSION_STATION");
    public static final OperationalScopeType TOPOLOGY_PUMPING_STATION = new OperationalScopeType("TOPOLOGY_PUMPING_STATION");
    public static final OperationalScopeType TOPOLOGY_DELIVERY_STATION = new OperationalScopeType("TOPOLOGY_DELIVERY_STATION");
    public static final OperationalScopeType TOPOLOGY_METERING_STATION = new OperationalScopeType("TOPOLOGY_METERING_STATION");
    public static final OperationalScopeType TOPOLOGY_PIPELINE = new OperationalScopeType("TOPOLOGY_PIPELINE");
    public static final OperationalScopeType TOPOLOGY_REGION = new OperationalScopeType("TOPOLOGY_REGION");
    public static final OperationalScopeType TOPOLOGY_FACILITY = new OperationalScopeType("TOPOLOGY_FACILITY");
    public static final OperationalScopeType GENERIC_OPERATIONAL_SCOPE = new OperationalScopeType("GENERIC_OPERATIONAL_SCOPE");

    private static final OperationalScopeType[] VALUES = {TOPOLOGY_STATION, TOPOLOGY_COMPRESSION_STATION,
            TOPOLOGY_PUMPING_STATION, TOPOLOGY_DELIVERY_STATION, TOPOLOGY_METERING_STATION,
            TOPOLOGY_PIPELINE, TOPOLOGY_REGION, TOPOLOGY_FACILITY, GENERIC_OPERATIONAL_SCOPE};

    private final String name;

    private OperationalScopeType(String name) {
        this.name = requireName(name);
    }

    public static OperationalScopeType valueOf(String name) {
        String normalizedName = requireName(name);
        return Arrays.stream(VALUES)
                .filter(value -> value.name.equals(normalizedName))
                .findFirst()
                .orElseThrow(() -> new InvalidValueObjectException("OperationalScopeType is not supported: " + name));
    }

    public static OperationalScopeType[] values() {
        return VALUES.clone();
    }

    public String name() {
        return name;
    }

    public boolean isStationScope() {
        return this == TOPOLOGY_STATION
                || this == TOPOLOGY_COMPRESSION_STATION
                || this == TOPOLOGY_PUMPING_STATION
                || this == TOPOLOGY_DELIVERY_STATION
                || this == TOPOLOGY_METERING_STATION;
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
        if (!(other instanceof OperationalScopeType that)) {
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
            throw new InvalidValueObjectException("OperationalScopeType name must not be null or blank.");
        }
        return value.trim().toUpperCase();
    }
}
