package dz.sh.hidra.platform.permissions;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

/**
 * Independently controlled Platform whitelist for native-protected HTTP routes.
 * No business module, annotation scan or application property can add entries.
 * Production intentionally starts with an empty registry until separately
 * reviewed Platform source registers an actual protected owner controller.
 */
@Component
public final class HidraTrustedNativeProtectedRouteRegistry {

    private final List<Route> candidates;
    private volatile Set<Route> verified = Set.of();

    public HidraTrustedNativeProtectedRouteRegistry() {
        this(List.of());
    }

    // Deliberately package-private for Platform's synthetic qualification tests.
    HidraTrustedNativeProtectedRouteRegistry(List<Route> candidates) {
        this.candidates = List.copyOf(Objects.requireNonNull(candidates, "trusted route declarations"));
        if (new HashSet<>(this.candidates).size() != this.candidates.size()) {
            throw new IllegalArgumentException("Duplicate trusted native-protected route");
        }
    }

    List<Route> candidates() {
        return candidates;
    }

    // Only the Platform startup validator may activate successfully reconciled entries.
    void activate(List<Route> accepted) {
        if (!candidates.equals(accepted)) {
            throw new IllegalStateException("Unverified native route activation");
        }
        verified = Set.copyOf(accepted);
    }

    boolean isTrusted(HandlerMethod handler, String verb, String pattern) {
        if (handler == null || verb == null || pattern == null
                || !handler.getMethod().isAnnotationPresent(NativeProtectedOperation.class)) {
            return false;
        }
        return verified.stream().anyMatch(entry ->
                entry.controller().equals(handler.getBeanType())
                        && entry.method().equals(handler.getMethod())
                        && entry.verb().equals(verb)
                        && entry.pattern().equals(pattern));
    }

    /** Exact declared method identity, not a method-name-only or pattern-prefix trust. */
    record Route(Class<?> controller, Method method, String verb, String pattern) {
        Route {
            Objects.requireNonNull(controller, "controller");
            Objects.requireNonNull(method, "method");
            Objects.requireNonNull(verb, "verb");
            Objects.requireNonNull(pattern, "pattern");
            if (!controller.equals(method.getDeclaringClass())
                    || !Set.of("GET", "POST", "PUT", "PATCH", "DELETE").contains(verb)
                    || !pattern.startsWith("/api/v1/") || pattern.contains("*")
                    || pattern.contains("?") || pattern.contains("#")
                    || pattern.equals("/api/v1/security/oidc")
                    || pattern.equals("/api/v1/identity/authentication/login")
                    || pattern.equals("/api/v1/identity/me")
                    || pattern.equals("/api/v1/identity/me/permissions")
                    || pattern.startsWith("/api/v1/realtime/ws")) {
                throw new IllegalArgumentException("Invalid exact trusted native route declaration");
            }
        }
    }
}
