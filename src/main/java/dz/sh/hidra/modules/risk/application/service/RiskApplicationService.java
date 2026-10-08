/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
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

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dz.sh.hidra.modules.audit.application.contract.risk.RiskRegisterAuditContract;
import dz.sh.hidra.modules.organization.application.contract.risk.RiskOrganizationReferenceContract;
import dz.sh.hidra.modules.topology.application.contract.risk.RiskTopologyScopeReferenceContract;
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
import dz.sh.hidra.modules.risk.domain.exception.InvalidRiskValueException;
import dz.sh.hidra.modules.risk.domain.model.RiskRegister;
import dz.sh.hidra.modules.risk.domain.value.RiskAssessmentStatus;
import dz.sh.hidra.modules.risk.domain.value.RiskId;
import dz.sh.hidra.modules.risk.domain.value.RiskRegisterStatus;

import java.time.Instant;
import java.util.Objects;

/**
 * Application service for risk register and assessment workflows.
 */
@Service
public class RiskApplicationService implements CreateRiskRegisterUseCase, CreateRiskAssessmentUseCase, AddRiskEvidenceUseCase {

    private final RiskRegisterRepositoryPort registerRepositoryPort;
    private final RiskAssessmentRepositoryPort assessmentRepositoryPort;
    private final RiskEvidenceLinkRepositoryPort evidenceRepositoryPort;
    private final RiskOrganizationReferenceContract organizationReferenceContract;
    private final RiskTopologyScopeReferenceContract topologyScopeReferenceContract;
    private final RiskRegisterAuditContract auditContract;
    private final dz.sh.hidra.modules.risk.application.port.out.RiskEvidenceLookupPort evidenceLookup;

    public RiskApplicationService(RiskRegisterRepositoryPort registers,
            RiskAssessmentRepositoryPort assessments, RiskEvidenceLinkRepositoryPort evidence,
            RiskOrganizationReferenceContract organization, RiskTopologyScopeReferenceContract topology,
            RiskRegisterAuditContract audit) {
        this(registers, assessments, evidence, organization, topology, audit,
                link -> { throw new InvalidRiskValueException("Evidence owner validation is not configured."); });
    }


    @org.springframework.beans.factory.annotation.Autowired
    public RiskApplicationService(
            RiskRegisterRepositoryPort registerRepositoryPort,
            RiskAssessmentRepositoryPort assessmentRepositoryPort,
            RiskEvidenceLinkRepositoryPort evidenceRepositoryPort,
            RiskOrganizationReferenceContract organizationReferenceContract,
            RiskTopologyScopeReferenceContract topologyScopeReferenceContract,
            RiskRegisterAuditContract auditContract,
            dz.sh.hidra.modules.risk.application.port.out.RiskEvidenceLookupPort evidenceLookup
    ) {
        this.registerRepositoryPort = Objects.requireNonNull(registerRepositoryPort, "Risk register repository port must not be null.");
        this.assessmentRepositoryPort = Objects.requireNonNull(assessmentRepositoryPort, "Risk assessment repository port must not be null.");
        this.evidenceRepositoryPort = Objects.requireNonNull(evidenceRepositoryPort, "Risk evidence repository port must not be null.");
        this.organizationReferenceContract = Objects.requireNonNull(organizationReferenceContract, "Risk Organization reference contract must not be null.");
        this.topologyScopeReferenceContract = Objects.requireNonNull(topologyScopeReferenceContract, "Risk Topology scope contract must not be null.");
        this.auditContract = Objects.requireNonNull(auditContract, "Risk Audit contract must not be null.");
        this.evidenceLookup = Objects.requireNonNull(evidenceLookup);
    }

    @Override
    @Transactional
    public RiskRegisterSummaryDto createRiskRegister(CreateRiskRegisterCommand command) {
        Objects.requireNonNull(command, "Create risk register command must not be null.");

        if (!registerRepositoryPort.isRegisterType(command.registerTypeId())) {
            throw new InvalidRiskValueException(
                    "RiskRegister registerTypeId must belong to RISK_REGISTER_TYPE."
            );
        }

        if (command.ownerOrganizationUnitId() != null
                && !command.ownerOrganizationUnitId().isBlank()
                && organizationReferenceContract.resolveOrganizationUnit(
                        command.ownerOrganizationUnitId()
                ).isEmpty()) {
            throw new InvalidRiskValueException(
                    "RiskRegister ownerOrganizationUnitId must reference an existing OrganizationUnit."
            );
        }

        validateScope(command.scopeType(), command.scopeId());

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
        RiskRegister saved = registerRepositoryPort.save(register);
        auditContract.appendCreated(new RiskRegisterAuditContract.CreationEvidence(
                saved.id(),
                saved.code(),
                firstText(saved.nameFr(), saved.nameEn(), saved.nameAr()),
                saved.createdByActorId(),
                saved.createdByDisplayNameSnapshot(),
                saved.ownerOrganizationUnitId(),
                null,
                now
        ));
        return RiskApplicationMapper.toSummary(saved);
    }

    private void validateScope(String scopeType, String scopeId) {
        if (scopeType == null || scopeType.isBlank() || scopeId == null || scopeId.isBlank()) {
            throw new InvalidRiskValueException(
                    "RiskRegister scopeType and scopeId must form a complete nonblank pair."
            );
        }

        String normalizedType = scopeType.trim().toUpperCase(java.util.Locale.ROOT);
        boolean resolved = switch (normalizedType) {
            case "ORGANIZATION_UNIT" ->
                    organizationReferenceContract.resolveOrganizationUnit(scopeId).isPresent();
            case "PIPELINE_SYSTEM", "PIPELINE", "FACILITY", "EQUIPMENT" ->
                    topologyScopeReferenceContract.resolve(normalizedType, scopeId).isPresent();
            default -> false;
        };

        if (!resolved) {
            throw new InvalidRiskValueException(
                    "RiskRegister scope must resolve through its registered owner boundary."
            );
        }
    }

    private static String firstText(String... values) {
        for (String value : values) {
            if (value != null && !value.isBlank()) {
                return value.trim();
            }
        }
        return null;
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
        return evidenceRepositoryPort.save(evidenceLookup.validate(evidence)).id();
    }
}
