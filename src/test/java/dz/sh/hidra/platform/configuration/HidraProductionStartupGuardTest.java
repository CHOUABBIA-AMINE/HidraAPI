/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraProductionStartupGuardTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Class
 * @Layer       : Platform Test
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.configuration
 *
 * @Description : Verifies fail-fast production profile and mandatory configuration enforcement.
 *
 */
package dz.sh.hidra.platform.configuration;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;

class HidraProductionStartupGuardTest {

    private final HidraProductionStartupGuard guard = new HidraProductionStartupGuard();

    @Test
    void allowsNonProductionRuntimeWithoutProductionInputs() {
        MockEnvironment environment = new MockEnvironment();
        environment.setActiveProfiles("dev");
        environment.setProperty("hidra.environment", "dev");

        assertThatCode(() -> guard.postProcessEnvironment(environment, null))
                .doesNotThrowAnyException();
    }

    @Test
    void rejectsProductionIntentWhenProductionProfileIsMissing() {
        MockEnvironment environment = new MockEnvironment();
        environment.setActiveProfiles("dev");
        environment.setProperty("HIDRA_ENVIRONMENT", "production");

        assertThatThrownBy(() -> guard.postProcessEnvironment(environment, null))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("SPRING_PROFILES_ACTIVE")
                .hasMessageContaining("production");
    }

    @Test
    void rejectsProductionProfileWhenMandatoryDatasourceUrlIsMissing() {
        MockEnvironment environment = validProductionEnvironment();
        environment.setProperty("spring.datasource.url", "");

        assertThatThrownBy(() -> guard.postProcessEnvironment(environment, null))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("HIDRA_DATASOURCE_URL");
    }

    @Test
    void rejectsProductionProfileWhenMandatoryDatasourceUsernameIsMissing() {
        MockEnvironment environment = validProductionEnvironment();
        environment.setProperty("spring.datasource.username", "");

        assertThatThrownBy(() -> guard.postProcessEnvironment(environment, null))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("HIDRA_DATASOURCE_USERNAME");
    }

    @Test
    void rejectsProductionProfileWhenMandatoryDatasourcePasswordIsMissing() {
        MockEnvironment environment = validProductionEnvironment();
        environment.setProperty("spring.datasource.password", "");

        assertThatThrownBy(() -> guard.postProcessEnvironment(environment, null))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("HIDRA_DATASOURCE_PASSWORD");
    }

    @Test
    void rejectsProductionProfileWhenCorsOriginsAreMissing() {
        MockEnvironment environment = validProductionEnvironment();
        environment.setProperty("hidra.platform.security.cors.allowed-origins", "");

        assertThatThrownBy(() -> guard.postProcessEnvironment(environment, null))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("HIDRA_CORS_ALLOWED_ORIGINS");
    }

    @Test
    void rejectsProductionProfileWhenAuthenticationIsDisabled() {
        MockEnvironment environment = validProductionEnvironment();
        environment.setProperty("hidra.platform.security.authentication-mode", "disabled");

        assertThatThrownBy(() -> guard.postProcessEnvironment(environment, null))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("HIDRA_SECURITY_AUTHENTICATION_MODE=jwt");
    }

    @Test
    void rejectsProductionProfileWhenJwtSecretIsMissing() {
        MockEnvironment environment = validProductionEnvironment();
        environment.setProperty("hidra.platform.security.jwt.hmac-secret", "");

        assertThatThrownBy(() -> guard.postProcessEnvironment(environment, null))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("HIDRA_JWT_HMAC_SECRET");
    }

    @Test
    void rejectsEnabledBootstrapWithoutBootstrapPassword() {
        MockEnvironment environment = validProductionEnvironment();
        environment.setProperty("hidra.security.bootstrap.enabled", "true");
        environment.setProperty("hidra.security.bootstrap.password", "");

        assertThatThrownBy(() -> guard.postProcessEnvironment(environment, null))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("HIDRA_SECURITY_BOOTSTRAP_PASSWORD");
    }

    @Test
    void acceptsCompleteProductionRuntimeContract() {
        MockEnvironment environment = validProductionEnvironment();

        assertThatCode(() -> guard.postProcessEnvironment(environment, null))
                .doesNotThrowAnyException();
    }

    private static MockEnvironment validProductionEnvironment() {
        MockEnvironment environment = new MockEnvironment();
        environment.setActiveProfiles("production");
        environment.setProperty("hidra.environment", "production");
        environment.setProperty("spring.datasource.url", "jdbc:postgresql://database.service/hidra");
        environment.setProperty("spring.datasource.username", "hidra");
        environment.setProperty("spring.datasource.password", "external-secret");
        environment.setProperty("hidra.platform.security.cors.enabled", "true");
        environment.setProperty("hidra.platform.security.cors.allowed-origins", "https://hidra.example");
        environment.setProperty("hidra.platform.security.authentication-mode", "jwt");
        environment.setProperty(
                "hidra.platform.security.jwt.hmac-secret",
                "0123456789abcdef0123456789abcdef"
        );
        environment.setProperty("hidra.security.bootstrap.enabled", "false");
        return environment;
    }
}
