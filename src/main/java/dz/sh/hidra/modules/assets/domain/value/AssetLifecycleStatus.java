/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetLifecycleStatus
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.domain.value
 *
 * @Description : Defines AssetLifecycleStatus values.
 *
 */
package dz.sh.hidra.modules.assets.domain.value;

/**
 * Defines AssetLifecycleStatus values.
 */
public enum AssetLifecycleStatus {
    DRAFT, ACTIVE, INSTALLED, COMMISSIONED, OUT_OF_SERVICE, UNDER_MAINTENANCE, RETIRED, DISPOSED, CANCELLED
}
