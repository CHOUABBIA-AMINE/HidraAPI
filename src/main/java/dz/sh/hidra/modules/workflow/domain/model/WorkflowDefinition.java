/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowDefinition
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.model
 *
 * @Description : Reusable workflow process definition.
 *
 */
package dz.sh.hidra.modules.workflow.domain.model;

import dz.sh.hidra.modules.workflow.domain.value.*;
import java.time.Instant;

    /**
     * Reusable workflow process definition.
     *
         * @param id id
     * @param code code
     * @param nameAr nameAr
     * @param nameFr nameFr
     * @param nameEn nameEn
     * @param typeId typeId
     * @param status status
     * @param version version
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record WorkflowDefinition(
            String id,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String typeId,
        WorkflowDefinitionStatus status,
        int version,
        Instant createdAt,
        Instant updatedAt
    ) {

        public WorkflowDefinition {
        id = normalize(id);
        code = normalize(code);
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
        typeId = normalize(typeId);
        }
        public boolean canStartInstance() {
            return status == WorkflowDefinitionStatus.ACTIVE;
        }
        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
