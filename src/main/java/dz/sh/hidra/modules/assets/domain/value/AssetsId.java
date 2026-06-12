/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetsId
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.domain.value
 *
 * @Description : Stable assets identifier.
 *
 */
package dz.sh.hidra.modules.assets.domain.value;

import dz.sh.hidra.modules.assets.domain.exception.InvalidAssetsValueException;

import java.util.UUID;

/**
 * Stable assets identifier.
 *
 * @param value identifier value
 */
public record AssetsId(String value) {

    public AssetsId {
        value = requireText(value, "Assets ID must not be null or blank.");
    }

    public static AssetsId of(String value) {
        return new AssetsId(value);
    }

    public static AssetsId newId() {
        return new AssetsId(UUID.randomUUID().toString());
    }

    private static String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new InvalidAssetsValueException(message);
        }
        return value.trim();
    }
}
