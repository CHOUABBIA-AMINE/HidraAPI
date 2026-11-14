/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : IncidentEscalationStorePort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.application.port.out
 * @Description : Durable correlation lock, immutable replay, and human timeline.
 */
package dz.sh.hidra.modules.incident.application.port.out;

import dz.sh.hidra.modules.incident.domain.model.IncidentSourceEscalation;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface IncidentEscalationStorePort {
    record Correlation(String evaluationId, String incidentId, String companyId,
             String assetType, String assetId, String classificationId, String severityId,
             String title, String reason, String actorId, Instant createdAt) {}
    void serialize(String evaluationId);
    Optional<Correlation> byEvaluation(String evaluationId);
    Optional<Correlation> byIncident(String incidentId);
    List<Correlation> correlationsForAssets(List<IncidentEscalationAuthorizationPort.AssetRef> assets);
    Optional<IncidentSourceEscalation> bySource(String type, String sourceId);
    List<IncidentSourceEscalation> timeline(String incidentId);
    void create(Correlation correlation);
    void link(IncidentSourceEscalation escalation);
}
