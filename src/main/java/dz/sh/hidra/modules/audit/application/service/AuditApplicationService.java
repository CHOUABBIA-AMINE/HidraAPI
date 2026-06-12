/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditApplicationService
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.application.service
 *
 * @Description : Application service for audit events, export requests, and access records.
 *
 */
package dz.sh.hidra.modules.audit.application.service;

import dz.sh.hidra.modules.audit.application.command.RecordAuditAccessCommand;
import dz.sh.hidra.modules.audit.application.command.RecordAuditEventCommand;
import dz.sh.hidra.modules.audit.application.command.RequestAuditExportCommand;
import dz.sh.hidra.modules.audit.application.dto.AuditAccessRecordSummaryDto;
import dz.sh.hidra.modules.audit.application.dto.AuditEventSummaryDto;
import dz.sh.hidra.modules.audit.application.dto.AuditExportRequestSummaryDto;
import dz.sh.hidra.modules.audit.application.mapper.AuditApplicationMapper;
import dz.sh.hidra.modules.audit.application.port.in.RecordAuditAccessUseCase;
import dz.sh.hidra.modules.audit.application.port.in.RecordAuditEventUseCase;
import dz.sh.hidra.modules.audit.application.port.in.RequestAuditExportUseCase;
import dz.sh.hidra.modules.audit.application.port.out.AuditAccessRecordRepositoryPort;
import dz.sh.hidra.modules.audit.application.port.out.AuditEventRepositoryPort;
import dz.sh.hidra.modules.audit.application.port.out.AuditExportRequestRepositoryPort;
import dz.sh.hidra.modules.audit.domain.model.AuditAccessRecord;
import dz.sh.hidra.modules.audit.domain.model.AuditEvent;
import dz.sh.hidra.modules.audit.domain.model.AuditExportRequest;
import dz.sh.hidra.modules.audit.domain.value.AuditEventStatus;
import dz.sh.hidra.modules.audit.domain.value.AuditExportStatus;
import dz.sh.hidra.modules.audit.domain.value.AuditId;

import java.time.Instant;
import java.util.Objects;

/**
 * Application service for audit events, export requests, and access records.
 */
public class AuditApplicationService implements RecordAuditEventUseCase, RequestAuditExportUseCase, RecordAuditAccessUseCase {

    private final AuditEventRepositoryPort auditEventRepositoryPort;
    private final AuditExportRequestRepositoryPort exportRequestRepositoryPort;
    private final AuditAccessRecordRepositoryPort accessRecordRepositoryPort;

    public AuditApplicationService(
            AuditEventRepositoryPort auditEventRepositoryPort,
            AuditExportRequestRepositoryPort exportRequestRepositoryPort,
            AuditAccessRecordRepositoryPort accessRecordRepositoryPort
    ) {
        this.auditEventRepositoryPort = Objects.requireNonNull(auditEventRepositoryPort, "Audit event repository port must not be null.");
        this.exportRequestRepositoryPort = Objects.requireNonNull(exportRequestRepositoryPort, "Audit export request repository port must not be null.");
        this.accessRecordRepositoryPort = Objects.requireNonNull(accessRecordRepositoryPort, "Audit access record repository port must not be null.");
    }

    @Override
    public AuditEventSummaryDto recordAuditEvent(RecordAuditEventCommand command) {
        Objects.requireNonNull(command, "Record audit event command must not be null.");
        Instant now = Instant.now();
        AuditEvent event = new AuditEvent(
                AuditId.newId().value(),
                command.eventTypeId(),
                command.eventCategoryId(),
                command.severityId(),
                command.sourceModule(),
                command.sourceComponent(),
                command.sourceEventId(),
                command.actionCode(),
                command.actionLabelSnapshot(),
                AuditEventStatus.RECORDED,
                command.actorId(),
                command.actorType(),
                command.actorDisplayNameSnapshot(),
                command.actorUsernameSnapshot(),
                null,
                null,
                null,
                null,
                command.targetModule(),
                command.targetType(),
                command.targetId(),
                command.targetCodeSnapshot(),
                command.targetLabelSnapshot(),
                command.operation(),
                command.decisionCode(),
                command.reasonId(),
                command.reasonText(),
                null,
                command.workflowInstanceId(),
                command.workflowTaskId(),
                command.workflowActionId(),
                null,
                null,
                command.requestId(),
                command.correlationId(),
                command.causationId(),
                null,
                null,
                null,
                command.occurredAt() == null ? now : command.occurredAt(),
                now,
                null,
                null,
                null,
                command.payloadJson()
        );
        return AuditApplicationMapper.toSummary(auditEventRepositoryPort.save(event));
    }

    @Override
    public AuditExportRequestSummaryDto requestAuditExport(RequestAuditExportCommand command) {
        Objects.requireNonNull(command, "Request audit export command must not be null.");
        Instant now = Instant.now();
        AuditExportRequest exportRequest = new AuditExportRequest(
                AuditId.newId().value(),
                command.requestedByActorId(),
                command.requestedByDisplayNameSnapshot(),
                command.purposeId(),
                command.filterJson(),
                command.format(),
                AuditExportStatus.REQUESTED,
                command.workflowInstanceId(),
                null,
                null,
                null,
                now,
                null,
                null
        );
        return AuditApplicationMapper.toSummary(exportRequestRepositoryPort.save(exportRequest));
    }

    @Override
    public AuditAccessRecordSummaryDto recordAuditAccess(RecordAuditAccessCommand command) {
        Objects.requireNonNull(command, "Record audit access command must not be null.");
        AuditAccessRecord accessRecord = new AuditAccessRecord(
                AuditId.newId().value(),
                command.actorId(),
                command.actorDisplayNameSnapshot(),
                command.accessType(),
                command.auditEventId(),
                command.searchFilterHash(),
                command.exportRequestId(),
                command.resultCount(),
                command.purposeText(),
                Instant.now(),
                command.correlationId()
        );
        return AuditApplicationMapper.toSummary(accessRecordRepositoryPort.save(accessRecord));
    }
}
