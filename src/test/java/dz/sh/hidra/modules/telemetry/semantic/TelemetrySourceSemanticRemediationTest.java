/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetrySourceSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Telemetry Test
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.semantic
 *
 * @Description : Verifies HMR-030 TelemetrySource identity, catalog, naming, secret and lifecycle semantics.
 *
 */
package dz.sh.hidra.modules.telemetry.semantic;

import dz.sh.hidra.modules.telemetry.application.command.CreateTelemetrySourceCommand;
import dz.sh.hidra.modules.telemetry.domain.exception.InvalidTelemetryValueException;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetrySource;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryLifecycleStatus;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter.JpaTelemetrySourceRepositoryAdapter;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository.TelemetrySourceJpaRepository;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TelemetrySourceSemanticRemediationTest {

    private static final Instant NOW = Instant.parse("2026-10-04T00:00:00Z");

    @Test
    void commandRequiresFrenchName() {
        assertThatThrownBy(() -> new CreateTelemetrySourceCommand(
                "SRC-1",
                null,
                " ",
                null,
                "source-type-1",
                "protocol-1",
                null,
                null
        ))
                .isInstanceOf(InvalidTelemetryValueException.class)
                .hasMessageContaining("French name must not be blank");

        CreateTelemetrySourceCommand command = new CreateTelemetrySourceCommand(
                " SRC-1 ",
                null,
                " Source principal ",
                null,
                " source-type-1 ",
                " protocol-1 ",
                null,
                null
        );

        assertThat(command.code()).isEqualTo("SRC-1");
        assertThat(command.nameFr()).isEqualTo("Source principal");
    }

    @Test
    void domainRejectsSecretMaterialAndSourceInvalidLifecycleStates() {
        assertThatThrownBy(() -> source(
                TelemetryLifecycleStatus.DRAFT,
                "https://operator:password@scada.example",
                null
        ))
                .isInstanceOf(InvalidTelemetryValueException.class)
                .hasMessageContaining("endpoint URI must not contain secret material");

        assertThatThrownBy(() -> source(
                TelemetryLifecycleStatus.DRAFT,
                null,
                "password=actual-secret"
        ))
                .isInstanceOf(InvalidTelemetryValueException.class)
                .hasMessageContaining("external reference must not contain secret material");

        assertThatThrownBy(() -> source(
                TelemetryLifecycleStatus.PLANNED,
                null,
                null
        ))
                .isInstanceOf(InvalidTelemetryValueException.class)
                .hasMessageContaining("not valid for acquisition sources");

        assertThatThrownBy(() -> source(
                TelemetryLifecycleStatus.MAINTENANCE,
                null,
                null
        ))
                .isInstanceOf(InvalidTelemetryValueException.class)
                .hasMessageContaining("not valid for acquisition sources");

        assertThat(source(TelemetryLifecycleStatus.ACTIVE, null, null).ingestionEligible())
                .isTrue();
    }

    @Test
    void repositoryAdapterRejectsDuplicateCodeAndWrongCatalogFamilies() {
        TelemetrySourceJpaRepository repository = mock(TelemetrySourceJpaRepository.class);
        JpaTelemetrySourceRepositoryAdapter adapter =
                new JpaTelemetrySourceRepositoryAdapter(repository);
        TelemetrySource source = source(TelemetryLifecycleStatus.DRAFT, null, null);

        when(repository.existsByCodeAndIdNot("SRC-1", "source-1")).thenReturn(true);
        assertThatThrownBy(() -> adapter.save(source))
                .isInstanceOf(InvalidTelemetryValueException.class)
                .hasMessageContaining("code must be unique");

        when(repository.existsByCodeAndIdNot("SRC-1", "source-1")).thenReturn(false);
        when(repository.existsActiveCatalogEntry("source-type-1", "SOURCE_TYPE"))
                .thenReturn(false);
        assertThatThrownBy(() -> adapter.save(source))
                .isInstanceOf(InvalidTelemetryValueException.class)
                .hasMessageContaining("active SOURCE_TYPE");

        when(repository.existsActiveCatalogEntry("source-type-1", "SOURCE_TYPE"))
                .thenReturn(true);
        when(repository.existsActiveCatalogEntry("protocol-1", "PROTOCOL"))
                .thenReturn(false);
        assertThatThrownBy(() -> adapter.save(source))
                .isInstanceOf(InvalidTelemetryValueException.class)
                .hasMessageContaining("active PROTOCOL");
    }

    @Test
    void migrationEnforcesAllSourceSemantics() throws Exception {
        String sql = Files.readString(Path.of(
                "src/main/resources/db/migration/"
                        + "V20261004_030__hmr_030_telemetry_telemetry_source.sql"
        ));

        assertThat(sql).contains("uq_hmr030_telemetry_source_code");
        assertThat(sql).contains("ck_hmr030_telemetry_source_name_fr_nonblank");
        assertThat(sql).contains("ck_hmr030_telemetry_source_status");
        assertThat(sql).contains("'DRAFT'", "'ACTIVE'", "'INACTIVE'", "'SUSPENDED'", "'RETIRED'");
        assertThat(sql).doesNotContain("'PLANNED'", "'MAINTENANCE'");
        assertThat(sql).contains("catalog_name = 'SOURCE_TYPE'");
        assertThat(sql).contains("catalog_name = 'PROTOCOL'");
        assertThat(sql).contains("active = true");
        assertThat(sql).contains("hmr030_contains_secret_material");
        assertThat(sql).contains("password=%");
        assertThat(sql).contains("trg_hmr030_telemetry_source_catalogs");
    }

    private static TelemetrySource source(
            TelemetryLifecycleStatus status,
            String endpointUri,
            String externalReference
    ) {
        return new TelemetrySource(
                "source-1",
                "SRC-1",
                null,
                "Source principal",
                null,
                "source-type-1",
                "protocol-1",
                endpointUri,
                externalReference,
                status,
                NOW,
                NOW
        );
    }
}
