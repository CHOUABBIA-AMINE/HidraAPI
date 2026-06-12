/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RaiseAlarmUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.application.port.in
 *
 * @Description : Use case for raising alarms.
 *
 */
package dz.sh.hidra.modules.alarm.application.port.in;

import dz.sh.hidra.modules.alarm.application.command.RaiseAlarmCommand;
import dz.sh.hidra.modules.alarm.application.dto.AlarmSummaryDto;

/**
 * Use case for raising alarms.
 */
public interface RaiseAlarmUseCase {

    AlarmSummaryDto raiseAlarm(RaiseAlarmCommand command);
}
