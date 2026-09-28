/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanScenario
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.domain.model
 *
 * @Description : Alternative planning case inside a plan revision.
 *
 */
package dz.sh.hidra.modules.planning.domain.model;

import dz.sh.hidra.modules.planning.domain.exception.InvalidPlanningValueException;
import dz.sh.hidra.modules.planning.domain.value.*;
import java.time.Instant;

    /**
     * Alternative planning case inside a plan revision.
     *
         * @param id id
     * @param revisionId revisionId
     * @param code code
     * @param nameAr nameAr
     * @param nameFr nameFr
     * @param nameEn nameEn
     * @param scenarioTypeId scenarioTypeId
     * @param primaryScenario primaryScenario
     * @param status status
     * @param description description
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record PlanScenario(
            String id,
        String revisionId,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String scenarioTypeId,
        boolean primaryScenario,
        PlanScenarioStatus status,
        String description,
        Instant createdAt,
        Instant updatedAt
    ) {

        public PlanScenario {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidPlanningValueException("PlanScenario id must not be blank.");
        }
        // HRA-051 required: revisionId
        if (revisionId == null || revisionId.isBlank()) {
            throw new InvalidPlanningValueException("PlanScenario revision id must not be blank.");
        }
        // HRA-051 required: code
        if (code == null || code.isBlank()) {
            throw new InvalidPlanningValueException("PlanScenario code must not be blank.");
        }
        // HRA-051 required: scenarioTypeId
        if (scenarioTypeId == null || scenarioTypeId.isBlank()) {
            throw new InvalidPlanningValueException("PlanScenario scenario type id must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidPlanningValueException("PlanScenario status must not be null.");
        }

        id = normalize(id);
        revisionId = normalize(revisionId);
        code = normalize(code);
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
        scenarioTypeId = normalize(scenarioTypeId);
        description = normalize(description);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
