/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationDeliveryGatewayResult
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.application.dto
 *
 * @Description : Represents the result returned by an outbound notification delivery gateway.
 *
 */
package dz.sh.hidra.modules.notification.application.dto;

/**
 * Represents the result returned by an outbound notification delivery gateway.
 *
 * @param accepted whether the provider accepted the notification for delivery
 * @param providerReference provider reference used for the attempt
 * @param providerMessageId provider-side message identifier when available
 * @param failureCode provider failure code when delivery was not accepted
 * @param failureMessage provider failure message when delivery was not accepted
 */
public record NotificationDeliveryGatewayResult(
        boolean accepted,
        String providerReference,
        String providerMessageId,
        String failureCode,
        String failureMessage
) {

    public static NotificationDeliveryGatewayResult accepted(
            String providerReference,
            String providerMessageId
    ) {
        return new NotificationDeliveryGatewayResult(true, providerReference, providerMessageId, null, null);
    }

    public static NotificationDeliveryGatewayResult rejected(
            String providerReference,
            String failureCode,
            String failureMessage
    ) {
        return new NotificationDeliveryGatewayResult(false, providerReference, null, failureCode, failureMessage);
    }
}
