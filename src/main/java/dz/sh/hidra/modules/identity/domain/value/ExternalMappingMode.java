/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ExternalMappingMode
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.value
 *
 * @Description : Defines identity ExternalMappingMode values.
 *
 */
package dz.sh.hidra.modules.identity.domain.value;

/**
 * Identity ExternalMappingMode values.
 */
public enum ExternalMappingMode {
    SYNC_MEMBERSHIP, ASSERTION_ONLY, MANUAL_APPROVAL, DIRECT_GRANT, REQUIRES_LOCAL_APPROVAL, DISABLED
}
