/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MaintenanceWorkOrderStatus
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.domain.value
 *
 * @Description : Defines MaintenanceWorkOrderStatus values.
 *
 */
package dz.sh.hidra.modules.assets.domain.value;

/**
 * Defines MaintenanceWorkOrderStatus values.
 */
public enum MaintenanceWorkOrderStatus {
    DRAFT, PLANNED, APPROVED, SCHEDULED, IN_PROGRESS, COMPLETED, VERIFIED, CLOSED, CANCELLED, REJECTED
}
