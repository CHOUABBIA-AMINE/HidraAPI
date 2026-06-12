/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowSlaPolicy
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.model
 *
 * @Description : SLA policy for workflow due dates and escalation timing.
 *
 */
package dz.sh.hidra.modules.workflow.domain.model;

import java.time.Instant;

    /**
     * SLA policy for workflow due dates and escalation timing.
     *
         * @param id id
     * @param code code
     * @param nameAr nameAr
     * @param nameFr nameFr
     * @param nameEn nameEn
     * @param durationSeconds durationSeconds
     * @param calendarMode calendarMode
     * @param warningBeforeSeconds warningBeforeSeconds
     * @param active active
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record WorkflowSlaPolicy(
            String id,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        int durationSeconds,
        String calendarMode,
        Integer warningBeforeSeconds,
        boolean active,
        Instant createdAt,
        Instant updatedAt
    ) {

        public WorkflowSlaPolicy {
        id = normalize(id);
        code = normalize(code);
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
        calendarMode = normalize(calendarMode);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
