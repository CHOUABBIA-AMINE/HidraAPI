/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ApiResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Kernel
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel.api.response
 *
 * @Description : Immutable generic API response wrapper for successful operations.
 *
 */
package dz.sh.hidra.kernel.api.response;

import java.time.Instant;

public record ApiResponse<T>(T data, String message, String correlationId, Instant timestamp) {

    public ApiResponse {
        if (timestamp == null) {
            throw new IllegalArgumentException("API response timestamp must not be null.");
        }
        message = normalize(message);
        correlationId = normalize(correlationId);
    }

    public static <T> ApiResponse<T> of(T data, String message, String correlationId) {
        return new ApiResponse<>(data, message, correlationId, Instant.now());
    }

    public static <T> ApiResponse<T> of(T data, String correlationId) {
        return new ApiResponse<>(data, null, correlationId, Instant.now());
    }

    private static String normalize(String value) {
        if (value == null) {
            return null;
        }
        String normalized = value.trim();
        return normalized.isBlank() ? null : normalized;
    }
}
