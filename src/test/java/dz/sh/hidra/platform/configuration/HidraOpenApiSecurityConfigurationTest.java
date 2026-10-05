/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraOpenApiSecurityConfigurationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Class
 * @Layer       : Platform Test
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.configuration
 *
 * @Description : Verifies generated OpenAPI security schemes and per-operation requirements.
 *
 */
package dz.sh.hidra.platform.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.junit.jupiter.api.Test;
import org.springdoc.core.customizers.OpenApiCustomizer;

class HidraOpenApiSecurityConfigurationTest {

    @Test
    void declaresBothVerifiedBearerSecuritySchemes() {
        OpenAPI openApi = openApiWithRepresentativeOperations();

        customizer().customise(openApi);

        assertThat(openApi.getComponents()).isNotNull();
        assertThat(openApi.getComponents().getSecuritySchemes())
                .containsKeys(
                        HidraOpenApiSecurityConfiguration.HIDRA_BEARER_SCHEME,
                        HidraOpenApiSecurityConfiguration.EXTERNAL_OIDC_BEARER_SCHEME
                );

        SecurityScheme hidraBearer = openApi.getComponents()
                .getSecuritySchemes()
                .get(HidraOpenApiSecurityConfiguration.HIDRA_BEARER_SCHEME);
        assertThat(hidraBearer.getType()).isEqualTo(SecurityScheme.Type.HTTP);
        assertThat(hidraBearer.getScheme()).isEqualTo("bearer");
        assertThat(hidraBearer.getBearerFormat()).isEqualTo("JWT");

        SecurityScheme externalOidc = openApi.getComponents()
                .getSecuritySchemes()
                .get(HidraOpenApiSecurityConfiguration.EXTERNAL_OIDC_BEARER_SCHEME);
        assertThat(externalOidc.getType()).isEqualTo(SecurityScheme.Type.HTTP);
        assertThat(externalOidc.getScheme()).isEqualTo("bearer");
        assertThat(externalOidc.getBearerFormat()).isEqualTo("JWT");
    }

    @Test
    void marksOrdinaryApiOperationAsHidraBearerProtected() {
        OpenAPI openApi = openApiWithRepresentativeOperations();

        customizer().customise(openApi);

        Operation operation = openApi.getPaths().get("/api/v1/topology/pipelines").getGet();
        assertThat(operation.getSecurity()).containsExactly(
                new SecurityRequirement().addList(HidraOpenApiSecurityConfiguration.HIDRA_BEARER_SCHEME)
        );
    }

    @Test
    void marksOidcCompletionAsExternalOidcBearerProtected() {
        OpenAPI openApi = openApiWithRepresentativeOperations();

        customizer().customise(openApi);

        Operation operation = openApi.getPaths()
                .get("/api/v1/identity/authentication/oidc/complete")
                .getPost();
        assertThat(operation.getSecurity()).containsExactly(
                new SecurityRequirement().addList(
                        HidraOpenApiSecurityConfiguration.EXTERNAL_OIDC_BEARER_SCHEME
                )
        );
    }

    @Test
    void leavesVerifiedPublicOperationsExplicitlyUnauthenticated() {
        OpenAPI openApi = openApiWithRepresentativeOperations();

        customizer().customise(openApi);

        assertThat(openApi.getPaths()
                .get("/api/v1/identity/authentication/login")
                .getPost()
                .getSecurity()).isEmpty();
        assertThat(openApi.getPaths()
                .get("/api/v1/security/oidc")
                .getGet()
                .getSecurity()).isEmpty();
        assertThat(openApi.getPaths()
                .get("/actuator/health")
                .getGet()
                .getSecurity()).isEmpty();
    }

    private static OpenApiCustomizer customizer() {
        return new HidraOpenApiSecurityConfiguration().hidraSecurityOpenApiCustomizer();
    }

    private static OpenAPI openApiWithRepresentativeOperations() {
        Paths paths = new Paths()
                .addPathItem(
                        "/api/v1/topology/pipelines",
                        new PathItem().get(new Operation().operationId("listPipelines"))
                )
                .addPathItem(
                        "/api/v1/identity/authentication/login",
                        new PathItem().post(new Operation().operationId("login"))
                )
                .addPathItem(
                        "/api/v1/identity/authentication/oidc/complete",
                        new PathItem().post(new Operation().operationId("completeOidc"))
                )
                .addPathItem(
                        "/api/v1/security/oidc",
                        new PathItem().get(new Operation().operationId("oidcContract"))
                )
                .addPathItem(
                        "/actuator/health",
                        new PathItem().get(new Operation().operationId("health"))
                );
        return new OpenAPI().paths(paths);
    }
}
