/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
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

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import dz.sh.hidra.modules.audit.application.contract.topology.PressureRestrictionAuditEvidenceContract;
import dz.sh.hidra.modules.audit.domain.value.AuditActorType;
import dz.sh.hidra.modules.audit.domain.value.AuditOperation;

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
@Service
public class AuditApplicationService implements RecordAuditEventUseCase, RequestAuditExportUseCase, RecordAuditAccessUseCase,
        PressureRestrictionAuditEvidenceContract {

    private final AuditInputPolicy inputPolicy;

    private final AuditEventRepositoryPort auditEventRepositoryPort;
    private final AuditExportRequestRepositoryPort exportRequestRepositoryPort;
    private final AuditAccessRecordRepositoryPort accessRecordRepositoryPort;

    public AuditApplicationService(
            AuditEventRepositoryPort auditEventRepositoryPort,
            AuditExportRequestRepositoryPort exportRequestRepositoryPort,
            AuditAccessRecordRepositoryPort accessRecordRepositoryPort,
            AuditInputPolicy inputPolicy
    ) {
        this.inputPolicy = Objects.requireNonNull(inputPolicy);
        this.auditEventRepositoryPort = Objects.requireNonNull(auditEventRepositoryPort, "Audit event repository port must not be null.");
        this.exportRequestRepositoryPort = Objects.requireNonNull(exportRequestRepositoryPort, "Audit export request repository port must not be null.");
        this.accessRecordRepositoryPort = Objects.requireNonNull(accessRecordRepositoryPort, "Audit access record repository port must not be null.");
    }

    @Override
    public AuditEventSummaryDto recordAuditEvent(RecordAuditEventCommand command) {
        Objects.requireNonNull(command, "Record audit event command must not be null.");
        if ("OPERATING_PRESSURE_RESTRICTION".equals(command.targetType())
                || java.util.Arrays.stream(PressureRestrictionAuditEvidenceContract.Kind.values()).anyMatch(kind ->
                    kind.typeId().equals(command.eventTypeId()) || kind.actionCode().equals(command.actionCode()))) {
            throw new IllegalArgumentException("Pressure restriction evidence requires owner publication");
        }
        return persistAuditEvent(command);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public String appendPressureWitness(Witness witness) {
        Objects.requireNonNull(witness);
        return persistAuditEvent(new RecordAuditEventCommand(
                witness.kind().typeId(), "t38_pressure_governance", null,
                "topology", "pressure-restriction-publication", witness.action(),
                witness.kind().actionCode(), null, witness.reviewer(), AuditActorType.USER, null, null,
                "topology", "OPERATING_PRESSURE_RESTRICTION", witness.candidate().toString(),
                "t36-v1:" + witness.candidate(), null, AuditOperation.APPROVE, "APPROVE", null, null,
                witness.instance(), witness.task(), witness.action(), null, null, null, witness.actedAt(), null)).id();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean matchesPressureWitness(String eventId, Witness witness) {
        return auditEventRepositoryPort.findById(eventId).filter(event ->
                witness.kind().typeId().equals(event.eventTypeId())
                && witness.kind().actionCode().equals(event.actionCode())
                && "topology".equals(event.targetModule()) && "topology".equals(event.sourceModule())
                && "OPERATING_PRESSURE_RESTRICTION".equals(event.targetType())
                && witness.candidate().toString().equals(event.targetId()) && event.operation() == AuditOperation.APPROVE
                && witness.reviewer().equals(event.actorId()) && witness.instance().equals(event.workflowInstanceId())
                && witness.task().equals(event.workflowTaskId()) && witness.action().equals(event.workflowActionId())
                && witness.actedAt().equals(event.occurredAt())).isPresent();
    }

    private AuditEventSummaryDto persistAuditEvent(RecordAuditEventCommand command) {
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
                inputPolicy.text(command.reasonText(),1000),
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
                inputPolicy.json(command.payloadJson(),false)
        );
        return AuditApplicationMapper.toSummary(auditEventRepositoryPort.save(event));
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public AuditExportRequestSummaryDto requestAuditExport(RequestAuditExportCommand command) {
        Objects.requireNonNull(command, "Request audit export command must not be null.");
        Instant now = Instant.now();
        AuditExportRequest exportRequest = new AuditExportRequest(
                AuditId.newId().value(),
                command.requestedByActorId(),
                command.requestedByDisplayNameSnapshot(),
                command.purposeId(),
                inputPolicy.json(command.filterJson(), true),
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
        AuditExportRequest saved = exportRequestRepositoryPort.save(exportRequest);
        accessRecordRepositoryPort.save(new AuditAccessRecord(
                AuditId.newId().value(), saved.requestedByActorId(), saved.requestedByDisplayNameSnapshot(),
                dz.sh.hidra.modules.audit.domain.value.AuditAccessType.EXPORT, null,
                inputPolicy.hash(saved.filterJson()), saved.id(), null, null, now, null));
        return AuditApplicationMapper.toSummary(saved);
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
