/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowAuditEventPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.port.out
 *
 * @Description : Outbound port for workflow audit event publication.
 *
 */
package dz.sh.hidra.modules.workflow.application.port.out;

import dz.sh.hidra.modules.workflow.domain.model.WorkflowAction;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowInstance;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowTask;

/**
 * Outbound port for workflow audit event publication.
 *
 * <p>Architecture role:
 * Outbound application port for audit-ready workflow events. V1 may use a no-op or logging adapter; full audit hardening is implemented later.
 */
public interface WorkflowAuditEventPort {

    void recordWorkflowStarted(WorkflowInstance instance);

    void recordWorkflowAction(WorkflowAction action);

    void recordWorkflowTaskChanged(WorkflowTask task);
}
