/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FindNotificationMessageByIdQuery
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.application.query
 *
 * @Description : Query to find notification message by ID.
 *
 */
package dz.sh.hidra.modules.notification.application.query;

/**
 * Query to find notification message by ID.
 */
public record FindNotificationMessageByIdQuery(String messageId) {
}
