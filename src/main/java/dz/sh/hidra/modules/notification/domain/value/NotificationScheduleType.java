/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationScheduleType
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.domain.value
 *
 * @Description : Defines NotificationScheduleType values.
 *
 */
package dz.sh.hidra.modules.notification.domain.value;

/**
 * Defines NotificationScheduleType values.
 */
public enum NotificationScheduleType {
    ONE_TIME, REMINDER, DIGEST, ESCALATION, RETRY
}
