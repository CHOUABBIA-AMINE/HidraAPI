/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmEvaluationTriggerConflictException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.domain.exception
 *
 * @Description : Signals a mismatched immutable alarm trigger replay.
 *
 */
package dz.sh.hidra.modules.alarm.domain.exception;

/** A prior evaluation-trigger claim cannot be changed by a replay. */
public final class AlarmEvaluationTriggerConflictException extends RuntimeException {
    public AlarmEvaluationTriggerConflictException() {
        super("The evaluation already produced a different Alarm trigger.");
    }
}
