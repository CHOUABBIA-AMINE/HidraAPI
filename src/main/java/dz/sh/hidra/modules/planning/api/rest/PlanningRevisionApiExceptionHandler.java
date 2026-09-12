/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningRevisionApiExceptionHandler
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
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
import dz.sh.hidra.modules.planning.domain.exception.PlanningRevisionConflictException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(assignableTypes = PlanRevisionCommandController.class)
public final class PlanningRevisionApiExceptionHandler {

    @ExceptionHandler(PlanningRevisionConflictException.class)
    public ProblemDetail conflict(PlanningRevisionConflictException exception) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        detail.setTitle("PLANNING_REVISION_CONFLICT");
        detail.setDetail(exception.getMessage());
        detail.setProperty("code", "PLANNING_REVISION_CONFLICT");
        return detail;
    }
}
