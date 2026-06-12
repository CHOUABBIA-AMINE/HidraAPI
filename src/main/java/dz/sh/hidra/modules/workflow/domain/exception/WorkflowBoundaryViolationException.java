/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowBoundaryViolationException
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.exception
 *
 * @Description : Workflow boundary violation exception.
 *
 */
package dz.sh.hidra.modules.workflow.domain.exception;

/**
 * Raised when workflow attempts to own a target module business fact.
 */
public class WorkflowBoundaryViolationException extends WorkflowDomainException {

    private static final long serialVersionUID = 1360452922776875633L;

	public WorkflowBoundaryViolationException(String message) {
        super(message);
    }
}
