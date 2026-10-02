/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ManageAlarmShelvingUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.application.port.in
 *
 * @Description : Defines audited, time-bounded alarm shelving and unshelving operations.
 *
 */
package dz.sh.hidra.modules.alarm.application.port.in;

import java.time.Instant;

public interface ManageAlarmShelvingUseCase {

    String shelve(ShelveAlarmCommand command);

    String unshelve(UnshelveAlarmCommand command);

    record ShelveAlarmCommand(
            String alarmId,
            String shelvingReasonId,
            String reasonText,
            String actorId,
            Instant shelvedUntil,
            String correlationId
    ) { }

    record UnshelveAlarmCommand(
            String alarmId,
            String shelvingId,
            String actorId,
            String correlationId
    ) { }
}
