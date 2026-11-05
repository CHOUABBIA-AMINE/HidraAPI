/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTransitionApiExceptionHandler
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest
 *
 * @Description : Stable HTTP mapping for transition conflict and authorization failures.
 *
 */
package dz.sh.hidra.modules.workflow.api.rest;

import dz.sh.hidra.modules.workflow.api.rest.controller.WorkflowTransitionController;
import dz.sh.hidra.modules.workflow.api.rest.controller.SpringWorkflowController;
import dz.sh.hidra.modules.workflow.api.rest.controller.WorkflowQueryController;
import dz.sh.hidra.modules.workflow.domain.exception.WorkflowDomainException;
import dz.sh.hidra.modules.workflow.domain.exception.WorkflowTransitionConflictException;
import dz.sh.hidra.modules.workflow.domain.exception.WorkflowTransitionDeniedException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(assignableTypes = {
        WorkflowTransitionController.class, SpringWorkflowController.class, WorkflowQueryController.class
})
public final class WorkflowTransitionApiExceptionHandler {

    /** Stable client error instead of leaking a 500 for a rejected Workflow command. */
    @ExceptionHandler(WorkflowDomainException.class)
    public ProblemDetail invalid(WorkflowDomainException exception) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        detail.setTitle("WORKFLOW_INVALID_OPERATION");
        detail.setDetail("Workflow request was rejected by its owner invariants.");
        detail.setProperty("code", "WORKFLOW_INVALID_OPERATION");
        return detail;
    }

    @ExceptionHandler(WorkflowTransitionConflictException.class)
    public ProblemDetail conflict(WorkflowTransitionConflictException exception) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        detail.setTitle("WORKFLOW_TRANSITION_CONFLICT");
        detail.setDetail(exception.getMessage());
        detail.setProperty("code", "WORKFLOW_TRANSITION_CONFLICT");
        return detail;
    }

    @ExceptionHandler(WorkflowTransitionDeniedException.class)
    public ProblemDetail forbidden(WorkflowTransitionDeniedException exception) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);
        detail.setTitle("WORKFLOW_TRANSITION_DENIED");
        detail.setDetail(exception.getMessage());
        detail.setProperty("code", "WORKFLOW_TRANSITION_DENIED");
        return detail;
    }
}
