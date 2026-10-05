/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ExternalSystemSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Class
 * @Layer       : Integration Test
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.semantic
 *
 * @Description : Verifies HMR-047 ExternalSystem uniqueness, catalog family and owner validation.
 *
 */
package dz.sh.hidra.modules.integration.semantic;

import dz.sh.hidra.modules.integration.application.command.RegisterExternalSystemCommand;
import dz.sh.hidra.modules.integration.application.port.out.ExternalSystemRepositoryPort;
import dz.sh.hidra.modules.integration.application.port.out.IntegrationExchangeMessageRepositoryPort;
import dz.sh.hidra.modules.integration.application.port.out.IntegrationJobRunRepositoryPort;
import dz.sh.hidra.modules.integration.application.service.IntegrationApplicationService;
import dz.sh.hidra.modules.integration.domain.exception.InvalidIntegrationValueException;
import dz.sh.hidra.modules.integration.domain.value.IntegrationCriticality;
import dz.sh.hidra.modules.integration.domain.value.IntegrationEnvironment;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ExternalSystemSemanticRemediationTest {

    @Test
    void rejectsDuplicateCodeBeforePersistence() {
        ExternalSystemRepositoryPort repository = mock(ExternalSystemRepositoryPort.class);
        when(repository.existsByCode("SCADA_MAIN")).thenReturn(true);

        IntegrationApplicationService service = new IntegrationApplicationService(
                repository,
                mock(IntegrationJobRunRepositoryPort.class),
                mock(IntegrationExchangeMessageRepositoryPort.class),
                id -> true
        );

        RegisterExternalSystemCommand command = new RegisterExternalSystemCommand(
                "SCADA_MAIN",
                null,
                "SCADA principal",
                null,
                "type-1",
                null,
                IntegrationEnvironment.PRODUCTION,
                IntegrationCriticality.CRITICAL,
                null
        );

        assertThatThrownBy(() -> service.registerExternalSystem(command))
                .isInstanceOf(InvalidIntegrationValueException.class)
                .hasMessageContaining("unique");
    }

    @Test
    void migrationProvidesConcurrencySafeCodeUniquenessWithoutOrganizationForeignKey() throws Exception {
        String sql = Files.readString(Path.of(
                "src/main/resources/db/migration/"
                        + "V20261004_047__hmr_047_integration_external_system.sql"
        ));

        assertThat(sql)
                .contains("CREATE UNIQUE INDEX")
                .contains("ON hidra_integration_external_system (code)")
                .doesNotContain("owner_organization_unit_id")
                .doesNotContain("FOREIGN KEY");
    }
}
