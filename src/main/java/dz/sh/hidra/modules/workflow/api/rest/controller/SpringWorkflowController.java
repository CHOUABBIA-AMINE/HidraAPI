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
import dz.sh.hidra.modules.workflow.api.rest.request.StartWorkflowInstanceRequest;
import dz.sh.hidra.modules.workflow.api.rest.response.WorkflowInstanceResponse;
import dz.sh.hidra.modules.workflow.api.rest.response.WorkflowTaskResponse;
import dz.sh.hidra.modules.workflow.application.port.in.CreateWorkflowTaskUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.StartWorkflowInstanceUseCase;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Objects;

/**
 * Spring MVC adapter exposing workflow REST endpoints.
 */
@RestController
@Validated
@RequestMapping("/api/v1/workflow")
public final class SpringWorkflowController implements WorkflowController {

    private final StartWorkflowInstanceUseCase startWorkflowInstanceUseCase;
    private final CreateWorkflowTaskUseCase createWorkflowTaskUseCase;

    public SpringWorkflowController(
            StartWorkflowInstanceUseCase startWorkflowInstanceUseCase,
            CreateWorkflowTaskUseCase createWorkflowTaskUseCase
    ) {
        this.startWorkflowInstanceUseCase = Objects.requireNonNull(startWorkflowInstanceUseCase, "StartWorkflowInstanceUseCase must not be null.");
        this.createWorkflowTaskUseCase = Objects.requireNonNull(createWorkflowTaskUseCase, "CreateWorkflowTaskUseCase must not be null.");
    }


    @Override
    @PostMapping("/start-workflow-instance")
    public WorkflowInstanceResponse startWorkflowInstance(@Valid @RequestBody StartWorkflowInstanceRequest request) {
        Objects.requireNonNull(request, "StartWorkflowInstanceRequest must not be null.");
        return WorkflowRestMapper.toResponse(startWorkflowInstanceUseCase.startWorkflowInstance(WorkflowRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping("/create-workflow-task")
    public WorkflowTaskResponse createWorkflowTask(@Valid @RequestBody CreateWorkflowTaskRequest request) {
        Objects.requireNonNull(request, "CreateWorkflowTaskRequest must not be null.");
        return WorkflowRestMapper.toResponse(createWorkflowTaskUseCase.createWorkflowTask(WorkflowRestMapper.toCommand(request)));
    }

}
