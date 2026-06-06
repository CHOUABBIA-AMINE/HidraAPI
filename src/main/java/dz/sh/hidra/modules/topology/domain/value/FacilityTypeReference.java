/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FacilityTypeReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.value
 *
 * @Description : Stable reference to a multilingual facility type catalog entry.
 *
 */
package dz.sh.hidra.modules.topology.domain.value;

import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Reference to a configurable facility type catalog entry.
 *
 * <p>Business role:
 * This value object identifies a facility type such as terminal, processing plant, production field,
 * compression station, pumping station, metering station, or storage facility without hard-coding the
 * type as a Java enum.
 *
 * <p>Architecture role:
 * This is a pure topology reference object. It carries only a stable catalog entry identifier and a
 * language-neutral code; localized labels are resolved by catalog application services.
 *
 * <p>Validation:
 * The catalog entry identifier and code are mandatory.
 *
 * @param id facility type catalog entry identifier
 * @param code language-neutral facility type code
 */
public record FacilityTypeReference(String id, TopologyCode code) implements ValueObject {

    public FacilityTypeReference {
        id = requireText(id, "FacilityTypeReference id");
        code = Objects.requireNonNull(code, "FacilityTypeReference code must not be null.");
    }

    public static FacilityTypeReference of(String id, TopologyCode code) {
        return new FacilityTypeReference(id, code);
    }

    public static FacilityTypeReference of(String id, String code) {
        return new FacilityTypeReference(id, TopologyCode.of(code));
    }

    public static FacilityTypeReference from(FacilityType facilityType) {
        Objects.requireNonNull(facilityType, "Facility type must not be null.");
        return of(facilityType.name(), facilityType.name());
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

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException(fieldName + " must not be null or blank.");
        }
        return value.trim();
    }
}
