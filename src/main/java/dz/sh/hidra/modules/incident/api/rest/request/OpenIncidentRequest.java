/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OpenIncidentRequest
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.api.rest.request
 *
 * @Description : REST request to open incident.
 *
 */
package dz.sh.hidra.modules.incident.api.rest.request;

import dz.sh.hidra.modules.incident.domain.value.IncidentSourceType;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * REST request to open incident.
 */
public record OpenIncidentRequest(
        String incidentNumber,
        String title,
        String description,
        String classificationId,
        String severityId,
        String priorityId,
        IncidentSourceType sourceType,
        String sourceReferenceId,
        String sourceReferenceCode,
        Instant detectedAt,
        Instant occurredAt,
        String topologyAssetTypeCode,
        String topologyAssetId,
        String topologyAssetCode,
        String topologyAssetNameSnapshot,
        String locationDescriptionAr,
        String locationDescriptionLt,
        BigDecimal latitude,
        BigDecimal longitude,
        String responsibleOrganizationUnitId,
        String responsibleOrganizationUnitCode,
        String responsibleOrganizationUnitNameSnapshot,
        String createdByActorId,
        String createdByActorNameSnapshot
) {
}
