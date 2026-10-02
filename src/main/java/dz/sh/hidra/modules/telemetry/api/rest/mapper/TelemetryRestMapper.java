/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.mapper
 *
 * @Description : Maps telemetry REST models to application models.
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
import java.util.Objects;

/**
 * Maps telemetry REST models to application models.
 */
public final class TelemetryRestMapper {

    private static final TelemetryGeneratedRestMapper GENERATED = TelemetryGeneratedRestMapper.INSTANCE;

    private TelemetryRestMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static CreateTelemetrySourceCommand toCommand(CreateTelemetrySourceRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "CreateTelemetrySourceRequest must not be null."));
    }

    public static RegisterTelemetryPointCommand toCommand(RegisterTelemetryPointRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "RegisterTelemetryPointRequest must not be null."));
    }

    public static TelemetrySourceResponse toResponse(TelemetrySourceSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "TelemetrySourceSummaryDto must not be null."));
    }

    public static TelemetryPointResponse toResponse(TelemetryPointSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "TelemetryPointSummaryDto must not be null."));
    }
}
