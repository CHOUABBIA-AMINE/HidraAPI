/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.api.rest.response
 *
 * @Description : REST response for incident summary.
 *
 */
package dz.sh.hidra.modules.incident.api.rest.response;

import dz.sh.hidra.modules.incident.domain.value.IncidentSourceType;
import dz.sh.hidra.modules.incident.domain.value.IncidentStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

/**
 * REST response for incident summary.
 */
public record IncidentResponse(
        String id,
        String incidentNumber,
        String title,
        String classificationId,
        String severityId,
        IncidentStatus status,
        // Keep the legacy open-incident response vocabulary unchanged. Suspected
        // leak candidates are escalated by the separate T14 source-qualified API.
        @Schema(allowableValues = {
            "MANUAL", "MONITORING_ALERT", "ALARM", "LEAK_CASE",
            "HSE_EVENT", "INTEGRATION", "OTHER"
        })
        IncidentSourceType sourceType,
        String topologyAssetTypeCode,
        String topologyAssetId,
        String topologyAssetCode,
        Instant detectedAt,
        Instant reportedAt,
        Instant closedAt
) {
}
