/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SpringWorkflowController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest.controller
 *
 * @Description : Spring MVC adapter exposing workflow REST endpoints.
 *
 */
package dz.sh.hidra.modules.workflow.api.rest.controller;
import dz.sh.hidra.modules.workflow.api.rest.mapper.WorkflowRestMapper;
import dz.sh.hidra.modules.workflow.api.rest.request.CreateWorkflowTaskRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.RecordWorkflowActionRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.StartWorkflowInstanceRequest;
import dz.sh.hidra.modules.workflow.api.rest.response.WorkflowActionResponse;
import dz.sh.hidra.modules.workflow.api.rest.response.WorkflowInstanceResponse;
import dz.sh.hidra.modules.workflow.api.rest.response.WorkflowTaskResponse;
import dz.sh.hidra.modules.workflow.application.port.in.CreateWorkflowTaskUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.RecordWorkflowActionUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.StartWorkflowInstanceUseCase;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring MVC adapter exposing workflow REST endpoints.
 */
@RestController
@Validated
@RequestMapping("/api/v1/workflow")
public class SpringWorkflowController implements WorkflowController {

    private final CreateWorkflowTaskUseCase createWorkflowTaskUseCase;
    private final RecordWorkflowActionUseCase recordWorkflowActionUseCase;
    private final StartWorkflowInstanceUseCase startWorkflowInstanceUseCase;

    public SpringWorkflowController(
            CreateWorkflowTaskUseCase createWorkflowTaskUseCase,
            RecordWorkflowActionUseCase recordWorkflowActionUseCase,
            StartWorkflowInstanceUseCase startWorkflowInstanceUseCase
    ) {
        this.createWorkflowTaskUseCase = Objects.requireNonNull(createWorkflowTaskUseCase, "CreateWorkflowTaskUseCase must not be null.");
        this.recordWorkflowActionUseCase = Objects.requireNonNull(recordWorkflowActionUseCase, "RecordWorkflowActionUseCase must not be null.");
        this.startWorkflowInstanceUseCase = Objects.requireNonNull(startWorkflowInstanceUseCase, "StartWorkflowInstanceUseCase must not be null.");
    }

    @GetMapping("/capabilities")
    public Map<String, Object> capabilities() {
        return Map.of(
                "module", "workflow",
                "mission", "Coordinate operational workflows, tasks, and actions across Hidra modules.",
                "objectives", List.of(
                "Start workflow instances.",
                "Create workflow tasks.",
                "Record workflow actions."
        ),
                "operations", List.of(
                "createWorkflowTask",
                "recordWorkflowAction",
                "startWorkflowInstance"
        ),
                "resourceEndpoints", List.of(
                "POST /api/v1/workflow/tasks",
                "POST /api/v1/workflow/actions",
                "POST /api/v1/workflow/instances"
        )
        );
    }

    @Override
    @PostMapping({"/create-workflow-task", "/tasks"})
    public WorkflowTaskResponse createWorkflowTask(@Valid @RequestBody CreateWorkflowTaskRequest request) {
        Objects.requireNonNull(request, "CreateWorkflowTaskRequest must not be null.");
        return WorkflowRestMapper.toResponse(createWorkflowTaskUseCase.createWorkflowTask(WorkflowRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping({"/record-workflow-action", "/actions"})
    public WorkflowActionResponse recordWorkflowAction(@Valid @RequestBody RecordWorkflowActionRequest request) {
        Objects.requireNonNull(request, "RecordWorkflowActionRequest must not be null.");
        return WorkflowRestMapper.toResponse(recordWorkflowActionUseCase.recordWorkflowAction(WorkflowRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping({"/start-workflow-instance", "/instances"})
    public WorkflowInstanceResponse startWorkflowInstance(@Valid @RequestBody StartWorkflowInstanceRequest request) {
        Objects.requireNonNull(request, "StartWorkflowInstanceRequest must not be null.");
        return WorkflowRestMapper.toResponse(startWorkflowInstanceUseCase.startWorkflowInstance(WorkflowRestMapper.toCommand(request)));
    }

}
