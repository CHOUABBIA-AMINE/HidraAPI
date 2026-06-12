/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FindWorkflowInstanceByIdQuery
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.query
 *
 * @Description : Query to find workflow instance by ID.
 *
 */
package dz.sh.hidra.modules.workflow.application.query;

/**
 * Query to find workflow instance by ID.
 */
public record FindWorkflowInstanceByIdQuery(String workflowInstanceId) {
}
