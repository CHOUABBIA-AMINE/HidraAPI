/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmLifecycleEventDto
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.application.dto
 *
 * @Description : Alarm lifecycle event DTO.
 *
 */
package dz.sh.hidra.modules.alarm.application.dto;

import dz.sh.hidra.modules.alarm.domain.value.AlarmLifecycleEventType;
import dz.sh.hidra.modules.alarm.domain.value.AlarmState;

import java.time.Instant;

/**
 * Alarm lifecycle event DTO.
 */
public record AlarmLifecycleEventDto(
        String id,
        String alarmId,
        AlarmLifecycleEventType eventType,
        AlarmState previousState,
        AlarmState newState,
        String actorId,
        Instant occurredAt
) {
}
