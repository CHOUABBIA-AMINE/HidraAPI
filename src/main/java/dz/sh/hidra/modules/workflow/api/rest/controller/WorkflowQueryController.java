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
import java.util.List;
import java.util.Objects;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/workflow")
public class WorkflowQueryController {

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
    public Page<TaskView> tasks(
            @RequestParam(defaultValue = "assigned") String view,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size
    ) {
        return useCase.tasks(actorResolver.currentActorId().value(), view, page, size);
    }

    @GetMapping("/tasks/{id}")
    public TaskView task(@PathVariable String id) {
        return useCase.task(id);
    }

    @GetMapping("/tasks/{id}/available-actions")
    public List<AvailableActionView> availableActions(@PathVariable String id, Authentication authentication) {
        return useCase.availableActions(
                id,
                actorResolver.currentActorId().value(),
                permissionResolver.resolve(authentication)
        );
    }

    @GetMapping("/instances/{id}")
    public InstanceView instance(@PathVariable String id) {
        return useCase.instance(id);
    }

    @GetMapping("/instances/{id}/timeline")
    public List<TimelineEntry> timeline(@PathVariable String id) {
        return useCase.timeline(id);
    }
}
