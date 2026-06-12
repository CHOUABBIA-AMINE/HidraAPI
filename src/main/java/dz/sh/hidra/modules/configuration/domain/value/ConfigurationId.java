/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationId
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.domain.value
 *
 * @Description : Stable configuration identifier.
 *
 */
package dz.sh.hidra.modules.configuration.domain.value;

import dz.sh.hidra.modules.configuration.domain.exception.InvalidConfigurationValueException;

import java.util.UUID;

/**
 * Stable configuration identifier.
 *
 * @param value identifier value
 */
public record ConfigurationId(String value) {

    public ConfigurationId {
        value = requireText(value, "Configuration ID must not be null or blank.");
    }

    public static ConfigurationId of(String value) {
        return new ConfigurationId(value);
    }

    public static ConfigurationId newId() {
        return new ConfigurationId(UUID.randomUUID().toString());
    }

    private static String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new InvalidConfigurationValueException(message);
        }
        return value.trim();
    }
}
