/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationId
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.domain.value
 *
 * @Description : Stable integration identifier.
 *
 */
package dz.sh.hidra.modules.integration.domain.value;

import dz.sh.hidra.modules.integration.domain.exception.InvalidIntegrationValueException;

import java.util.UUID;

/**
 * Stable integration identifier.
 *
 * @param value identifier value
 */
public record IntegrationId(String value) {

    public IntegrationId {
        value = requireText(value, "Integration ID must not be null or blank.");
    }

    public static IntegrationId of(String value) {
        return new IntegrationId(value);
    }

    public static IntegrationId newId() {
        return new IntegrationId(UUID.randomUUID().toString());
    }

    private static String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new InvalidIntegrationValueException(message);
        }
        return value.trim();
    }
}
