/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowDefinitionTargetBinding
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.model
 *
 * @Description : Allowed target binding for a workflow definition.
 *
 */
package dz.sh.hidra.modules.workflow.domain.model;

import java.time.Instant;

    /**
     * Allowed target binding for a workflow definition.
     *
         * @param id id
     * @param definitionId definitionId
     * @param targetModule targetModule
     * @param targetTypeId targetTypeId
     * @param workflowPurposeId workflowPurposeId
     * @param active active
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record WorkflowDefinitionTargetBinding(
            String id,
        String definitionId,
        String targetModule,
        String targetTypeId,
        String workflowPurposeId,
        boolean active,
        Instant createdAt,
        Instant updatedAt
    ) {

        public WorkflowDefinitionTargetBinding {
        id = normalize(id);
        definitionId = normalize(definitionId);
        targetModule = normalize(targetModule);
        targetTypeId = normalize(targetTypeId);
        workflowPurposeId = normalize(workflowPurposeId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
