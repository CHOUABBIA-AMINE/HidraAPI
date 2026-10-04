/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningPeriod
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.planning.domain.exception.InvalidPlanningValueException;
import dz.sh.hidra.modules.planning.domain.value.*;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.ZoneId;

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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidPlanningValueException("PlanningPeriod id must not be blank.");
        }
        // HRA-051 required: code
        if (code == null || code.isBlank()) {
            throw new InvalidPlanningValueException("PlanningPeriod code must not be blank.");
        }
        // HMR-006 required: nameFr
        if (nameFr == null || nameFr.isBlank()) {
            throw new InvalidPlanningValueException("PlanningPeriod French name must not be blank.");
        }
        // HRA-051 required: periodTypeId
        if (periodTypeId == null || periodTypeId.isBlank()) {
            throw new InvalidPlanningValueException("PlanningPeriod period type id must not be blank.");
        }
        // HRA-051 required: periodStart
        if (periodStart == null) {
            throw new InvalidPlanningValueException("PlanningPeriod period start must not be null.");
        }
        // HRA-051 required: periodEnd
        if (periodEnd == null) {
            throw new InvalidPlanningValueException("PlanningPeriod period end must not be null.");
        }
        if (timeZone == null || timeZone.isBlank()) {
            throw new InvalidPlanningValueException("PlanningPeriod time zone must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidPlanningValueException("PlanningPeriod status must not be null.");
        }
        // HRA-051 required: createdByActorId
        if (createdByActorId == null || createdByActorId.isBlank()) {
            throw new InvalidPlanningValueException("PlanningPeriod created by actor id must not be blank.");
        }
        // HRA-051 order: periodStart < periodEnd
        if (periodStart != null && periodEnd != null && !periodStart.isBefore(periodEnd)) {
            throw new InvalidPlanningValueException("PlanningPeriod period start must be before period end.");
        }

        id = normalize(id);
        code = normalize(code);
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
        periodTypeId = normalize(periodTypeId);
        try {
            timeZone = ZoneId.of(timeZone.trim()).getId();
        } catch (DateTimeException ex) {
            throw new InvalidPlanningValueException("PlanningPeriod time zone must be a valid IANA zone id.");
        }
        createdByActorId = normalize(createdByActorId);
        }

        public boolean allowsNewPlanRevisions() {
            return status != PlanningPeriodStatus.CLOSED;
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
