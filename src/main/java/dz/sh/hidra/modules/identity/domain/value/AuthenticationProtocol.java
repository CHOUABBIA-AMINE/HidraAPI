/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthenticationProtocol
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.value
 *
 * @Description : Defines identity AuthenticationProtocol values.
 *
 */
package dz.sh.hidra.modules.identity.domain.value;

/**
 * Identity AuthenticationProtocol values.
 */
public enum AuthenticationProtocol {
    LOCAL, LDAP, OIDC, SAML2, OAUTH2, API_TOKEN, SYSTEM
}
