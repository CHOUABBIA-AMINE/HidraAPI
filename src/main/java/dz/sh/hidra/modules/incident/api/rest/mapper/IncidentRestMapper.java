/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.api.rest.mapper
 *
 * @Description : Maps incident REST models to application models.
 *
 */
package dz.sh.hidra.modules.incident.api.rest.mapper;
import dz.sh.hidra.modules.incident.api.rest.request.CloseIncidentRequest;
import dz.sh.hidra.modules.incident.api.rest.request.OpenIncidentRequest;
import dz.sh.hidra.modules.incident.api.rest.request.RecordIncidentResponseActionRequest;
import dz.sh.hidra.modules.incident.api.rest.response.IncidentResponse;
import dz.sh.hidra.modules.incident.application.command.CloseIncidentCommand;
import dz.sh.hidra.modules.incident.application.command.OpenIncidentCommand;
import dz.sh.hidra.modules.incident.application.command.RecordIncidentResponseActionCommand;
import dz.sh.hidra.modules.incident.application.dto.IncidentSummaryDto;

/**
 * Maps incident REST models to application models.
 */
public final class IncidentRestMapper {

    private IncidentRestMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static CloseIncidentCommand toCommand(CloseIncidentRequest request) {
        return new CloseIncidentCommand(
                request.incidentId(),
                request.closureSummary(),
                request.resolutionVerified(),
                request.evidenceReviewed(),
                request.rootCauseReviewed(),
                request.followUpActionsCreated(),
                request.closedByActorId(),
                request.closedByActorNameSnapshot(),
                request.workflowInstanceId()
        );
    }

    public static OpenIncidentCommand toCommand(OpenIncidentRequest request) {
        return new OpenIncidentCommand(
                request.incidentNumber(),
                request.title(),
                request.description(),
                request.classificationId(),
                request.severityId(),
                request.priorityId(),
                request.sourceType(),
                request.sourceReferenceId(),
                request.sourceReferenceCode(),
                request.detectedAt(),
                request.occurredAt(),
                request.topologyAssetTypeCode(),
                request.topologyAssetId(),
                request.topologyAssetCode(),
                request.topologyAssetNameSnapshot(),
                request.locationDescriptionAr(),
                request.locationDescriptionLt(),
                request.latitude(),
                request.longitude(),
                request.responsibleOrganizationUnitId(),
                request.responsibleOrganizationUnitCode(),
                request.responsibleOrganizationUnitNameSnapshot(),
                request.createdByActorId(),
                request.createdByActorNameSnapshot()
        );
    }

    public static RecordIncidentResponseActionCommand toCommand(RecordIncidentResponseActionRequest request) {
        return new RecordIncidentResponseActionCommand(
                request.incidentId(),
                request.actionTypeId(),
                request.actionStatus(),
                request.description(),
                request.targetType(),
                request.targetReferenceId(),
                request.targetReferenceCode(),
                request.plannedStartAt(),
                request.plannedEndAt(),
                request.startedAt(),
                request.completedAt(),
                request.performedByActorId(),
                request.performedByActorNameSnapshot(),
                request.organizationUnitId(),
                request.resultSummary(),
                request.failureReason()
        );
    }

    public static IncidentResponse toResponse(IncidentSummaryDto dto) {
        return new IncidentResponse(
                dto.id(),
                dto.incidentNumber(),
                dto.title(),
                dto.classificationId(),
                dto.severityId(),
                dto.status(),
                dto.sourceType(),
                dto.topologyAssetTypeCode(),
                dto.topologyAssetId(),
                dto.topologyAssetCode(),
                dto.detectedAt(),
                dto.reportedAt(),
                dto.closedAt()
        );
    }
}
