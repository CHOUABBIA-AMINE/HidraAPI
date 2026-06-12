/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmLifecycleGuard
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.domain.service
 *
 * @Description : Guards alarm lifecycle transitions.
 *
 */
package dz.sh.hidra.modules.alarm.domain.service;

import dz.sh.hidra.modules.alarm.domain.exception.AlarmLifecycleViolationException;
import dz.sh.hidra.modules.alarm.domain.model.Alarm;
import dz.sh.hidra.modules.alarm.domain.value.AlarmState;

/**
 * Guards alarm lifecycle transitions.
 */
public class AlarmLifecycleGuard {

    public void ensureCanAcknowledge(Alarm alarm) {
        if (alarm == null) {
            throw new AlarmLifecycleViolationException("Alarm must not be null.");
        }
        if (alarm.closed()) {
            throw new AlarmLifecycleViolationException("Closed or cancelled alarms cannot be acknowledged.");
        }
    }

    public void ensureCanClose(Alarm alarm, boolean cancelled) {
        if (alarm == null) {
            throw new AlarmLifecycleViolationException("Alarm must not be null.");
        }
        if (!cancelled && alarm.currentState() != AlarmState.CLEARED && alarm.currentState() != AlarmState.ESCALATED) {
            throw new AlarmLifecycleViolationException("Alarm cannot be closed before clear unless explicitly cancelled.");
        }
    }
}
