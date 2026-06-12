/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ExternalIdentityStatus
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.value
 *
 * @Description : Defines identity ExternalIdentityStatus values.
 *
 */
package dz.sh.hidra.modules.identity.domain.value;

/**
 * Identity ExternalIdentityStatus values.
 */
public enum ExternalIdentityStatus {
    LINKED, DISABLED_EXTERNAL, ORPHANED, CONFLICT, UNLINKED
}
