/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkbenchExceptionHandler
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.workbench
 *
 * @Description : Returns fixed workbench error messages without query values or provider details.
 *
 */
package dz.sh.hidra.platform.workbench;

import java.util.NoSuchElementException;
import java.net.URI;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** Also sanitizes request parsing/binding failures that occur before service validation. */
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(assignableTypes = HidraOperationalWorkbenchController.class)
public final class WorkbenchExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ProblemDetail handle(Exception exception) {
        HttpStatusCode status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (exception instanceof AccessDeniedException) {
            status = HttpStatus.FORBIDDEN;
        } else if (exception instanceof NoSuchElementException) {
            status = HttpStatus.NOT_FOUND;
        } else if (exception instanceof IllegalArgumentException
                || exception instanceof TypeMismatchException
                || exception instanceof HttpMessageNotReadableException
                || exception instanceof ConstraintViolationException) {
            status = HttpStatus.BAD_REQUEST;
        } else if (exception instanceof ErrorResponse error && error.getStatusCode().is4xxClientError()) {
            status = error.getStatusCode();
        }
        String message = status.value() == 404 ? "Workbench resource unavailable."
                : status.value() == 403 ? "Workbench access denied."
                : status.is4xxClientError() ? "Invalid workbench request." : "Workbench read failed.";
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, message);
        problem.setTitle("WORKBENCH_ERROR");
        problem.setInstance(URI.create("/api/v1/workbench"));
        return problem;
    }
}
