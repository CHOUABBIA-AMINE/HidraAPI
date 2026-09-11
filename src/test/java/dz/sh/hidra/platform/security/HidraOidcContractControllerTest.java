/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraOidcContractControllerTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Class
 * @Layer       : Platform Test
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.security
 *
 * @Description : Verifies repository-owned OIDC metadata and explicit external IdP dependency signaling.
 *
 */
package dz.sh.hidra.platform.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class HidraOidcContractControllerTest {

    @Test
    void reportsConfiguredRepositoryContractWhenIssuerAndClientArePresent() {
        HidraOidcContractController controller = new HidraOidcContractController(
                "jwt",
                "https://idp.example/realms/hidra",
                "hidra-web",
                "hidra-api",
                "openid,profile,hidra",
                "https://idp.example/logout"
        );

        var contract = controller.contract();

        assertEquals("authorization_code_pkce", contract.authorizationFlow());
        assertEquals("memory", contract.browserTokenStorage());
        assertEquals(3, contract.scopes().size());
        assertTrue(contract.repositoryConfigurationComplete());
        assertTrue(contract.externalIdpRegistrationRequired());
    }

    @Test
    void doesNotClaimExternalIdpRegistrationWhenRepositoryValuesAreMissing() {
        HidraOidcContractController controller = new HidraOidcContractController(
                "jwt", "", "", "hidra-api", "openid,profile", ""
        );

        var contract = controller.contract();

        assertFalse(contract.repositoryConfigurationComplete());
        assertTrue(contract.externalIdpRegistrationRequired());
    }
}
