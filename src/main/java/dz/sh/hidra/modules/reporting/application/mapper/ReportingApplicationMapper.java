/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingApplicationMapper
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Application
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.application.mapper
 *
 * @Description : Maps reporting domain models to DTOs.
 *
 */
package dz.sh.hidra.modules.reporting.application.mapper;

import dz.sh.hidra.modules.reporting.application.dto.ReportDefinitionSummaryDto;
import dz.sh.hidra.modules.reporting.application.dto.ReportOutputArtifactSummaryDto;
import dz.sh.hidra.modules.reporting.application.dto.ReportRequestSummaryDto;
import dz.sh.hidra.modules.reporting.application.dto.ReportRunSummaryDto;
import dz.sh.hidra.modules.reporting.domain.model.ReportDefinition;
import dz.sh.hidra.modules.reporting.domain.model.ReportOutputArtifact;
import dz.sh.hidra.modules.reporting.domain.model.ReportRequest;
import dz.sh.hidra.modules.reporting.domain.model.ReportRun;

/**
 * Maps reporting domain models to DTOs.
 */
public final class ReportingApplicationMapper {

    private ReportingApplicationMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static ReportDefinitionSummaryDto toSummary(ReportDefinition definition) {
        return new ReportDefinitionSummaryDto(definition.id(), definition.code(), definition.nameFr(), definition.reportCategoryId(), definition.ownerModule(), definition.active(), definition.currentTemplateVersionId(), definition.requiresApproval(), definition.restricted());
    }

    public static ReportRequestSummaryDto toSummary(ReportRequest request) {
        return new ReportRequestSummaryDto(request.id(), request.reportDefinitionId(), request.requestedByActorId(), request.requestedByDisplayNameSnapshot(), request.organizationUnitId(), request.requestedAt(), request.status(), request.correlationId(), request.workflowReferenceId());
    }

    public static ReportRunSummaryDto toSummary(ReportRun run) {
        return new ReportRunSummaryDto(run.id(), run.reportRequestId(), run.reportDefinitionId(), run.templateVersionId(), run.status(), run.runMode(), run.queuedAt(), run.completedAt(), run.correlationId());
    }

    public static ReportOutputArtifactSummaryDto toSummary(ReportOutputArtifact artifact) {
        return new ReportOutputArtifactSummaryDto(artifact.id(), artifact.reportRunId(), artifact.artifactType(), artifact.format(), artifact.fileName(), artifact.mimeType(), artifact.documentReferenceId(), artifact.checksum(), artifact.sizeBytes(), artifact.generatedAt());
    }
}
