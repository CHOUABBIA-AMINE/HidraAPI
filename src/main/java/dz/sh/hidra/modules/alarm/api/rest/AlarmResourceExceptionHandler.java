/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Name        : AlarmResourceExceptionHandler
 * @UpdatedOn   : 2026-11-08
 * @Type        : Class
 * @Layer       : Class
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.api.rest
 * @Description : Masks missing and unauthorized alarm existence.
 */
package dz.sh.hidra.modules.alarm.api.rest;

import dz.sh.hidra.modules.alarm.domain.exception.AlarmResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@RestControllerAdvice
public final class AlarmResourceExceptionHandler {
    @ExceptionHandler(AlarmResourceNotFoundException.class)
    public ProblemDetail notFound(AlarmResourceNotFoundException ignored) {
        ProblemDetail result=ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        result.setTitle("RESOURCE_NOT_FOUND");
        result.setDetail("Resource not found.");
        result.setProperty("code","RESOURCE_NOT_FOUND");
        return result;
    }
}
