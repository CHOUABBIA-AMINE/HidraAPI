/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NodeTypeReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.value
 *
 * @Description : Stable reference to a multilingual topology node type catalog entry.
 *
 */
package dz.sh.hidra.modules.topology.domain.value;

import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Reference to a configurable topology node type catalog entry.
 *
 * <p>Business role:
 * This value object identifies a topology node type such as facility inlet, facility outlet, pipeline
 * junction, valve point, injection point, extraction point, purge point, vent, drain, scraper point,
 * receipt point, or delivery point without hard-coding the value as a Java enum.
 *
 * <p>Architecture role:
 * This is a pure topology reference object. It carries only a stable catalog entry identifier and a
 * language-neutral code; localized labels are resolved by catalog application services.
 *
 * <p>Validation:
 * The catalog entry identifier and code are mandatory.
 *
 * @param id node type catalog entry identifier
 * @param code language-neutral node type code
 */
public record NodeTypeReference(String id, TopologyCode code) implements ValueObject {

    public NodeTypeReference {
        id = requireText(id, "NodeTypeReference id");
        code = Objects.requireNonNull(code, "NodeTypeReference code must not be null.");
    }

    public static NodeTypeReference of(String id, TopologyCode code) {
        return new NodeTypeReference(id, code);
    }

    public static NodeTypeReference of(String id, String code) {
        return new NodeTypeReference(id, TopologyCode.of(code));
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException(fieldName + " must not be null or blank.");
        }
        return value.trim();
    }
}
