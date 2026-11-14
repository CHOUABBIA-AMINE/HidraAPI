/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : LeakEvaluationTrigger
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.domain.model
 * @Description : T13 governed source-linked suspected leak candidate delivery.
 */
package dz.sh.hidra.modules.leakdetection.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/** An immutable human-declared suspicion provenance record; not a leak verdict. */
public record LeakEvaluationTrigger(
        String evaluationId, String candidateId, String ruleId, long ruleRevision,
        String companyPartyId, String assetType, String assetId, String unitId,
        String targetId, String approvalId, String trustedReadingId, Instant evaluatedAt,
        String profileId, BigDecimal declaredConfidence, String reason,
        String actorId, Instant createdAt) {
    public LeakEvaluationTrigger {
        if (evaluationId == null || evaluationId.isBlank() || candidateId == null || candidateId.isBlank()
                || ruleId == null || ruleId.isBlank() || ruleRevision <= 0
                || companyPartyId == null || companyPartyId.isBlank()
                || assetType == null || assetType.isBlank() || assetId == null || assetId.isBlank()
                || unitId == null || unitId.isBlank() || targetId == null || targetId.isBlank()
                || approvalId == null || approvalId.isBlank() || trustedReadingId == null
                || trustedReadingId.isBlank() || evaluatedAt == null || profileId == null
                || profileId.isBlank() || declaredConfidence == null || reason == null
                || reason.isBlank() || actorId == null || actorId.isBlank() || createdAt == null) {
            throw new IllegalArgumentException("Exact Leak candidate provenance is required.");
        }
        if (declaredConfidence.compareTo(BigDecimal.ZERO) < 0
                || declaredConfidence.compareTo(BigDecimal.ONE) > 0
                || declaredConfidence.scale() > 6) {
            throw new IllegalArgumentException("Declared confidence must be within [0,1].");
        }
    }
    public boolean sameSource(String ruleId, long revision, String company, String type, String asset,
            String unit, String target, String approval, String reading, Instant at) {
        return this.ruleId.equals(ruleId) && ruleRevision == revision
                && companyPartyId.equals(company) && assetType.equals(type) && assetId.equals(asset)
                && unitId.equals(unit) && targetId.equals(target) && approvalId.equals(approval)
                && trustedReadingId.equals(reading) && evaluatedAt.equals(at);
    }
    public boolean sameCommand(String profile, BigDecimal confidence, String summary) {
        return profileId.equals(profile) && declaredConfidence.compareTo(confidence) == 0
                && reason.equals(summary);
    }
}
