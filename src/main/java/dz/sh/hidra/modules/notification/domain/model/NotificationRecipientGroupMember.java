/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationRecipientGroupMember
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.domain.model
 *
 * @Description : Member of a notification recipient group.
 *
 */
package dz.sh.hidra.modules.notification.domain.model;

import dz.sh.hidra.modules.notification.domain.value.*;
import java.time.Instant;

    /**
     * Member of a notification recipient group.
     *
         * @param id id
     * @param groupId groupId
     * @param memberType memberType
     * @param memberReferenceId memberReferenceId
     * @param memberLabelSnapshot memberLabelSnapshot
     * @param active active
     * @param validFrom validFrom
     * @param validTo validTo
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record NotificationRecipientGroupMember(
            String id,
        String groupId,
        NotificationRecipientType memberType,
        String memberReferenceId,
        String memberLabelSnapshot,
        boolean active,
        Instant validFrom,
        Instant validTo,
        Instant createdAt,
        Instant updatedAt
    ) {

        public NotificationRecipientGroupMember {
        id = normalize(id);
        groupId = normalize(groupId);
        memberReferenceId = normalize(memberReferenceId);
        memberLabelSnapshot = normalize(memberLabelSnapshot);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
