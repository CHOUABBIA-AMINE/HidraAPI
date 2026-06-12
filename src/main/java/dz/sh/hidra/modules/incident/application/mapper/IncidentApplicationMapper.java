/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentApplicationMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.application.mapper
 *
 * @Description : Maps incident domain models to DTOs.
 *
 */
package dz.sh.hidra.modules.incident.application.mapper;

import dz.sh.hidra.modules.incident.application.dto.IncidentSummaryDto;
import dz.sh.hidra.modules.incident.domain.model.Incident;

/**
 * Maps incident domain models to DTOs.
 */
public final class IncidentApplicationMapper {

    private IncidentApplicationMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static IncidentSummaryDto toSummary(Incident incident) {
        return new IncidentSummaryDto(
                incident.id(),
                incident.incidentNumber(),
                incident.title(),
                incident.classificationId(),
                incident.severityId(),
                incident.status(),
                incident.sourceType(),
                incident.topologyAssetTypeCode(),
                incident.topologyAssetId(),
                incident.topologyAssetCode(),
                incident.detectedAt(),
                incident.reportedAt(),
                incident.closedAt()
        );
    }
}
