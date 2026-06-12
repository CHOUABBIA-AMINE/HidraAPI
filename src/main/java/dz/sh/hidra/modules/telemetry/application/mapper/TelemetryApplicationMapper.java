/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryApplicationMapper
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.mapper
 *
 * @Description : Maps telemetry domain models to DTOs.
 *
 */
package dz.sh.hidra.modules.telemetry.application.mapper;

import dz.sh.hidra.modules.telemetry.application.dto.TelemetryPointSummaryDto;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetrySourceSummaryDto;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryPoint;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetrySource;

/**
 * Maps telemetry domain models to DTOs.
 */
public final class TelemetryApplicationMapper {

    private TelemetryApplicationMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static TelemetrySourceSummaryDto toSummary(TelemetrySource source) {
        return new TelemetrySourceSummaryDto(
                source.id(),
                source.code(),
                source.nameFr(),
                source.sourceTypeId(),
                source.protocolId(),
                source.status()
        );
    }

    public static TelemetryPointSummaryDto toSummary(TelemetryPoint point) {
        return new TelemetryPointSummaryDto(
                point.id(),
                point.deviceId(),
                point.code(),
                point.nameFr(),
                point.signalTypeId(),
                point.unitId(),
                point.status()
        );
    }
}
