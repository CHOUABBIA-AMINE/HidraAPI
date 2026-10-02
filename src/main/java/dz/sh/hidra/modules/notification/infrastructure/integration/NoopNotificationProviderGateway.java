/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NoopNotificationProviderGateway
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.integration
 *
 * @Description : No-op notification provider gateway.
 *
 */
package dz.sh.hidra.modules.notification.infrastructure.integration;

/**
 * No-op notification provider gateway.
 */
public class NoopNotificationProviderGateway implements NotificationProviderGateway {

    @Override
    public boolean providerAvailable(String providerReference) {
        return true;
    }

    @Override
    public boolean supportsChannel(String providerReference, String channelCode) {
        return true;
    }
}
