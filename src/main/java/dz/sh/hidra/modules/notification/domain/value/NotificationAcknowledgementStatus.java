/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationAcknowledgementStatus
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.domain.value
 *
 * @Description : Defines NotificationAcknowledgementStatus values.
 *
 */
package dz.sh.hidra.modules.notification.domain.value;

/**
 * Defines NotificationAcknowledgementStatus values.
 */
public enum NotificationAcknowledgementStatus {
    REQUIRED, ACKNOWLEDGED, DECLINED, EXPIRED, NOT_REQUIRED
}
