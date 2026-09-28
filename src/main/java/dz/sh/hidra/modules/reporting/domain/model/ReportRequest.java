/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.reporting.domain.exception.InvalidReportingValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidReportingValueException("ReportRequest id must not be blank.");
        }
        // HRA-051 required: reportDefinitionId
        if (reportDefinitionId == null || reportDefinitionId.isBlank()) {
            throw new InvalidReportingValueException("ReportRequest report definition id must not be blank.");
        }
        // HRA-051 required: requestedByActorId
        if (requestedByActorId == null || requestedByActorId.isBlank()) {
            throw new InvalidReportingValueException("ReportRequest requested by actor id must not be blank.");
        }
        // HRA-051 required: requestedAt
        if (requestedAt == null) {
            throw new InvalidReportingValueException("ReportRequest requested at must not be null.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidReportingValueException("ReportRequest status must not be null.");
        }

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
