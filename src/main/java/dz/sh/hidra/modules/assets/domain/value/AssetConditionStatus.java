/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetConditionStatus
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.domain.value
 *
 * @Description : Defines AssetConditionStatus values.
 *
 */
package dz.sh.hidra.modules.assets.domain.value;

/**
 * Defines AssetConditionStatus values.
 */
public enum AssetConditionStatus {
    NORMAL, WATCH, DEGRADED, CRITICAL, UNKNOWN
}
