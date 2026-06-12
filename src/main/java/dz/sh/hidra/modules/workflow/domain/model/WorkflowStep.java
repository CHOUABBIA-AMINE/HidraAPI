/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowStep
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.model
 *
 * @Description : Ordered step inside a workflow definition.
 *
 */
package dz.sh.hidra.modules.workflow.domain.model;

import java.time.Instant;

    /**
     * Ordered step inside a workflow definition.
     *
         * @param id id
     * @param definitionId definitionId
     * @param code code
     * @param nameAr nameAr
     * @param nameFr nameFr
     * @param nameEn nameEn
     * @param stepOrder stepOrder
     * @param mandatory mandatory
     * @param stepTypeId stepTypeId
     * @param defaultAssignmentRuleId defaultAssignmentRuleId
     * @param slaPolicyId slaPolicyId
     * @param allowClaim allowClaim
     * @param allowDelegation allowDelegation
     * @param allowEscalation allowEscalation
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record WorkflowStep(
            String id,
        String definitionId,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        int stepOrder,
        boolean mandatory,
        String stepTypeId,
        String defaultAssignmentRuleId,
        String slaPolicyId,
        boolean allowClaim,
        boolean allowDelegation,
        boolean allowEscalation,
        Instant createdAt,
        Instant updatedAt
    ) {

        public WorkflowStep {
        id = normalize(id);
        definitionId = normalize(definitionId);
        code = normalize(code);
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
        stepTypeId = normalize(stepTypeId);
        defaultAssignmentRuleId = normalize(defaultAssignmentRuleId);
        slaPolicyId = normalize(slaPolicyId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
