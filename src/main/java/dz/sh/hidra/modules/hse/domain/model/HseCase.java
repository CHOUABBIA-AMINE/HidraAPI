/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.hse.domain.exception.InvalidHseValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidHseValueException("HseCase id must not be blank.");
        }
        // HRA-051 required: caseNumber
        if (caseNumber == null || caseNumber.isBlank()) {
            throw new InvalidHseValueException("HseCase case number must not be blank.");
        }
        // HRA-051 required: caseTypeId
        if (caseTypeId == null || caseTypeId.isBlank()) {
            throw new InvalidHseValueException("HseCase case type id must not be blank.");
        }
        // HRA-051 required: severityId
        if (severityId == null || severityId.isBlank()) {
            throw new InvalidHseValueException("HseCase severity id must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidHseValueException("HseCase status must not be null.");
        }
        // HRA-051 required: sourceType
        if (sourceType == null) {
            throw new InvalidHseValueException("HseCase source type must not be null.");
        }
        // HRA-051 required: reportedAt
        if (reportedAt == null) {
            throw new InvalidHseValueException("HseCase reported at must not be null.");
        }

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
        /** Copies unrelated context unchanged while recording the authoritative closure time. */
        public HseCase closedAt(Instant at) {
            java.util.Objects.requireNonNull(at, "Closure time required.");
            return new HseCase(id, caseNumber, title, description, caseTypeId, severityId, priorityId, HseCaseStatus.CLOSED, sourceType, incidentReferenceId, incidentCodeSnapshot, incidentTitleSnapshot, targetModule, targetTypeCode, targetId, targetCodeSnapshot, targetLabelSnapshot, occurredAt, reportedAt, reportedByActorId, reportedByDisplayNameSnapshot, responsibleOrganizationUnitId, responsibleOrganizationUnitNameSnapshot, workflowInstanceId, auditReferenceId, controlledAt, resolvedAt, at, createdAt, at);
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
