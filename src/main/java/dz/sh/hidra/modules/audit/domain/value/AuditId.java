/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditId
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.domain.value
 *
 * @Description : Stable audit identifier.
 *
 */
package dz.sh.hidra.modules.audit.domain.value;

import dz.sh.hidra.modules.audit.domain.exception.InvalidAuditValueException;

import java.util.UUID;

/**
 * Stable audit identifier.
 *
 * @param value identifier value
 */
public record AuditId(String value) {

    public AuditId {
        value = requireText(value, "Audit ID must not be null or blank.");
    }

    public static AuditId of(String value) {
        return new AuditId(value);
    }

    public static AuditId newId() {
        return new AuditId(UUID.randomUUID().toString());
    }

    private static String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new InvalidAuditValueException(message);
        }
        return value.trim();
    }
}
