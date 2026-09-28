/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditRetentionPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.audit.domain.exception.InvalidAuditValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidAuditValueException("AuditRetentionPolicy id must not be blank.");
        }
        // HRA-051 required: code
        if (code == null || code.isBlank()) {
            throw new InvalidAuditValueException("AuditRetentionPolicy code must not be blank.");
        }
        // HRA-051 required: retentionDays
        if (retentionDays == null) {
            throw new InvalidAuditValueException("AuditRetentionPolicy retention days must not be null.");
        }
        // HRA-051 required: validFrom
        if (validFrom == null) {
            throw new InvalidAuditValueException("AuditRetentionPolicy valid from must not be null.");
        }
        // HRA-051 order: validFrom <= validTo
        if (validFrom != null && validTo != null && validTo.isBefore(validFrom)) {
            throw new InvalidAuditValueException("AuditRetentionPolicy valid to must not be before valid from.");
        }

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
