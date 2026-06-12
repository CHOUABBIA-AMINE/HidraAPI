/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MembershipType
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.value
 *
 * @Description : Defines identity MembershipType values.
 *
 */
package dz.sh.hidra.modules.identity.domain.value;

/**
 * Identity MembershipType values.
 */
public enum MembershipType {
    DIRECT, EXTERNAL_SYNC, EXTERNAL_ASSERTION, TEMPORARY
}
