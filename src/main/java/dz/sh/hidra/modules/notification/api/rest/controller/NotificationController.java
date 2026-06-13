/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Interface
 * @Layer       : API
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.api.rest.controller
 *
 * @Description : Framework-neutral notification controller contract.
 *
 */
package dz.sh.hidra.modules.notification.api.rest.controller;
import dz.sh.hidra.modules.notification.api.rest.request.*;
import dz.sh.hidra.modules.notification.api.rest.response.*;

/**
 * Framework-neutral notification controller contract.
 */
public interface NotificationController {
    NotificationMessageResponse createNotificationMessage(CreateNotificationMessageRequest request);
    NotificationRequestResponse receiveNotificationRequest(ReceiveNotificationRequestRequest request);
    NotificationDeliveryAttemptResponse recordDeliveryAttempt(RecordDeliveryAttemptRequest request);
}
