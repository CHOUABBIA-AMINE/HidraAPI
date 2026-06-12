/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportRequest
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.domain.model
 *
 * @Description : User or system request to generate a report.
 *
 */
package dz.sh.hidra.modules.reporting.domain.model;

import dz.sh.hidra.modules.reporting.domain.value.*;
import java.time.Instant;

    /**
     * User or system request to generate a report.
     *
         * @param id id
     * @param reportDefinitionId reportDefinitionId
     * @param requestedByActorId requestedByActorId
     * @param requestedByUsernameSnapshot requestedByUsernameSnapshot
     * @param requestedByDisplayNameSnapshot requestedByDisplayNameSnapshot
     * @param requestedByRoleCodeSnapshot requestedByRoleCodeSnapshot
     * @param organizationUnitId organizationUnitId
     * @param organizationUnitNameSnapshot organizationUnitNameSnapshot
     * @param requestedAt requestedAt
     * @param purpose purpose
     * @param status status
     * @param correlationId correlationId
     * @param workflowReferenceId workflowReferenceId
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record ReportRequest(
            String id,
        String reportDefinitionId,
        String requestedByActorId,
        String requestedByUsernameSnapshot,
        String requestedByDisplayNameSnapshot,
        String requestedByRoleCodeSnapshot,
        String organizationUnitId,
        String organizationUnitNameSnapshot,
        Instant requestedAt,
        String purpose,
        ReportRequestStatus status,
        String correlationId,
        String workflowReferenceId,
        Instant createdAt,
        Instant updatedAt
    ) {

        public ReportRequest {
        id = normalize(id);
        reportDefinitionId = normalize(reportDefinitionId);
        requestedByActorId = normalize(requestedByActorId);
        requestedByUsernameSnapshot = normalize(requestedByUsernameSnapshot);
        requestedByDisplayNameSnapshot = normalize(requestedByDisplayNameSnapshot);
        requestedByRoleCodeSnapshot = normalize(requestedByRoleCodeSnapshot);
        organizationUnitId = normalize(organizationUnitId);
        organizationUnitNameSnapshot = normalize(organizationUnitNameSnapshot);
        purpose = normalize(purpose);
        correlationId = normalize(correlationId);
        workflowReferenceId = normalize(workflowReferenceId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
