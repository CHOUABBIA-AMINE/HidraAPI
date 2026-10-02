/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationDeliveryGatewayPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.application.port.out
 *
 * @Description : Outbound gateway port for pushing notification messages to a technical provider.
 *
 */
package dz.sh.hidra.modules.notification.application.port.out;

import dz.sh.hidra.modules.notification.application.dto.NotificationDeliveryGatewayResult;
import dz.sh.hidra.modules.notification.domain.model.NotificationMessage;

/**
 * Outbound gateway port for pushing notification messages to a technical provider.
 */
public interface NotificationDeliveryGatewayPort {

    NotificationDeliveryGatewayResult push(NotificationMessage message);
}
