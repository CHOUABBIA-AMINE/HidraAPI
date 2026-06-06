/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EquipmentType
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.value
 *
 * @Description : Deprecated compatibility constant class for the equipment type catalog.
 *
 */
package dz.sh.hidra.modules.topology.domain.value;

import java.util.Arrays;
import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

@Deprecated(forRemoval = false)
public final class EquipmentType implements ValueObject {

    public static final EquipmentType COMPRESSOR = new EquipmentType("COMPRESSOR");
    public static final EquipmentType PUMP = new EquipmentType("PUMP");
    public static final EquipmentType VALVE = new EquipmentType("VALVE");
    public static final EquipmentType METER = new EquipmentType("METER");
    public static final EquipmentType SEPARATOR = new EquipmentType("SEPARATOR");
    public static final EquipmentType SCRAPER_LAUNCHER = new EquipmentType("SCRAPER_LAUNCHER");
    public static final EquipmentType SCRAPER_RECEIVER = new EquipmentType("SCRAPER_RECEIVER");
    public static final EquipmentType ACTUATOR = new EquipmentType("ACTUATOR");
    public static final EquipmentType CONTROL_PANEL = new EquipmentType("CONTROL_PANEL");
    public static final EquipmentType INSTRUMENTATION = new EquipmentType("INSTRUMENTATION");
    public static final EquipmentType OTHER = new EquipmentType("OTHER");

    private static final EquipmentType[] VALUES = {COMPRESSOR, PUMP, VALVE, METER, SEPARATOR,
            SCRAPER_LAUNCHER, SCRAPER_RECEIVER, ACTUATOR, CONTROL_PANEL, INSTRUMENTATION, OTHER};

    private final String name;

    private EquipmentType(String name) {
        this.name = requireName(name);
    }

    public static EquipmentType valueOf(String name) {
        String normalizedName = requireName(name);
        return Arrays.stream(VALUES)
                .filter(value -> value.name.equals(normalizedName))
                .findFirst()
                .orElseThrow(() -> new InvalidValueObjectException("EquipmentType is not supported: " + name));
    }

    public static EquipmentType[] values() {
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
        if (!(other instanceof EquipmentType that)) {
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
            throw new InvalidValueObjectException("EquipmentType name must not be null or blank.");
        }
        return value.trim().toUpperCase();
    }
}
