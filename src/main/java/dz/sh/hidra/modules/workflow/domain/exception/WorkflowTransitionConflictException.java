/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTransitionConflictException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.exception
 *
 * @Description : Signals stale or already-consumed workflow transition state.
 *
 */
package dz.sh.hidra.modules.workflow.domain.exception;

public final class WorkflowTransitionConflictException extends WorkflowDomainException {

    private static final long serialVersionUID = 1L;

    public WorkflowTransitionConflictException(String message) {
        super(message);
    }
}
