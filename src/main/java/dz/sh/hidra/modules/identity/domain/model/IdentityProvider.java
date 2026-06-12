/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityProvider
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.model
 *
 * @Description : Represents a configured identity provider.
 *
 */
package dz.sh.hidra.modules.identity.domain.model;

import dz.sh.hidra.modules.identity.domain.value.*;
import java.time.Instant;

/**
 * Represents a configured identity provider.
 *
     * @param id id
 * @param code code
 * @param name name
 * @param providerType providerType
 * @param issuerUri issuerUri
 * @param authorizationEndpoint authorizationEndpoint
 * @param tokenEndpoint tokenEndpoint
 * @param jwksUri jwksUri
 * @param directoryBaseDn directoryBaseDn
 * @param userSearchBase userSearchBase
 * @param groupSearchBase groupSearchBase
 * @param usernameAttribute usernameAttribute
 * @param emailAttribute emailAttribute
 * @param displayNameAttribute displayNameAttribute
 * @param externalIdAttribute externalIdAttribute
 * @param groupMembershipAttribute groupMembershipAttribute
 * @param syncEnabled syncEnabled
 * @param justInTimeProvisioningEnabled justInTimeProvisioningEnabled
 * @param status status
 * @param metadata metadata
 * @param secretReference secretReference
 * @param createdAt createdAt
 * @param updatedAt updatedAt
 */
public record IdentityProvider(
        String id,
    String code,
    String name,
    ProviderType providerType,
    String issuerUri,
    String authorizationEndpoint,
    String tokenEndpoint,
    String jwksUri,
    String directoryBaseDn,
    String userSearchBase,
    String groupSearchBase,
    String usernameAttribute,
    String emailAttribute,
    String displayNameAttribute,
    String externalIdAttribute,
    String groupMembershipAttribute,
    boolean syncEnabled,
    boolean justInTimeProvisioningEnabled,
    IdentityProviderStatus status,
    String metadata,
    String secretReference,
    Instant createdAt,
    Instant updatedAt
) {

    public IdentityProvider {
    id = normalize(id);
    code = normalize(code);
    name = normalize(name);
    issuerUri = normalize(issuerUri);
    authorizationEndpoint = normalize(authorizationEndpoint);
    tokenEndpoint = normalize(tokenEndpoint);
    jwksUri = normalize(jwksUri);
    directoryBaseDn = normalize(directoryBaseDn);
    userSearchBase = normalize(userSearchBase);
    groupSearchBase = normalize(groupSearchBase);
    usernameAttribute = normalize(usernameAttribute);
    emailAttribute = normalize(emailAttribute);
    displayNameAttribute = normalize(displayNameAttribute);
    externalIdAttribute = normalize(externalIdAttribute);
    groupMembershipAttribute = normalize(groupMembershipAttribute);
    metadata = normalize(metadata);
    secretReference = normalize(secretReference);
    }



    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
