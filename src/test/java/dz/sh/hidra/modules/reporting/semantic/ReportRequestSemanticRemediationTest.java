/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportRequestSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Class
 * @Layer       : Reporting Test
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.semantic
 *
 * @Description : Verifies HMR-048 request eligibility and workflow approval gating.
 *
 */
package dz.sh.hidra.modules.reporting.semantic;

import dz.sh.hidra.modules.reporting.application.command.RequestReportCommand;
import dz.sh.hidra.modules.reporting.application.port.out.ReportDefinitionRepositoryPort;
import dz.sh.hidra.modules.reporting.application.port.out.ReportOutputArtifactRepositoryPort;
import dz.sh.hidra.modules.reporting.application.port.out.ReportRequestRepositoryPort;
import dz.sh.hidra.modules.reporting.application.port.out.ReportRunRepositoryPort;
import dz.sh.hidra.modules.reporting.application.service.ReportingApplicationService;
import dz.sh.hidra.modules.reporting.domain.exception.InvalidReportingValueException;
import dz.sh.hidra.modules.reporting.domain.model.ReportDefinition;
import dz.sh.hidra.modules.reporting.domain.value.ReportDefinitionStatus;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReportRequestSemanticRemediationTest {

    private static final Instant NOW = Instant.parse("2026-10-05T00:00:00Z");

    @Test
    void rejectsRequestAgainstInactiveDefinition() {
        ReportDefinitionRepositoryPort definitions = mock(ReportDefinitionRepositoryPort.class);
        when(definitions.findById("definition-1")).thenReturn(Optional.of(definition(ReportDefinitionStatus.DRAFT)));

        ReportingApplicationService service = new ReportingApplicationService(
                definitions,
                mock(ReportRequestRepositoryPort.class),
                mock(ReportRunRepositoryPort.class),
                mock(ReportOutputArtifactRepositoryPort.class),
                request -> true,
                (workflow, request) -> true,
                id -> true
        );

        assertThatThrownBy(() -> service.requestReport(command()))
                .isInstanceOf(InvalidReportingValueException.class)
                .hasMessageContaining("ACTIVE");
    }

    @Test
    void migrationRejectsBlankExternalReferenceIdentifiersWithoutCrossModuleForeignKeys() throws Exception {
        String sql = Files.readString(Path.of(
                "src/main/resources/db/migration/"
                        + "V20261004_048__hmr_048_reporting_report_request.sql"
        ));

        assertThat(sql)
                .contains("organization_unit_id IS NULL OR btrim(organization_unit_id) <> ''")
                .contains("workflow_reference_id IS NULL OR btrim(workflow_reference_id) <> ''")
                .doesNotContain("FOREIGN KEY");
    }

    private static RequestReportCommand command() {
        return new RequestReportCommand(
                "definition-1",
                "actor-1",
                "user",
                "Actor One",
                "REPORT_VIEWER",
                null,
                null,
                "purpose",
                "corr-1",
                null
        );
    }

    private static ReportDefinition definition(ReportDefinitionStatus status) {
        return new ReportDefinition(
                "definition-1",
                "REPORT-1",
                null,
                "Rapport",
                null,
                "category-1",
                "reporting",
                null,
                status,
                null,
                false,
                false,
                NOW,
                NOW
        );
    }
}
