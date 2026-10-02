/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmSuppressionQueryUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-02
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.application.port.in
 *
 * @Description : Defines governed alarm suppression query behavior.
 *
 */
package dz.sh.hidra.modules.alarm.application.port.in;

import dz.sh.hidra.modules.alarm.application.dto.AlarmSuppressionDto;
import dz.sh.hidra.modules.alarm.application.dto.AlarmSuppressionPageDto;
import dz.sh.hidra.modules.alarm.application.query.AlarmSuppressionQuery;

/**
 * Application boundary for suppression evidence reads.
 */
public interface AlarmSuppressionQueryUseCase {

    AlarmSuppressionDto suppression(String suppressionId);

    AlarmSuppressionPageDto suppressions(AlarmSuppressionQuery query);
}
