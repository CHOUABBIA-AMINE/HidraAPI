/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProductTypeReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.value
 *
 * @Description : Stable reference to a multilingual product type catalog entry.
 *
 */
package dz.sh.hidra.modules.topology.domain.value;

import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Reference to a configurable product type catalog entry.
 *
 * <p>Business role:
 * This value object identifies the transported product family such as crude oil, gas, condensate,
 * LPG, refined products, water, or multi-product service without hard-coding the value as a Java enum.
 *
 * <p>Architecture role:
 * This is a pure topology reference object. It carries only a stable catalog entry identifier and a
 * language-neutral code; localized labels are resolved by catalog application services.
 *
 * <p>Validation:
 * The catalog entry identifier and code are mandatory.
 *
 * @param id product type catalog entry identifier
 * @param code language-neutral product type code
 */
public record ProductTypeReference(String id, TopologyCode code) implements ValueObject {

    public ProductTypeReference {
        id = requireText(id, "ProductTypeReference id");
        code = Objects.requireNonNull(code, "ProductTypeReference code must not be null.");
    }

    public static ProductTypeReference of(String id, TopologyCode code) {
        return new ProductTypeReference(id, code);
    }

    public static ProductTypeReference of(String id, String code) {
        return new ProductTypeReference(id, TopologyCode.of(code));
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException(fieldName + " must not be null or blank.");
        }
        return value.trim();
    }
}
