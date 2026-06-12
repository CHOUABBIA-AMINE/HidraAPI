/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryRestMapper
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
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

/**
 * Maps telemetry REST models to application models.
 */
public final class TelemetryRestMapper {

    private TelemetryRestMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static CreateTelemetrySourceCommand toCommand(CreateTelemetrySourceRequest request) {
        return new CreateTelemetrySourceCommand(request.code(), request.nameAr(), request.nameFr(), request.nameEn(), request.sourceTypeId(), request.protocolId(), request.endpointUri(), request.externalReference());
    }

    public static RegisterTelemetryPointCommand toCommand(RegisterTelemetryPointRequest request) {
        return new RegisterTelemetryPointCommand(request.deviceId(), request.code(), request.nameAr(), request.nameFr(), request.nameEn(), request.pointTypeId(), request.signalTypeId(), request.unitId(), request.samplingPeriodSeconds(), request.minOperationalValue(), request.maxOperationalValue());
    }

    public static TelemetrySourceResponse toResponse(TelemetrySourceSummaryDto dto) {
        return new TelemetrySourceResponse(dto.id(), dto.code(), dto.nameFr(), dto.sourceTypeId(), dto.protocolId(), dto.status());
    }

    public static TelemetryPointResponse toResponse(TelemetryPointSummaryDto dto) {
        return new TelemetryPointResponse(dto.id(), dto.deviceId(), dto.code(), dto.nameFr(), dto.signalTypeId(), dto.unitId(), dto.status());
    }
}
