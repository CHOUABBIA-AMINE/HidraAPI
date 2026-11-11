/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningRevisionApiExceptionHandler
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-11
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.api.rest
 *
 * @Description : Stable HTTP mapping for planning revision concurrency conflicts.
 *
 */
package dz.sh.hidra.modules.planning.api.rest;

import dz.sh.hidra.modules.planning.api.rest.controller.PlanRevisionCommandController;
import dz.sh.hidra.modules.planning.api.rest.controller.PlanningDraftController;
import dz.sh.hidra.modules.planning.api.rest.controller.PlanningRevisionSubmissionController;
import dz.sh.hidra.modules.planning.api.rest.controller.PlanningApprovalController;
import dz.sh.hidra.modules.planning.domain.exception.PlanningRevisionConflictException;
import dz.sh.hidra.modules.planning.domain.exception.InvalidPlanningValueException;
import dz.sh.hidra.modules.workflow.application.contract.planning.PlanningWorkflowContract;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(assignableTypes = {PlanRevisionCommandController.class, PlanningDraftController.class, PlanningRevisionSubmissionController.class, PlanningApprovalController.class})
public final class PlanningRevisionApiExceptionHandler {

    @ExceptionHandler(InvalidPlanningValueException.class)
    public ProblemDetail invalidValue(InvalidPlanningValueException exception) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
        detail.setTitle("INVALID_PLANNING_VALUE");
        detail.setProperty("code", "INVALID_PLANNING_VALUE");
        return detail;
    }

    @ExceptionHandler(PlanningRevisionConflictException.class)
    public ProblemDetail conflict(PlanningRevisionConflictException exception) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        detail.setTitle("PLANNING_REVISION_CONFLICT");
        detail.setDetail(exception.getMessage());
        detail.setProperty("code", "PLANNING_REVISION_CONFLICT");
        return detail;
    }

    @ExceptionHandler(PlanningWorkflowContract.TransitionConflict.class)
    public ProblemDetail workflowTaskConflict(PlanningWorkflowContract.TransitionConflict exception) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        detail.setTitle("PLANNING_REVISION_CONFLICT");
        // The authenticated caller must refresh the task; never disclose an internal Workflow identifier.
        detail.setDetail("Planning approval task changed. Refresh the current task before retrying.");
        detail.setProperty("code", "PLANNING_REVISION_CONFLICT");
        return detail;
    }
}
