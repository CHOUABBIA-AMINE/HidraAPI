/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProviderType
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.value
 *
 * @Description : Defines identity ProviderType values.
 *
 */
package dz.sh.hidra.modules.identity.domain.value;

/**
 * Identity ProviderType values.
 */
public enum ProviderType {
    LOCAL, LDAP, ACTIVE_DIRECTORY, OIDC, OAUTH2, SAML2, KEYCLOAK, AZURE_AD, OKTA
}
