/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetLifecycleEventType
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.domain.value
 *
 * @Description : Defines AssetLifecycleEventType values.
 *
 */
package dz.sh.hidra.modules.assets.domain.value;

/**
 * Defines AssetLifecycleEventType values.
 */
public enum AssetLifecycleEventType {
    REGISTERED, INSTALLED, COMMISSIONED, MOVED, DECOMMISSIONED, RETIRED, DISPOSED, STATUS_CHANGED
}
