/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringRuleSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Class
 * @Layer       : Monitoring Test
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.semantic
 *
 * @Description : Verifies HMR-040 TelemetryPoint owner validation.
 *
 */
package dz.sh.hidra.modules.monitoring.semantic;

import dz.sh.hidra.modules.monitoring.application.command.CreateMonitoringRuleCommand;
import dz.sh.hidra.modules.monitoring.application.port.out.MonitoringRuleRepositoryPort;
import dz.sh.hidra.modules.monitoring.application.service.MonitoringRuleApplicationService;
import dz.sh.hidra.modules.monitoring.domain.exception.InvalidMonitoringValueException;
import dz.sh.hidra.modules.monitoring.domain.model.MonitoringRule;
import dz.sh.hidra.modules.monitoring.domain.value.MonitoringRuleType;
import dz.sh.hidra.modules.telemetry.application.contract.monitoring.MonitoringTelemetryPointReferenceContract;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MonitoringRuleSemanticRemediationTest {

    @Test
    void rejectsUnknownPopulatedTelemetryPoint() {
        MonitoringRuleRepositoryPort repository = new MonitoringRuleRepositoryPort() {
            @Override
            public MonitoringRule save(MonitoringRule model) {
                return model;
            }

            @Override
            public Optional<MonitoringRule> findById(String id) {
                return Optional.empty();
            }
        };
        MonitoringTelemetryPointReferenceContract telemetry = pointId -> false;
        MonitoringRuleApplicationService service =
                new MonitoringRuleApplicationService(repository, telemetry);

        CreateMonitoringRuleCommand command = new CreateMonitoringRuleCommand(
                "RULE-1",
                null,
                "Règle 1",
                null,
                MonitoringRuleType.PLAN_COMPARISON,
                null,
                null,
                null,
                null,
                "point-404",
                null,
                null,
                null
        );

        assertThatThrownBy(() -> service.createMonitoringRule(command))
                .isInstanceOf(InvalidMonitoringValueException.class)
                .hasMessageContaining("existing TelemetryPoint");
    }

    @Test
    void migrationDoesNotIntroduceCrossModuleForeignKey() throws Exception {
        String sql = Files.readString(Path.of(
                "src/main/resources/db/migration/"
                        + "V20261004_040__hmr_040_monitoring_monitoring_rule.sql"
        ));
        org.assertj.core.api.Assertions.assertThat(sql)
                .contains("cross-module scalar reference")
                .doesNotContain("FOREIGN KEY");
    }
}
