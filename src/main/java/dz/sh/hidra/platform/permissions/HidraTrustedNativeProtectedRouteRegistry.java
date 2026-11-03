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
 * Production activates only explicitly registered manufacturer and Topology approval tuples after
 * exact Spring MVC compilation validation. This is NOT a native Identity grant.
 */
@Component
public final class HidraTrustedNativeProtectedRouteRegistry {

    private final List<Route> candidates;
    private volatile Set<Route> verified = Set.of();

    public HidraTrustedNativeProtectedRouteRegistry() {
        this(approvedCandidates());
    }

    // Deliberately package-private for Platform's synthetic qualification tests.
    HidraTrustedNativeProtectedRouteRegistry(List<Route> candidates) {
        this.candidates = List.copyOf(Objects.requireNonNull(candidates, "trusted route declarations"));
        if (new HashSet<>(this.candidates).size() != this.candidates.size()) {
            throw new IllegalArgumentException("Duplicate trusted native-protected route");
        }
    }

    /**
     * Independent, fixed Platform-owned exact compiled tuple registration.
     * Literal reflective class/method signatures avoid a prohibited Platform->module
     * compile dependency. No scans, configuration overrides or runtime registration.
     */
    private static List<Route> approvedCandidates() {
        final String prefix="dz.sh.hidra.modules.simulation.api.rest.";
        final String controllerName=prefix+"controller.ManufacturerCharacteristicRevisionController";
        final String base="/api/v1/simulation/manufacturer-characteristics/{sourceId}";
        try {
            Class<?> controller=Class.forName(controllerName);
            Class<?> save=Class.forName(prefix+"request.ManufacturerCharacteristicRevisionRequests$Save");
            Class<?> qualify=Class.forName(prefix+"request.ManufacturerCharacteristicRevisionRequests$Qualify");
            final String topology="dz.sh.hidra.modules.topology.api.rest.";
            Class<?> installed=Class.forName(topology+"controller.InstalledCharacteristicLinkController");
            Class<?> approval=Class.forName(topology+"request.InstalledCharacteristicLinkRequests$CharacteristicLinkApprovalCommand");
            final String linkBase="/api/v1/topology/installed-units/{unitId}/characteristic-links/{linkId}";
            Class<?> baseline=Class.forName(topology+"controller.EngineeringBaselineDraftController");
            Class<?> baselineCreate=Class.forName(topology+"request.EngineeringBaselineDraftRequests$Create");
            Class<?> baselineUpdate=Class.forName(topology+"request.EngineeringBaselineDraftRequests$Update");
            final String baselineBase="/api/v1/topology/engineering-baselines";
            return List.of(
                new Route(baseline,baseline.getDeclaredMethod("create",baselineCreate),"POST",baselineBase),
                new Route(baseline,baseline.getDeclaredMethod("update",java.util.UUID.class,baselineUpdate),"PUT",baselineBase+"/{manifestId}"),
                new Route(baseline,baseline.getDeclaredMethod("current",java.util.UUID.class,String.class,String.class),"GET",baselineBase+"/{manifestId}"),
                new Route(baseline,baseline.getDeclaredMethod("revision",java.util.UUID.class,long.class,String.class,String.class),"GET",baselineBase+"/{manifestId}/revisions/{version}"),
                new Route(baseline,baseline.getDeclaredMethod("validate",java.util.UUID.class,String.class,String.class,Long.class),"POST",baselineBase+"/{manifestId}/validation"),
                new Route(controller,controller.getDeclaredMethod("save",String.class,String.class,save),
                    "PUT",base+"/revisions/{revisionId}"),
                new Route(controller,controller.getDeclaredMethod("revision",String.class,String.class,String.class),
                    "GET",base+"/revisions/{revisionId}"),
                new Route(controller,controller.getDeclaredMethod("history",String.class,String.class,
                    Integer.class,String.class),"GET",base+"/history"),
                new Route(controller,controller.getDeclaredMethod("certificate",String.class,String.class,
                    String.class),"GET",base+"/revisions/{revisionId}/certificate"),
                new Route(controller,controller.getDeclaredMethod("qualify",String.class,String.class,qualify),
                    "POST",base+"/revisions/{revisionId}/qualification"),
                new Route(installed,installed.getDeclaredMethod("approve",String.class,String.class,long.class,approval),
                    "POST",linkBase+"/revisions/{revisionId}/approval"),
                new Route(installed,installed.getDeclaredMethod("approvedRevision",String.class,String.class,long.class,String.class),
                    "GET",linkBase+"/revisions/{revisionId}/approval"),
                new Route(installed,installed.getDeclaredMethod("approvalHistory",String.class,String.class,
                    String.class,int.class,Long.class),"GET",linkBase+"/approvals/history")
            );
        } catch (ClassNotFoundException | NoSuchMethodException contractDrift) {
            throw new IllegalStateException("Missing approved compiled native route contract",
                    contractDrift);
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
