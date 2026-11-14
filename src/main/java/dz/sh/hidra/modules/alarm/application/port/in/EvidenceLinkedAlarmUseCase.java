/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EvidenceLinkedAlarmUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.application.port.in
 *
 * @Description : Defines scoped manual Alarm creation and evidence readback.
 *
 */
package dz.sh.hidra.modules.alarm.application.port.in;

import java.math.BigDecimal;
import java.time.Instant;

public interface EvidenceLinkedAlarmUseCase {
    record Command(String evaluationId, String alarmTypeId, String severityId, String titleFr) { }
    record Result(String alarmId, String evaluationId, String ruleId, long ruleRevision,
                  String currentState, String unitId, BigDecimal difference,
                  String targetId, String approvalId, String trustedReadingId,
                  Instant evaluatedAt, boolean created) { }
    Result raise(Command command);
    Result evidence(String alarmId);
}
