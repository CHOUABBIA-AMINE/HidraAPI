/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : IncidentLeakCandidateEvidenceQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 * @Type        : Class
 * @Layer       : Application
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.application.service
 * @Description : Incident-targeted owner READ of qualified T13 suspected candidate provenance.
 */
package dz.sh.hidra.modules.leakdetection.application.service;

import dz.sh.hidra.modules.leakdetection.application.contract.incident.IncidentLeakCandidateEvidenceContract;
import dz.sh.hidra.modules.leakdetection.application.port.in.EvidenceLinkedLeakCandidateUseCase;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IncidentLeakCandidateEvidenceQueryService implements IncidentLeakCandidateEvidenceContract {
    private final EvidenceLinkedLeakCandidateUseCase evidence;
    public IncidentLeakCandidateEvidenceQueryService(EvidenceLinkedLeakCandidateUseCase evidence) {
        this.evidence=Objects.requireNonNull(evidence);
    }
    @Override @Transactional
    public Evidence authorized(String candidateId) {
        var r=evidence.evidence(candidateId);
        return new Evidence(r.candidateId(),r.evaluationId(),r.companyPartyId(),
                r.assetType(),r.assetId(),r.evaluatedAt(),r.status());
    }
}
