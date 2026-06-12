/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentSummaryDto
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.application.dto
 *
 * @Description : Incident summary DTO.
 *
 */
package dz.sh.hidra.modules.incident.application.dto;

import dz.sh.hidra.modules.incident.domain.value.IncidentSourceType;
import dz.sh.hidra.modules.incident.domain.value.IncidentStatus;

import java.time.Instant;

/**
 * Incident summary DTO.
 */
public record IncidentSummaryDto(
        String id,
        String incidentNumber,
        String title,
        String classificationId,
        String severityId,
        IncidentStatus status,
        IncidentSourceType sourceType,
        String topologyAssetTypeCode,
        String topologyAssetId,
        String topologyAssetCode,
        Instant detectedAt,
        Instant reportedAt,
        Instant closedAt
) {
}
