/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseQueryUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.application.port.in
 *
 * @Description : Read-only HSE case and CAPA query contract.
 *
 */
package dz.sh.hidra.modules.hse.application.port.in;

import java.time.Instant;
import java.util.List;

public interface HseQueryUseCase {

    Page<HseCaseView> cases(int page, int size);

    HseCaseView hseCase(String id);

    Page<CapaView> capas(int page, int size);

    CapaView capa(String id);

    record Page<T>(List<T> content, int page, int size, long totalElements, int totalPages, boolean hasNext) { }

    record HseCaseView(
            String id, String caseNumber, String title, String description,
            String caseTypeId, String severityId, String priorityId, String status, String sourceType,
            String incidentReferenceId, String incidentCodeSnapshot, String incidentTitleSnapshot,
            String targetModule, String targetTypeCode, String targetId, String targetCodeSnapshot, String targetLabelSnapshot,
            Instant occurredAt, Instant reportedAt, String reportedByActorId, String reportedByDisplayNameSnapshot,
            String responsibleOrganizationUnitId, String responsibleOrganizationUnitNameSnapshot,
            String workflowInstanceId, String auditReferenceId,
            Instant controlledAt, Instant resolvedAt, Instant closedAt, Instant createdAt, Instant updatedAt
    ) { }

    record CapaView(
            String id, String hseCaseId, String actionNumber, String actionTypeId, String title, String description,
            String ownerActorId, String ownerDisplayNameSnapshot,
            String ownerOrganizationUnitId, String ownerOrganizationUnitNameSnapshot,
            Instant targetDate, Instant completedAt, boolean verificationRequired,
            String verifiedByActorId, Instant verifiedAt, String status,
            String linkedWorkOrderId, String workflowTaskId, Instant createdAt, Instant updatedAt
    ) { }
}
