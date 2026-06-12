/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationTargetReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.domain.value
 *
 * @Description : Neutral target object reference for notification.
 *
 */
package dz.sh.hidra.modules.notification.domain.value;

/**
 * Neutral target object reference for notification.
 *
 * @param sourceModule source module
 * @param targetType target type
 * @param targetId target identifier
 * @param targetCodeSnapshot target code snapshot
 * @param targetLabelSnapshot target label snapshot
 */
public record NotificationTargetReference(
        String sourceModule,
        String targetType,
        String targetId,
        String targetCodeSnapshot,
        String targetLabelSnapshot
) {
}
