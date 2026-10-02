/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmSuppressionQuery
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-02
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.application.query
 *
 * @Description : Query contract for governed alarm suppression reads.
 *
 */
package dz.sh.hidra.modules.alarm.application.query;

import dz.sh.hidra.modules.alarm.domain.value.AlarmSuppressionScopeType;
import dz.sh.hidra.modules.alarm.domain.value.AlarmSuppressionStatus;

/**
 * Filters suppression evidence without embedding persistence-specific criteria.
 */
public record AlarmSuppressionQuery(
        String suppressionId,
        AlarmSuppressionScopeType scopeType,
        String scopeReferenceId,
        String alarmId,
        AlarmSuppressionStatus status,
        int page,
        int size
) {
}
