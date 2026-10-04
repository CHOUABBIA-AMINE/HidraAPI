/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FeatureFlagSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Configuration Test
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.semantic
 *
 * @Description : Verifies HMR-026 FeatureFlag ownership requiredness.
 *
 */
package dz.sh.hidra.modules.configuration.semantic;

import dz.sh.hidra.modules.configuration.application.command.CreateFeatureFlagCommand;
import dz.sh.hidra.modules.configuration.domain.exception.InvalidConfigurationValueException;
import dz.sh.hidra.modules.configuration.domain.model.FeatureFlag;
import dz.sh.hidra.modules.configuration.domain.value.FeatureFlagEvaluationStrategy;
import dz.sh.hidra.modules.configuration.domain.value.FeatureFlagStatus;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FeatureFlagSemanticRemediationTest {

    private static final Instant NOW = Instant.parse("2026-10-04T00:00:00Z");

    @Test
    void commandRequiresNonblankOwningModule() {
        assertThatThrownBy(() -> new CreateFeatureFlagCommand(
                "FLAG-1",
                "Drapeau",
                null,
                null,
                " ",
                FeatureFlagEvaluationStrategy.BOOLEAN,
                false,
                null
        ))
                .isInstanceOf(InvalidConfigurationValueException.class)
                .hasMessageContaining("owning module must not be blank");

        CreateFeatureFlagCommand command = new CreateFeatureFlagCommand(
                "FLAG-1",
                "Drapeau",
                null,
                null,
                " analytics ",
                FeatureFlagEvaluationStrategy.BOOLEAN,
                false,
                null
        );

        assertThat(command.owningModule()).isEqualTo("analytics");
    }

    @Test
    void domainRequiresNonblankOwningModule() {
        assertThatThrownBy(() -> featureFlag(" "))
                .isInstanceOf(InvalidConfigurationValueException.class)
                .hasMessageContaining("owning module must not be blank");

        assertThat(featureFlag(" monitoring ").owningModule()).isEqualTo("monitoring");
    }

    @Test
    void migrationRejectsBlankOwningModule() throws Exception {
        String sql = Files.readString(Path.of(
                "src/main/resources/db/migration/"
                        + "V20261004_026__hmr_026_configuration_feature_flag.sql"
        ));

        assertThat(sql).contains("ck_hmr026_feature_flag_owning_module_nonblank");
        assertThat(sql).contains("btrim(owning_module) <> ''");
    }

    private static FeatureFlag featureFlag(String owningModule) {
        return new FeatureFlag(
                "flag-1",
                "FLAG-1",
                "Drapeau",
                null,
                null,
                owningModule,
                FeatureFlagStatus.DRAFT,
                FeatureFlagEvaluationStrategy.BOOLEAN,
                false,
                null,
                NOW,
                NOW
        );
    }
}
