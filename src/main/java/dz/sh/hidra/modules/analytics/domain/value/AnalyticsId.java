/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsId
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.domain.value
 *
 * @Description : Stable analytics identifier.
 *
 */
package dz.sh.hidra.modules.analytics.domain.value;

import dz.sh.hidra.modules.analytics.domain.exception.InvalidAnalyticsValueException;

import java.util.UUID;

/**
 * Stable analytics identifier.
 *
 * @param value identifier value
 */
public record AnalyticsId(String value) {

    public AnalyticsId {
        value = requireText(value, "Analytics ID must not be null or blank.");
    }

    public static AnalyticsId of(String value) {
        return new AnalyticsId(value);
    }

    public static AnalyticsId newId() {
        return new AnalyticsId(UUID.randomUUID().toString());
    }

    private static String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new InvalidAnalyticsValueException(message);
        }
        return value.trim();
    }
}
