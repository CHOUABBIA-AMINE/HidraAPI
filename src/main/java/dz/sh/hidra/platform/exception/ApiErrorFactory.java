/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ApiErrorFactory
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.exception
 *
 * @Description : Maps platform exceptions to stable technical API error payloads.
 *
 */
package dz.sh.hidra.platform.exception;

import java.time.Instant;
import java.util.Objects;

/**
 * Framework-neutral factory for stable technical API error payloads.
 */
public final class ApiErrorFactory {

    private ApiErrorFactory() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static TechnicalApiError from(Throwable throwable, String path, String correlationId) {
        Objects.requireNonNull(throwable, "Throwable must not be null.");
        return new TechnicalApiError(
                technicalCodeFor(throwable),
                normalizeMessage(throwable.getMessage()),
                normalize(path),
                normalize(correlationId),
                Instant.now()
        );
    }

    private static String technicalCodeFor(Throwable throwable) {
        if (throwable instanceof InfrastructureException) {
            return "INFRASTRUCTURE_ERROR";
        }
        if (throwable instanceof PlatformException) {
            return "PLATFORM_ERROR";
        }
        return "INTERNAL_ERROR";
    }

    private static String normalizeMessage(String message) {
        if (message == null || message.isBlank()) {
            return "Unexpected technical failure.";
        }
        return message.trim();
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }

    public record TechnicalApiError(
            String code,
            String message,
            String path,
            String correlationId,
            Instant timestamp
    ) {
    }
}
