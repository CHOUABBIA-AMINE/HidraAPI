/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationProviderGateway
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.integration
 *
 * @Description : Gateway contract for notification providers.
 *
 */
package dz.sh.hidra.modules.notification.infrastructure.integration;

/**
 * Gateway contract for notification providers.
 */
public interface NotificationProviderGateway {

    boolean providerAvailable(String providerReference);

    boolean supportsChannel(String providerReference, String channelCode);
}
