/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanScenario
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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
