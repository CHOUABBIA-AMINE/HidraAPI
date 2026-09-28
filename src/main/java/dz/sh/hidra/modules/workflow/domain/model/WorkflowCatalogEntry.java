/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowCatalogEntry
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.model
 *
 * @Description : Workflow controlled vocabulary entry.
 *
 */
package dz.sh.hidra.modules.workflow.domain.model;

import dz.sh.hidra.modules.workflow.domain.exception.InvalidWorkflowValueException;
import java.time.Instant;

    /**
     * Workflow controlled vocabulary entry.
     *
         * @param id id
     * @param catalogName catalogName
     * @param code code
     * @param active active
     * @param sortOrder sortOrder
     * @param systemDefined systemDefined
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record WorkflowCatalogEntry(
            String id,
        String catalogName,
        String code,
        boolean active,
        int sortOrder,
        boolean systemDefined,
        Instant createdAt,
        Instant updatedAt
    ) {

        public WorkflowCatalogEntry {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidWorkflowValueException("WorkflowCatalogEntry id must not be blank.");
        }
        // HRA-051 required: code
        if (code == null || code.isBlank()) {
            throw new InvalidWorkflowValueException("WorkflowCatalogEntry code must not be blank.");
        }

        id = normalize(id);
        catalogName = normalize(catalogName);
        code = normalize(code);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
