/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : EscalateEvidenceToIncidentUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.application.port.in
 * @Description : Human T14 command and protected typed timeline.
 */
package dz.sh.hidra.modules.incident.application.port.in;

import java.time.Instant;
import java.util.List;

public interface EscalateEvidenceToIncidentUseCase {
    record Command(String sourceType, String sourceId,
                   String classificationId, String severityId, String titleFr, String reason) {}
    record Result(String incidentId, String evaluationId, String sourceType, String sourceId,
                  String status, String actorId, Instant escalatedAt, boolean created) {}
    Result escalate(Command command);
    Result receipt(String incidentId);
    List<Result> timeline(String incidentId);
}
