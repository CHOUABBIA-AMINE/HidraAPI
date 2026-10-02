/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.api.rest.mapper
 *
 * @Description : Maps alarm REST models to application models.
 *
 */
package dz.sh.hidra.modules.alarm.api.rest.mapper;

import dz.sh.hidra.modules.alarm.api.rest.request.AcknowledgeAlarmRequest;
import dz.sh.hidra.modules.alarm.api.rest.request.CloseAlarmRequest;
import dz.sh.hidra.modules.alarm.api.rest.request.RaiseAlarmRequest;
import dz.sh.hidra.modules.alarm.api.rest.response.AlarmResponse;
import dz.sh.hidra.modules.alarm.application.command.AcknowledgeAlarmCommand;
import dz.sh.hidra.modules.alarm.application.command.CloseAlarmCommand;
import dz.sh.hidra.modules.alarm.application.command.RaiseAlarmCommand;
import dz.sh.hidra.modules.alarm.application.dto.AlarmSummaryDto;
import java.util.Objects;

/**
 * Maps alarm REST models to application models.
 */
public final class AlarmRestMapper {

    private static final AlarmGeneratedRestMapper GENERATED = AlarmGeneratedRestMapper.INSTANCE;

    private AlarmRestMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static AcknowledgeAlarmCommand toCommand(
            AcknowledgeAlarmRequest request,
            String authenticatedActorId,
            String authenticatedPrincipalName
    ) {
        return new AcknowledgeAlarmCommand(
                request.alarmId(),
                authenticatedActorId,
                authenticatedPrincipalName,
                request.organizationUnitId(),
                request.organizationUnitCode(),
                request.comment(),
                request.correlationId()
        );
    }

    public static CloseAlarmCommand toCommand(CloseAlarmRequest request, String authenticatedActorId) {
        return new CloseAlarmCommand(
                request.alarmId(),
                request.closureType(),
                request.closureReasonId(),
                request.closureComment(),
                authenticatedActorId,
                request.requiresReview(),
                request.reviewWorkflowInstanceId(),
                request.correlationId()
        );
    }

    public static RaiseAlarmCommand toCommand(RaiseAlarmRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "RaiseAlarmRequest must not be null."));
    }

    public static AlarmResponse toResponse(AlarmSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "AlarmSummaryDto must not be null."));
    }
}
