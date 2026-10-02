/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmGeneratedRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Interface
 * @Layer       : API
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.api.rest.mapper
 *
 * @Description : Generates exact alarm API/application boundary mappings at compile time.
 *
 */
package dz.sh.hidra.modules.alarm.api.rest.mapper;

import dz.sh.hidra.modules.alarm.api.rest.request.RaiseAlarmRequest;
import dz.sh.hidra.modules.alarm.api.rest.response.AlarmResponse;
import dz.sh.hidra.modules.alarm.application.command.RaiseAlarmCommand;
import dz.sh.hidra.modules.alarm.application.dto.AlarmSummaryDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * Generates only the HRA-070-approved exact alarm boundary mappings.
 */
@Mapper(
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface AlarmGeneratedRestMapper {

    AlarmGeneratedRestMapper INSTANCE = Mappers.getMapper(AlarmGeneratedRestMapper.class);

    RaiseAlarmCommand toCommand(RaiseAlarmRequest request);

    AlarmResponse toResponse(AlarmSummaryDto dto);
}
