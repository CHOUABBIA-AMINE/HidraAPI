/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowAuthorityLookupPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.port.out
 *
 * @Description : Outbound port for workflow authority lookup.
 *
 */
package dz.sh.hidra.modules.workflow.application.port.out;

import dz.sh.hidra.modules.workflow.domain.value.WorkflowActorReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDecision;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowOrganizationReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTargetReference;

/**
 * Outbound port for workflow authority lookup.
 *
 * <p>Architecture role:
 * Outbound application port for workflow authorization and authority checks without importing identity or organization implementation classes.
 */
public interface WorkflowAuthorityLookupPort {

    boolean canActOnTarget(
            WorkflowActorReference actor,
            WorkflowOrganizationReference organization,
            WorkflowTargetReference target,
            WorkflowDecision decision);

    boolean canClaimTask(
            WorkflowActorReference actor,
            WorkflowOrganizationReference organization,
            WorkflowTargetReference target);
}
