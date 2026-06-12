/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraPlatformProperties
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.configuration
 *
 * @Description : Represents typed technical platform configuration.
 *
 */
package dz.sh.hidra.platform.configuration;

import java.util.List;

/**
 * Typed technical platform configuration.
 *
 * <p>This record is intentionally framework-neutral. Spring configuration binding,
 * when used, belongs to configuration adapter code.</p>
 *
 * @param observability observability configuration
 * @param security security configuration
 * @param persistence persistence configuration
 * @param events event/outbox configuration
 * @param tenancy tenancy configuration
 */
public record HidraPlatformProperties(
        Observability observability,
        Security security,
        Persistence persistence,
        Events events,
        Tenancy tenancy
) {

    public HidraPlatformProperties {
        observability = observability == null ? Observability.defaults() : observability;
        security = security == null ? Security.defaults() : security;
        persistence = persistence == null ? Persistence.defaults() : persistence;
        events = events == null ? Events.defaults() : events;
        tenancy = tenancy == null ? Tenancy.defaults() : tenancy;
    }

    public static HidraPlatformProperties defaults() {
        return new HidraPlatformProperties(null, null, null, null, null);
    }

    public record Observability(
            Correlation correlation,
            Request request,
            Logging logging
    ) {

        public Observability {
            correlation = correlation == null ? Correlation.defaults() : correlation;
            request = request == null ? Request.defaults() : request;
            logging = logging == null ? Logging.defaults() : logging;
        }

        public static Observability defaults() {
            return new Observability(null, null, null);
        }
    }

    public record Correlation(
            String headerName,
            boolean responseHeaderEnabled
    ) {

        public static Correlation defaults() {
            return new Correlation("X-Correlation-Id", true);
        }
    }

    public record Request(
            String headerName,
            boolean responseHeaderEnabled
    ) {

        public static Request defaults() {
            return new Request("X-Request-Id", true);
        }
    }

    public record Logging(
            boolean structured,
            boolean maskSensitiveValues
    ) {

        public static Logging defaults() {
            return new Logging(true, true);
        }
    }

    public record Security(
            boolean enabled,
            Cors cors,
            Csrf csrf
    ) {

        public Security {
            cors = cors == null ? Cors.defaults() : cors;
            csrf = csrf == null ? Csrf.defaults() : csrf;
        }

        public static Security defaults() {
            return new Security(true, null, null);
        }
    }

    public record Cors(
            boolean enabled,
            List<String> allowedOrigins,
            List<String> allowedMethods,
            List<String> allowedHeaders,
            List<String> exposedHeaders
    ) {

        public Cors {
            allowedOrigins = allowedOrigins == null ? List.of() : List.copyOf(allowedOrigins);
            allowedMethods = allowedMethods == null
                    ? List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                    : List.copyOf(allowedMethods);
            allowedHeaders = allowedHeaders == null
                    ? List.of("Authorization", "Content-Type", "X-Correlation-Id", "X-Request-Id")
                    : List.copyOf(allowedHeaders);
            exposedHeaders = exposedHeaders == null
                    ? List.of("X-Correlation-Id", "X-Request-Id")
                    : List.copyOf(exposedHeaders);
        }

        public static Cors defaults() {
            return new Cors(true, List.of(), null, null, null);
        }
    }

    public record Csrf(boolean enabled) {

        public static Csrf defaults() {
            return new Csrf(false);
        }
    }

    public record Persistence(
            boolean auditingEnabled,
            boolean optimisticLockingEnabled
    ) {

        public static Persistence defaults() {
            return new Persistence(true, true);
        }
    }

    public record Events(Outbox outbox) {

        public Events {
            outbox = outbox == null ? Outbox.defaults() : outbox;
        }

        public static Events defaults() {
            return new Events(null);
        }
    }

    public record Outbox(
            boolean enabled,
            String tableName,
            int batchSize,
            int maxRetryCount,
            boolean cleanupEnabled
    ) {

        public Outbox {
            tableName = normalizeOrDefault(tableName, "hidra_platform_outbox_event");
            if (batchSize < 1) {
                batchSize = 50;
            }
            if (maxRetryCount < 0) {
                maxRetryCount = 5;
            }
        }

        public static Outbox defaults() {
            return new Outbox(true, "hidra_platform_outbox_event", 50, 5, false);
        }
    }

    public record Tenancy(
            boolean enabled,
            String defaultScope
    ) {

        public Tenancy {
            defaultScope = normalizeOrDefault(defaultScope, "SH");
        }

        public static Tenancy defaults() {
            return new Tenancy(false, "SH");
        }
    }

    private static String normalizeOrDefault(String value, String defaultValue) {
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        return value.trim();
    }
}
