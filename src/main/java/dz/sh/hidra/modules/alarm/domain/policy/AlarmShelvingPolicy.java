/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmShelvingPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.domain.policy
 *
 * @Description : Enforces Alarm-owned transactional lifecycle evidence and integrity.
 *
 */
package dz.sh.hidra.modules.alarm.domain.policy;

import dz.sh.hidra.modules.alarm.domain.model.Alarm;
import dz.sh.hidra.modules.alarm.domain.model.AlarmLifecycleEvent;
import dz.sh.hidra.modules.alarm.domain.value.AlarmState;
import java.time.Instant;

public final class AlarmShelvingPolicy {
    private AlarmShelvingPolicy() { }
    public static void ensureCanShelve(Alarm alarm) {
        boolean open = alarm.currentState()==AlarmState.RAISED || alarm.currentState()==AlarmState.ACTIVE
                || alarm.currentState()==AlarmState.ACKNOWLEDGED || alarm.currentState()==AlarmState.ESCALATED;
        if(!open || alarm.closedAt()!=null || alarm.clearedAt()!=null) throw new IllegalStateException("Only uncleared open Alarm can be shelved.");
    }
    public static Instant changedAt(Alarm alarm,Instant occurredAt) {
        return alarm.lastUpdatedAt().isAfter(occurredAt)?alarm.lastUpdatedAt():occurredAt;
    }
    public static AlarmState restorationState(Alarm alarm,AlarmLifecycleEvent source) {
        if(alarm.currentState()!=AlarmState.SHELVED) return alarm.currentState();
        if(alarm.closedAt()!=null) return AlarmState.CLOSED;
        if(alarm.clearedAt()!=null) return AlarmState.CLEARED;
        if(source==null || source.previousState()==null || !alarm.id().equals(source.alarmId())
                || source.eventType()!=dz.sh.hidra.modules.alarm.domain.value.AlarmLifecycleEventType.SHELVED
                || source.newState()!=AlarmState.SHELVED)
            throw new IllegalStateException("Historical shelving lacks authoritative previous-state evidence.");
        AlarmState previous=source.previousState();
        if(previous!=AlarmState.RAISED && previous!=AlarmState.ACTIVE
                && previous!=AlarmState.ACKNOWLEDGED && previous!=AlarmState.ESCALATED)
            throw new IllegalStateException("Shelving source does not establish an open previous state.");
        if(previous!=AlarmState.ESCALATED && alarm.acknowledgedAt()!=null
                && !alarm.acknowledgedAt().isBefore(source.occurredAt())) return AlarmState.ACKNOWLEDGED;
        return previous;
    }
}
