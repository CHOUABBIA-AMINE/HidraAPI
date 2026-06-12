/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReceiveNotificationRequestUseCase
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.application.port.in
 *
 * @Description : Use case for receiving notification requests.
 *
 */
package dz.sh.hidra.modules.notification.application.port.in;

import dz.sh.hidra.modules.notification.application.command.ReceiveNotificationRequestCommand;
import dz.sh.hidra.modules.notification.application.dto.NotificationRequestSummaryDto;

/**
 * Use case for receiving notification requests.
 */
public interface ReceiveNotificationRequestUseCase {

    NotificationRequestSummaryDto receiveNotificationRequest(ReceiveNotificationRequestCommand command);
}
