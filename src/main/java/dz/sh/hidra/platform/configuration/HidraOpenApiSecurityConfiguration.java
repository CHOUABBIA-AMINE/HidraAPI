/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraOpenApiSecurityConfiguration
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.configuration
 *
 * @Description : Publishes machine-readable OpenAPI security schemes and operation requirements.
 *
 */
package dz.sh.hidra.platform.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import java.util.List;
import java.util.Set;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Aligns generated OpenAPI security metadata with the verified Spring Security boundary.
 */
@Configuration(proxyBeanMethods = false)
public class HidraOpenApiSecurityConfiguration {

    static final String HIDRA_BEARER_SCHEME = "hidraBearerJwt";
    static final String EXTERNAL_OIDC_BEARER_SCHEME = "externalOidcBearerJwt";

    private static final String OIDC_COMPLETION_PATH = "/api/v1/identity/authentication/oidc/complete";

    private static final Set<String> PUBLIC_API_PATHS = Set.of(
            "/api/v1/security/oidc",
            "/api/v1/identity/authentication/login",
            "/actuator/health",
            "/actuator/info"
    );

    @Bean
    OpenApiCustomizer hidraSecurityOpenApiCustomizer() {
        return openApi -> {
            Components components = components(openApi);
            components.addSecuritySchemes(
                    HIDRA_BEARER_SCHEME,
                    new SecurityScheme()
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
                            .description("Hidra-issued bearer JWT used by ordinary protected HidraAPI operations.")
            );
            components.addSecuritySchemes(
                    EXTERNAL_OIDC_BEARER_SCHEME,
                    new SecurityScheme()
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
                            .description(
                                    "Externally issued OIDC bearer JWT accepted only by the OIDC completion bridge."
                            )
            );

            Paths paths = openApi.getPaths();
            if (paths == null) {
                return;
            }

            paths.forEach((path, pathItem) -> applySecurity(path, pathItem));
        };
    }

    private static Components components(OpenAPI openApi) {
        if (openApi.getComponents() == null) {
            openApi.setComponents(new Components());
        }
        return openApi.getComponents();
    }

    private static void applySecurity(String path, PathItem pathItem) {
        if (pathItem == null) {
            return;
        }
        for (Operation operation : pathItem.readOperations()) {
            if (operation == null) {
                continue;
            }
            if (OIDC_COMPLETION_PATH.equals(path)) {
                operation.setSecurity(List.of(new SecurityRequirement().addList(EXTERNAL_OIDC_BEARER_SCHEME)));
                continue;
            }
            if (isPublicPath(path)) {
                operation.setSecurity(List.of());
                continue;
            }
            operation.setSecurity(List.of(new SecurityRequirement().addList(HIDRA_BEARER_SCHEME)));
        }
    }

    private static boolean isPublicPath(String path) {
        if (path == null) {
            return false;
        }
        if (PUBLIC_API_PATHS.contains(path)) {
            return true;
        }
        return path.startsWith("/actuator/health/")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/swagger-ui");
    }
}
