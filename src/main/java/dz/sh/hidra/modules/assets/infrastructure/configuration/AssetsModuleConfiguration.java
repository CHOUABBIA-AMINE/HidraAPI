/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetsModuleConfiguration
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Infrastructure
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.infrastructure.configuration
 *
 * @Description : Assets infrastructure configuration.
 *
 */
package dz.sh.hidra.modules.assets.infrastructure.configuration;

/**
 * Assets infrastructure configuration.
 */
public record AssetsModuleConfiguration(
        boolean maintenanceWorkOrderEnabled,
        boolean warrantyTrackingEnabled,
        boolean sparePartCompatibilityEnabled,
        boolean conditionTrackingEnabled
) {

    public static AssetsModuleConfiguration defaults() {
        return new AssetsModuleConfiguration(true, true, true, true);
    }
}
