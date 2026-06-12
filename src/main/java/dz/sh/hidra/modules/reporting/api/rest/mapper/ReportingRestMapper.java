/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.api.rest.mapper
 *
 * @Description : Maps reporting REST models to application models.
 *
 */
package dz.sh.hidra.modules.reporting.api.rest.mapper;

import dz.sh.hidra.modules.reporting.api.rest.request.CreateReportDefinitionRequest;
import dz.sh.hidra.modules.reporting.api.rest.request.RequestReportRequest;
import dz.sh.hidra.modules.reporting.api.rest.response.ReportDefinitionResponse;
import dz.sh.hidra.modules.reporting.api.rest.response.ReportRequestResponse;
import dz.sh.hidra.modules.reporting.application.command.CreateReportDefinitionCommand;
import dz.sh.hidra.modules.reporting.application.command.RequestReportCommand;
import dz.sh.hidra.modules.reporting.application.dto.ReportDefinitionSummaryDto;
import dz.sh.hidra.modules.reporting.application.dto.ReportRequestSummaryDto;

/**
 * Maps reporting REST models to application models.
 */
public final class ReportingRestMapper {

    private ReportingRestMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static CreateReportDefinitionCommand toCommand(CreateReportDefinitionRequest request) {
        return new CreateReportDefinitionCommand(request.code(), request.nameAr(), request.nameFr(), request.nameEn(), request.reportCategoryId(), request.ownerModule(), request.description(), request.requiresApproval(), request.restricted());
    }

    public static RequestReportCommand toCommand(RequestReportRequest request) {
        return new RequestReportCommand(request.reportDefinitionId(), request.requestedByActorId(), request.requestedByUsernameSnapshot(), request.requestedByDisplayNameSnapshot(), request.requestedByRoleCodeSnapshot(), request.organizationUnitId(), request.organizationUnitNameSnapshot(), request.purpose(), request.correlationId(), request.workflowReferenceId());
    }

    public static ReportDefinitionResponse toResponse(ReportDefinitionSummaryDto dto) {
        return new ReportDefinitionResponse(dto.id(), dto.code(), dto.nameFr(), dto.reportCategoryId(), dto.ownerModule(), dto.active(), dto.currentTemplateVersionId(), dto.requiresApproval(), dto.restricted());
    }

    public static ReportRequestResponse toResponse(ReportRequestSummaryDto dto) {
        return new ReportRequestResponse(dto.id(), dto.reportDefinitionId(), dto.requestedByActorId(), dto.requestedByDisplayNameSnapshot(), dto.organizationUnitId(), dto.requestedAt(), dto.status(), dto.correlationId(), dto.workflowReferenceId());
    }
}
