/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CloseAlarmUseCase
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.application.port.in
 *
 * @Description : Use case for closing alarms.
 *
 */
package dz.sh.hidra.modules.alarm.application.port.in;

import dz.sh.hidra.modules.alarm.application.command.CloseAlarmCommand;

/**
 * Use case for closing alarms.
 */
public interface CloseAlarmUseCase {

    String closeAlarm(CloseAlarmCommand command);
}
