/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthenticationEventType
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.value
 *
 * @Description : Defines identity AuthenticationEventType values.
 *
 */
package dz.sh.hidra.modules.identity.domain.value;

/**
 * Identity AuthenticationEventType values.
 */
public enum AuthenticationEventType {
    LOGIN_SUCCESS, LOGIN_FAILED, LOGOUT, TOKEN_REFRESH, ACCOUNT_LOCKED, MFA_REQUIRED, MFA_FAILED
}
