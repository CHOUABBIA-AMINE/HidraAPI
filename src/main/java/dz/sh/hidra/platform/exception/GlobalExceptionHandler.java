/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : GlobalExceptionHandler
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.exception
 *
 * @Description : Spring REST controller advice that returns kernel API error responses.
 *
 */
package dz.sh.hidra.platform.exception;

import dz.sh.hidra.kernel.api.error.ApiErrorResponse;
import dz.sh.hidra.kernel.api.error.ValidationErrorDetail;
import dz.sh.hidra.kernel.domain.exception.DomainException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final ApiErrorFactory apiErrorFactory;

    public GlobalExceptionHandler(ApiErrorFactory apiErrorFactory) {
        this.apiErrorFactory = apiErrorFactory;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception,
            HttpServletRequest request) {
        return response(apiErrorFactory.validationError(requestPath(request), bindingDetails(exception)));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiErrorResponse> handleBindException(
            BindException exception,
            HttpServletRequest request) {
        return response(apiErrorFactory.validationError(requestPath(request), bindingDetails(exception)));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleConstraintViolation(
            ConstraintViolationException exception,
            HttpServletRequest request) {
        List<ValidationErrorDetail> details = exception.getConstraintViolations().stream()
                .map(GlobalExceptionHandler::constraintViolationDetail)
                .toList();
        return response(apiErrorFactory.validationError(requestPath(request), details));
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ApiErrorResponse> handleDomainException(
            DomainException exception,
            HttpServletRequest request) {
        return response(apiErrorFactory.domainError(exception, requestPath(request)));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiErrorResponse> handleAuthenticationException(
            AuthenticationException exception,
            HttpServletRequest request) {
        return response(apiErrorFactory.authenticationError(exception, requestPath(request)));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrorResponse> handleAccessDeniedException(
            AccessDeniedException exception,
            HttpServletRequest request) {
        return response(apiErrorFactory.authorizationError(exception, requestPath(request)));
    }

    @ExceptionHandler(InfrastructureException.class)
    public ResponseEntity<ApiErrorResponse> handleInfrastructureException(
            InfrastructureException exception,
            HttpServletRequest request) {
        return response(apiErrorFactory.infrastructureError(exception, requestPath(request)));
    }

    @ExceptionHandler(PlatformException.class)
    public ResponseEntity<ApiErrorResponse> handlePlatformException(
            PlatformException exception,
            HttpServletRequest request) {
        return response(apiErrorFactory.platformError(exception, requestPath(request)));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(
            Exception exception,
            HttpServletRequest request) {
        return response(apiErrorFactory.internalError(exception, requestPath(request)));
    }

    private static ResponseEntity<ApiErrorResponse> response(ApiErrorResponse errorResponse) {
        return ResponseEntity.status(errorResponse.status()).body(errorResponse);
    }

    private static List<ValidationErrorDetail> bindingDetails(BindException exception) {
        return exception.getBindingResult().getAllErrors().stream()
                .map(GlobalExceptionHandler::bindingDetail)
                .toList();
    }

    private static ValidationErrorDetail bindingDetail(ObjectError error) {
        if (error instanceof FieldError fieldError) {
            return new ValidationErrorDetail(
                    fieldError.getField(),
                    fieldError.getDefaultMessage(),
                    rejectedValue(fieldError.getRejectedValue()));
        }
        return new ValidationErrorDetail(error.getObjectName(), error.getDefaultMessage(), null);
    }

    private static ValidationErrorDetail constraintViolationDetail(ConstraintViolation<?> violation) {
        return new ValidationErrorDetail(
                violation.getPropertyPath() == null ? null : violation.getPropertyPath().toString(),
                violation.getMessage(),
                rejectedValue(violation.getInvalidValue()));
    }

    private static String rejectedValue(Object value) {
        return value == null ? null : String.valueOf(value);
    }

    private static String requestPath(HttpServletRequest request) {
        return request == null ? null : request.getRequestURI();
    }
}
