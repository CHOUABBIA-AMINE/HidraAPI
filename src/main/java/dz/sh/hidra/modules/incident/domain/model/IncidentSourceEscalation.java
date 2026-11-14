/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : IncidentSourceEscalation
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.domain.model
 * @Description : Immutable human escalation evidence of one owner-qualified source.
 */
package dz.sh.hidra.modules.incident.domain.model;

import dz.sh.hidra.modules.incident.domain.value.IncidentEscalationSourceType;
import java.time.Instant;

public record IncidentSourceEscalation(String incidentId, String evaluationId,
        IncidentEscalationSourceType sourceType, String sourceId,
        String actorId, String reason, Instant at) {
    public IncidentSourceEscalation {
        if (incidentId==null||incidentId.isBlank()||evaluationId==null||evaluationId.isBlank()
                ||sourceType==null||sourceId==null||sourceId.isBlank()
                ||actorId==null||actorId.isBlank()||reason==null||reason.isBlank()||at==null)
            throw new IllegalArgumentException("Complete human escalation origin is required.");
    }
}
