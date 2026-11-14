/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EvidenceLinkedAlarmResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.api.rest.response
 *
 * @Description : Returns current scoped Alarm state and protected evaluation provenance.
 *
 */
package dz.sh.hidra.modules.alarm.api.rest.response;

import dz.sh.hidra.modules.alarm.application.port.in.EvidenceLinkedAlarmUseCase;
import java.math.BigDecimal;
import java.time.Instant;

public record EvidenceLinkedAlarmResponse(
        String alarmId, String evaluationId, String ruleId, long ruleRevision, String currentState,
        String unitId, BigDecimal difference, String targetId, String approvalId,
        String trustedReadingId, Instant evaluatedAt, boolean created) {
    public static EvidenceLinkedAlarmResponse of(EvidenceLinkedAlarmUseCase.Result value) {
        return new EvidenceLinkedAlarmResponse(value.alarmId(), value.evaluationId(), value.ruleId(),
                value.ruleRevision(), value.currentState(), value.unitId(), value.difference(),
                value.targetId(), value.approvalId(), value.trustedReadingId(),
                value.evaluatedAt(), value.created());
    }
}
