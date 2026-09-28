/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest.mapper
 *
 * @Description : Maps workflow REST models to application models.
 *
 */
package dz.sh.hidra.modules.workflow.api.rest.mapper;
import dz.sh.hidra.modules.workflow.api.rest.request.CreateWorkflowTaskRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.RecordWorkflowActionRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.StartWorkflowInstanceRequest;
import dz.sh.hidra.modules.workflow.api.rest.response.WorkflowActionResponse;
import dz.sh.hidra.modules.workflow.api.rest.response.WorkflowInstanceResponse;
import dz.sh.hidra.modules.workflow.api.rest.response.WorkflowTaskResponse;
import dz.sh.hidra.modules.workflow.application.command.CreateWorkflowTaskCommand;
import dz.sh.hidra.modules.workflow.application.command.RecordWorkflowActionCommand;
import dz.sh.hidra.modules.workflow.application.command.StartWorkflowInstanceCommand;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowActionSummaryDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowInstanceSummaryDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowTaskSummaryDto;
import java.util.Objects;

/**
 * Maps workflow REST models to application models.
 */
public final class WorkflowRestMapper {

    private static final WorkflowGeneratedRestMapper GENERATED = WorkflowGeneratedRestMapper.INSTANCE;

    private WorkflowRestMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static CreateWorkflowTaskCommand toCommand(CreateWorkflowTaskRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "CreateWorkflowTaskRequest must not be null."));
    }

    public static RecordWorkflowActionCommand toCommand(RecordWorkflowActionRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "RecordWorkflowActionRequest must not be null."));
    }

    public static StartWorkflowInstanceCommand toCommand(StartWorkflowInstanceRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "StartWorkflowInstanceRequest must not be null."));
    }

    public static WorkflowTaskResponse toResponse(WorkflowTaskSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "WorkflowTaskSummaryDto must not be null."));
    }

    public static WorkflowActionResponse toResponse(WorkflowActionSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "WorkflowActionSummaryDto must not be null."));
    }

    public static WorkflowInstanceResponse toResponse(WorkflowInstanceSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "WorkflowInstanceSummaryDto must not be null."));
    }
}
