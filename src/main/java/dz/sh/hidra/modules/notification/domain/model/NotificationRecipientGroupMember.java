/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationRecipientGroupMember
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.notification.domain.exception.InvalidNotificationValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidNotificationValueException("NotificationRecipientGroupMember id must not be blank.");
        }
        // HRA-051 required: groupId
        if (groupId == null || groupId.isBlank()) {
            throw new InvalidNotificationValueException("NotificationRecipientGroupMember group id must not be blank.");
        }
        // HRA-051 required: memberType
        if (memberType == null) {
            throw new InvalidNotificationValueException("NotificationRecipientGroupMember member type must not be null.");
        }
        // HRA-051 required: memberReferenceId
        if (memberReferenceId == null || memberReferenceId.isBlank()) {
            throw new InvalidNotificationValueException("NotificationRecipientGroupMember member reference id must not be blank.");
        }
        // HRA-051 order: validFrom <= validTo
        if (validFrom != null && validTo != null && validTo.isBefore(validFrom)) {
            throw new InvalidNotificationValueException("NotificationRecipientGroupMember valid to must not be before valid from.");
        }

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
