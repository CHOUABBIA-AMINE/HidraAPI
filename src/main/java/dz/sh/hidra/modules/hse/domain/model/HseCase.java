/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseCase
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.domain.model
 *
 * @Description : HSE case lifecycle.
 *
 */
package dz.sh.hidra.modules.hse.domain.model;

import dz.sh.hidra.modules.hse.domain.value.*;
import java.time.Instant;

    /**
     * HSE case lifecycle.
     *
         * @param id id
     * @param caseNumber caseNumber
     * @param title title
     * @param description description
     * @param caseTypeId caseTypeId
     * @param severityId severityId
     * @param priorityId priorityId
     * @param status status
     * @param sourceType sourceType
     * @param incidentReferenceId incidentReferenceId
     * @param incidentCodeSnapshot incidentCodeSnapshot
     * @param incidentTitleSnapshot incidentTitleSnapshot
     * @param targetModule targetModule
     * @param targetTypeCode targetTypeCode
     * @param targetId targetId
     * @param targetCodeSnapshot targetCodeSnapshot
     * @param targetLabelSnapshot targetLabelSnapshot
     * @param occurredAt occurredAt
     * @param reportedAt reportedAt
     * @param reportedByActorId reportedByActorId
     * @param reportedByDisplayNameSnapshot reportedByDisplayNameSnapshot
     * @param responsibleOrganizationUnitId responsibleOrganizationUnitId
     * @param responsibleOrganizationUnitNameSnapshot responsibleOrganizationUnitNameSnapshot
     * @param workflowInstanceId workflowInstanceId
     * @param auditReferenceId auditReferenceId
     * @param controlledAt controlledAt
     * @param resolvedAt resolvedAt
     * @param closedAt closedAt
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record HseCase(
            String id,
        String caseNumber,
        String title,
        String description,
        String caseTypeId,
        String severityId,
        String priorityId,
        HseCaseStatus status,
        HseCaseSourceType sourceType,
        String incidentReferenceId,
        String incidentCodeSnapshot,
        String incidentTitleSnapshot,
        String targetModule,
        String targetTypeCode,
        String targetId,
        String targetCodeSnapshot,
        String targetLabelSnapshot,
        Instant occurredAt,
        Instant reportedAt,
        String reportedByActorId,
        String reportedByDisplayNameSnapshot,
        String responsibleOrganizationUnitId,
        String responsibleOrganizationUnitNameSnapshot,
        String workflowInstanceId,
        String auditReferenceId,
        Instant controlledAt,
        Instant resolvedAt,
        Instant closedAt,
        Instant createdAt,
        Instant updatedAt
    ) {

        public HseCase {
        id = normalize(id);
        caseNumber = normalize(caseNumber);
        title = normalize(title);
        description = normalize(description);
        caseTypeId = normalize(caseTypeId);
        severityId = normalize(severityId);
        priorityId = normalize(priorityId);
        incidentReferenceId = normalize(incidentReferenceId);
        incidentCodeSnapshot = normalize(incidentCodeSnapshot);
        incidentTitleSnapshot = normalize(incidentTitleSnapshot);
        targetModule = normalize(targetModule);
        targetTypeCode = normalize(targetTypeCode);
        targetId = normalize(targetId);
        targetCodeSnapshot = normalize(targetCodeSnapshot);
        targetLabelSnapshot = normalize(targetLabelSnapshot);
        reportedByActorId = normalize(reportedByActorId);
        reportedByDisplayNameSnapshot = normalize(reportedByDisplayNameSnapshot);
        responsibleOrganizationUnitId = normalize(responsibleOrganizationUnitId);
        responsibleOrganizationUnitNameSnapshot = normalize(responsibleOrganizationUnitNameSnapshot);
        workflowInstanceId = normalize(workflowInstanceId);
        auditReferenceId = normalize(auditReferenceId);
        }
        public boolean closedLifecycle() {
            return status == HseCaseStatus.CLOSED
                    || status == HseCaseStatus.CANCELLED;
        }
        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
