/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentGeneratedRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Interface
 * @Layer       : API
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.api.rest.mapper
 *
 * @Description : Generates exact incident API/application boundary mappings at compile time.
 *
 */
package dz.sh.hidra.modules.incident.api.rest.mapper;

import dz.sh.hidra.modules.incident.api.rest.request.CloseIncidentRequest;
import dz.sh.hidra.modules.incident.api.rest.request.OpenIncidentRequest;
import dz.sh.hidra.modules.incident.api.rest.request.RecordIncidentResponseActionRequest;
import dz.sh.hidra.modules.incident.api.rest.response.IncidentResponse;
import dz.sh.hidra.modules.incident.application.command.CloseIncidentCommand;
import dz.sh.hidra.modules.incident.application.command.OpenIncidentCommand;
import dz.sh.hidra.modules.incident.application.command.RecordIncidentResponseActionCommand;
import dz.sh.hidra.modules.incident.application.dto.IncidentSummaryDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * Generates only the HRA-070-approved exact incident boundary mappings.
 */
@Mapper(
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface IncidentGeneratedRestMapper {

    IncidentGeneratedRestMapper INSTANCE = Mappers.getMapper(IncidentGeneratedRestMapper.class);

    CloseIncidentCommand toCommand(CloseIncidentRequest request);

    OpenIncidentCommand toCommand(OpenIncidentRequest request);

    RecordIncidentResponseActionCommand toCommand(RecordIncidentResponseActionRequest request);

    IncidentResponse toResponse(IncidentSummaryDto dto);
}
