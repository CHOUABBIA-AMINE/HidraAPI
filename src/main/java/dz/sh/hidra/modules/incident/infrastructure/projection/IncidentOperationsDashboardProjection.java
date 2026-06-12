/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentOperationsDashboardProjection
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Infrastructure
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.infrastructure.projection
 *
 * @Description : Incident operations dashboard projection.
 *
 */
package dz.sh.hidra.modules.incident.infrastructure.projection;

import dz.sh.hidra.modules.incident.domain.value.IncidentStatus;

import java.time.Instant;

/**
 * Incident operations dashboard projection.
 */
public record IncidentOperationsDashboardProjection(
        String incidentId,
        String incidentNumber,
        String title,
        String severityId,
        IncidentStatus status,
        String topologyAssetTypeCode,
        String topologyAssetId,
        String responsibleOrganizationUnitId,
        Instant detectedAt,
        Instant reportedAt
) {
}
