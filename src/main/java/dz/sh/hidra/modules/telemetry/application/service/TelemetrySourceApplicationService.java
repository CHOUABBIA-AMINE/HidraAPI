/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetrySourceApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.service
 *
 * @Description : Application service for telemetry sources.
 *
 */
package dz.sh.hidra.modules.telemetry.application.service;

import dz.sh.hidra.modules.telemetry.application.command.CreateTelemetrySourceCommand;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetrySourceSummaryDto;
import dz.sh.hidra.modules.telemetry.application.mapper.TelemetryApplicationMapper;
import dz.sh.hidra.modules.telemetry.application.port.in.CreateTelemetrySourceUseCase;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetrySourceRepositoryPort;
import dz.sh.hidra.modules.telemetry.domain.exception.InvalidTelemetryValueException;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetrySource;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryLifecycleStatus;
import java.time.Instant;
import java.util.Objects;
import org.springframework.stereotype.Service;

/**
 * Application service for telemetry sources.
 */
@Service
public final class TelemetrySourceApplicationService implements CreateTelemetrySourceUseCase {

    private static final String SOURCE_TYPE_CATALOG = "SOURCE_TYPE";
    private static final String PROTOCOL_CATALOG = "PROTOCOL";

    private final TelemetrySourceRepositoryPort repositoryPort;

    public TelemetrySourceApplicationService(TelemetrySourceRepositoryPort repositoryPort) {
        this.repositoryPort = Objects.requireNonNull(
                repositoryPort,
                "Telemetry source repository port must not be null."
        );
    }

    @Override
    public TelemetrySourceSummaryDto createTelemetrySource(
            CreateTelemetrySourceCommand command
    ) {
        Objects.requireNonNull(
                command,
                "Create telemetry source command must not be null."
        );

        if (repositoryPort.existsByCode(command.code())) {
            throw new InvalidTelemetryValueException(
                    "TelemetrySource code must be unique: " + command.code()
            );
        }
        if (!repositoryPort.activeCatalogEntryExists(
                command.sourceTypeId(),
                SOURCE_TYPE_CATALOG
        )) {
            throw new InvalidTelemetryValueException(
                    "TelemetrySource source type must resolve to an active SOURCE_TYPE entry: "
                            + command.sourceTypeId()
            );
        }
        if (!repositoryPort.activeCatalogEntryExists(
                command.protocolId(),
                PROTOCOL_CATALOG
        )) {
            throw new InvalidTelemetryValueException(
                    "TelemetrySource protocol must resolve to an active PROTOCOL entry: "
                            + command.protocolId()
            );
        }

        Instant now = Instant.now();
        TelemetrySource source = new TelemetrySource(
                TelemetryId.newId().value(),
                command.code(),
                command.nameAr(),
                command.nameFr(),
                command.nameEn(),
                command.sourceTypeId(),
                command.protocolId(),
                command.endpointUri(),
                command.externalReference(),
                TelemetryLifecycleStatus.DRAFT,
                now,
                now
        );
        return TelemetryApplicationMapper.toSummary(repositoryPort.save(source));
    }
}
