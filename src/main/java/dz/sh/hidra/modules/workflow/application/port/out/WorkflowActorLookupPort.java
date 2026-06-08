/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowActorLookupPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.port.out
 *
 * @Description : Outbound port for workflow actor lookup.
 *
 */
package dz.sh.hidra.modules.workflow.application.port.out;

import dz.sh.hidra.modules.workflow.domain.value.WorkflowActorReference;

/**
 * Outbound port for workflow actor lookup.
 *
 * <p>Architecture role:
 * Outbound application port for validating workflow actor references without importing identity domain or infrastructure classes.
 */
public interface WorkflowActorLookupPort {

    boolean exists(WorkflowActorReference actor);

    WorkflowActorReference resolve(WorkflowActorReference actor);
}
