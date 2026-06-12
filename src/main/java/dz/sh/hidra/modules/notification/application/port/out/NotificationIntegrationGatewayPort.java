/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationIntegrationGatewayPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.application.port.out
 *
 * @Description : Outbound notification port NotificationIntegrationGatewayPort.
 *
 */
package dz.sh.hidra.modules.notification.application.port.out;

/**
 * Outbound notification port.
 */
public interface NotificationIntegrationGatewayPort {

    boolean available(String referenceId);
}
