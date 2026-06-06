/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ValveTypeReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.value
 *
 * @Description : Stable reference to a multilingual valve type catalog entry.
 *
 */
package dz.sh.hidra.modules.topology.domain.value;

import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Reference to a configurable valve type catalog entry.
 *
 * <p>Business role:
 * This value object identifies valve specializations such as block valve, check valve, control valve,
 * isolation valve, shutdown valve, pressure relief valve, ball valve, gate valve, or butterfly valve
 * without hard-coding the value as a Java enum.
 *
 * <p>Architecture role:
 * This is a pure topology reference object. It carries only a stable catalog entry identifier and a
 * language-neutral code; localized labels are resolved by catalog application services.
 *
 * <p>Validation:
 * The catalog entry identifier and code are mandatory.
 *
 * @param id valve type catalog entry identifier
 * @param code language-neutral valve type code
 */
public record ValveTypeReference(String id, TopologyCode code) implements ValueObject {

    public ValveTypeReference {
        id = requireText(id, "ValveTypeReference id");
        code = Objects.requireNonNull(code, "ValveTypeReference code must not be null.");
    }

    public static ValveTypeReference of(String id, TopologyCode code) {
        return new ValveTypeReference(id, code);
    }

    public static ValveTypeReference of(String id, String code) {
        return new ValveTypeReference(id, TopologyCode.of(code));
    }

    public static ValveTypeReference from(ValveType valveType) {
        if (valveType == null) {
            return null;
        }
        return of(valveType.name(), valveType.name());
    }

    public String name() {
        return code.value();
    }

    public boolean is(String expectedCode) {
        return code.value().equals(TopologyCode.of(expectedCode).value());
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException(fieldName + " must not be null or blank.");
        }
        return value.trim();
    }
}
