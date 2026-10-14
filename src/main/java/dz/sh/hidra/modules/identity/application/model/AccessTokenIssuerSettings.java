/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AccessTokenIssuerSettings
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-14
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.model
 *
 * @Description : Defines validated trusted token configuration for bound issuance.
 *
 */
package dz.sh.hidra.modules.identity.application.model;

import java.nio.charset.StandardCharsets;

/** Trusted, configured native issuer, audience and positive bearer TTL. */
public record AccessTokenIssuerSettings(String issuer, String audience, long ttlSeconds) {
    public AccessTokenIssuerSettings {
        issuer = configured(issuer, "issuer");
        audience = configured(audience, "audience");
        if (ttlSeconds <= 0) throw new IllegalArgumentException("Invalid access-token TTL");
    }

    private static String configured(String value, String field) {
        if (value == null
                || value.isBlank()
                || (!value.equals(value.strip()) || !value.equals(value.trim()))
                || value.getBytes(StandardCharsets.UTF_8).length > 512) {
            throw new IllegalArgumentException("Invalid configured " + field);
        }
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (c == 0) throw new IllegalArgumentException("Invalid configured Unicode");
            if (Character.isHighSurrogate(c)) {
                if (++i >= value.length() || !Character.isLowSurrogate(value.charAt(i)))
                    throw new IllegalArgumentException("Invalid configured Unicode");
            } else if (Character.isLowSurrogate(c))
                throw new IllegalArgumentException("Invalid configured Unicode");
        }
        return value;
    }
}
