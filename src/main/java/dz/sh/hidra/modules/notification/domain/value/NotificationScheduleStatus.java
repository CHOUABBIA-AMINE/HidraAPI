/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationScheduleStatus
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.domain.value
 *
 * @Description : Defines NotificationScheduleStatus values.
 *
 */
package dz.sh.hidra.modules.notification.domain.value;

/**
 * Defines NotificationScheduleStatus values.
 */
public enum NotificationScheduleStatus {
    PLANNED, DUE, DISPATCHED, CANCELLED, EXPIRED, FAILED
}
