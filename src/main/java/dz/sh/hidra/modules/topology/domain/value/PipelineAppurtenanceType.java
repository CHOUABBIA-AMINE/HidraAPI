/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineAppurtenanceType
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.value
 *
 * @Description : Deprecated compatibility constant class for the pipeline appurtenance type catalog.
 *
 */
package dz.sh.hidra.modules.topology.domain.value;

import java.util.Arrays;
import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

@Deprecated(forRemoval = false)
public final class PipelineAppurtenanceType implements ValueObject {

    public static final PipelineAppurtenanceType VALVE = new PipelineAppurtenanceType("VALVE");
    public static final PipelineAppurtenanceType INJECTION_POINT = new PipelineAppurtenanceType("INJECTION_POINT");
    public static final PipelineAppurtenanceType EXTRACTION_POINT = new PipelineAppurtenanceType("EXTRACTION_POINT");
    public static final PipelineAppurtenanceType PURGE_POINT = new PipelineAppurtenanceType("PURGE_POINT");
    public static final PipelineAppurtenanceType VENT_POINT = new PipelineAppurtenanceType("VENT_POINT");
    public static final PipelineAppurtenanceType DRAIN_POINT = new PipelineAppurtenanceType("DRAIN_POINT");
    public static final PipelineAppurtenanceType SAMPLING_POINT = new PipelineAppurtenanceType("SAMPLING_POINT");
    public static final PipelineAppurtenanceType METERING_POINT = new PipelineAppurtenanceType("METERING_POINT");
    public static final PipelineAppurtenanceType SCRAPER_LAUNCHER = new PipelineAppurtenanceType("SCRAPER_LAUNCHER");
    public static final PipelineAppurtenanceType SCRAPER_RECEIVER = new PipelineAppurtenanceType("SCRAPER_RECEIVER");
    public static final PipelineAppurtenanceType HOT_TAP_POINT = new PipelineAppurtenanceType("HOT_TAP_POINT");
    public static final PipelineAppurtenanceType BYPASS_POINT = new PipelineAppurtenanceType("BYPASS_POINT");
    public static final PipelineAppurtenanceType CONNECTION_POINT = new PipelineAppurtenanceType("CONNECTION_POINT");
    public static final PipelineAppurtenanceType OTHER = new PipelineAppurtenanceType("OTHER");

    private static final PipelineAppurtenanceType[] VALUES = {VALVE, INJECTION_POINT, EXTRACTION_POINT,
            PURGE_POINT, VENT_POINT, DRAIN_POINT, SAMPLING_POINT, METERING_POINT, SCRAPER_LAUNCHER,
            SCRAPER_RECEIVER, HOT_TAP_POINT, BYPASS_POINT, CONNECTION_POINT, OTHER};

    private final String name;

    private PipelineAppurtenanceType(String name) {
        this.name = requireName(name);
    }

    public static PipelineAppurtenanceType valueOf(String name) {
        String normalizedName = requireName(name);
        return Arrays.stream(VALUES)
                .filter(value -> value.name.equals(normalizedName))
                .findFirst()
                .orElseThrow(() -> new InvalidValueObjectException("PipelineAppurtenanceType is not supported: " + name));
    }

    public static PipelineAppurtenanceType[] values() {
        return VALUES.clone();
    }

    public String name() {
        return name;
    }

    public boolean isValve() {
        return this == VALVE;
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
        if (!(other instanceof PipelineAppurtenanceType that)) {
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
            throw new InvalidValueObjectException("PipelineAppurtenanceType name must not be null or blank.");
        }
        return value.trim().toUpperCase();
    }
}
