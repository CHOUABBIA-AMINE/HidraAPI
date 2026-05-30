/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PagedApiResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Kernel
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel.api.response
 *
 * @Description : Immutable generic API response wrapper for paged successful operations.
 *
 */
package dz.sh.hidra.kernel.api.response;

import java.time.Instant;
import java.util.List;

public record PagedApiResponse<T>(
        List<T> data,
        int page,
        int size,
        long totalElements,
        int totalPages,
        String correlationId,
        Instant timestamp
) {

    public PagedApiResponse {
        if (data == null) {
            data = List.of();
        } else {
            data = List.copyOf(data);
        }
        if (page < 0) {
            throw new IllegalArgumentException("Page index must not be negative.");
        }
        if (size < 1) {
            throw new IllegalArgumentException("Page size must be at least 1.");
        }
        if (totalElements < 0) {
            throw new IllegalArgumentException("Total elements must not be negative.");
        }
        if (totalPages < 0) {
            throw new IllegalArgumentException("Total pages must not be negative.");
        }
        correlationId = normalize(correlationId);
        if (timestamp == null) {
            throw new IllegalArgumentException("Paged API response timestamp must not be null.");
        }
    }

    public static <T> PagedApiResponse<T> of(
            List<T> data,
            int page,
            int size,
            long totalElements,
            int totalPages,
            String correlationId
    ) {
        return new PagedApiResponse<>(data, page, size, totalElements, totalPages, correlationId, Instant.now());
    }

    public static <T> PagedApiResponse<T> empty(int page, int size, String correlationId) {
        return new PagedApiResponse<>(List.of(), page, size, 0, 0, correlationId, Instant.now());
    }

    public boolean empty() {
        return data.isEmpty();
    }

    private static String normalize(String value) {
        if (value == null) {
            return null;
        }
        String normalized = value.trim();
        return normalized.isBlank() ? null : normalized;
    }
}
