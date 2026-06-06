/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FacilityType
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.value
 *
 * @Description : Deprecated compatibility constant class for the facility type catalog.
 *
 */
package dz.sh.hidra.modules.topology.domain.value;

import java.util.Arrays;
import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Deprecated compatibility constant class for the facility type controlled vocabulary.
 *
 * <p>Business role:
 * Preserves legacy source compatibility while this business taxonomy is governed by catalog
 * entities with multilingual labels.
 *
 * <p>Architecture role:
 * This is intentionally not a Java enum. New code should use FacilityTypeReference instead.
 *
 * <p>Validation:
 * Only the predefined compatibility constants are accepted through valueOf(String).
 */
@Deprecated(forRemoval = false)
public final class FacilityType implements ValueObject {

    public static final FacilityType COMPRESSION_STATION = new FacilityType("COMPRESSION_STATION");
    public static final FacilityType PUMPING_STATION = new FacilityType("PUMPING_STATION");
    public static final FacilityType METERING_STATION = new FacilityType("METERING_STATION");
    public static final FacilityType VALVE_STATION = new FacilityType("VALVE_STATION");
    public static final FacilityType TERMINAL = new FacilityType("TERMINAL");
    public static final FacilityType PROCESSING_PLANT = new FacilityType("PROCESSING_PLANT");
    public static final FacilityType PRODUCTION_FIELD = new FacilityType("PRODUCTION_FIELD");
    public static final FacilityType GATHERING_CENTER = new FacilityType("GATHERING_CENTER");
    public static final FacilityType STORAGE_FACILITY = new FacilityType("STORAGE_FACILITY");
    public static final FacilityType DELIVERY_FACILITY = new FacilityType("DELIVERY_FACILITY");
    public static final FacilityType RECEIPT_FACILITY = new FacilityType("RECEIPT_FACILITY");
    public static final FacilityType DISPATCHING_CENTER = new FacilityType("DISPATCHING_CENTER");
    public static final FacilityType OTHER = new FacilityType("OTHER");

    private static final FacilityType[] VALUES = {
            COMPRESSION_STATION,
            PUMPING_STATION,
            METERING_STATION,
            VALVE_STATION,
            TERMINAL,
            PROCESSING_PLANT,
            PRODUCTION_FIELD,
            GATHERING_CENTER,
            STORAGE_FACILITY,
            DELIVERY_FACILITY,
            RECEIPT_FACILITY,
            DISPATCHING_CENTER,
            OTHER
    };

    private final String name;

    private FacilityType(String name) {
        this.name = requireName(name);
    }

    public static FacilityType valueOf(String name) {
        String normalizedName = requireName(name);
        return Arrays.stream(VALUES)
                .filter(value -> value.name.equals(normalizedName))
                .findFirst()
                .orElseThrow(() -> new InvalidValueObjectException("FacilityType is not supported: " + name));
    }

    public static FacilityType[] values() {
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
        if (!(other instanceof FacilityType that)) {
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
            throw new InvalidValueObjectException("FacilityType name must not be null or blank.");
        }
        return value.trim().toUpperCase();
    }
}
