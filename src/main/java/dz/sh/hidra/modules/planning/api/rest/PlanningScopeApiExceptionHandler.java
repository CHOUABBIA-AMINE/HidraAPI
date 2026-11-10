/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningScopeApiExceptionHandler
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-10
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.api.rest
 *
 * @Description : Maps scoped Planning creation conflicts.
 *
 */
package dz.sh.hidra.modules.planning.api.rest;

import dz.sh.hidra.modules.planning.api.rest.controller.PlanningPeriodV2Controller;
import dz.sh.hidra.modules.planning.domain.exception.PlanningPeriodCodeConflictException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(assignableTypes = PlanningPeriodV2Controller.class)
public class PlanningScopeApiExceptionHandler {

    @ExceptionHandler(PlanningPeriodCodeConflictException.class)
    public ProblemDetail conflict(PlanningPeriodCodeConflictException exception) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, exception.getMessage());
        detail.setTitle("PLANNING_PERIOD_CODE_CONFLICT");
        detail.setProperty("code", "PLANNING_PERIOD_CODE_CONFLICT");
        return detail;
    }
}
