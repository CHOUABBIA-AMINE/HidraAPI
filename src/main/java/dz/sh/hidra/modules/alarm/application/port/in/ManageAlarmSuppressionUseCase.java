/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ManageAlarmSuppressionUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-02
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.application.port.in
 *
 * @Description : Defines governed alarm suppression creation, release, and expiry evaluation.
 *
 */
package dz.sh.hidra.modules.alarm.application.port.in;

import dz.sh.hidra.modules.alarm.application.command.CreateAlarmSuppressionCommand;
import dz.sh.hidra.modules.alarm.application.command.EvaluateAlarmSuppressionExpiryCommand;
import dz.sh.hidra.modules.alarm.application.command.ReleaseAlarmSuppressionCommand;
import dz.sh.hidra.modules.alarm.application.dto.AlarmSuppressionDto;

/**
 * Application boundary for suppression lifecycle mutations.
 *
 * <p>Implementations must enforce the approved GAP-ALARM-004 policy. This contract does
 * not define persistence, scheduling, REST, or workflow implementation details.</p>
 */
public interface ManageAlarmSuppressionUseCase {

    AlarmSuppressionDto createSuppression(CreateAlarmSuppressionCommand command);

    AlarmSuppressionDto releaseSuppression(ReleaseAlarmSuppressionCommand command);

    int expireDueSuppressions(EvaluateAlarmSuppressionExpiryCommand command);
}
