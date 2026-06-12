/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MaintenanceTaskStatus
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.domain.value
 *
 * @Description : Defines MaintenanceTaskStatus values.
 *
 */
package dz.sh.hidra.modules.assets.domain.value;

/**
 * Defines MaintenanceTaskStatus values.
 */
public enum MaintenanceTaskStatus {
    PLANNED, IN_PROGRESS, COMPLETED, SKIPPED, FAILED, CANCELLED
}
