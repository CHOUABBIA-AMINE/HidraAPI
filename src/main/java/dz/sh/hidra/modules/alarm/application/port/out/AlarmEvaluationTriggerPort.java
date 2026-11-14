/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmEvaluationTriggerPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.application.port.out
 *
 * @Description : Serializes evaluation trigger claims in Alarm-owned persistence.
 *
 */
package dz.sh.hidra.modules.alarm.application.port.out;

import dz.sh.hidra.modules.alarm.domain.model.AlarmEvaluationTrigger;
import java.util.Optional;

public interface AlarmEvaluationTriggerPort {
    /** Transaction-scoped, per-evaluation PostgreSQL lock; invoke before reading the mapping. */
    void serialize(String evaluationId);
    Optional<AlarmEvaluationTrigger> byEvaluation(String evaluationId);
    Optional<AlarmEvaluationTrigger> byAlarm(String alarmId);
    void save(AlarmEvaluationTrigger trigger);
}
