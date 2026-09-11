/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTransitionDeniedException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.exception
 *
 * @Description : Signals actor or permission denial for workflow transition execution.
 *
 */
package dz.sh.hidra.modules.workflow.domain.exception;

public final class WorkflowTransitionDeniedException extends WorkflowDomainException {

    private static final long serialVersionUID = 1L;

    public WorkflowTransitionDeniedException(String message) {
        super(message);
    }
}
