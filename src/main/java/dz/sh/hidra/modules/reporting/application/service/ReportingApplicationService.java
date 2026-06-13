/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.application.service
 *
 * @Description : Application service for report definitions, requests, runs, and artifacts.
 *
 */
package dz.sh.hidra.modules.reporting.application.service;

import org.springframework.stereotype.Service;

import dz.sh.hidra.modules.reporting.application.command.CreateReportDefinitionCommand;
import dz.sh.hidra.modules.reporting.application.command.GenerateReportArtifactCommand;
import dz.sh.hidra.modules.reporting.application.command.QueueReportRunCommand;
import dz.sh.hidra.modules.reporting.application.command.RequestReportCommand;
import dz.sh.hidra.modules.reporting.application.dto.ReportDefinitionSummaryDto;
import dz.sh.hidra.modules.reporting.application.dto.ReportOutputArtifactSummaryDto;
import dz.sh.hidra.modules.reporting.application.dto.ReportRequestSummaryDto;
import dz.sh.hidra.modules.reporting.application.dto.ReportRunSummaryDto;
import dz.sh.hidra.modules.reporting.application.mapper.ReportingApplicationMapper;
import dz.sh.hidra.modules.reporting.application.port.in.CreateReportDefinitionUseCase;
import dz.sh.hidra.modules.reporting.application.port.in.GenerateReportArtifactUseCase;
import dz.sh.hidra.modules.reporting.application.port.in.QueueReportRunUseCase;
import dz.sh.hidra.modules.reporting.application.port.in.RequestReportUseCase;
import dz.sh.hidra.modules.reporting.application.port.out.ReportDefinitionRepositoryPort;
import dz.sh.hidra.modules.reporting.application.port.out.ReportOutputArtifactRepositoryPort;
import dz.sh.hidra.modules.reporting.application.port.out.ReportRequestRepositoryPort;
import dz.sh.hidra.modules.reporting.application.port.out.ReportRunRepositoryPort;
import dz.sh.hidra.modules.reporting.domain.model.ReportDefinition;
import dz.sh.hidra.modules.reporting.domain.model.ReportOutputArtifact;
import dz.sh.hidra.modules.reporting.domain.model.ReportRequest;
import dz.sh.hidra.modules.reporting.domain.model.ReportRun;
import dz.sh.hidra.modules.reporting.domain.service.ReportReproducibilityGuard;
import dz.sh.hidra.modules.reporting.domain.value.ReportRequestStatus;
import dz.sh.hidra.modules.reporting.domain.value.ReportRunStatus;
import dz.sh.hidra.modules.reporting.domain.value.ReportingId;

import java.time.Instant;
import java.util.Objects;

/**
 * Application service for report definitions, requests, runs, and artifacts.
 */
@Service
public final class ReportingApplicationService implements CreateReportDefinitionUseCase, RequestReportUseCase, QueueReportRunUseCase, GenerateReportArtifactUseCase {

    private final ReportDefinitionRepositoryPort definitionRepositoryPort;
    private final ReportRequestRepositoryPort requestRepositoryPort;
    private final ReportRunRepositoryPort runRepositoryPort;
    private final ReportOutputArtifactRepositoryPort artifactRepositoryPort;
    private final ReportReproducibilityGuard reproducibilityGuard = new ReportReproducibilityGuard();

    public ReportingApplicationService(
            ReportDefinitionRepositoryPort definitionRepositoryPort,
            ReportRequestRepositoryPort requestRepositoryPort,
            ReportRunRepositoryPort runRepositoryPort,
            ReportOutputArtifactRepositoryPort artifactRepositoryPort
    ) {
        this.definitionRepositoryPort = Objects.requireNonNull(definitionRepositoryPort, "Report definition repository port must not be null.");
        this.requestRepositoryPort = Objects.requireNonNull(requestRepositoryPort, "Report request repository port must not be null.");
        this.runRepositoryPort = Objects.requireNonNull(runRepositoryPort, "Report run repository port must not be null.");
        this.artifactRepositoryPort = Objects.requireNonNull(artifactRepositoryPort, "Report output artifact repository port must not be null.");
    }

    @Override
    public ReportDefinitionSummaryDto createReportDefinition(CreateReportDefinitionCommand command) {
        Objects.requireNonNull(command, "Create report definition command must not be null.");
        reproducibilityGuard.ensureNoSecretMaterial(command.description());
        Instant now = Instant.now();
        ReportDefinition definition = new ReportDefinition(
                ReportingId.newId().value(),
                command.code(),
                command.nameAr(),
                command.nameFr(),
                command.nameEn(),
                command.reportCategoryId(),
                command.ownerModule(),
                command.description(),
                true,
                null,
                command.requiresApproval(),
                command.restricted(),
                now,
                now
        );
        return ReportingApplicationMapper.toSummary(definitionRepositoryPort.save(definition));
    }

    @Override
    public ReportRequestSummaryDto requestReport(RequestReportCommand command) {
        Objects.requireNonNull(command, "Request report command must not be null.");
        Instant now = Instant.now();
        ReportRequest request = new ReportRequest(
                ReportingId.newId().value(),
                command.reportDefinitionId(),
                command.requestedByActorId(),
                command.requestedByUsernameSnapshot(),
                command.requestedByDisplayNameSnapshot(),
                command.requestedByRoleCodeSnapshot(),
                command.organizationUnitId(),
                command.organizationUnitNameSnapshot(),
                now,
                command.purpose(),
                ReportRequestStatus.SUBMITTED,
                command.correlationId(),
                command.workflowReferenceId(),
                now,
                now
        );
        return ReportingApplicationMapper.toSummary(requestRepositoryPort.save(request));
    }

    @Override
    public ReportRunSummaryDto queueReportRun(QueueReportRunCommand command) {
        Objects.requireNonNull(command, "Queue report run command must not be null.");
        Instant now = Instant.now();
        ReportRun run = new ReportRun(
                ReportingId.newId().value(),
                command.reportRequestId(),
                command.reportDefinitionId(),
                command.templateVersionId(),
                ReportRunStatus.QUEUED,
                command.runMode(),
                now,
                null,
                null,
                null,
                null,
                0L,
                0L,
                null,
                command.correlationId(),
                now,
                now
        );
        return ReportingApplicationMapper.toSummary(runRepositoryPort.save(run));
    }

    @Override
    public ReportOutputArtifactSummaryDto generateReportArtifact(GenerateReportArtifactCommand command) {
        Objects.requireNonNull(command, "Generate report artifact command must not be null.");
        ReportOutputArtifact artifact = new ReportOutputArtifact(
                ReportingId.newId().value(),
                command.reportRunId(),
                command.artifactType(),
                command.format(),
                command.fileName(),
                command.mimeType(),
                command.storageObjectReferenceId(),
                command.documentReferenceId(),
                command.checksum(),
                command.sizeBytes(),
                Instant.now(),
                command.expiresAt(),
                Instant.now()
        );
        return ReportingApplicationMapper.toSummary(artifactRepositoryPort.save(artifact));
    }
}
