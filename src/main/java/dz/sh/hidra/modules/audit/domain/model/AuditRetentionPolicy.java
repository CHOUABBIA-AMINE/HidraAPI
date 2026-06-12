/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditRetentionPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.domain.model
 *
 * @Description : Retention and archival behavior policy.
 *
 */
package dz.sh.hidra.modules.audit.domain.model;

import java.time.Instant;
import java.time.LocalDate;

    /**
     * Retention and archival behavior policy.
     *
         * @param id id
     * @param code code
     * @param nameAr nameAr
     * @param nameFr nameFr
     * @param nameEn nameEn
     * @param eventCategoryId eventCategoryId
     * @param retentionDays retentionDays
     * @param archiveAfterDays archiveAfterDays
     * @param legalHoldSupported legalHoldSupported
     * @param purgeAllowed purgeAllowed
     * @param active active
     * @param validFrom validFrom
     * @param validTo validTo
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record AuditRetentionPolicy(
            String id,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String eventCategoryId,
        Integer retentionDays,
        Integer archiveAfterDays,
        boolean legalHoldSupported,
        boolean purgeAllowed,
        boolean active,
        LocalDate validFrom,
        LocalDate validTo,
        Instant createdAt,
        Instant updatedAt
    ) {

        public AuditRetentionPolicy {
        id = normalize(id);
        code = normalize(code);
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
        eventCategoryId = normalize(eventCategoryId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
