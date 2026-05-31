/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraPlatformProperties
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.configuration
 *
 * @Description : Binds typed technical configuration for hidra.platform properties.
 *
 */
package dz.sh.hidra.platform.configuration;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "hidra.platform")
@Validated
public record HidraPlatformProperties(
        @Valid Observability observability,
        @Valid Security security,
        @Valid Persistence persistence,
        @Valid Events events,
        @Valid Tenancy tenancy) {

    public HidraPlatformProperties {
        observability = observability == null ? Observability.defaults() : observability;
        security = security == null ? Security.defaults() : security;
        persistence = persistence == null ? Persistence.defaults() : persistence;
        events = events == null ? Events.defaults() : events;
        tenancy = tenancy == null ? Tenancy.defaults() : tenancy;
    }

    public record Observability(Correlation correlation, Request request, Logging logging) {

        public Observability {
            correlation = correlation == null ? Correlation.defaults() : correlation;
            request = request == null ? Request.defaults() : request;
            logging = logging == null ? Logging.defaults() : logging;
        }

        private static Observability defaults() {
            return new Observability(Correlation.defaults(), Request.defaults(), Logging.defaults());
        }
    }

    public record Correlation(String headerName, boolean responseHeaderEnabled) {

        public Correlation {
            headerName = defaultString(headerName, "X-Correlation-Id");
        }

        private static Correlation defaults() {
            return new Correlation("X-Correlation-Id", true);
        }
    }

    public record Request(String headerName, boolean responseHeaderEnabled) {

        public Request {
            headerName = defaultString(headerName, "X-Request-Id");
        }

        private static Request defaults() {
            return new Request("X-Request-Id", true);
        }
    }

    public record Logging(boolean structured, boolean maskSensitiveValues) {

        private static Logging defaults() {
            return new Logging(true, true);
        }
    }

    public record Security(boolean enabled, Cors cors, Csrf csrf) {

        public Security {
            cors = cors == null ? Cors.defaults() : cors;
            csrf = csrf == null ? Csrf.defaults() : csrf;
        }

        private static Security defaults() {
            return new Security(true, Cors.defaults(), Csrf.defaults());
        }
    }

    public record Cors(
            boolean enabled,
            List<String> allowedOrigins,
            List<String> allowedMethods,
            List<String> allowedHeaders,
            List<String> exposedHeaders) {

        public Cors {
            allowedOrigins = immutableList(allowedOrigins);
            allowedMethods = defaultList(allowedMethods, List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
            allowedHeaders = defaultList(allowedHeaders, List.of("Authorization", "Content-Type", "X-Correlation-Id", "X-Request-Id"));
            exposedHeaders = defaultList(exposedHeaders, List.of("X-Correlation-Id", "X-Request-Id"));
        }

        private static Cors defaults() {
            return new Cors(
                    true,
                    List.of(),
                    List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"),
                    List.of("Authorization", "Content-Type", "X-Correlation-Id", "X-Request-Id"),
                    List.of("X-Correlation-Id", "X-Request-Id"));
        }
    }

    public record Csrf(boolean enabled) {

        private static Csrf defaults() {
            return new Csrf(false);
        }
    }

    public record Persistence(Auditing auditing, OptimisticLocking optimisticLocking) {

        public Persistence {
            auditing = auditing == null ? Auditing.defaults() : auditing;
            optimisticLocking = optimisticLocking == null ? OptimisticLocking.defaults() : optimisticLocking;
        }

        private static Persistence defaults() {
            return new Persistence(Auditing.defaults(), OptimisticLocking.defaults());
        }
    }

    public record Auditing(boolean enabled) {

        private static Auditing defaults() {
            return new Auditing(true);
        }
    }

    public record OptimisticLocking(boolean enabled) {

        private static OptimisticLocking defaults() {
            return new OptimisticLocking(true);
        }
    }

    public record Events(Outbox outbox) {

        public Events {
            outbox = outbox == null ? Outbox.defaults() : outbox;
        }

        private static Events defaults() {
            return new Events(Outbox.defaults());
        }
    }

    public record Outbox(
            boolean enabled,
            String tableName,
            @Min(1) int batchSize,
            @Min(0) int maxRetryCount,
            boolean cleanupEnabled) {

        public Outbox {
            tableName = defaultString(tableName, "hidra_platform_outbox_event");
        }

        private static Outbox defaults() {
            return new Outbox(true, "hidra_platform_outbox_event", 50, 5, false);
        }
    }

    public record Tenancy(boolean enabled, String defaultScope) {

        public Tenancy {
            defaultScope = defaultString(defaultScope, "SH");
        }

        private static Tenancy defaults() {
            return new Tenancy(false, "SH");
        }
    }

    private static String defaultString(String value, String defaultValue) {
        return value == null || value.isBlank() ? defaultValue : value;
    }

    private static List<String> defaultList(List<String> values, List<String> defaultValues) {
        return values == null || values.isEmpty() ? List.copyOf(defaultValues) : immutableList(values);
    }

    private static List<String> immutableList(List<String> values) {
        return values == null ? List.of() : List.copyOf(values);
    }
}
