/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmSuppressionApiExceptionHandler
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-02
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.api.rest
 *
 * @Description : Maps Alarm suppression conflicts to a stable HTTP 409 contract.
 *
 */
package dz.sh.hidra.modules.alarm.api.rest;

import dz.sh.hidra.modules.alarm.api.rest.controller.AlarmSuppressionController;
import dz.sh.hidra.modules.alarm.domain.exception.AlarmSuppressionConflictException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(assignableTypes = AlarmSuppressionController.class)
public final class AlarmSuppressionApiExceptionHandler {

    @ExceptionHandler(AlarmSuppressionConflictException.class)
    public ProblemDetail conflict(AlarmSuppressionConflictException exception) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        detail.setTitle("ALARM_SUPPRESSION_CONFLICT");
        detail.setDetail(exception.getMessage());
        detail.setProperty("code", "ALARM_SUPPRESSION_CONFLICT");
        return detail;
    }
}
