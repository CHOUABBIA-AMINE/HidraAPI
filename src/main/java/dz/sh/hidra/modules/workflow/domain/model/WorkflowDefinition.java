/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowDefinition
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.workflow.domain.exception.InvalidWorkflowValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidWorkflowValueException("WorkflowDefinition id must not be blank.");
        }
        // HRA-051 required: code
        if (code == null || code.isBlank()) {
            throw new InvalidWorkflowValueException("WorkflowDefinition code must not be blank.");
        }
        // HMR-003 required: nameFr
        if (nameFr == null || nameFr.isBlank()) {
            throw new InvalidWorkflowValueException("WorkflowDefinition French name must not be blank.");
        }
        // HRA-051 required: typeId
        if (typeId == null || typeId.isBlank()) {
            throw new InvalidWorkflowValueException("WorkflowDefinition type id must not be blank.");
        }
        if (version < 1) {
            throw new InvalidWorkflowValueException("WorkflowDefinition version must be greater than or equal to 1.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidWorkflowValueException("WorkflowDefinition status must not be null.");
        }

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
