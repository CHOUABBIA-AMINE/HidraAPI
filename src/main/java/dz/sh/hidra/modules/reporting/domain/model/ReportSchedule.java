/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportSchedule
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.domain.model
 *
 * @Description : Recurring report generation rule.
 *
 */
package dz.sh.hidra.modules.reporting.domain.model;

import java.time.Instant;

    /**
     * Recurring report generation rule.
     *
         * @param id id
     * @param reportDefinitionId reportDefinitionId
     * @param code code
     * @param nameAr nameAr
     * @param nameFr nameFr
     * @param nameEn nameEn
     * @param cronExpression cronExpression
     * @param timezone timezone
     * @param active active
     * @param nextRunAt nextRunAt
     * @param lastRunAt lastRunAt
     * @param createdByActorId createdByActorId
     * @param createdByDisplayNameSnapshot createdByDisplayNameSnapshot
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record ReportSchedule(
            String id,
        String reportDefinitionId,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String cronExpression,
        String timezone,
        boolean active,
        Instant nextRunAt,
        Instant lastRunAt,
        String createdByActorId,
        String createdByDisplayNameSnapshot,
        Instant createdAt,
        Instant updatedAt
    ) {

        public ReportSchedule {
        id = normalize(id);
        reportDefinitionId = normalize(reportDefinitionId);
        code = normalize(code);
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
        cronExpression = normalize(cronExpression);
        timezone = normalize(timezone);
        createdByActorId = normalize(createdByActorId);
        createdByDisplayNameSnapshot = normalize(createdByDisplayNameSnapshot);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
