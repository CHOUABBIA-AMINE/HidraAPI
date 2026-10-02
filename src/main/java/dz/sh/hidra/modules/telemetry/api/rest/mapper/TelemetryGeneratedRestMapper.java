/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryGeneratedRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Interface
 * @Layer       : API
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.mapper
 *
 * @Description : Generates exact telemetry API/application boundary mappings at compile time.
 *
 */
package dz.sh.hidra.modules.telemetry.api.rest.mapper;

import dz.sh.hidra.modules.telemetry.api.rest.request.CreateTelemetrySourceRequest;
import dz.sh.hidra.modules.telemetry.api.rest.request.RegisterTelemetryPointRequest;
import dz.sh.hidra.modules.telemetry.api.rest.response.TelemetryPointResponse;
import dz.sh.hidra.modules.telemetry.api.rest.response.TelemetrySourceResponse;
import dz.sh.hidra.modules.telemetry.application.command.CreateTelemetrySourceCommand;
import dz.sh.hidra.modules.telemetry.application.command.RegisterTelemetryPointCommand;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetryPointSummaryDto;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetrySourceSummaryDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * Generates only the HRA-070-approved exact telemetry boundary mappings.
 */
@Mapper(
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface TelemetryGeneratedRestMapper {

    TelemetryGeneratedRestMapper INSTANCE = Mappers.getMapper(TelemetryGeneratedRestMapper.class);

    CreateTelemetrySourceCommand toCommand(CreateTelemetrySourceRequest request);

    RegisterTelemetryPointCommand toCommand(RegisterTelemetryPointRequest request);

    TelemetryPointResponse toResponse(TelemetryPointSummaryDto dto);

    TelemetrySourceResponse toResponse(TelemetrySourceSummaryDto dto);
}
