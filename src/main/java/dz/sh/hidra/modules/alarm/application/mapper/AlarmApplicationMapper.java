/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmApplicationMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.application.mapper
 *
 * @Description : Maps alarm domain models to DTOs.
 *
 */
package dz.sh.hidra.modules.alarm.application.mapper;

import dz.sh.hidra.modules.alarm.application.dto.AlarmSummaryDto;
import dz.sh.hidra.modules.alarm.domain.model.Alarm;

/**
 * Maps alarm domain models to DTOs.
 */
public final class AlarmApplicationMapper {

    private AlarmApplicationMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static AlarmSummaryDto toSummary(Alarm alarm) {
        return new AlarmSummaryDto(
                alarm.id(),
                alarm.alarmNumber(),
                alarm.alarmTypeId(),
                alarm.severityId(),
                alarm.titleFr(),
                alarm.sourceType(),
                alarm.topologyAssetTypeCode(),
                alarm.topologyAssetId(),
                alarm.topologyAssetCode(),
                alarm.currentState(),
                alarm.raisedAt(),
                alarm.acknowledgedAt(),
                alarm.closedAt()
        );
    }
}
