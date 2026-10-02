/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateNotificationMessageUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.application.port.in
 *
 * @Description : Use case for creating notification messages.
 *
 */
package dz.sh.hidra.modules.notification.application.port.in;

import dz.sh.hidra.modules.notification.application.command.CreateNotificationMessageCommand;
import dz.sh.hidra.modules.notification.application.dto.NotificationMessageSummaryDto;

/**
 * Use case for creating notification messages.
 */
public interface CreateNotificationMessageUseCase {

    NotificationMessageSummaryDto createNotificationMessage(CreateNotificationMessageCommand command);
}
