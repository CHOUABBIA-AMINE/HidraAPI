/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineAppurtenanceTypeReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.value
 *
 * @Description : Stable reference to a multilingual pipeline appurtenance type catalog entry.
 *
 */
package dz.sh.hidra.modules.topology.domain.value;

import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Reference to a configurable pipeline appurtenance type catalog entry.
 *
 * <p>Business role:
 * This value object identifies point assets installed on or along a pipeline, such as valves,
 * injection points, extraction points, purge points, pig launchers, pig receivers, metering points,
 * pressure regulation points, safety devices, sampling points, and connection points.
 *
 * <p>Architecture role:
 * This is a pure topology reference object. It carries only a stable catalog entry identifier and a
 * language-neutral code; localized labels are resolved by catalog application services.
 *
 * <p>Validation:
 * The catalog entry identifier and code are mandatory.
 *
 * @param id pipeline appurtenance type catalog entry identifier
 * @param code language-neutral pipeline appurtenance type code
 */
public record PipelineAppurtenanceTypeReference(String id, TopologyCode code) implements ValueObject {

    public PipelineAppurtenanceTypeReference {
        id = requireText(id, "PipelineAppurtenanceTypeReference id");
        code = Objects.requireNonNull(code, "PipelineAppurtenanceTypeReference code must not be null.");
    }

    public static PipelineAppurtenanceTypeReference of(String id, TopologyCode code) {
        return new PipelineAppurtenanceTypeReference(id, code);
    }

    public static PipelineAppurtenanceTypeReference of(String id, String code) {
        return new PipelineAppurtenanceTypeReference(id, TopologyCode.of(code));
    }

    public static PipelineAppurtenanceTypeReference from(PipelineAppurtenanceType appurtenanceType) {
        Objects.requireNonNull(appurtenanceType, "Pipeline appurtenance type must not be null.");
        return of(appurtenanceType.name(), appurtenanceType.name());
    }

    public String name() {
        return code.value();
    }

    public boolean is(String expectedCode) {
        return code.value().equals(TopologyCode.of(expectedCode).value());
    }

    public boolean isAny(String... expectedCodes) {
        for (String expectedCode : expectedCodes) {
            if (is(expectedCode)) {
                return true;
            }
        }
        return false;
    }

    public boolean isValve() {
        return is("VALVE");
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException(fieldName + " must not be null or blank.");
        }
        return value.trim();
    }
}
