package dz.sh.hidra.platform.permissions;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * Fail startup before accepting requests whenever a Platform-trusted tuple
 * drifts from Spring MVC's compiled handler mappings or the owner declaration.
 */
@Component
public final class HidraTrustedNativeProtectedRouteStartupValidator implements SmartInitializingSingleton {

    private final RequestMappingHandlerMapping mappings;
    private final HidraTrustedNativeProtectedRouteRegistry registry;

    public HidraTrustedNativeProtectedRouteStartupValidator(
            @Qualifier("requestMappingHandlerMapping") RequestMappingHandlerMapping mappings,
            HidraTrustedNativeProtectedRouteRegistry registry) {
        this.mappings = Objects.requireNonNull(mappings, "MVC mappings");
        this.registry = Objects.requireNonNull(registry, "trusted registry");
    }

    @Override
    public void afterSingletonsInstantiated() {
        validate();
    }

    void validate() {
        Map<RequestMappingInfo, HandlerMethod> actual = mappings.getHandlerMethods();
        for (HidraTrustedNativeProtectedRouteRegistry.Route route : registry.candidates()) {
            if (!route.method().isAnnotationPresent(NativeProtectedOperation.class)) {
                throw new IllegalStateException("Trusted route lacks native-protected method declaration");
            }
            var owned = actual.entrySet().stream()
                    .filter(e -> e.getValue().getBeanType().equals(route.controller())
                            && e.getValue().getMethod().equals(route.method()))
                    .toList();
            if (owned.size() != 1) {
                throw new IllegalStateException("Trusted native route has missing or ambiguous MVC handler");
            }
            RequestMappingInfo info = owned.get(0).getKey();
            if (!info.getPatternValues().equals(Set.of(route.pattern()))
                    || !info.getMethodsCondition().getMethods()
                            .equals(Set.of(RequestMethod.valueOf(route.verb())))) {
                throw new IllegalStateException("Trusted native route MVC verb or pattern drift");
            }
            long collisions = actual.entrySet().stream()
                    .filter(e -> e.getKey().getPatternValues().contains(route.pattern()))
                    .filter(e -> e.getKey().getMethodsCondition().getMethods()
                            .contains(RequestMethod.valueOf(route.verb())))
                    .count();
            if (collisions != 1) {
                throw new IllegalStateException("Ambiguous native-protected MVC route");
            }
        }
        registry.activate(registry.candidates());
    }
}
