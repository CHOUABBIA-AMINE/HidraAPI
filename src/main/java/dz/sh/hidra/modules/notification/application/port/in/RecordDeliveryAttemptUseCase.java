/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RecordDeliveryAttemptUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.application.port.in
 *
 * @Description : Use case for recording notification delivery attempts.
 *
 */
package dz.sh.hidra.modules.notification.application.port.in;

import dz.sh.hidra.modules.notification.application.command.RecordDeliveryAttemptCommand;
import dz.sh.hidra.modules.notification.application.dto.NotificationDeliveryAttemptSummaryDto;

/**
 * Use case for recording notification delivery attempts.
 */
public interface RecordDeliveryAttemptUseCase {

    NotificationDeliveryAttemptSummaryDto recordDeliveryAttempt(RecordDeliveryAttemptCommand command);
}
