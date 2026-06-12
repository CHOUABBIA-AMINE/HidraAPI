/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryPointApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.service
 *
 * @Description : Application service for telemetry points.
 *
 */
package dz.sh.hidra.modules.telemetry.application.service;

import dz.sh.hidra.modules.telemetry.application.command.RegisterTelemetryPointCommand;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetryPointSummaryDto;
import dz.sh.hidra.modules.telemetry.application.mapper.TelemetryApplicationMapper;
import dz.sh.hidra.modules.telemetry.application.port.in.RegisterTelemetryPointUseCase;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryPointRepositoryPort;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryPoint;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryLifecycleStatus;

import java.time.Instant;
import java.util.Objects;

/**
 * Application service for telemetry points.
 */
public final class TelemetryPointApplicationService implements RegisterTelemetryPointUseCase {

    private final TelemetryPointRepositoryPort repositoryPort;

    public TelemetryPointApplicationService(TelemetryPointRepositoryPort repositoryPort) {
        this.repositoryPort = Objects.requireNonNull(repositoryPort, "Telemetry point repository port must not be null.");
    }

    @Override
    public TelemetryPointSummaryDto registerTelemetryPoint(RegisterTelemetryPointCommand command) {
        Objects.requireNonNull(command, "Register telemetry point command must not be null.");
        Instant now = Instant.now();
        TelemetryPoint point = new TelemetryPoint(
                TelemetryId.newId().value(),
                command.deviceId(),
                command.code(),
                command.nameAr(),
                command.nameFr(),
                command.nameEn(),
                command.pointTypeId(),
                command.signalTypeId(),
                command.unitId(),
                null,
                command.samplingPeriodSeconds(),
                null,
                null,
                command.minOperationalValue(),
                command.maxOperationalValue(),
                TelemetryLifecycleStatus.PLANNED,
                now,
                now
        );
        return TelemetryApplicationMapper.toSummary(repositoryPort.save(point));
    }
}
