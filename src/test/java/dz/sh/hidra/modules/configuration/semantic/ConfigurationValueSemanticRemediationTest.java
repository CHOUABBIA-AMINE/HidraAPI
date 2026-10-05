/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationValueSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Class
 * @Layer       : Configuration Test
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.semantic
 *
 * @Description : Verifies HMR-039 ConfigurationValue environment and definition-version integrity.
 *
 */
package dz.sh.hidra.modules.configuration.semantic;

import dz.sh.hidra.modules.configuration.domain.exception.InvalidConfigurationValueException;
import dz.sh.hidra.modules.configuration.domain.model.ConfigurationValue;
import dz.sh.hidra.modules.configuration.domain.value.ConfigurationValueStatus;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ConfigurationValueSemanticRemediationTest {

    private static final Instant NOW = Instant.parse("2026-10-05T00:00:00Z");

    @Test
    void rejectsBlankEnvironment() {
        assertThatThrownBy(() -> value(" "))
                .isInstanceOf(InvalidConfigurationValueException.class)
                .hasMessageContaining("environment");
    }

    @Test
    void normalizesValidEnvironment() {
        assertThat(value(" PROD ").environment()).isEqualTo("PROD");
    }

    @Test
    void migrationProtectsOptionalDefinitionVersionReference() throws Exception {
        String sql = Files.readString(Path.of(
                "src/main/resources/db/migration/"
                        + "V20261004_039__hmr_039_configuration_configuration_value.sql"
        ));
        assertThat(sql)
                .contains("FOREIGN KEY (definition_version_id)")
                .contains("REFERENCES hidra_configuration_definition_version (id)")
                .contains("btrim(environment) <> ''");
    }

    private static ConfigurationValue value(String environment) {
        return new ConfigurationValue(
                "value-1",
                "definition-1",
                "definition-version-1",
                environment,
                "ordinary-value",
                null,
                null,
                ConfigurationValueStatus.ACTIVE,
                NOW,
                null,
                "actor-1",
                NOW,
                NOW
        );
    }
}
