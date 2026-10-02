/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentQueryUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.application.port.in
 *
 * @Description : Read-only incident register and detail contract.
 *
 */
package dz.sh.hidra.modules.incident.application.port.in;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public interface IncidentQueryUseCase {

    Page<IncidentView> incidents(int page, int size);

    IncidentView incident(String id);

    record Page<T>(
            List<T> content,
            int page,
            int size,
            long totalElements,
            int totalPages,
            boolean hasNext
    ) { }

    record IncidentView(
            String id,
            String incidentNumber,
            String title,
            String description,
            String classificationId,
            String severityId,
            String priorityId,
            String status,
            String sourceType,
            String sourceReferenceId,
            String sourceReferenceCode,
            Instant detectedAt,
            Instant reportedAt,
            Instant occurredAt,
            String topologyAssetTypeCode,
            String topologyAssetId,
            String topologyAssetCode,
            String topologyAssetName,
            String locationDescriptionAr,
            String locationDescriptionLt,
            BigDecimal latitude,
            BigDecimal longitude,
            String responsibleOrganizationUnitId,
            String responsibleOrganizationUnitCode,
            String responsibleOrganizationUnitName,
            String responsibleActorId,
            String responsibleActorName,
            String workflowInstanceId,
            int currentEscalationLevel,
            Instant containedAt,
            Instant resolvedAt,
            Instant closedAt,
            Instant cancelledAt,
            Instant createdAt,
            Instant updatedAt
    ) { }
}
