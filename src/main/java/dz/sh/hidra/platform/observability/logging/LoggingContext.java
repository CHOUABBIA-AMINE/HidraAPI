/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LoggingContext
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.observability.logging
 *
 * @Description : Writes safe platform context values to the mapped diagnostic context.
 *
 */
package dz.sh.hidra.platform.observability.logging;

import org.slf4j.MDC;

public final class LoggingContext {

    public static final String CORRELATION_ID = "correlationId";
    public static final String REQUEST_ID = "requestId";
    public static final String ACTOR_ID = "actorId";
    public static final String MODULE = "module";
    public static final String OPERATION = "operation";

    private LoggingContext() {
    }

    public static void putCorrelationId(String correlationId) {
        put(CORRELATION_ID, correlationId);
    }

    public static void putRequestId(String requestId) {
        put(REQUEST_ID, requestId);
    }

    public static void putActorId(String actorId) {
        put(ACTOR_ID, actorId);
    }

    public static void putModule(String module) {
        put(MODULE, module);
    }

    public static void putOperation(String operation) {
        put(OPERATION, operation);
    }

    public static void put(String key, String value) {
        if (key == null || key.isBlank()) {
            return;
        }
        String safeValue = SensitiveValueMasker.mask(key, value);
        if (safeValue == null) {
            MDC.remove(key);
            return;
        }
        MDC.put(key, safeValue);
    }

    public static String get(String key) {
        if (key == null || key.isBlank()) {
            return null;
        }
        return MDC.get(key);
    }

    public static void remove(String key) {
        if (key == null || key.isBlank()) {
            return;
        }
        MDC.remove(key);
    }

    public static void clearPlatformContext() {
        MDC.remove(CORRELATION_ID);
        MDC.remove(REQUEST_ID);
        MDC.remove(ACTOR_ID);
        MDC.remove(MODULE);
        MDC.remove(OPERATION);
    }
}
