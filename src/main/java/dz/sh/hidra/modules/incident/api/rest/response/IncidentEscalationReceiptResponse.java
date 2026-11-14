/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : IncidentEscalationReceiptResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 * @Type        : Record
 * @Layer       : API
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.api.rest.response
 * @Description : Typed durable human escalation receipt with no numeric Monitoring disclosure.
 */
package dz.sh.hidra.modules.incident.api.rest.response;

import dz.sh.hidra.modules.incident.application.port.in.EscalateEvidenceToIncidentUseCase;
import java.time.Instant;

public record IncidentEscalationReceiptResponse(String incidentId, String evaluationId,
         String sourceType, String sourceId, String status,
         String actorId, Instant escalatedAt, boolean created) {
    public static IncidentEscalationReceiptResponse from(EscalateEvidenceToIncidentUseCase.Result value) {
        return new IncidentEscalationReceiptResponse(value.incidentId(), value.evaluationId(),
            value.sourceType(), value.sourceId(), value.status(), value.actorId(),
            value.escalatedAt(), value.created());
    }
}
