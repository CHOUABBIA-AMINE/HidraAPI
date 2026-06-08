/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTargetLookupPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.port.out
 *
 * @Description : Outbound port for workflow target lookup.
 *
 */
package dz.sh.hidra.modules.workflow.application.port.out;

import dz.sh.hidra.modules.workflow.domain.value.WorkflowTargetReference;

/**
 * Outbound port for workflow target lookup.
 *
 * <p>Architecture role:
 * Outbound application port for validating neutral workflow targets. Implementations may call telemetry application read ports but must not expose telemetry domain or infrastructure types to workflow.
 */
public interface WorkflowTargetLookupPort {

    boolean exists(WorkflowTargetReference target);

    boolean canStartWorkflow(WorkflowTargetReference target);

    WorkflowTargetReference resolve(WorkflowTargetReference target);
}
