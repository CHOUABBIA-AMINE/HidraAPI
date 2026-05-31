/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ApiErrorFactory
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.exception
 *
 * @Description : Converts platform and framework exceptions into kernel API error responses.
 *
 */
package dz.sh.hidra.platform.exception;

import dz.sh.hidra.kernel.api.error.ApiErrorCode;
import dz.sh.hidra.kernel.api.error.ApiErrorResponse;
import dz.sh.hidra.kernel.api.error.ValidationErrorDetail;
import dz.sh.hidra.kernel.domain.exception.DomainException;
import dz.sh.hidra.platform.observability.correlation.CorrelationContext;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class ApiErrorFactory {

    private static final String VALIDATION_FAILED_MESSAGE = "Request validation failed.";
    private static final String AUTHENTICATION_FAILED_MESSAGE = "Authentication is required to access this resource.";
    private static final String AUTHORIZATION_FAILED_MESSAGE = "Access is denied for this resource.";
    private static final String INFRASTRUCTURE_FAILED_MESSAGE = "A technical infrastructure error occurred.";
    private static final String INTERNAL_FAILED_MESSAGE = "An unexpected internal error occurred.";

    public ApiErrorResponse validationError(String path, List<ValidationErrorDetail> details) {
        return ApiErrorResponse.withDetails(
                HttpStatus.BAD_REQUEST.value(),
                ApiErrorCode.VALIDATION_ERROR,
                VALIDATION_FAILED_MESSAGE,
                path,
                currentCorrelationId(),
                details);
    }

    public ApiErrorResponse domainError(DomainException exception, String path) {
        return ApiErrorResponse.of(
                HttpStatus.BAD_REQUEST.value(),
                ApiErrorCode.DOMAIN_ERROR,
                safeMessage(exception, "A domain rule was violated."),
                path,
                currentCorrelationId());
    }

    public ApiErrorResponse authenticationError(AuthenticationException exception, String path) {
        return ApiErrorResponse.of(
                HttpStatus.UNAUTHORIZED.value(),
                ApiErrorCode.AUTHENTICATION_ERROR,
                AUTHENTICATION_FAILED_MESSAGE,
                path,
                currentCorrelationId());
    }

    public ApiErrorResponse authorizationError(AccessDeniedException exception, String path) {
        return ApiErrorResponse.of(
                HttpStatus.FORBIDDEN.value(),
                ApiErrorCode.AUTHORIZATION_ERROR,
                AUTHORIZATION_FAILED_MESSAGE,
                path,
                currentCorrelationId());
    }

    public ApiErrorResponse infrastructureError(InfrastructureException exception, String path) {
        return ApiErrorResponse.of(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ApiErrorCode.INFRASTRUCTURE_ERROR,
                INFRASTRUCTURE_FAILED_MESSAGE,
                path,
                currentCorrelationId());
    }

    public ApiErrorResponse platformError(PlatformException exception, String path) {
        return ApiErrorResponse.of(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ApiErrorCode.INFRASTRUCTURE_ERROR,
                safeMessage(exception, INFRASTRUCTURE_FAILED_MESSAGE),
                path,
                currentCorrelationId());
    }

    public ApiErrorResponse internalError(Exception exception, String path) {
        return ApiErrorResponse.of(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ApiErrorCode.INTERNAL_ERROR,
                INTERNAL_FAILED_MESSAGE,
                path,
                currentCorrelationId());
    }

    private static String currentCorrelationId() {
        return CorrelationContext.current()
                .map(CorrelationContext::correlationId)
                .map(correlationId -> correlationId.value())
                .orElse(null);
    }

    private static String safeMessage(Exception exception, String defaultMessage) {
        if (exception == null || exception.getMessage() == null || exception.getMessage().isBlank()) {
            return defaultMessage;
        }
        return exception.getMessage();
    }
}
