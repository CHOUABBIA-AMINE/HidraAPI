/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowController
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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

import dz.sh.hidra.modules.workflow.api.rest.request.CreateWorkflowTaskRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.StartWorkflowInstanceRequest;
import dz.sh.hidra.modules.workflow.api.rest.response.WorkflowInstanceResponse;
import dz.sh.hidra.modules.workflow.api.rest.response.WorkflowTaskResponse;

/**
 * Framework-neutral workflow controller contract.
 */
public interface WorkflowController {

    WorkflowInstanceResponse startWorkflowInstance(StartWorkflowInstanceRequest request);

    WorkflowTaskResponse createWorkflowTask(CreateWorkflowTaskRequest request);
}
