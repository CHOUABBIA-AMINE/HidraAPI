/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationModule
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification
 *
 * @Description : Defines notification module constants.
 *
 */
package dz.sh.hidra.modules.notification;

/**
 * Notification module constants.
 */
public final class NotificationModule {

    public static final String MODULE_NAME = "notification";
    public static final String TABLE_PREFIX = "hidra_notification_";

    private NotificationModule() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
