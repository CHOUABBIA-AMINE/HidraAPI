/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthenticatedPrincipalInput
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.model
 *
 * @Description : Application-owned normalized authenticated-principal input independent of Spring web/security types.
 *
 */
package dz.sh.hidra.modules.identity.application.model;

import dz.sh.hidra.modules.identity.domain.value.ProviderType;
import java.util.Objects;
import java.util.Set;

/**
 * Application input used to complete an already authenticated identity.
 */
public record AuthenticatedPrincipalInput(
        String userId,
        String username,
        String displayName,
        ProviderType authenticationType,
        String identityProviderId,
        Set<String> roles,
        Set<String> permissions,
        String externalIdentityId
) {

    public AuthenticatedPrincipalInput(String userId, String username, String displayName, ProviderType authenticationType,
            String identityProviderId, Set<String> roles, Set<String> permissions) {
        this(userId, username, displayName, authenticationType, identityProviderId, roles, permissions, null);
    }

    public AuthenticatedPrincipalInput {
        userId = requireText(userId, "userId");
        username = requireText(username, "username");
        displayName = normalize(displayName);
        authenticationType = Objects.requireNonNull(authenticationType, "authenticationType must not be null.");
        identityProviderId = normalize(identityProviderId);
        externalIdentityId = normalize(externalIdentityId);
        roles = immutableSet(roles);
        permissions = immutableSet(permissions);
    }

    private static String requireText(String value, String fieldName) {
        String normalized = normalize(value);
        if (normalized == null) {
            throw new IllegalArgumentException(fieldName + " must not be blank.");
        }
        return normalized;
    }

    private static String normalize(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }

    private static Set<String> immutableSet(Set<String> values) {
        if (values == null || values.isEmpty()) {
            return Set.of();
        }
        return values.stream()
                .map(AuthenticatedPrincipalInput::normalize)
                .filter(Objects::nonNull)
                .collect(java.util.stream.Collectors.toUnmodifiableSet());
    }
}
