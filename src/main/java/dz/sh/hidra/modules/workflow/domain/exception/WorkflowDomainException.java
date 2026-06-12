/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowDomainException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.exception
 *
 * @Description : Base workflow domain exception.
 *
 */
package dz.sh.hidra.modules.workflow.domain.exception;

/**
 * Base exception for workflow domain failures.
 */
public class WorkflowDomainException extends RuntimeException {

    private static final long serialVersionUID = 6175467124776102541L;

	public WorkflowDomainException(String message) {
        super(requireMessage(message));
    }

    public WorkflowDomainException(String message, Throwable cause) {
        super(requireMessage(message), cause);
    }

    private static String requireMessage(String message) {
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Workflow exception message must not be null or blank.");
        }
        return message.trim();
    }
}
