/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : IncidentLeakCandidateEvidenceContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.application.contract.incident
 * @Description : Only the Incident-approved scalar read of protected T13 candidate evidence.
 */
package dz.sh.hidra.modules.leakdetection.application.contract.incident;

import java.time.Instant;

public interface IncidentLeakCandidateEvidenceContract {
    record Evidence(String candidateId, String evaluationId, String companyId,
                    String assetType, String assetId, Instant evaluatedAt, String state) {}
    Evidence authorized(String candidateId);
}
