/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InvalidWorkflowValueException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.exception
 *
 * @Description : Invalid workflow value exception.
 *
 */
package dz.sh.hidra.modules.workflow.domain.exception;

/**
 * Raised when a workflow value is invalid.
 */
public class InvalidWorkflowValueException extends WorkflowDomainException {

    private static final long serialVersionUID = -6446743762144807587L;

	public InvalidWorkflowValueException(String message) {
        super(message);
    }
}
