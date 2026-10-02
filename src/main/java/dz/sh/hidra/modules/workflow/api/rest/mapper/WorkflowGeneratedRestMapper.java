/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowGeneratedRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Interface
 * @Layer       : API
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest.mapper
 *
 * @Description : Generates exact workflow API/application boundary mappings at compile time.
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
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * Generates only the HRA-070-approved exact workflow boundary mappings.
 */
@Mapper(
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface WorkflowGeneratedRestMapper {

    WorkflowGeneratedRestMapper INSTANCE = Mappers.getMapper(WorkflowGeneratedRestMapper.class);

    CreateWorkflowTaskCommand toCommand(CreateWorkflowTaskRequest request);

    RecordWorkflowActionCommand toCommand(RecordWorkflowActionRequest request);

    StartWorkflowInstanceCommand toCommand(StartWorkflowInstanceRequest request);

    WorkflowActionResponse toResponse(WorkflowActionSummaryDto dto);

    WorkflowInstanceResponse toResponse(WorkflowInstanceSummaryDto dto);

    WorkflowTaskResponse toResponse(WorkflowTaskSummaryDto dto);
}
