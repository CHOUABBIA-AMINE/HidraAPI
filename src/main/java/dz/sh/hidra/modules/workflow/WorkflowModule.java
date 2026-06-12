/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowModule
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow
 *
 * @Description : Defines workflow module constants.
 *
 */
package dz.sh.hidra.modules.workflow;

/**
 * Workflow module constants.
 */
public final class WorkflowModule {

    public static final String MODULE_NAME = "workflow";
    public static final String TABLE_PREFIX = "hidra_workflow_";

    private WorkflowModule() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
