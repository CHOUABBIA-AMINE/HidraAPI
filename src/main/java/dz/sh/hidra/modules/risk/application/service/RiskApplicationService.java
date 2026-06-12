/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskApplicationService
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.application.service
 *
 * @Description : Application service for risk register and assessment workflows.
 *
 */
package dz.sh.hidra.modules.risk.application.service;

import dz.sh.hidra.modules.risk.application.command.AddRiskEvidenceCommand;
import dz.sh.hidra.modules.risk.application.command.CreateRiskAssessmentCommand;
import dz.sh.hidra.modules.risk.application.command.CreateRiskRegisterCommand;
import dz.sh.hidra.modules.risk.application.dto.RiskAssessmentSummaryDto;
import dz.sh.hidra.modules.risk.application.dto.RiskRegisterSummaryDto;
import dz.sh.hidra.modules.risk.application.mapper.RiskApplicationMapper;
import dz.sh.hidra.modules.risk.application.port.in.AddRiskEvidenceUseCase;
import dz.sh.hidra.modules.risk.application.port.in.CreateRiskAssessmentUseCase;
import dz.sh.hidra.modules.risk.application.port.in.CreateRiskRegisterUseCase;
import dz.sh.hidra.modules.risk.application.port.out.RiskAssessmentRepositoryPort;
import dz.sh.hidra.modules.risk.application.port.out.RiskEvidenceLinkRepositoryPort;
import dz.sh.hidra.modules.risk.application.port.out.RiskRegisterRepositoryPort;
import dz.sh.hidra.modules.risk.domain.model.RiskAssessment;
import dz.sh.hidra.modules.risk.domain.model.RiskEvidenceLink;
import dz.sh.hidra.modules.risk.domain.model.RiskRegister;
import dz.sh.hidra.modules.risk.domain.value.RiskAssessmentStatus;
import dz.sh.hidra.modules.risk.domain.value.RiskId;
import dz.sh.hidra.modules.risk.domain.value.RiskRegisterStatus;

import java.time.Instant;
import java.util.Objects;

/**
 * Application service for risk register and assessment workflows.
 */
public class RiskApplicationService implements CreateRiskRegisterUseCase, CreateRiskAssessmentUseCase, AddRiskEvidenceUseCase {

    private final RiskRegisterRepositoryPort registerRepositoryPort;
    private final RiskAssessmentRepositoryPort assessmentRepositoryPort;
    private final RiskEvidenceLinkRepositoryPort evidenceRepositoryPort;

    public RiskApplicationService(
            RiskRegisterRepositoryPort registerRepositoryPort,
            RiskAssessmentRepositoryPort assessmentRepositoryPort,
            RiskEvidenceLinkRepositoryPort evidenceRepositoryPort
    ) {
        this.registerRepositoryPort = Objects.requireNonNull(registerRepositoryPort, "Risk register repository port must not be null.");
        this.assessmentRepositoryPort = Objects.requireNonNull(assessmentRepositoryPort, "Risk assessment repository port must not be null.");
        this.evidenceRepositoryPort = Objects.requireNonNull(evidenceRepositoryPort, "Risk evidence repository port must not be null.");
    }

    @Override
    public RiskRegisterSummaryDto createRiskRegister(CreateRiskRegisterCommand command) {
        Objects.requireNonNull(command, "Create risk register command must not be null.");
        Instant now = Instant.now();
        RiskRegister register = new RiskRegister(
                RiskId.newId().value(),
                command.code(),
                command.nameAr(),
                command.nameFr(),
                command.nameEn(),
                command.description(),
                command.registerTypeId(),
                command.ownerOrganizationUnitId(),
                command.ownerOrganizationUnitNameSnapshot(),
                command.scopeType(),
                command.scopeId(),
                command.scopeCodeSnapshot(),
                command.scopeLabelSnapshot(),
                RiskRegisterStatus.DRAFT,
                command.reviewFrequencyId(),
                command.effectiveFrom(),
                command.effectiveTo(),
                command.createdByActorId(),
                command.createdByDisplayNameSnapshot(),
                now,
                now
        );
        return RiskApplicationMapper.toSummary(registerRepositoryPort.save(register));
    }

    @Override
    public RiskAssessmentSummaryDto createRiskAssessment(CreateRiskAssessmentCommand command) {
        Objects.requireNonNull(command, "Create risk assessment command must not be null.");
        Instant now = Instant.now();
        RiskAssessment assessment = new RiskAssessment(
                RiskId.newId().value(),
                command.riskRegisterId(),
                command.assessmentNumber(),
                command.title(),
                command.description(),
                command.assessmentTypeId(),
                command.methodologyId(),
                command.scopeId(),
                command.riskScenarioId(),
                RiskAssessmentStatus.DRAFT,
                command.assessmentDate() == null ? now : command.assessmentDate(),
                command.validFrom(),
                command.validTo(),
                command.assessedByActorId(),
                command.assessedByDisplayNameSnapshot(),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                now,
                now
        );
        return RiskApplicationMapper.toSummary(assessmentRepositoryPort.save(assessment));
    }

    @Override
    public String addRiskEvidence(AddRiskEvidenceCommand command) {
        Objects.requireNonNull(command, "Add risk evidence command must not be null.");
        RiskEvidenceLink evidence = new RiskEvidenceLink(
                RiskId.newId().value(),
                command.riskAssessmentId(),
                command.evidenceModule(),
                command.evidenceType(),
                command.evidenceId(),
                command.evidenceCodeSnapshot(),
                command.evidenceLabelSnapshot(),
                command.evidenceTimestamp(),
                command.evidenceHash(),
                command.evidenceSummary(),
                Instant.now()
        );
        return evidenceRepositoryPort.save(evidence).id();
    }
}
