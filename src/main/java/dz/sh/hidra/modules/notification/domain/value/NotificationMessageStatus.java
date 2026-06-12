/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationMessageStatus
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.domain.value
 *
 * @Description : Defines NotificationMessageStatus values.
 *
 */
package dz.sh.hidra.modules.notification.domain.value;

/**
 * Defines NotificationMessageStatus values.
 */
public enum NotificationMessageStatus {
    DRAFT, READY, SCHEDULED, DISPATCHING, SENT, DELIVERED, READ, ACKNOWLEDGED, FAILED, RETRY_PENDING, SUPPRESSED, CANCELLED, EXPIRED
}
