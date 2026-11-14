/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : IncidentEscalationSourcePort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.application.port.out
 * @Description : Retrieve only already owner-authorized operational evidence.
 */
package dz.sh.hidra.modules.incident.application.port.out;

import dz.sh.hidra.modules.incident.domain.value.IncidentEscalationSourceType;
import java.time.Instant;

public interface IncidentEscalationSourcePort {
    record Snapshot(IncidentEscalationSourceType sourceType, String sourceId, String evaluationId,
             String companyId, String assetType, String assetId, Instant evaluatedAt, String sourceStatus) {}
    Snapshot authorized(IncidentEscalationSourceType type, String sourceId);
    /** Isolate source-owned authorization failures from the caller's list/count transaction. */
    boolean readableCorrelation(IncidentEscalationStorePort.Correlation correlation);
}
