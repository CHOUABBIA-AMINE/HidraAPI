/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RecordIncidentResponseActionRequest
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.api.rest.request
 *
 * @Description : REST request to record incident response action.
 *
 */
package dz.sh.hidra.modules.incident.api.rest.request;

import dz.sh.hidra.modules.incident.domain.value.ResponseActionStatus;
import dz.sh.hidra.modules.incident.domain.value.ResponseTargetType;

import java.time.Instant;

/**
 * REST request to record incident response action.
 */
public record RecordIncidentResponseActionRequest(
        String incidentId,
        String actionTypeId,
        ResponseActionStatus actionStatus,
        String description,
        ResponseTargetType targetType,
        String targetReferenceId,
        String targetReferenceCode,
        Instant plannedStartAt,
        Instant plannedEndAt,
        Instant startedAt,
        Instant completedAt,
        String performedByActorId,
        String performedByActorNameSnapshot,
        String organizationUnitId,
        String resultSummary,
        String failureReason
) {
}
