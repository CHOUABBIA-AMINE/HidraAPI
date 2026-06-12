/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationRequestStatus
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.domain.value
 *
 * @Description : Defines NotificationRequestStatus values.
 *
 */
package dz.sh.hidra.modules.notification.domain.value;

/**
 * Defines NotificationRequestStatus values.
 */
public enum NotificationRequestStatus {
    RECEIVED, VALIDATED, RECIPIENTS_RESOLVED, MESSAGES_CREATED, SCHEDULED, COMPLETED, PARTIALLY_FAILED, FAILED, CANCELLED, EXPIRED, SUPPRESSED
}
