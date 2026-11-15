/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.application.service
 *
 * @Description : Application service for incident lifecycle commands.
 *
 */
package dz.sh.hidra.modules.incident.application.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import dz.sh.hidra.modules.incident.application.port.out.IncidentEscalationAuthorizationPort;

import dz.sh.hidra.modules.incident.application.command.CloseIncidentCommand;
import dz.sh.hidra.modules.incident.application.command.OpenIncidentCommand;
import dz.sh.hidra.modules.incident.application.command.RecordIncidentResponseActionCommand;
import dz.sh.hidra.modules.incident.application.dto.IncidentSummaryDto;
import dz.sh.hidra.modules.incident.application.mapper.IncidentApplicationMapper;
import dz.sh.hidra.modules.incident.application.port.in.CloseIncidentUseCase;
import dz.sh.hidra.modules.incident.application.port.in.OpenIncidentUseCase;
import dz.sh.hidra.modules.incident.application.port.in.RecordIncidentResponseActionUseCase;
import dz.sh.hidra.modules.incident.application.port.out.IncidentClosureRepositoryPort;
import dz.sh.hidra.modules.incident.application.port.out.IncidentRepositoryPort;
import dz.sh.hidra.modules.incident.application.port.out.IncidentResponseActionRepositoryPort;
import dz.sh.hidra.modules.incident.domain.model.Incident;
import dz.sh.hidra.modules.incident.domain.model.IncidentClosure;
import dz.sh.hidra.modules.incident.domain.model.IncidentResponseAction;
import dz.sh.hidra.modules.incident.domain.value.IncidentId;
import dz.sh.hidra.modules.incident.domain.value.IncidentSourceType;
import dz.sh.hidra.modules.incident.domain.value.IncidentStatus;
import dz.sh.hidra.modules.incident.domain.value.ResponseActionStatus;

import java.time.Instant;
import java.util.Objects;

/**
 * Application service for incident lifecycle commands.
 */
@Service
public class IncidentApplicationService implements OpenIncidentUseCase, RecordIncidentResponseActionUseCase, CloseIncidentUseCase {

    private final IncidentRepositoryPort incidentRepositoryPort;
    private final IncidentResponseActionRepositoryPort responseActionRepositoryPort;
    private final IncidentClosureRepositoryPort closureRepositoryPort;
    private final IncidentEscalationAuthorizationPort currentScopes;
    private final dz.sh.hidra.modules.incident.application.port.out.IncidentInvestigationStorePort investigations;

    private final dz.sh.hidra.modules.incident.application.port.out.IncidentReferencePolicyPort references;

    @Deprecated
    public IncidentApplicationService(IncidentRepositoryPort incidentRepositoryPort,
            IncidentResponseActionRepositoryPort responseActionRepositoryPort,
            IncidentClosureRepositoryPort closureRepositoryPort,
            dz.sh.hidra.modules.incident.application.port.out.IncidentReferencePolicyPort references) {
        this(incidentRepositoryPort,responseActionRepositoryPort,closureRepositoryPort,references,null);
    }


    /** Backward-compatible test/legacy dependency constructor, without T16 native case authority. */
    @Deprecated
    public IncidentApplicationService(
            IncidentRepositoryPort incidentRepositoryPort,
            IncidentResponseActionRepositoryPort responseActionRepositoryPort,
            IncidentClosureRepositoryPort closureRepositoryPort,
            dz.sh.hidra.modules.incident.application.port.out.IncidentReferencePolicyPort references,
            IncidentEscalationAuthorizationPort currentScopes) {
        this(incidentRepositoryPort,responseActionRepositoryPort,closureRepositoryPort,
                references,currentScopes,null);
    }

    @Autowired
    public IncidentApplicationService(
            IncidentRepositoryPort incidentRepositoryPort,
            IncidentResponseActionRepositoryPort responseActionRepositoryPort,
            IncidentClosureRepositoryPort closureRepositoryPort,
            dz.sh.hidra.modules.incident.application.port.out.IncidentReferencePolicyPort references,
            IncidentEscalationAuthorizationPort currentScopes,
            dz.sh.hidra.modules.incident.application.port.out.IncidentInvestigationStorePort investigations
    ) {
        this.references=Objects.requireNonNull(references);
        this.currentScopes=currentScopes;
        this.investigations=investigations;
        this.incidentRepositoryPort = Objects.requireNonNull(incidentRepositoryPort, "Incident repository port must not be null.");
        this.responseActionRepositoryPort = Objects.requireNonNull(responseActionRepositoryPort, "Incident response action repository port must not be null.");
        this.closureRepositoryPort = Objects.requireNonNull(closureRepositoryPort, "Incident closure repository port must not be null.");
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public IncidentSummaryDto openIncident(OpenIncidentCommand command) {
        Objects.requireNonNull(command, "Open incident command must not be null.");
        // Legacy commands cannot manufacture T12/T13 evidence-qualified origins.
        if(command.sourceType()==IncidentSourceType.ALARM
                ||command.sourceType()==IncidentSourceType.LEAK_CANDIDATE)
            throw new AccessDeniedException("Use the source-authorized Incident escalation command.");
        if(currentScopes!=null) {
            if(command.topologyAssetId()==null || command.topologyAssetTypeCode()==null
                    || currentScopes.permittedAssets("incident:incidents:execute").stream()
                      .noneMatch(a->a.type().equals(command.topologyAssetTypeCode())
                                 && a.id().equals(command.topologyAssetId())))
                throw new AccessDeniedException("Current scoped Incident execution required.");
        }
        Instant now = Instant.now();
        var actor=references.currentActor(now);
        var unit=command.responsibleOrganizationUnitId()==null ? null : references.organizationUnit(command.responsibleOrganizationUnitId());
        if((command.topologyAssetId()==null)!=(command.topologyAssetTypeCode()==null)) throw new IllegalArgumentException("Topology type and identifier must be supplied together.");
        var asset=command.topologyAssetId()==null ? null : references.topologyAsset(command.topologyAssetTypeCode(),command.topologyAssetId());
        Incident incident = new Incident(
                IncidentId.newId().value(),
                command.incidentNumber(),
                command.title(),
                command.description(),
                command.classificationId(),
                command.severityId(),
                command.priorityId(),
                IncidentStatus.OPEN,
                command.sourceType() == null ? IncidentSourceType.MANUAL : command.sourceType(),
                command.sourceReferenceId(),
                command.sourceReferenceCode(),
                command.detectedAt() == null ? now : command.detectedAt(),
                now,
                command.occurredAt(),
                command.topologyAssetTypeCode(),
                command.topologyAssetId(),
                asset==null ? command.topologyAssetCode() : asset.code(),
                asset==null ? command.topologyAssetNameSnapshot() : asset.label(),
                command.locationDescriptionAr(),
                command.locationDescriptionLt(),
                command.latitude(),
                command.longitude(),
                command.responsibleOrganizationUnitId(),
                unit==null ? command.responsibleOrganizationUnitCode() : unit.code(),
                unit==null ? command.responsibleOrganizationUnitNameSnapshot() : unit.label(),
                null,
                null,
                null,
                0,
                null,
                null,
                null,
                null,
                actor.id(),
                actor.label(),
                now,
                now
        );
        return IncidentApplicationMapper.toSummary(incidentRepositoryPort.save(incident));
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public String recordResponseAction(RecordIncidentResponseActionCommand command) {
        Objects.requireNonNull(command, "Record incident response action command must not be null.");
        // Recheck current resource scope before the parent row, state or history is disclosed.
        if(currentScopes!=null)
            currentScopes.requireIncident("incident:incidents:execute",command.incidentId());
        // An assigned T16 investigation has its own versioned response pathway and reviewer gate.
        // The legacy alias cannot append actions without a corresponding case receipt.
        if(investigations!=null && currentScopes!=null
                && !"NEW".equals(investigations.view(command.incidentId()).state()))
            throw new AccessDeniedException("Use the governed Incident investigation action operation.");
        var parent=incidentRepositoryPort.findByIdForUpdate(command.incidentId()).orElseThrow(() -> new IllegalArgumentException("Unknown Incident."));
        new dz.sh.hidra.modules.incident.domain.service.IncidentLifecycleGuard().ensureResponseActionAllowed(parent);
        Instant now = Instant.now();
        // Legacy action metadata is untrusted; derive attribution from the current signed Identity actor.
        var performer= currentScopes==null ? null : references.currentActor(now);
        if(performer!=null && command.performedByActorId()!=null
                && !performer.id().equals(command.performedByActorId()))
            throw new AccessDeniedException("Cannot impersonate an Incident response performer.");
        IncidentResponseAction action = new IncidentResponseAction(
                IncidentId.newId().value(),
                command.incidentId(),
                command.actionTypeId(),
                command.actionStatus() == null ? ResponseActionStatus.PLANNED : command.actionStatus(),
                command.description(),
                command.targetType(),
                command.targetReferenceId(),
                command.targetReferenceCode(),
                command.plannedStartAt(),
                command.plannedEndAt(),
                command.startedAt(),
                command.completedAt(),
                performer==null?command.performedByActorId():performer.id(),
                performer==null?command.performedByActorNameSnapshot():performer.label(),
                command.organizationUnitId(),
                command.resultSummary(),
                command.failureReason(),
                now,
                now
        );
        return responseActionRepositoryPort.save(action).id();
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public String closeIncident(CloseIncidentCommand command) {
        Objects.requireNonNull(command, "Close incident command must not be null.");
        // Recheck current resource scope before the parent row, state or history is disclosed.
        if(currentScopes!=null)
            currentScopes.requireIncident("incident:incidents:execute",command.incidentId());
        var parent=incidentRepositoryPort.findByIdForUpdate(command.incidentId()).orElseThrow(() -> new IllegalArgumentException("Unknown Incident."));
        if(parent.status()!=IncidentStatus.RESOLVED) throw new IllegalArgumentException("Only RESOLVED incidents may close.");
        var actor=references.currentActor(Instant.now());
        if(investigations!=null && currentScopes!=null
                && !investigations.reviewedForClosure(command.incidentId(),actor.id()))
            throw new AccessDeniedException("Governed Incident closure requires independent reviewed T16 close transition.");
        IncidentClosure closure = new IncidentClosure(
                IncidentId.newId().value(),
                command.incidentId(),
                command.closureSummary(),
                command.resolutionVerified(),
                command.evidenceReviewed(),
                command.rootCauseReviewed(),
                command.followUpActionsCreated(),
                actor.id(),
                actor.label(),
                Instant.now(),
                command.workflowInstanceId()
        );
        return closureRepositoryPort.save(closure).id();
    }
}
