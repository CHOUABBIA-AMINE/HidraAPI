/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationRecipientReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.domain.value
 *
 * @Description : Neutral recipient reference snapshot.
 *
 */
package dz.sh.hidra.modules.notification.domain.value;

/**
 * Neutral recipient reference snapshot.
 *
 * @param recipientType recipient type
 * @param recipientReferenceId recipient reference identifier
 * @param recipientCodeSnapshot recipient code snapshot
 * @param recipientDisplayNameSnapshot recipient display-name snapshot
 * @param recipientLocale recipient locale
 */
public record NotificationRecipientReference(
        String recipientType,
        String recipientReferenceId,
        String recipientCodeSnapshot,
        String recipientDisplayNameSnapshot,
        String recipientLocale
) {
}
