/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.api.rest.mapper
 *
 * @Description : Maps integrity REST models to application models.
 *
 */
package dz.sh.hidra.modules.integrity.api.rest.mapper;

import dz.sh.hidra.modules.integrity.api.rest.request.CreateIntegrityAssessmentRequest;
import dz.sh.hidra.modules.integrity.api.rest.request.CreateIntegrityProgramRequest;
import dz.sh.hidra.modules.integrity.api.rest.request.OpenIntegrityCaseRequest;
import dz.sh.hidra.modules.integrity.api.rest.response.IntegrityAssessmentResponse;
import dz.sh.hidra.modules.integrity.api.rest.response.IntegrityCaseResponse;
import dz.sh.hidra.modules.integrity.api.rest.response.IntegrityProgramResponse;
import dz.sh.hidra.modules.integrity.application.command.CreateIntegrityAssessmentCommand;
import dz.sh.hidra.modules.integrity.application.command.CreateIntegrityProgramCommand;
import dz.sh.hidra.modules.integrity.application.command.OpenIntegrityCaseCommand;
import dz.sh.hidra.modules.integrity.application.dto.IntegrityAssessmentSummaryDto;
import dz.sh.hidra.modules.integrity.application.dto.IntegrityCaseSummaryDto;
import dz.sh.hidra.modules.integrity.application.dto.IntegrityProgramSummaryDto;

/**
 * Maps integrity REST models to application models.
 */
public final class IntegrityRestMapper {

    private IntegrityRestMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static CreateIntegrityProgramCommand toCommand(CreateIntegrityProgramRequest request) {
        return new CreateIntegrityProgramCommand(request.code(), request.nameAr(), request.nameFr(), request.nameEn(), request.description(), request.programTypeId(), request.ownerOrganizationUnitId(), request.ownerOrganizationUnitNameSnapshot(), request.plannedStartAt(), request.plannedEndAt(), request.createdByActorId());
    }

    public static CreateIntegrityAssessmentCommand toCommand(CreateIntegrityAssessmentRequest request) {
        return new CreateIntegrityAssessmentCommand(request.programId(), request.assessmentNumber(), request.title(), request.description(), request.assessmentTypeId(), request.methodologyId(), request.assessmentDate(), request.assessedByActorId(), request.workflowInstanceId());
    }

    public static OpenIntegrityCaseCommand toCommand(OpenIntegrityCaseRequest request) {
        return new OpenIntegrityCaseCommand(request.caseNumber(), request.title(), request.description(), request.caseTypeId(), request.severityId(), request.topologyAssetTypeCode(), request.topologyAssetId(), request.topologyAssetCodeSnapshot(), request.primaryDefectId(), request.sourceIncidentId(), request.sourceHseCaseId(), request.responsibleOrganizationUnitId(), request.workflowInstanceId(), request.openedByActorId());
    }

    public static IntegrityProgramResponse toResponse(IntegrityProgramSummaryDto dto) {
        return new IntegrityProgramResponse(dto.id(), dto.code(), dto.nameFr(), dto.programTypeId(), dto.status(), dto.plannedStartAt(), dto.plannedEndAt());
    }

    public static IntegrityAssessmentResponse toResponse(IntegrityAssessmentSummaryDto dto) {
        return new IntegrityAssessmentResponse(dto.id(), dto.programId(), dto.assessmentNumber(), dto.title(), dto.assessmentTypeId(), dto.status(), dto.assessmentDate());
    }

    public static IntegrityCaseResponse toResponse(IntegrityCaseSummaryDto dto) {
        return new IntegrityCaseResponse(dto.id(), dto.caseNumber(), dto.title(), dto.caseTypeId(), dto.status(), dto.topologyAssetTypeCode(), dto.topologyAssetId(), dto.primaryDefectId(), dto.openedAt(), dto.closedAt());
    }
}
