/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraPrincipal
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-15
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.model
 *
 * @Description : Represents the normalized authenticated Hidra identity shared across authentication providers.
 *
 */
package dz.sh.hidra.modules.identity.domain.model;

import dz.sh.hidra.modules.identity.domain.value.ProviderType;
import java.security.Principal;
import java.util.Objects;
import java.util.Set;

/**
 * Normalized authenticated Hidra identity independent of the upstream authentication provider.
 *
 * @param userId stable Hidra user identifier
 * @param username Hidra username
 * @param displayName display name
 * @param authenticationType authentication source selected for the login
 * @param identityProviderId Hidra identity-provider identifier when applicable
 * @param roles Hidra-owned role codes
 * @param permissions Hidra-owned effective permission codes
 */
public record HidraPrincipal(
        String userId,
        String username,
        String displayName,
        ProviderType authenticationType,
        String identityProviderId,
        Set<String> roles,
        Set<String> permissions
) implements Principal {

    public HidraPrincipal {
        userId = requireText(userId, "userId");
        username = requireText(username, "username");
        displayName = normalize(displayName);
        authenticationType = Objects.requireNonNull(authenticationType, "authenticationType must not be null");
        identityProviderId = normalize(identityProviderId);
        roles = immutableSet(roles);
        permissions = immutableSet(permissions);
    }

    @Override
    public String getName() {
        return userId;
    }

    private static String requireText(String value, String fieldName) {
        String normalized = normalize(value);
        if (normalized == null) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return normalized;
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }

    private static Set<String> immutableSet(Set<String> values) {
        if (values == null || values.isEmpty()) {
            return Set.of();
        }
        return values.stream()
                .map(HidraPrincipal::normalize)
                .filter(Objects::nonNull)
                .collect(java.util.stream.Collectors.toUnmodifiableSet());
    }
}
