/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ApiErrorResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Kernel
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel.api.error
 *
 * @Description : Immutable framework-independent API error response body.
 *
 */
package dz.sh.hidra.kernel.api.error;

import java.time.Instant;
import java.util.List;

public record ApiErrorResponse(
        Instant timestamp,
        int status,
        ApiErrorCode error,
        String message,
        String path,
        String correlationId,
        List<ValidationErrorDetail> details
) {

    public ApiErrorResponse {
        if (timestamp == null) {
            throw new IllegalArgumentException("API error timestamp must not be null.");
        }
        if (error == null) {
            throw new IllegalArgumentException("API error code must not be null.");
        }
        message = normalize(message);
        path = normalize(path);
        correlationId = normalize(correlationId);
        if (details == null) {
            details = List.of();
        } else {
            details = List.copyOf(details);
        }
    }

    public static ApiErrorResponse of(
            int status,
            ApiErrorCode error,
            String message,
            String path,
            String correlationId
    ) {
        return new ApiErrorResponse(Instant.now(), status, error, message, path, correlationId, List.of());
    }

    public static ApiErrorResponse withDetails(
            int status,
            ApiErrorCode error,
            String message,
            String path,
            String correlationId,
            List<ValidationErrorDetail> details
    ) {
        return new ApiErrorResponse(Instant.now(), status, error, message, path, correlationId, details);
    }

    public boolean hasDetails() {
        return !details.isEmpty();
    }

    private static String normalize(String value) {
        if (value == null) {
            return null;
        }
        String normalized = value.trim();
        return normalized.isBlank() ? null : normalized;
    }
}
