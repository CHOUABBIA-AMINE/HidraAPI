/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RevisionApprovalEvidenceQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-10
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.service
 *
 * @Description : Reads current configured approval and terminal action evidence without creating decisions.
 *
 */
package dz.sh.hidra.modules.workflow.application.service;

import dz.sh.hidra.modules.workflow.application.contract.target.RevisionApprovalEvidenceContract;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowActionRepositoryPort;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowConfigurationPort;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowInstanceRepositoryPort;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowTaskRepositoryPort;
import dz.sh.hidra.modules.workflow.domain.exception.InvalidWorkflowValueException;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowActionType;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDecision;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowInstanceStatus;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTaskStatus;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RevisionApprovalEvidenceQueryService implements RevisionApprovalEvidenceContract {
    private final WorkflowInstanceRepositoryPort instances;
    private final WorkflowTaskRepositoryPort tasks;
    private final WorkflowActionRepositoryPort actions;
    private final WorkflowConfigurationPort configuration;
    public RevisionApprovalEvidenceQueryService(WorkflowInstanceRepositoryPort instances,
            WorkflowTaskRepositoryPort tasks, WorkflowActionRepositoryPort actions,
            WorkflowConfigurationPort configuration) {
        this.instances = instances; this.tasks = tasks; this.actions = actions;
        this.configuration = configuration;
    }
    @Override
    @Transactional(readOnly = true)
    public Optional<Evidence> resolve(Request r) {
        if (r == null || r.evaluationAt() == null || r.targetDigest() == null
                || !r.targetDigest().matches("[0-9a-f]{64}") || r.definitionVersion() < 1
                || blank(r.definitionId()) || blank(r.targetTypeId()) || blank(r.purposeId())
                || blank(r.instanceId()) || blank(r.taskId()) || blank(r.actionId())) return Optional.empty();
        String type; String purpose;
        if ("custody".equals(r.targetModule())) {
            type = "GAS_FLUID_REVISION"; purpose = "GAS_FLUID_INPUT_QUALIFICATION";
        } else if ("simulation".equals(r.targetModule())) {
            type = "EQUIPMENT_PARAMETER_REVISION"; purpose = "EQUIPMENT_PARAMETER_QUALIFICATION";
        } else return Optional.empty();
        try {
            var t = configuration.requireActiveCatalog(r.targetTypeId(), "WORKFLOW_TARGET_TYPE");
            var p = configuration.requireActiveCatalog(r.purposeId(), "WORKFLOW_PURPOSE");
            if (!t.active() || !p.active() || !type.equals(t.code()) || !purpose.equals(p.code())
                    || !r.targetTypeId().equals(t.id()) || !r.purposeId().equals(p.id())
                    || !configuration.activeBinding(r.definitionId(), r.targetModule(), r.targetTypeId(), r.purposeId()))
                return Optional.empty();
        } catch (InvalidWorkflowValueException unavailableConfiguration) { return Optional.empty(); }
        var i = instances.findById(r.instanceId()).orElse(null);
        var t = tasks.findById(r.taskId()).orElse(null);
        var a = actions.findById(r.actionId()).orElse(null);
        if (i == null || t == null || a == null || !r.instanceId().equals(i.id())
                || !r.taskId().equals(t.id()) || !r.actionId().equals(a.id())
                || !r.definitionId().equals(i.definitionId()) || r.definitionVersion() != i.definitionVersion()
                || !r.targetModule().equals(i.targetModule()) || !r.targetDigest().equals(i.targetId())
                || !r.targetTypeId().equals(i.targetTypeId()) || !r.purposeId().equals(i.workflowPurposeId())
                || i.status() != WorkflowInstanceStatus.COMPLETED || t.status() != WorkflowTaskStatus.APPROVED
                || !i.id().equals(t.instanceId()) || !i.id().equals(a.instanceId()) || !t.id().equals(a.taskId())
                || a.actionType() != WorkflowActionType.APPROVE || a.decision() != WorkflowDecision.APPROVE
                || !Objects.equals(a.actorId(), t.completedByActorId()) || blank(a.actorDisplayNameSnapshot())
                || !a.actedAt().equals(t.completedAt()) || !a.actedAt().equals(i.completedAt())
                || a.actedAt().isAfter(r.evaluationAt())
                || a.actionSequence() != actions.nextSequence(i.id()) - 1) return Optional.empty();
        return Optional.of(new Evidence(i.id(), t.id(), a.id(), i.definitionId(), i.definitionVersion(),
                i.targetTypeId(), i.workflowPurposeId(), a.actorId(), a.actorDisplayNameSnapshot(), a.actedAt()));
    }
    private static boolean blank(String s) { return s == null || s.isBlank(); }
}
