/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningApprovalApiExceptionHandler
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.api.rest
 *
 * @Description : Translates deterministic planning approval failures into stable HTTP problem responses.
 *
 */
package dz.sh.hidra.modules.planning.api.rest;

import dz.sh.hidra.modules.planning.api.rest.controller.PlanningApprovalController;
import dz.sh.hidra.modules.planning.application.PlanningApprovalException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = PlanningApprovalController.class)
public final class PlanningApprovalApiExceptionHandler {

    @ExceptionHandler(PlanningApprovalException.class)
    public ProblemDetail handle(PlanningApprovalException exception) {
        HttpStatus status = switch (exception.kind()) {
            case NOT_FOUND -> HttpStatus.NOT_FOUND;
            case BOUNDARY -> HttpStatus.BAD_REQUEST;
            case DENIED -> HttpStatus.FORBIDDEN;
            case CONFLICT -> HttpStatus.CONFLICT;
        };
        ProblemDetail detail = ProblemDetail.forStatus(status);
        detail.setTitle("PLANNING_APPROVAL_" + exception.kind().name());
        detail.setDetail(exception.getMessage());
        return detail;
    }
}
