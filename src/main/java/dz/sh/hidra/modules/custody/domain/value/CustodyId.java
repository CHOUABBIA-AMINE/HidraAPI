/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyId
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.domain.value
 *
 * @Description : Stable custody identifier.
 *
 */
package dz.sh.hidra.modules.custody.domain.value;

import dz.sh.hidra.modules.custody.domain.exception.InvalidCustodyValueException;

import java.util.UUID;

/**
 * Stable custody identifier.
 *
 * @param value identifier value
 */
public record CustodyId(String value) {

    public CustodyId {
        value = requireText(value, "Custody ID must not be null or blank.");
    }

    public static CustodyId of(String value) {
        return new CustodyId(value);
    }

    public static CustodyId newId() {
        return new CustodyId(UUID.randomUUID().toString());
    }

    private static String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new InvalidCustodyValueException(message);
        }
        return value.trim();
    }
}
