/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : EvidenceLinkedLeakCandidateUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 * @Type        : Class
 * @Layer       : Application
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.application.port.in
 * @Description : T13 governed source-linked suspected leak candidate delivery.
 */
package dz.sh.hidra.modules.leakdetection.application.port.in;

import java.math.BigDecimal;
import java.time.Instant;

public interface EvidenceLinkedLeakCandidateUseCase {
    record Command(String evaluationId, String profileId, BigDecimal declaredConfidence, String reason) { }
    record Result(String candidateId, String evaluationId, String ruleId, long ruleRevision,
            String profileId, String companyPartyId, String assetType, String assetId,
            String unitId, String targetId, String approvalId, String trustedReadingId,
            Instant evaluatedAt, BigDecimal declaredConfidence, String reason,
            String status, boolean created) { }
    Result create(Command command);
    Result evidence(String candidateId);
}
