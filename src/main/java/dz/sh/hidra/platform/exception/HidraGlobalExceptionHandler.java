/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraGlobalExceptionHandler
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.exception
 *
 * @Description : Maps unhandled application exceptions to stable ProblemDetail responses.
 *
 */
package dz.sh.hidra.platform.exception;


import dz.sh.hidra.kernel.exception.DomainException;
import dz.sh.hidra.platform.observability.LoggingContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.Instant;
import java.util.List;

/**
 * Global REST exception handler for platform and module APIs.
 */
@RestControllerAdvice(basePackages = "dz.sh.hidra")
public final class HidraGlobalExceptionHandler {

    private final boolean includeDebugDetails;

    public HidraGlobalExceptionHandler(
            @Value("${hidra.api.errors.include-debug-details:false}") boolean includeDebugDetails
    ) {
        this.includeDebugDetails = includeDebugDetails;
    }

    @ExceptionHandler(DomainException.class)
    public ProblemDetail handleDomainException(DomainException exception, HttpServletRequest request) {
        return problem(HttpStatus.UNPROCESSABLE_CONTENT, "DOMAIN_ERROR", exception, request);
    }

    @ExceptionHandler(PlatformException.class)
    public ProblemDetail handlePlatformException(PlatformException exception, HttpServletRequest request) {
        return problem(HttpStatus.INTERNAL_SERVER_ERROR, "PLATFORM_ERROR", exception, request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgumentException(IllegalArgumentException exception, HttpServletRequest request) {
        return problem(HttpStatus.BAD_REQUEST, "INVALID_REQUEST", exception, request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpServletRequest request) {
        ProblemDetail detail = problem(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", exception, request);
        List<String> fieldErrors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(HidraGlobalExceptionHandler::formatFieldError)
                .toList();
        detail.setProperty("fieldErrors", fieldErrors);
        return detail;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleConstraintViolation(ConstraintViolationException exception, HttpServletRequest request) {
        return problem(HttpStatus.BAD_REQUEST, "CONSTRAINT_VIOLATION", exception, request);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ProblemDetail handleAuthenticationException(AuthenticationException exception, HttpServletRequest request) {
        return problem(HttpStatus.UNAUTHORIZED, "AUTHENTICATION_REQUIRED", exception, request);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ProblemDetail handleAccessDeniedException(AccessDeniedException exception, HttpServletRequest request) {
        return problem(HttpStatus.FORBIDDEN, "ACCESS_DENIED", exception, request);
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleUnexpectedException(Exception exception, HttpServletRequest request) {
        return problem(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR", exception, request);
    }

    private ProblemDetail problem(HttpStatus status, String code, Exception exception, HttpServletRequest request) {
        ProblemDetail detail = ProblemDetail.forStatus(status);
        detail.setTitle(code);
        detail.setDetail(safeMessage(exception));
        detail.setInstance(URI.create(request.getRequestURI()));
        detail.setProperty("code", code);
        detail.setProperty("path", request.getRequestURI());
        detail.setProperty("timestamp", Instant.now().toString());
        LoggingContext.get(LoggingContext.CORRELATION_ID).ifPresent(value -> detail.setProperty("correlationId", value));
        LoggingContext.get(LoggingContext.REQUEST_ID).ifPresent(value -> detail.setProperty("requestId", value));
        if (includeDebugDetails) {
            detail.setProperty("exception", exception.getClass().getName());
        }
        return detail;
    }

    private static String safeMessage(Exception exception) {
        String message = exception.getMessage();
        if (message == null || message.isBlank()) {
            return "Unexpected error.";
        }
        return message.trim();
    }

    private static String formatFieldError(FieldError error) {
        return error.getField() + ": " + (error.getDefaultMessage() == null ? "invalid value" : error.getDefaultMessage());
    }
}
