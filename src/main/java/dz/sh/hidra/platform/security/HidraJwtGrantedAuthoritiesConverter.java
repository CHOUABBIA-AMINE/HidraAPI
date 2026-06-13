/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraJwtGrantedAuthoritiesConverter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.security
 *
 * @Description : Maps JWT roles and scopes to Spring Security authorities.
 *
 */
package dz.sh.hidra.platform.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Maps JWT roles and scopes to Spring Security authorities.
 */
public final class HidraJwtGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    private final String rolesClaim;
    private final String scopeClaim;
    private final String authorityPrefix;

    public HidraJwtGrantedAuthoritiesConverter(String rolesClaim, String scopeClaim, String authorityPrefix) {
        this.rolesClaim = normalize(rolesClaim, "roles");
        this.scopeClaim = normalize(scopeClaim, "scope");
        this.authorityPrefix = normalize(authorityPrefix, "ROLE_");
    }

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Set<String> authorities = new LinkedHashSet<>();
        authorities.addAll(extractAuthorities(jwt.getClaim(rolesClaim), authorityPrefix));
        authorities.addAll(extractAuthorities(jwt.getClaim(scopeClaim), "SCOPE_"));
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .map(GrantedAuthority.class::cast)
                .toList();
    }

    private static List<String> extractAuthorities(Object claimValue, String prefix) {
        if (claimValue == null) {
            return List.of();
        }
        List<String> rawValues = new ArrayList<>();
        if (claimValue instanceof String text) {
            for (String item : text.split("[ ,]")) {
                if (!item.isBlank()) {
                    rawValues.add(item.trim());
                }
            }
        } else if (claimValue instanceof Collection<?> collection) {
            for (Object item : collection) {
                if (item != null && !item.toString().isBlank()) {
                    rawValues.add(item.toString().trim());
                }
            }
        }
        String normalizedPrefix = normalize(prefix, "");
        return rawValues.stream()
                .map(value -> value.startsWith(normalizedPrefix) ? value : normalizedPrefix + value)
                .distinct()
                .toList();
    }

    private static String normalize(String value, String defaultValue) {
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        return value.trim();
    }
}
