/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LoginSessionStatus
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.value
 *
 * @Description : Defines identity LoginSessionStatus values.
 *
 */
package dz.sh.hidra.modules.identity.domain.value;

/**
 * Identity LoginSessionStatus values.
 */
public enum LoginSessionStatus {
    ACTIVE, EXPIRED, REVOKED, LOGGED_OUT
}
