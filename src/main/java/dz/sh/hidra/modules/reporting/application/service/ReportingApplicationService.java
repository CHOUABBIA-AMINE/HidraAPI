/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
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

import org.springframework.beans.factory.annotation.Autowired;
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
import dz.sh.hidra.modules.reporting.domain.exception.InvalidReportingValueException;
import dz.sh.hidra.modules.reporting.domain.value.ReportDefinitionStatus;
import dz.sh.hidra.modules.identity.application.contract.reporting.ReportingAccessAuthorizationContract;
import dz.sh.hidra.modules.organization.application.contract.reporting.ReportingOrganizationUnitReferenceContract;
import dz.sh.hidra.modules.workflow.application.contract.reporting.ReportingWorkflowApprovalContract;
import dz.sh.hidra.modules.reporting.domain.service.ReportReproducibilityGuard;
import dz.sh.hidra.modules.reporting.domain.value.ReportRequestStatus;
import dz.sh.hidra.modules.reporting.domain.value.ReportRunStatus;
import dz.sh.hidra.modules.reporting.domain.value.ReportingId;

import java.time.Instant;
import dz.sh.hidra.modules.reporting.application.port.out.ReportQueueEvidencePort;
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
    private final ReportingAccessAuthorizationContract accessAuthorizationContract;
    private final ReportingWorkflowApprovalContract workflowApprovalContract;
    private final ReportingOrganizationUnitReferenceContract organizationUnitReferenceContract;
    private final ReportQueueEvidencePort queueEvidence;
    private final ReportReproducibilityGuard reproducibilityGuard = new ReportReproducibilityGuard();

    @Autowired
    public ReportingApplicationService(
            ReportDefinitionRepositoryPort definitionRepositoryPort,
            ReportRequestRepositoryPort requestRepositoryPort,
            ReportRunRepositoryPort runRepositoryPort,
            ReportOutputArtifactRepositoryPort artifactRepositoryPort,
            ReportingAccessAuthorizationContract accessAuthorizationContract,
            ReportingWorkflowApprovalContract workflowApprovalContract,
            ReportingOrganizationUnitReferenceContract organizationUnitReferenceContract,
            ReportQueueEvidencePort queueEvidence
    ) {
        this.queueEvidence = Objects.requireNonNull(queueEvidence);
        this.definitionRepositoryPort = Objects.requireNonNull(definitionRepositoryPort, "Report definition repository port must not be null.");
        this.requestRepositoryPort = Objects.requireNonNull(requestRepositoryPort, "Report request repository port must not be null.");
        this.runRepositoryPort = Objects.requireNonNull(runRepositoryPort, "Report run repository port must not be null.");
        this.artifactRepositoryPort = Objects.requireNonNull(artifactRepositoryPort, "Report output artifact repository port must not be null.");
        this.accessAuthorizationContract = Objects.requireNonNull(accessAuthorizationContract, "Reporting access authorization contract must not be null.");
        this.workflowApprovalContract = Objects.requireNonNull(workflowApprovalContract, "Reporting workflow approval contract must not be null.");
        this.organizationUnitReferenceContract = Objects.requireNonNull(organizationUnitReferenceContract, "Reporting OrganizationUnit contract must not be null.");
    }

    public ReportingApplicationService(ReportDefinitionRepositoryPort definitions,ReportRequestRepositoryPort requests,
            ReportRunRepositoryPort runs,ReportOutputArtifactRepositoryPort artifacts,
            ReportingAccessAuthorizationContract access,ReportingWorkflowApprovalContract approval,
            ReportingOrganizationUnitReferenceContract organization){
        this(definitions,requests,runs,artifacts,access,approval,organization,new ReportQueueEvidencePort(){
            public boolean eligibleTemplate(String version,String definition){return false;}
            public boolean requiredParametersPresent(String request,String definition){return false;}
        });
    }

    public ReportingApplicationService(
            ReportDefinitionRepositoryPort definitionRepositoryPort,
            ReportRequestRepositoryPort requestRepositoryPort,
            ReportRunRepositoryPort runRepositoryPort,
            ReportOutputArtifactRepositoryPort artifactRepositoryPort
    ) {
        this(
                definitionRepositoryPort,
                requestRepositoryPort,
                runRepositoryPort,
                artifactRepositoryPort,
                request -> false,
                (workflowReferenceId, reportRequestId) -> false,
                organizationUnitId -> false
        );
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
        ReportDefinition definition = definitionRepositoryPort.findById(command.reportDefinitionId())
                .orElseThrow(() -> new InvalidReportingValueException(
                        "ReportRequest report definition must exist."
                ));
        if (definition.status() != ReportDefinitionStatus.ACTIVE) {
            throw new InvalidReportingValueException(
                    "ReportRequest requires an ACTIVE ReportDefinition."
            );
        }
        if (command.organizationUnitId() != null
                && !command.organizationUnitId().isBlank()
                && !organizationUnitReferenceContract.exists(command.organizationUnitId())) {
            throw new InvalidReportingValueException(
                    "ReportRequest organizationUnitId must reference an existing OrganizationUnit."
            );
        }
        if (definition.restricted() && !hasRestrictedAccess(command, definition)) {
            throw new InvalidReportingValueException(
                    "Restricted ReportDefinition access was not authorized."
            );
        }
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
        ReportRequest request = requestRepositoryPort.findById(command.reportRequestId())
                .orElseThrow(() -> new InvalidReportingValueException(
                        "ReportRun report request must exist."
                ));
        ReportDefinition definition = definitionRepositoryPort.findById(command.reportDefinitionId())
                .orElseThrow(() -> new InvalidReportingValueException(
                        "ReportRun report definition must exist."
                ));
        if (!request.reportDefinitionId().equals(definition.id())) {
            throw new InvalidReportingValueException(
                    "ReportRun request and definition must reference the same report definition."
            );
        }
        if(definition.status()!=ReportDefinitionStatus.ACTIVE
                || (request.status()!=ReportRequestStatus.SUBMITTED && request.status()!=ReportRequestStatus.APPROVED))
            throw new InvalidReportingValueException("New queue requires an ACTIVE definition and a queueable request.");
        if(definition.restricted() && !hasRestrictedAccess(new RequestReportCommand(
                request.reportDefinitionId(),request.requestedByActorId(),request.requestedByUsernameSnapshot(),
                request.requestedByDisplayNameSnapshot(),request.requestedByRoleCodeSnapshot(),request.organizationUnitId(),
                request.organizationUnitNameSnapshot(),request.purpose(),request.correlationId(),request.workflowReferenceId()),definition))
            throw new InvalidReportingValueException("Restricted queue access was not authorized.");
        if(!queueEvidence.eligibleTemplate(command.templateVersionId(),definition.id()))
            throw new InvalidReportingValueException("Queue template must be active and belong to the selected definition.");
        if(!queueEvidence.requiredParametersPresent(request.id(),definition.id()))
            throw new InvalidReportingValueException("Required concrete request parameters are missing or invalid.");
        if (definition.requiresApproval()) {
            if (request.status() != ReportRequestStatus.APPROVED
                    || request.workflowReferenceId() == null
                    || request.workflowReferenceId().isBlank()
                    || !workflowApprovalContract.approved(
                            request.workflowReferenceId(),
                            request.id()
                    )) {
                throw new InvalidReportingValueException(
                        "Approval-required report requests must be workflow-approved before queueing."
                );
            }
        }
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

    private boolean hasRestrictedAccess(
            RequestReportCommand command,
            ReportDefinition definition
    ) {
        return requestRepositoryPort.accessPoliciesForDefinition(definition.id()).stream()
                .filter(policy -> policyMatchesRequest(policy, command, definition))
                .anyMatch(policy -> accessAuthorizationContract.permitted(
                        new ReportingAccessAuthorizationContract.AccessRequest(
                                command.requestedByActorId(),
                                policy.permissionCode(),
                                definition.id(),
                                policy.scopeType(),
                                policy.scopeReferenceId()
                        )
                ));
    }

    private static boolean policyMatchesRequest(
            ReportRequestRepositoryPort.AccessPolicyView policy,
            RequestReportCommand command,
            ReportDefinition definition
    ) {
        return switch (policy.scopeType()) {
            case "GLOBAL" -> true;
            case "ORGANIZATION_UNIT" -> Objects.equals(
                    policy.scopeReferenceId(),
                    normalize(command.organizationUnitId())
            );
            case "ROLE" -> Objects.equals(
                    policy.scopeReferenceId(),
                    normalize(command.requestedByRoleCodeSnapshot())
            );
            case "ACTOR" -> Objects.equals(
                    policy.scopeReferenceId(),
                    normalize(command.requestedByActorId())
            );
            case "MODULE_SCOPE" -> Objects.equals(
                    policy.scopeReferenceId(),
                    normalize(definition.ownerModule())
            );
            default -> false;
        };
    }

    private static String normalize(String value) {
        return value == null || value.isBlank() ? null : value.trim();
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
