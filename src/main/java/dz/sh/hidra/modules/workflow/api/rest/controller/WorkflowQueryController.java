/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowQueryController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest.controller
 *
 * @Description : Exposes My Tasks, workflow detail/history, and server-defined transition metadata.
 *
 */
package dz.sh.hidra.modules.workflow.api.rest.controller;

import dz.sh.hidra.modules.workflow.application.port.in.WorkflowQueryUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.WorkflowQueryUseCase.AvailableActionView;
import dz.sh.hidra.modules.workflow.application.port.in.WorkflowQueryUseCase.InstanceView;
import dz.sh.hidra.modules.workflow.application.port.in.WorkflowQueryUseCase.Page;
import dz.sh.hidra.modules.workflow.application.port.in.WorkflowQueryUseCase.TaskView;
import dz.sh.hidra.modules.workflow.application.port.in.WorkflowQueryUseCase.TimelineEntry;
import dz.sh.hidra.platform.security.CurrentActorResolver;
import dz.sh.hidra.platform.security.HidraEffectivePermissionResolver;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.util.List;
import java.util.Objects;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/api/v1/workflow")
@Tag(name = "Workflow Queries", description = "Authenticated task inbox, workflow history, and available transition metadata.")
public final class WorkflowQueryController {

    private final WorkflowQueryUseCase useCase;
    private final CurrentActorResolver actorResolver;
    private final HidraEffectivePermissionResolver permissionResolver;

    public WorkflowQueryController(
            WorkflowQueryUseCase useCase,
            CurrentActorResolver actorResolver,
            HidraEffectivePermissionResolver permissionResolver
    ) {
        this.useCase = Objects.requireNonNull(useCase, "WorkflowQueryUseCase must not be null.");
        this.actorResolver = Objects.requireNonNull(actorResolver, "CurrentActorResolver must not be null.");
        this.permissionResolver = Objects.requireNonNull(permissionResolver, "HidraEffectivePermissionResolver must not be null.");
    }

    @GetMapping("/tasks")
    @Operation(summary = "Get authenticated actor task inbox")
    public Page<TaskView> tasks(
            @RequestParam(defaultValue = "assigned") String view,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(200) int size
    ) {
        return useCase.tasks(actorResolver.currentActorId().value(), view, page, size);
    }

    @GetMapping("/tasks/{id}")
    @Operation(summary = "Get workflow task detail")
    public TaskView task(@PathVariable String id) {
        return useCase.task(id);
    }

    @GetMapping("/tasks/{id}/available-actions")
    @Operation(summary = "Get backend-authoritative available task actions")
    public List<AvailableActionView> availableActions(@PathVariable String id, Authentication authentication) {
        return useCase.availableActions(
                id,
                actorResolver.currentActorId().value(),
                permissionResolver.resolve(authentication)
        );
    }

    @GetMapping("/instances/{id}")
    @Operation(summary = "Get workflow instance detail")
    public InstanceView instance(@PathVariable String id) {
        return useCase.instance(id);
    }

    @GetMapping("/instances/{id}/timeline")
    @Operation(summary = "Get workflow instance timeline")
    public List<TimelineEntry> timeline(@PathVariable String id) {
        return useCase.timeline(id);
    }
}
