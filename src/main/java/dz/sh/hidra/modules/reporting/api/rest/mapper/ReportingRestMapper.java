/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
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
import dz.sh.hidra.modules.reporting.api.rest.request.GenerateReportArtifactRequest;
import dz.sh.hidra.modules.reporting.api.rest.request.QueueReportRunRequest;
import dz.sh.hidra.modules.reporting.api.rest.request.RequestReportRequest;
import dz.sh.hidra.modules.reporting.api.rest.response.ReportDefinitionResponse;
import dz.sh.hidra.modules.reporting.api.rest.response.ReportOutputArtifactResponse;
import dz.sh.hidra.modules.reporting.api.rest.response.ReportRequestResponse;
import dz.sh.hidra.modules.reporting.api.rest.response.ReportRunResponse;
import dz.sh.hidra.modules.reporting.application.command.CreateReportDefinitionCommand;
import dz.sh.hidra.modules.reporting.application.command.GenerateReportArtifactCommand;
import dz.sh.hidra.modules.reporting.application.command.QueueReportRunCommand;
import dz.sh.hidra.modules.reporting.application.command.RequestReportCommand;
import dz.sh.hidra.modules.reporting.application.dto.ReportDefinitionSummaryDto;
import dz.sh.hidra.modules.reporting.application.dto.ReportOutputArtifactSummaryDto;
import dz.sh.hidra.modules.reporting.application.dto.ReportRequestSummaryDto;
import dz.sh.hidra.modules.reporting.application.dto.ReportRunSummaryDto;

/**
 * Maps reporting REST models to application models.
 */
public final class ReportingRestMapper {

    private ReportingRestMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static CreateReportDefinitionCommand toCommand(CreateReportDefinitionRequest request) {
        return new CreateReportDefinitionCommand(
                request.code(),
                request.nameAr(),
                request.nameFr(),
                request.nameEn(),
                request.reportCategoryId(),
                request.ownerModule(),
                request.description(),
                request.requiresApproval(),
                request.restricted()
        );
    }

    public static GenerateReportArtifactCommand toCommand(GenerateReportArtifactRequest request) {
        return new GenerateReportArtifactCommand(
                request.reportRunId(),
                request.artifactType(),
                request.format(),
                request.fileName(),
                request.mimeType(),
                request.storageObjectReferenceId(),
                request.documentReferenceId(),
                request.checksum(),
                request.sizeBytes(),
                request.expiresAt()
        );
    }

    public static QueueReportRunCommand toCommand(QueueReportRunRequest request) {
        return new QueueReportRunCommand(
                request.reportRequestId(),
                request.reportDefinitionId(),
                request.templateVersionId(),
                request.runMode(),
                request.correlationId()
        );
    }

    public static RequestReportCommand toCommand(RequestReportRequest request) {
        return new RequestReportCommand(
                request.reportDefinitionId(),
                request.requestedByActorId(),
                request.requestedByUsernameSnapshot(),
                request.requestedByDisplayNameSnapshot(),
                request.requestedByRoleCodeSnapshot(),
                request.organizationUnitId(),
                request.organizationUnitNameSnapshot(),
                request.purpose(),
                request.correlationId(),
                request.workflowReferenceId()
        );
    }

    public static ReportDefinitionResponse toResponse(ReportDefinitionSummaryDto dto) {
        return new ReportDefinitionResponse(
                dto.id(),
                dto.code(),
                dto.nameFr(),
                dto.reportCategoryId(),
                dto.ownerModule(),
                dto.active(),
                dto.currentTemplateVersionId(),
                dto.requiresApproval(),
                dto.restricted()
        );
    }

    public static ReportOutputArtifactResponse toResponse(ReportOutputArtifactSummaryDto dto) {
        return new ReportOutputArtifactResponse(
                dto.id(),
                dto.reportRunId(),
                dto.artifactType(),
                dto.format(),
                dto.fileName(),
                dto.mimeType(),
                dto.documentReferenceId(),
                dto.checksum(),
                dto.sizeBytes(),
                dto.generatedAt()
        );
    }

    public static ReportRunResponse toResponse(ReportRunSummaryDto dto) {
        return new ReportRunResponse(
                dto.id(),
                dto.reportRequestId(),
                dto.reportDefinitionId(),
                dto.templateVersionId(),
                dto.status(),
                dto.runMode(),
                dto.queuedAt(),
                dto.completedAt(),
                dto.correlationId()
        );
    }

    public static ReportRequestResponse toResponse(ReportRequestSummaryDto dto) {
        return new ReportRequestResponse(
                dto.id(),
                dto.reportDefinitionId(),
                dto.requestedByActorId(),
                dto.requestedByDisplayNameSnapshot(),
                dto.organizationUnitId(),
                dto.requestedAt(),
                dto.status(),
                dto.correlationId(),
                dto.workflowReferenceId()
        );
    }
}
