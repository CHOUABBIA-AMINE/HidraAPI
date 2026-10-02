/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationModuleConfiguration
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.configuration
 *
 * @Description : Notification infrastructure configuration.
 *
 */
package dz.sh.hidra.modules.notification.infrastructure.configuration;

/**
 * Notification infrastructure configuration.
 */
public record NotificationModuleConfiguration(
        boolean providerSecretsBlocked,
        boolean sourceBusinessMutationBlocked,
        boolean auditReadyEventsEnabled,
        boolean retrySchedulingEnabled
) {

    public static NotificationModuleConfiguration defaults() {
        return new NotificationModuleConfiguration(true, true, true, true);
    }
}
