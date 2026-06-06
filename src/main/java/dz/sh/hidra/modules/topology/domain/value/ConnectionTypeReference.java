/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConnectionTypeReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.value
 *
 * @Description : Stable reference to a multilingual topology connection type catalog entry.
 *
 */
package dz.sh.hidra.modules.topology.domain.value;

import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Reference to a configurable topology connection type catalog entry.
 *
 * <p>Business role:
 * This value object identifies the physical or semantic connection type between topology nodes, such
 * as pipeline segment, facility link, bypass, branch, tie-in, injection connection, extraction
 * connection, purge connection, or metering connection without hard-coding the value as a Java enum.
 *
 * <p>Architecture role:
 * This is a pure topology reference object. It carries only a stable catalog entry identifier and a
 * language-neutral code; localized labels are resolved by catalog application services.
 *
 * <p>Validation:
 * The catalog entry identifier and code are mandatory.
 *
 * @param id connection type catalog entry identifier
 * @param code language-neutral connection type code
 */
public record ConnectionTypeReference(String id, TopologyCode code) implements ValueObject {

    public ConnectionTypeReference {
        id = requireText(id, "ConnectionTypeReference id");
        code = Objects.requireNonNull(code, "ConnectionTypeReference code must not be null.");
    }

    public static ConnectionTypeReference of(String id, TopologyCode code) {
        return new ConnectionTypeReference(id, code);
    }

    public static ConnectionTypeReference of(String id, String code) {
        return new ConnectionTypeReference(id, TopologyCode.of(code));
    }

    public static ConnectionTypeReference from(ConnectionType connectionType) {
        Objects.requireNonNull(connectionType, "Connection type must not be null.");
        return of(connectionType.name(), connectionType.name());
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
