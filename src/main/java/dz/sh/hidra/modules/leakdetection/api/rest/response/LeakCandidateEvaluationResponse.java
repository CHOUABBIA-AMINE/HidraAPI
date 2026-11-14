/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : LeakCandidateEvaluationResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 * @Type        : Record
 * @Layer       : API
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.api.rest.response
 * @Description : T13 governed source-linked suspected leak candidate delivery.
 */
package dz.sh.hidra.modules.leakdetection.api.rest.response;

import dz.sh.hidra.modules.leakdetection.application.port.in.EvidenceLinkedLeakCandidateUseCase.Result;
import java.math.BigDecimal;
import java.time.Instant;

public record LeakCandidateEvaluationResponse(
        String candidateId, String evaluationId, String ruleId, long ruleRevision,
        String profileId, String companyPartyId, String assetType, String assetId,
        String unitId, String targetId, String approvalId, String trustedReadingId,
        Instant evaluatedAt, BigDecimal declaredConfidence, String reason,
        String status, boolean created) {
    public static LeakCandidateEvaluationResponse of(Result r) {
        return new LeakCandidateEvaluationResponse(r.candidateId(), r.evaluationId(), r.ruleId(),
            r.ruleRevision(), r.profileId(), r.companyPartyId(), r.assetType(), r.assetId(),
            r.unitId(), r.targetId(), r.approvalId(), r.trustedReadingId(), r.evaluatedAt(),
            r.declaredConfidence(), r.reason(), r.status(), r.created());
    }
}
