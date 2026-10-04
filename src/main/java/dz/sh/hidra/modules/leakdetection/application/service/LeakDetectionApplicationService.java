/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakDetectionApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.application.service
 *
 * @Description : Application service for leak detection candidate/case workflows.
 *
 */
package dz.sh.hidra.modules.leakdetection.application.service;

import dz.sh.hidra.modules.leakdetection.application.command.CreateLeakCandidateCommand;
import dz.sh.hidra.modules.leakdetection.application.command.EscalateLeakCaseCommand;
import dz.sh.hidra.modules.leakdetection.application.command.OpenLeakCaseCommand;
import dz.sh.hidra.modules.leakdetection.application.dto.LeakCandidateSummaryDto;
import dz.sh.hidra.modules.leakdetection.application.dto.LeakCaseSummaryDto;
import dz.sh.hidra.modules.leakdetection.application.mapper.LeakDetectionApplicationMapper;
import dz.sh.hidra.modules.leakdetection.application.port.in.CreateLeakCandidateUseCase;
import dz.sh.hidra.modules.leakdetection.application.port.in.EscalateLeakCaseUseCase;
import dz.sh.hidra.modules.leakdetection.application.port.in.OpenLeakCaseUseCase;
import dz.sh.hidra.modules.leakdetection.application.port.out.LeakCandidateRepositoryPort;
import dz.sh.hidra.modules.leakdetection.application.port.out.LeakDetectionCaseRepositoryPort;
import dz.sh.hidra.modules.leakdetection.application.port.out.LeakEscalationReferenceRepositoryPort;
import dz.sh.hidra.modules.leakdetection.domain.exception.InvalidLeakDetectionValueException;
import dz.sh.hidra.modules.leakdetection.domain.model.LeakCandidate;
import dz.sh.hidra.modules.leakdetection.domain.model.LeakDetectionCase;
import dz.sh.hidra.modules.leakdetection.domain.model.LeakEscalationReference;
import dz.sh.hidra.modules.leakdetection.domain.service.LeakConfidenceClassifier;
import dz.sh.hidra.modules.leakdetection.domain.value.LeakCandidateStatus;
import dz.sh.hidra.modules.leakdetection.domain.value.LeakDetectionCaseStatus;
import dz.sh.hidra.modules.leakdetection.domain.value.LeakDetectionId;
import dz.sh.hidra.modules.leakdetection.domain.value.LeakEscalationStatus;
import dz.sh.hidra.modules.leakdetection.domain.value.LeakSeverityLevel;
import dz.sh.hidra.modules.topology.application.contract.leakdetection.LeakDetectionTopologyAssetContract;
import java.time.Instant;
import java.util.Locale;
import java.util.Objects;
import org.springframework.stereotype.Service;

/**
 * Application service for leak detection candidate/case workflows.
 */
@Service
public final class LeakDetectionApplicationService
        implements CreateLeakCandidateUseCase, OpenLeakCaseUseCase, EscalateLeakCaseUseCase {

    private final LeakCandidateRepositoryPort candidateRepositoryPort;
    private final LeakDetectionCaseRepositoryPort caseRepositoryPort;
    private final LeakEscalationReferenceRepositoryPort escalationRepositoryPort;
    private final LeakConfidenceClassifier confidenceClassifier;
    private final LeakDetectionTopologyAssetContract topologyAssetContract;

    public LeakDetectionApplicationService(
            LeakCandidateRepositoryPort candidateRepositoryPort,
            LeakDetectionCaseRepositoryPort caseRepositoryPort,
            LeakEscalationReferenceRepositoryPort escalationRepositoryPort,
            LeakConfidenceClassifier confidenceClassifier,
            LeakDetectionTopologyAssetContract topologyAssetContract
    ) {
        this.candidateRepositoryPort = Objects.requireNonNull(
                candidateRepositoryPort,
                "Leak candidate repository port must not be null."
        );
        this.caseRepositoryPort = Objects.requireNonNull(
                caseRepositoryPort,
                "Leak detection case repository port must not be null."
        );
        this.escalationRepositoryPort = Objects.requireNonNull(
                escalationRepositoryPort,
                "Leak escalation repository port must not be null."
        );
        this.confidenceClassifier = Objects.requireNonNull(
                confidenceClassifier,
                "Leak confidence classifier must not be null."
        );
        this.topologyAssetContract = Objects.requireNonNull(
                topologyAssetContract,
                "Leak Detection Topology asset contract must not be null."
        );
    }

    @Override
    public LeakCandidateSummaryDto createLeakCandidate(CreateLeakCandidateCommand command) {
        Objects.requireNonNull(command, "Create leak candidate command must not be null.");

        String topologyAssetType = requireTopologyAssetType(command.topologyAssetType());
        var resolution = topologyAssetContract.resolve(topologyAssetType, command.topologyAssetId());
        if (!resolution.supported()) {
            throw new InvalidLeakDetectionValueException(
                    "Unsupported LeakCandidate topology asset type: " + topologyAssetType + "."
            );
        }
        if (!resolution.exists()) {
            throw new InvalidLeakDetectionValueException(
                    "LeakCandidate topology asset does not exist: "
                            + topologyAssetType + "/" + command.topologyAssetId() + "."
            );
        }
        if (command.topologyAssetCode() != null
                && !command.topologyAssetCode().isBlank()
                && !resolution.code().equals(command.topologyAssetCode().trim())) {
            throw new InvalidLeakDetectionValueException(
                    "LeakCandidate topology asset code does not match the Topology owner."
            );
        }

        Instant now = Instant.now();
        LeakSeverityLevel severity = confidenceClassifier.classify(command.confidenceScore());
        LeakCandidate candidate = new LeakCandidate(
                LeakDetectionId.newId().value(),
                command.runId(),
                command.profileId(),
                command.candidateNumber(),
                topologyAssetType,
                resolution.id(),
                resolution.code(),
                resolution.name(),
                command.suspectedAt() == null ? now : command.suspectedAt(),
                command.firstEvidenceAt(),
                command.confidenceScore(),
                severity,
                LeakCandidateStatus.NEW,
                command.summary(),
                command.correlationId(),
                now,
                now
        );
        return LeakDetectionApplicationMapper.toSummary(candidateRepositoryPort.save(candidate));
    }

    @Override
    public LeakCaseSummaryDto openLeakCase(OpenLeakCaseCommand command) {
        Objects.requireNonNull(command, "Open leak case command must not be null.");
        Instant now = Instant.now();
        LeakDetectionCase leakCase = new LeakDetectionCase(
                LeakDetectionId.newId().value(),
                command.caseNumber(),
                command.primaryCandidateId(),
                command.topologyAssetType(),
                command.topologyAssetId(),
                command.topologyAssetCode(),
                command.owningOrganizationUnitId(),
                LeakDetectionCaseStatus.OPEN,
                confidenceClassifier.classify(command.confidenceScore()),
                command.confidenceScore(),
                now,
                null,
                command.openedByActorId(),
                null,
                null,
                command.correlationId(),
                now,
                now
        );
        return LeakDetectionApplicationMapper.toSummary(caseRepositoryPort.save(leakCase));
    }

    @Override
    public String escalateLeakCase(EscalateLeakCaseCommand command) {
        Objects.requireNonNull(command, "Escalate leak case command must not be null.");
        LeakEscalationReference escalation = new LeakEscalationReference(
                LeakDetectionId.newId().value(),
                command.caseId(),
                command.candidateId(),
                command.targetType(),
                command.targetReferenceId(),
                command.targetCodeSnapshot(),
                command.targetNameSnapshot(),
                LeakEscalationStatus.REQUESTED,
                command.escalatedByActorId(),
                Instant.now(),
                command.reasonText(),
                command.correlationId()
        );
        return escalationRepositoryPort.save(escalation).id();
    }

    private static String requireTopologyAssetType(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidLeakDetectionValueException(
                    "LeakCandidate topology asset type must not be blank."
            );
        }
        return value.trim().toUpperCase(Locale.ROOT);
    }
}
