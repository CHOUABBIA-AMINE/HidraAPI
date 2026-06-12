/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityApplicationService
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.application.service
 *
 * @Description : Application service for integrity program, assessment, and case workflows.
 *
 */
package dz.sh.hidra.modules.integrity.application.service;

import dz.sh.hidra.modules.integrity.application.command.CreateIntegrityAssessmentCommand;
import dz.sh.hidra.modules.integrity.application.command.CreateIntegrityProgramCommand;
import dz.sh.hidra.modules.integrity.application.command.OpenIntegrityCaseCommand;
import dz.sh.hidra.modules.integrity.application.dto.IntegrityAssessmentSummaryDto;
import dz.sh.hidra.modules.integrity.application.dto.IntegrityCaseSummaryDto;
import dz.sh.hidra.modules.integrity.application.dto.IntegrityProgramSummaryDto;
import dz.sh.hidra.modules.integrity.application.mapper.IntegrityApplicationMapper;
import dz.sh.hidra.modules.integrity.application.port.in.CreateIntegrityAssessmentUseCase;
import dz.sh.hidra.modules.integrity.application.port.in.CreateIntegrityProgramUseCase;
import dz.sh.hidra.modules.integrity.application.port.in.OpenIntegrityCaseUseCase;
import dz.sh.hidra.modules.integrity.application.port.out.IntegrityAssessmentRepositoryPort;
import dz.sh.hidra.modules.integrity.application.port.out.IntegrityCaseRepositoryPort;
import dz.sh.hidra.modules.integrity.application.port.out.IntegrityProgramRepositoryPort;
import dz.sh.hidra.modules.integrity.domain.model.IntegrityAssessment;
import dz.sh.hidra.modules.integrity.domain.model.IntegrityCase;
import dz.sh.hidra.modules.integrity.domain.model.IntegrityProgram;
import dz.sh.hidra.modules.integrity.domain.value.IntegrityAssessmentStatus;
import dz.sh.hidra.modules.integrity.domain.value.IntegrityCaseStatus;
import dz.sh.hidra.modules.integrity.domain.value.IntegrityId;
import dz.sh.hidra.modules.integrity.domain.value.IntegrityProgramStatus;

import java.time.Instant;
import java.util.Objects;

/**
 * Application service for integrity program, assessment, and case workflows.
 */
public class IntegrityApplicationService implements CreateIntegrityProgramUseCase, CreateIntegrityAssessmentUseCase, OpenIntegrityCaseUseCase {

    private final IntegrityProgramRepositoryPort programRepositoryPort;
    private final IntegrityAssessmentRepositoryPort assessmentRepositoryPort;
    private final IntegrityCaseRepositoryPort caseRepositoryPort;

    public IntegrityApplicationService(
            IntegrityProgramRepositoryPort programRepositoryPort,
            IntegrityAssessmentRepositoryPort assessmentRepositoryPort,
            IntegrityCaseRepositoryPort caseRepositoryPort
    ) {
        this.programRepositoryPort = Objects.requireNonNull(programRepositoryPort, "Integrity program repository port must not be null.");
        this.assessmentRepositoryPort = Objects.requireNonNull(assessmentRepositoryPort, "Integrity assessment repository port must not be null.");
        this.caseRepositoryPort = Objects.requireNonNull(caseRepositoryPort, "Integrity case repository port must not be null.");
    }

    @Override
    public IntegrityProgramSummaryDto createIntegrityProgram(CreateIntegrityProgramCommand command) {
        Objects.requireNonNull(command, "Create integrity program command must not be null.");
        Instant now = Instant.now();
        IntegrityProgram program = new IntegrityProgram(
                IntegrityId.newId().value(),
                command.code(),
                command.nameAr(),
                command.nameFr(),
                command.nameEn(),
                command.description(),
                command.programTypeId(),
                command.ownerOrganizationUnitId(),
                command.ownerOrganizationUnitNameSnapshot(),
                IntegrityProgramStatus.DRAFT,
                command.plannedStartAt(),
                command.plannedEndAt(),
                null,
                null,
                command.createdByActorId(),
                now,
                now
        );
        return IntegrityApplicationMapper.toSummary(programRepositoryPort.save(program));
    }

    @Override
    public IntegrityAssessmentSummaryDto createIntegrityAssessment(CreateIntegrityAssessmentCommand command) {
        Objects.requireNonNull(command, "Create integrity assessment command must not be null.");
        Instant now = Instant.now();
        IntegrityAssessment assessment = new IntegrityAssessment(
                IntegrityId.newId().value(),
                command.programId(),
                command.assessmentNumber(),
                command.title(),
                command.description(),
                command.assessmentTypeId(),
                command.methodologyId(),
                IntegrityAssessmentStatus.DRAFT,
                command.assessmentDate() == null ? now : command.assessmentDate(),
                command.assessedByActorId(),
                null,
                null,
                null,
                command.workflowInstanceId(),
                null,
                now,
                now
        );
        return IntegrityApplicationMapper.toSummary(assessmentRepositoryPort.save(assessment));
    }

    @Override
    public IntegrityCaseSummaryDto openIntegrityCase(OpenIntegrityCaseCommand command) {
        Objects.requireNonNull(command, "Open integrity case command must not be null.");
        Instant now = Instant.now();
        IntegrityCase integrityCase = new IntegrityCase(
                IntegrityId.newId().value(),
                command.caseNumber(),
                command.title(),
                command.description(),
                command.caseTypeId(),
                IntegrityCaseStatus.OPEN,
                command.severityId(),
                command.topologyAssetTypeCode(),
                command.topologyAssetId(),
                command.topologyAssetCodeSnapshot(),
                command.primaryDefectId(),
                command.sourceIncidentId(),
                command.sourceHseCaseId(),
                command.responsibleOrganizationUnitId(),
                command.workflowInstanceId(),
                now,
                null,
                command.openedByActorId(),
                now,
                now
        );
        return IntegrityApplicationMapper.toSummary(caseRepositoryPort.save(integrityCase));
    }
}
