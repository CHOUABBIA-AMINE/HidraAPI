/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportAccessPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.domain.model
 *
 * @Description : Report-specific access policy metadata.
 *
 */
package dz.sh.hidra.modules.reporting.domain.model;

import dz.sh.hidra.modules.reporting.domain.exception.InvalidReportingValueException;
import dz.sh.hidra.modules.reporting.domain.value.*;
import java.time.Instant;

    /**
     * Report-specific access policy metadata.
     *
         * @param id id
     * @param reportDefinitionId reportDefinitionId
     * @param scopeType scopeType
     * @param scopeReferenceId scopeReferenceId
     * @param permissionCode permissionCode
     * @param restricted restricted
     * @param maskSensitiveValues maskSensitiveValues
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record ReportAccessPolicy(
            String id,
        String reportDefinitionId,
        ReportAccessScopeType scopeType,
        String scopeReferenceId,
        String permissionCode,
        boolean restricted,
        boolean maskSensitiveValues,
        Instant createdAt,
        Instant updatedAt
    ) {

        public ReportAccessPolicy {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidReportingValueException("ReportAccessPolicy id must not be blank.");
        }
        // HRA-051 required: reportDefinitionId
        if (reportDefinitionId == null || reportDefinitionId.isBlank()) {
            throw new InvalidReportingValueException("ReportAccessPolicy report definition id must not be blank.");
        }
        // HRA-051 required: scopeType
        if (scopeType == null) {
            throw new InvalidReportingValueException("ReportAccessPolicy scope type must not be null.");
        }
        // HRA-051 required: permissionCode
        if (permissionCode == null || permissionCode.isBlank()) {
            throw new InvalidReportingValueException("ReportAccessPolicy permission code must not be blank.");
        }

        id = normalize(id);
        reportDefinitionId = normalize(reportDefinitionId);
        scopeReferenceId = normalize(scopeReferenceId);
        permissionCode = normalize(permissionCode);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
