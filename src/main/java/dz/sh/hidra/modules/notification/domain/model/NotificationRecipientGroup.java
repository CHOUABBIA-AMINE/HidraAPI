/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationRecipientGroup
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.domain.model
 *
 * @Description : Managed distribution list or notification group.
 *
 */
package dz.sh.hidra.modules.notification.domain.model;

import java.time.Instant;

    /**
     * Managed distribution list or notification group.
     *
         * @param id id
     * @param code code
     * @param nameAr nameAr
     * @param nameFr nameFr
     * @param nameEn nameEn
     * @param groupTypeId groupTypeId
     * @param active active
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record NotificationRecipientGroup(
            String id,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String groupTypeId,
        boolean active,
        Instant createdAt,
        Instant updatedAt
    ) {

        public NotificationRecipientGroup {
        id = normalize(id);
        code = normalize(code);
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
        groupTypeId = normalize(groupTypeId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
