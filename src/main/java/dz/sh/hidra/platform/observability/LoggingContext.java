/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LoggingContext
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.observability
 *
 * @Description : Stores safe platform context values for structured logging.
 *
 */
package dz.sh.hidra.platform.observability;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Lightweight framework-neutral logging context.
 *
 * <p>Concrete MDC integration may adapt this context in infrastructure code.</p>
 */
public final class LoggingContext {

    public static final String CORRELATION_ID = "correlationId";
    public static final String REQUEST_ID = "requestId";
    public static final String ACTOR_ID = "actorId";
    public static final String MODULE = "module";
    public static final String OPERATION = "operation";

    private static final ThreadLocal<Map<String, String>> CONTEXT =
            ThreadLocal.withInitial(HashMap::new);

    private LoggingContext() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static void putCorrelationId(String value) {
        put(CORRELATION_ID, value);
    }

    public static void putRequestId(String value) {
        put(REQUEST_ID, value);
    }

    public static void putActorId(String value) {
        put(ACTOR_ID, value);
    }

    public static void putModule(String value) {
        put(MODULE, value);
    }

    public static void putOperation(String value) {
        put(OPERATION, value);
    }

    public static void put(String key, String value) {
        String normalizedKey = normalize(key);
        String normalizedValue = normalize(value);
        if (normalizedKey == null || normalizedValue == null) {
            return;
        }
        CONTEXT.get().put(normalizedKey, SensitiveValueMasker.maskIfSensitive(normalizedKey, normalizedValue));
    }

    public static Optional<String> get(String key) {
        String normalizedKey = normalize(key);
        if (normalizedKey == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(CONTEXT.get().get(normalizedKey));
    }

    public static Map<String, String> snapshot() {
        return Map.copyOf(CONTEXT.get());
    }

    public static void remove(String key) {
        String normalizedKey = normalize(key);
        if (normalizedKey != null) {
            CONTEXT.get().remove(normalizedKey);
        }
    }

    public static void clearPlatformContext() {
        CONTEXT.get().remove(CORRELATION_ID);
        CONTEXT.get().remove(REQUEST_ID);
        CONTEXT.get().remove(ACTOR_ID);
        CONTEXT.get().remove(MODULE);
        CONTEXT.get().remove(OPERATION);
    }

    public static void clearAll() {
        CONTEXT.remove();
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
