/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowDefinitionDiscoveryController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-06
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest.controller
 *
 * @Description : Publishes only target-specific, source-authorized active Workflow definitions.
 *
 */
package dz.sh.hidra.modules.workflow.api.rest.controller;

import dz.sh.hidra.modules.workflow.application.port.in.WorkflowDefinitionDiscoveryUseCase;
import dz.sh.hidra.modules.workflow.api.rest.response.WorkflowDefinitionDiscoveryResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Objects;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/workflow/definitions")
@Tag(name = "Workflow Definition Discovery", description = "Owner-authorized supported active process bindings.")
public final class WorkflowDefinitionDiscoveryController {
    private final WorkflowDefinitionDiscoveryUseCase discovery;
    public WorkflowDefinitionDiscoveryController(WorkflowDefinitionDiscoveryUseCase discovery) {
        this.discovery = Objects.requireNonNull(discovery);
    }
    @GetMapping("/installed-characteristic-link-target")
    @dz.sh.hidra.platform.permissions.NativeProtectedOperation
    @Operation(operationId = "readInstalledCharacteristicLinkWorkflowTarget",
            summary = "Read owner-authorized Workflow target addressing for one exact installed link revision")
    public ResponseEntity<WorkflowDefinitionDiscoveryResponses.TargetHandoff> installedCharacteristicLinkTarget(
            @RequestParam String companyPartyId, @RequestParam String installedUnitId,
            @RequestParam String linkId, @RequestParam long revisionId) {
        return discovery.installedCharacteristicLinkTarget(companyPartyId,installedUnitId,linkId,revisionId)
                .map(t -> ResponseEntity.ok(new WorkflowDefinitionDiscoveryResponses.TargetHandoff(
                        t.targetModule(),t.targetTypeId(),t.workflowPurposeId(),t.targetId(),
                        t.targetLocator(),t.revisionId(),t.digestFormat())))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Discover available active Workflow definitions for one verified target")
    public List<WorkflowDefinitionDiscoveryResponses.Definition> definitions(
            @RequestParam String targetModule,
            @RequestParam String targetTypeId,
            @RequestParam String workflowPurposeId,
            @RequestParam String targetId,
            @RequestParam String targetLocator) {
        return discovery.discover(targetModule, targetTypeId, workflowPurposeId, targetId, targetLocator)
                .stream().map(d -> new WorkflowDefinitionDiscoveryResponses.Definition(
                        d.id(), d.code(), d.nameFr(), d.version(), d.targetModule(),
                        d.targetTypeId(), d.workflowPurposeId(), d.firstStepId(),
                        d.assignmentChoices().stream()
                                .map(a -> new WorkflowDefinitionDiscoveryResponses.Assignment(
                                        a.actorId(), a.username(), a.displayName())).toList(),
                        d.availableDecisions().stream()
                                .map(a -> new WorkflowDefinitionDiscoveryResponses.Decision(
                                        a.transitionId(), a.decision(), a.fromStepId(), a.toStepId(),
                                        a.reasonRequired(), a.commentRequired(), a.requiredPermissionCode())).toList()))
                .toList();
    }
}
