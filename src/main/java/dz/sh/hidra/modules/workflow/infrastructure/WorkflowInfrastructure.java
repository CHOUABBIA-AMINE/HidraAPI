/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowInfrastructure
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure
 *
 * @Description : Workflow infrastructure constants.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure;

/**
 * Workflow infrastructure constants.
 */
public final class WorkflowInfrastructure {

    public static final String TABLE_PREFIX = "hidra_workflow_";

    private WorkflowInfrastructure() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
