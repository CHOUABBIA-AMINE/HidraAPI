/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationRecommendationAuditContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.application.contract.simulation
 *
 * @Description : Records scalar Simulation publication evidence through the Audit owner.
 *
 */
package dz.sh.hidra.modules.audit.application.contract.simulation;

import java.time.Instant;

public interface SimulationRecommendationAuditContract {
    String appendPublished(PublicationEvidence evidence);

    record PublicationEvidence(String recommendationId, String runId, String candidateId,
            String recommendationTypeId, String actorId, Instant occurredAt) {}
}
