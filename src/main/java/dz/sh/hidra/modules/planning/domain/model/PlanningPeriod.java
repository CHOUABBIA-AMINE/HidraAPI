/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningPeriod
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.domain.model
 *
 * @Description : Planning horizon such as day, week, month, campaign, or operational window.
 *
 */
package dz.sh.hidra.modules.planning.domain.model;

import dz.sh.hidra.modules.planning.domain.value.*;
import java.time.Instant;

    /**
     * Planning horizon such as day, week, month, campaign, or operational window.
     *
         * @param id id
     * @param code code
     * @param nameAr nameAr
     * @param nameFr nameFr
     * @param nameEn nameEn
     * @param periodTypeId periodTypeId
     * @param periodStart periodStart
     * @param periodEnd periodEnd
     * @param timeZone timeZone
     * @param status status
     * @param createdByActorId createdByActorId
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record PlanningPeriod(
            String id,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String periodTypeId,
        Instant periodStart,
        Instant periodEnd,
        String timeZone,
        PlanningPeriodStatus status,
        String createdByActorId,
        Instant createdAt,
        Instant updatedAt
    ) {

        public PlanningPeriod {
        id = normalize(id);
        code = normalize(code);
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
        periodTypeId = normalize(periodTypeId);
        timeZone = normalize(timeZone);
        createdByActorId = normalize(createdByActorId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
