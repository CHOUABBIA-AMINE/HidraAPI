/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Interface
 * @Layer       : API
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest.controller
 *
 * @Description : Framework-neutral workflow controller contract.
 *
 */
package dz.sh.hidra.modules.workflow.api.rest.controller;
import dz.sh.hidra.modules.workflow.api.rest.request.*;
import dz.sh.hidra.modules.workflow.api.rest.response.*;

/**
 * Framework-neutral workflow controller contract.
 */
public interface WorkflowController {
    WorkflowTaskResponse createWorkflowTask(CreateWorkflowTaskRequest request);
    WorkflowActionResponse recordWorkflowAction(RecordWorkflowActionRequest request);
    WorkflowInstanceResponse startWorkflowInstance(StartWorkflowInstanceRequest request);
}
