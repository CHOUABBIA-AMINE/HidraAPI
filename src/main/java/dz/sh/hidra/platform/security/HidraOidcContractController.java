/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraOidcContractController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.security
 *
 * @Description : Publishes the repository-owned browser OIDC/JWT acquisition contract without exposing secrets.
 *
 */
package dz.sh.hidra.platform.security;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Public bootstrap metadata required by browser clients before authentication.
 */
@RestController
@RequestMapping("/api/v1/security/oidc")
@Tag(name = "Security OIDC", description = "Repository-side OIDC/JWT browser integration contract.")
public final class HidraOidcContractController {

    private final String authenticationMode;
    private final String issuerUri;
    private final String clientId;
    private final String audience;
    private final List<String> scopes;
    private final String logoutUri;

    public HidraOidcContractController(
            @Value("${hidra.platform.security.authentication-mode:jwt}") String authenticationMode,
            @Value("${hidra.platform.security.jwt.issuer-uri:}") String issuerUri,
            @Value("${hidra.platform.security.oidc.client-id:}") String clientId,
            @Value("${hidra.platform.security.jwt.audience:hidra-api}") String audience,
            @Value("${hidra.platform.security.oidc.scopes:openid,profile}") String scopes,
            @Value("${hidra.platform.security.oidc.logout-uri:}") String logoutUri
    ) {
        this.authenticationMode = normalize(authenticationMode);
        this.issuerUri = normalize(issuerUri);
        this.clientId = normalize(clientId);
        this.audience = normalize(audience);
        this.scopes = csv(scopes);
        this.logoutUri = normalize(logoutUri);
    }

    @GetMapping
    @Operation(summary = "Get browser OIDC contract", description = "Returns non-secret issuer, client, scope, audience, and PKCE integration metadata.")
    public OidcContract contract() {
        boolean configured = "jwt".equalsIgnoreCase(authenticationMode)
                && issuerUri != null
                && clientId != null;
        return new OidcContract(
                authenticationMode,
                "authorization_code_pkce",
                issuerUri,
                clientId,
                audience,
                scopes,
                logoutUri,
                "memory",
                configured,
                true
        );
    }

    private static List<String> csv(String value) {
        if (value == null || value.isBlank()) {
            return List.of();
        }
        return Arrays.stream(value.split(","))
                .map(String::trim)
                .filter(item -> !item.isBlank())
                .distinct()
                .toList();
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }

    public record OidcContract(
            String authenticationMode,
            String authorizationFlow,
            String issuerUri,
            String clientId,
            String audience,
            List<String> scopes,
            String logoutUri,
            String browserTokenStorage,
            boolean repositoryConfigurationComplete,
            boolean externalIdpRegistrationRequired
    ) { }
}
