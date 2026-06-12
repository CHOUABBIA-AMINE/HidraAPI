/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.api.rest.mapper
 *
 * @Description : Maps HSE REST models to application models.
 *
 */
package dz.sh.hidra.modules.hse.api.rest.mapper;

import dz.sh.hidra.modules.hse.api.rest.request.CreateHseCapaRequest;
import dz.sh.hidra.modules.hse.api.rest.request.OpenHseCaseRequest;
import dz.sh.hidra.modules.hse.api.rest.response.HseCapaResponse;
import dz.sh.hidra.modules.hse.api.rest.response.HseCaseResponse;
import dz.sh.hidra.modules.hse.application.command.CreateHseCapaCommand;
import dz.sh.hidra.modules.hse.application.command.OpenHseCaseCommand;
import dz.sh.hidra.modules.hse.application.dto.HseCapaSummaryDto;
import dz.sh.hidra.modules.hse.application.dto.HseCaseSummaryDto;

/**
 * Maps HSE REST models to application models.
 */
public final class HseRestMapper {

    private HseRestMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static OpenHseCaseCommand toCommand(OpenHseCaseRequest request) {
        return new OpenHseCaseCommand(
                request.caseNumber(),
                request.title(),
                request.description(),
                request.caseTypeId(),
                request.severityId(),
                request.priorityId(),
                request.sourceType(),
                request.incidentReferenceId(),
                request.incidentCodeSnapshot(),
                request.incidentTitleSnapshot(),
                request.targetModule(),
                request.targetTypeCode(),
                request.targetId(),
                request.targetCodeSnapshot(),
                request.targetLabelSnapshot(),
                request.occurredAt(),
                request.reportedByActorId(),
                request.reportedByDisplayNameSnapshot(),
                request.responsibleOrganizationUnitId(),
                request.responsibleOrganizationUnitNameSnapshot(),
                request.workflowInstanceId()
        );
    }

    public static CreateHseCapaCommand toCommand(CreateHseCapaRequest request) {
        return new CreateHseCapaCommand(
                request.hseCaseId(),
                request.actionNumber(),
                request.actionTypeId(),
                request.title(),
                request.description(),
                request.ownerActorId(),
                request.ownerDisplayNameSnapshot(),
                request.ownerOrganizationUnitId(),
                request.ownerOrganizationUnitNameSnapshot(),
                request.targetDate(),
                request.verificationRequired(),
                request.linkedWorkOrderId(),
                request.workflowTaskId()
        );
    }

    public static HseCaseResponse toResponse(HseCaseSummaryDto dto) {
        return new HseCaseResponse(
                dto.id(),
                dto.caseNumber(),
                dto.title(),
                dto.caseTypeId(),
                dto.severityId(),
                dto.status(),
                dto.sourceType(),
                dto.incidentReferenceId(),
                dto.targetModule(),
                dto.targetTypeCode(),
                dto.targetId(),
                dto.reportedAt(),
                dto.closedAt()
        );
    }

    public static HseCapaResponse toResponse(HseCapaSummaryDto dto) {
        return new HseCapaResponse(
                dto.id(),
                dto.hseCaseId(),
                dto.actionNumber(),
                dto.title(),
                dto.status(),
                dto.targetDate(),
                dto.completedAt()
        );
    }
}
