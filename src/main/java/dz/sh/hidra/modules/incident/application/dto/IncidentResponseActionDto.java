/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentResponseActionDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.application.dto
 *
 * @Description : Incident response action DTO.
 *
 */
package dz.sh.hidra.modules.incident.application.dto;

import dz.sh.hidra.modules.incident.domain.value.ResponseActionStatus;

import java.time.Instant;

/**
 * Incident response action DTO.
 */
public record IncidentResponseActionDto(
        String id,
        String incidentId,
        String actionTypeId,
        ResponseActionStatus actionStatus,
        String description,
        Instant createdAt
) {
}
