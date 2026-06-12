/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.api.rest.mapper
 *
 * @Description : Maps audit REST models to application models.
 *
 */
package dz.sh.hidra.modules.audit.api.rest.mapper;

import dz.sh.hidra.modules.audit.api.rest.request.RecordAuditEventRequest;
import dz.sh.hidra.modules.audit.api.rest.request.RequestAuditExportRequest;
import dz.sh.hidra.modules.audit.api.rest.response.AuditEventResponse;
import dz.sh.hidra.modules.audit.api.rest.response.AuditExportRequestResponse;
import dz.sh.hidra.modules.audit.application.command.RecordAuditEventCommand;
import dz.sh.hidra.modules.audit.application.command.RequestAuditExportCommand;
import dz.sh.hidra.modules.audit.application.dto.AuditEventSummaryDto;
import dz.sh.hidra.modules.audit.application.dto.AuditExportRequestSummaryDto;

/**
 * Maps audit REST models to application models.
 */
public final class AuditRestMapper {

    private AuditRestMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static RecordAuditEventCommand toCommand(RecordAuditEventRequest request) {
        return new RecordAuditEventCommand(request.eventTypeId(), request.eventCategoryId(), request.severityId(), request.sourceModule(), request.sourceComponent(), request.sourceEventId(), request.actionCode(), request.actionLabelSnapshot(), request.actorId(), request.actorType(), request.actorDisplayNameSnapshot(), request.actorUsernameSnapshot(), request.targetModule(), request.targetType(), request.targetId(), request.targetCodeSnapshot(), request.targetLabelSnapshot(), request.operation(), request.decisionCode(), request.reasonId(), request.reasonText(), request.workflowInstanceId(), request.workflowTaskId(), request.workflowActionId(), request.requestId(), request.correlationId(), request.causationId(), request.occurredAt(), request.payloadJson());
    }

    public static RequestAuditExportCommand toCommand(RequestAuditExportRequest request) {
        return new RequestAuditExportCommand(request.requestedByActorId(), request.requestedByDisplayNameSnapshot(), request.purposeId(), request.filterJson(), request.format(), request.workflowInstanceId());
    }

    public static AuditEventResponse toResponse(AuditEventSummaryDto dto) {
        return new AuditEventResponse(dto.id(), dto.sourceModule(), dto.actionCode(), dto.eventStatus(), dto.actorId(), dto.actorType(), dto.targetModule(), dto.targetType(), dto.targetId(), dto.operation(), dto.correlationId(), dto.occurredAt(), dto.recordedAt());
    }

    public static AuditExportRequestResponse toResponse(AuditExportRequestSummaryDto dto) {
        return new AuditExportRequestResponse(dto.id(), dto.requestedByActorId(), dto.purposeId(), dto.format(), dto.status(), dto.recordCount(), dto.requestedAt(), dto.completedAt());
    }
}
