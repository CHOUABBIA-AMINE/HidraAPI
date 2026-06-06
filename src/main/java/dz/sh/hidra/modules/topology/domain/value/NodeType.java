/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NodeType
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.value
 *
 * @Description : Deprecated compatibility constant class for the node type catalog.
 *
 */
package dz.sh.hidra.modules.topology.domain.value;

import java.util.Arrays;
import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

@Deprecated(forRemoval = false)
public final class NodeType implements ValueObject {

    public static final NodeType FACILITY_INLET = new NodeType("FACILITY_INLET");
    public static final NodeType FACILITY_OUTLET = new NodeType("FACILITY_OUTLET");
    public static final NodeType FACILITY_INTERNAL = new NodeType("FACILITY_INTERNAL");
    public static final NodeType PIPELINE_JUNCTION = new NodeType("PIPELINE_JUNCTION");
    public static final NodeType PIPELINE_VALVE_POINT = new NodeType("PIPELINE_VALVE_POINT");
    public static final NodeType INJECTION_POINT = new NodeType("INJECTION_POINT");
    public static final NodeType EXTRACTION_POINT = new NodeType("EXTRACTION_POINT");
    public static final NodeType PURGE_POINT = new NodeType("PURGE_POINT");
    public static final NodeType VENT_POINT = new NodeType("VENT_POINT");
    public static final NodeType DRAIN_POINT = new NodeType("DRAIN_POINT");
    public static final NodeType METERING_POINT = new NodeType("METERING_POINT");
    public static final NodeType SAMPLING_POINT = new NodeType("SAMPLING_POINT");
    public static final NodeType SCRAPER_POINT = new NodeType("SCRAPER_POINT");
    public static final NodeType RECEIPT_POINT = new NodeType("RECEIPT_POINT");
    public static final NodeType DELIVERY_POINT = new NodeType("DELIVERY_POINT");
    public static final NodeType CONNECTION_POINT = new NodeType("CONNECTION_POINT");
    public static final NodeType OTHER = new NodeType("OTHER");

    private static final NodeType[] VALUES = {FACILITY_INLET, FACILITY_OUTLET, FACILITY_INTERNAL,
            PIPELINE_JUNCTION, PIPELINE_VALVE_POINT, INJECTION_POINT, EXTRACTION_POINT, PURGE_POINT,
            VENT_POINT, DRAIN_POINT, METERING_POINT, SAMPLING_POINT, SCRAPER_POINT, RECEIPT_POINT,
            DELIVERY_POINT, CONNECTION_POINT, OTHER};

    private final String name;

    private NodeType(String name) {
        this.name = requireName(name);
    }

    public static NodeType valueOf(String name) {
        String normalizedName = requireName(name);
        return Arrays.stream(VALUES)
                .filter(value -> value.name.equals(normalizedName))
                .findFirst()
                .orElseThrow(() -> new InvalidValueObjectException("NodeType is not supported: " + name));
    }

    public static NodeType[] values() {
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
        if (!(other instanceof NodeType that)) {
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
            throw new InvalidValueObjectException("NodeType name must not be null or blank.");
        }
        return value.trim().toUpperCase();
    }
}
