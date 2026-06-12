/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OpenIncidentCommand
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.application.command
 *
 * @Description : Command to open an incident.
 *
 */
package dz.sh.hidra.modules.incident.application.command;

import dz.sh.hidra.modules.incident.domain.value.IncidentSourceType;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Command to open an incident.
 */
public record OpenIncidentCommand(
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
