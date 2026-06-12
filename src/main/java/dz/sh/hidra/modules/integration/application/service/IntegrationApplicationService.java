/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationApplicationService
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.application.service
 *
 * @Description : Application service for integration registry, job runs, and exchange messages.
 *
 */
package dz.sh.hidra.modules.integration.application.service;

import dz.sh.hidra.modules.integration.application.command.RecordExchangeMessageCommand;
import dz.sh.hidra.modules.integration.application.command.RegisterExternalSystemCommand;
import dz.sh.hidra.modules.integration.application.command.StartIntegrationJobRunCommand;
import dz.sh.hidra.modules.integration.application.dto.ExternalSystemSummaryDto;
import dz.sh.hidra.modules.integration.application.dto.IntegrationExchangeMessageSummaryDto;
import dz.sh.hidra.modules.integration.application.dto.IntegrationJobRunSummaryDto;
import dz.sh.hidra.modules.integration.application.mapper.IntegrationApplicationMapper;
import dz.sh.hidra.modules.integration.application.port.in.RecordExchangeMessageUseCase;
import dz.sh.hidra.modules.integration.application.port.in.RegisterExternalSystemUseCase;
import dz.sh.hidra.modules.integration.application.port.in.StartIntegrationJobRunUseCase;
import dz.sh.hidra.modules.integration.application.port.out.ExternalSystemRepositoryPort;
import dz.sh.hidra.modules.integration.application.port.out.IntegrationExchangeMessageRepositoryPort;
import dz.sh.hidra.modules.integration.application.port.out.IntegrationJobRunRepositoryPort;
import dz.sh.hidra.modules.integration.domain.model.ExternalSystem;
import dz.sh.hidra.modules.integration.domain.model.IntegrationExchangeMessage;
import dz.sh.hidra.modules.integration.domain.model.IntegrationJobRun;
import dz.sh.hidra.modules.integration.domain.value.ExternalSystemStatus;
import dz.sh.hidra.modules.integration.domain.value.IntegrationId;
import dz.sh.hidra.modules.integration.domain.value.JobRunStatus;
import dz.sh.hidra.modules.integration.domain.value.PayloadStorageMode;

import java.time.Instant;
import java.util.Objects;

/**
 * Application service for integration registry, job runs, and exchange messages.
 */
public class IntegrationApplicationService implements RegisterExternalSystemUseCase, StartIntegrationJobRunUseCase, RecordExchangeMessageUseCase {

    private final ExternalSystemRepositoryPort externalSystemRepositoryPort;
    private final IntegrationJobRunRepositoryPort jobRunRepositoryPort;
    private final IntegrationExchangeMessageRepositoryPort exchangeMessageRepositoryPort;

    public IntegrationApplicationService(
            ExternalSystemRepositoryPort externalSystemRepositoryPort,
            IntegrationJobRunRepositoryPort jobRunRepositoryPort,
            IntegrationExchangeMessageRepositoryPort exchangeMessageRepositoryPort
    ) {
        this.externalSystemRepositoryPort = Objects.requireNonNull(externalSystemRepositoryPort, "External system repository port must not be null.");
        this.jobRunRepositoryPort = Objects.requireNonNull(jobRunRepositoryPort, "Integration job run repository port must not be null.");
        this.exchangeMessageRepositoryPort = Objects.requireNonNull(exchangeMessageRepositoryPort, "Integration exchange message repository port must not be null.");
    }

    @Override
    public ExternalSystemSummaryDto registerExternalSystem(RegisterExternalSystemCommand command) {
        Objects.requireNonNull(command, "Register external system command must not be null.");
        Instant now = Instant.now();
        ExternalSystem externalSystem = new ExternalSystem(
                IntegrationId.newId().value(),
                command.code(),
                command.nameAr(),
                command.nameFr(),
                command.nameEn(),
                command.systemTypeId(),
                command.ownerOrganizationUnitId(),
                command.environment(),
                command.criticality(),
                ExternalSystemStatus.DRAFT,
                command.description(),
                now,
                now
        );
        return IntegrationApplicationMapper.toSummary(externalSystemRepositoryPort.save(externalSystem));
    }

    @Override
    public IntegrationJobRunSummaryDto startIntegrationJobRun(StartIntegrationJobRunCommand command) {
        Objects.requireNonNull(command, "Start integration job run command must not be null.");
        Instant now = Instant.now();
        IntegrationJobRun jobRun = new IntegrationJobRun(
                IntegrationId.newId().value(),
                command.jobDefinitionId(),
                command.runNumber(),
                command.triggerType(),
                command.triggeredByActorId(),
                JobRunStatus.RUNNING,
                command.correlationId(),
                now,
                null,
                0L,
                0L,
                0L,
                0L,
                0L,
                0L,
                null,
                now,
                now
        );
        return IntegrationApplicationMapper.toSummary(jobRunRepositoryPort.save(jobRun));
    }

    @Override
    public IntegrationExchangeMessageSummaryDto recordExchangeMessage(RecordExchangeMessageCommand command) {
        Objects.requireNonNull(command, "Record exchange message command must not be null.");
        Instant now = Instant.now();
        IntegrationExchangeMessage message = new IntegrationExchangeMessage(
                IntegrationId.newId().value(),
                command.jobRunId(),
                command.externalSystemId(),
                command.endpointId(),
                command.direction(),
                command.messageTypeId(),
                command.externalMessageId(),
                command.payloadFormatId(),
                command.payloadStorageMode() == null ? PayloadStorageMode.HASH_ONLY : command.payloadStorageMode(),
                command.payloadSanitized(),
                command.payloadReference(),
                command.payloadHash(),
                command.contentLengthBytes(),
                command.receivedOrSentAt() == null ? now : command.receivedOrSentAt(),
                command.correlationId(),
                command.status(),
                now
        );
        return IntegrationApplicationMapper.toSummary(exchangeMessageRepositoryPort.save(message));
    }
}
