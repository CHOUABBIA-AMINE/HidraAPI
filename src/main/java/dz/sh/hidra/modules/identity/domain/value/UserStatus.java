/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : UserStatus
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.value
 *
 * @Description : Defines identity UserStatus values.
 *
 */
package dz.sh.hidra.modules.identity.domain.value;

/**
 * Identity UserStatus values.
 */
public enum UserStatus {
    REGISTERED, ACTIVE, SUSPENDED, DISABLED, LOCKED
}
