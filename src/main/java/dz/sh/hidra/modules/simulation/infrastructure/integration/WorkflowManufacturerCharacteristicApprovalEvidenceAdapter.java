/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowManufacturerCharacteristicApprovalEvidenceAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-30
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.integration
 *
 * @Description : Exact terminal Workflow approval reads, never source authority.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.integration;

import dz.sh.hidra.modules.simulation.application.port.out.ManufacturerCharacteristicApprovalEvidencePort;
import dz.sh.hidra.modules.workflow.application.contract.target.RevisionApprovalEvidenceContract;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public final class WorkflowManufacturerCharacteristicApprovalEvidenceAdapter
        implements ManufacturerCharacteristicApprovalEvidencePort {
    /** Workflow-owned relation set; exposed as physical fence requirements, never an authorization. */
    private static final java.util.List<dz.sh.hidra.platform.persistence.ProtectedRelationFence> WORKFLOW_FENCES =
        java.util.List.of(
          "public.hidra_workflow_action","public.hidra_workflow_definition",
          "public.hidra_workflow_definition_target_binding","public.hidra_workflow_instance",
          "public.hidra_workflow_state_history","public.hidra_workflow_step",
          "public.hidra_workflow_task","public.hidra_workflow_transition",
          "public.hidra_workflow_type_catalog").stream()
        .map(name->new dz.sh.hidra.platform.persistence.ProtectedRelationFence(
            name,dz.sh.hidra.platform.persistence.ProtectedRelationFence.Mode.SHARE)).toList();
    private final RevisionApprovalEvidenceContract workflow;
    public WorkflowManufacturerCharacteristicApprovalEvidenceAdapter(
            @org.springframework.context.annotation.Lazy RevisionApprovalEvidenceContract workflow) {
        this.workflow = Objects.requireNonNull(workflow);
    }
    @Override
    public Optional<Evidence> resolve(Request r) {
        Objects.requireNonNull(r);
        var request = new RevisionApprovalEvidenceContract.Request(
                "simulation", r.targetDigest(), r.definitionId(), r.definitionVersion(),
                r.targetTypeId(), r.purposeId(), r.instanceId(), r.taskId(), r.actionId(), r.evaluatedAt());
        return workflow.resolve(request).map(x -> new Evidence(r.targetDigest(),x.instanceId(),x.taskId(),
                x.actionId(),x.actorId(),x.actorDisplayName(),x.actedAt(),WORKFLOW_FENCES));
    }
}
