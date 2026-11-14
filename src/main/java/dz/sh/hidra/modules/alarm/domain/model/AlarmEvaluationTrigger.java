/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmEvaluationTrigger
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.domain.model
 *
 * @Description : Pins one immutable, Alarm-owned evaluation-trigger mapping.
 *
 */
package dz.sh.hidra.modules.alarm.domain.model;

import java.time.Instant;
import java.util.Objects;

/** One Monitoring evaluation may originate at most one formal Alarm. */
public record AlarmEvaluationTrigger(
        String evaluationId, String alarmId, String ruleId, long ruleRevision,
        String companyPartyId, String assetType, String assetId, String unitId,
        String targetId, String approvalId, String trustedReadingId, Instant evaluatedAt,
        String alarmTypeId, String severityId, String titleFr, String actorId, Instant createdAt) {

    public AlarmEvaluationTrigger {
        for (String field : new String[] { evaluationId, alarmId, ruleId, companyPartyId,
                assetType, assetId, unitId, targetId, approvalId, trustedReadingId,
                alarmTypeId, severityId, titleFr, actorId }) {
            if (field == null || field.isBlank()) {
                throw new IllegalArgumentException("Complete evaluation-trigger provenance is required.");
            }
        }
        if (ruleRevision < 1 || evaluatedAt == null || createdAt == null) {
            throw new IllegalArgumentException("Exact revision and event times are required.");
        }
    }

    public boolean sameCommand(String type, String severity, String title) {
        return Objects.equals(alarmTypeId, type) && Objects.equals(severityId, severity)
                && Objects.equals(titleFr, title);
    }

    public boolean sameSource(String expectedRule, long expectedRevision, String expectedCompany,
            String expectedAssetType, String expectedAssetId, String expectedUnit, String expectedTarget,
            String expectedApproval, String expectedReading, Instant expectedAt) {
        return ruleId.equals(expectedRule) && ruleRevision == expectedRevision
                && companyPartyId.equals(expectedCompany) && assetType.equals(expectedAssetType)
                && assetId.equals(expectedAssetId) && unitId.equals(expectedUnit)
                && targetId.equals(expectedTarget) && approvalId.equals(expectedApproval)
                && trustedReadingId.equals(expectedReading) && evaluatedAt.equals(expectedAt);
    }
}
