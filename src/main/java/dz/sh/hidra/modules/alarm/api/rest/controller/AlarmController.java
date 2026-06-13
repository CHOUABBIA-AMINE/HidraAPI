/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Interface
 * @Layer       : API
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.api.rest.controller
 *
 * @Description : Framework-neutral alarm controller contract.
 *
 */
package dz.sh.hidra.modules.alarm.api.rest.controller;
import dz.sh.hidra.modules.alarm.api.rest.request.*;
import dz.sh.hidra.modules.alarm.api.rest.response.*;

/**
 * Framework-neutral alarm controller contract.
 */
public interface AlarmController {
    String acknowledgeAlarm(AcknowledgeAlarmRequest request);
    String closeAlarm(CloseAlarmRequest request);
    AlarmResponse raiseAlarm(RaiseAlarmRequest request);
}
