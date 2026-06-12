/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportAccessPolicy
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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
