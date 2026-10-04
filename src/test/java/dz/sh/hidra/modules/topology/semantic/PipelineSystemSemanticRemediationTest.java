/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineSystemSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Topology Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.semantic
 *
 * @Description : Verifies HMR-022 PipelineSystem classification catalog semantics.
 *
 */
package dz.sh.hidra.modules.topology.semantic;

import dz.sh.hidra.modules.topology.application.command.CreatePipelineSystemCommand;
import dz.sh.hidra.modules.topology.application.port.out.PipelineSystemRepositoryPort;
import dz.sh.hidra.modules.topology.application.service.PipelineSystemApplicationService;
import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import dz.sh.hidra.modules.topology.domain.model.PipelineSystem;
import dz.sh.hidra.modules.topology.domain.value.PipelineSystemType;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.PipelineSystemJpaEntity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PipelineSystemSemanticRemediationTest {

    @Test
    void systemTypeIsAnExtensibleCatalogReferenceRatherThanAnEnum() {
        PipelineSystemType reference = new PipelineSystemType(
                "FUTURE_CODE",
                " FUTURE_CODE ",
                " ",
                null,
                " Future "
        );

        assertThat(PipelineSystemType.class.isEnum()).isFalse();
        assertThat(reference.id()).isEqualTo("FUTURE_CODE");
        assertThat(reference.code()).isEqualTo("FUTURE_CODE");
        assertThat(reference.nameAr()).isNull();
        assertThat(reference.nameFr()).isNull();
        assertThat(reference.nameEn()).isEqualTo("Future");
    }

    @Test
    void createRequiresAnExplicitKnownSystemTypeCode() {
        StubRepository repository = new StubRepository();
        PipelineSystemApplicationService service = new PipelineSystemApplicationService(repository);

        assertThatThrownBy(() -> service.createPipelineSystem(command(null)))
                .isInstanceOf(InvalidTopologyValueException.class)
                .hasMessageContaining("system type code must not be blank");

        assertThatThrownBy(() -> service.createPipelineSystem(command("UNKNOWN")))
                .isInstanceOf(InvalidTopologyValueException.class)
                .hasMessageContaining("Unknown PipelineSystem system type code");

        var summary = service.createPipelineSystem(command(" TRANSPORT "));

        assertThat(summary.systemTypeId()).isEqualTo("TRANSPORT");
        assertThat(summary.systemTypeCode()).isEqualTo("TRANSPORT");
        assertThat(summary.systemTypeNameAr()).isNull();
        assertThat(summary.systemTypeNameFr()).isNull();
        assertThat(summary.systemTypeNameEn()).isNull();
        assertThat(repository.saved.systemType()).isEqualTo(PipelineSystemType.TRANSPORT);
    }

    @Test
    void jpaUsesMandatoryCatalogReferenceColumn() throws Exception {
        var field = PipelineSystemJpaEntity.class.getDeclaredField("systemType");

        ManyToOne manyToOne = field.getAnnotation(ManyToOne.class);
        JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);

        assertThat(manyToOne).isNotNull();
        assertThat(manyToOne.optional()).isFalse();
        assertThat(joinColumn).isNotNull();
        assertThat(joinColumn.name()).isEqualTo("system_type_id");
        assertThat(joinColumn.nullable()).isFalse();
    }

    @Test
    void migrationSeedsOnlyAuthoritativeCodesAndRetiresEnumColumn() throws Exception {
        String sql = Files.readString(Path.of(
                "src/main/resources/db/migration/V20261004_022__hmr_022_topology_pipeline_system.sql"
        ));

        assertThat(sql).contains("CREATE TABLE hidra_topology_pipeline_system_type");
        assertThat(sql).contains("'TRANSPORT'", "'GATHERING'", "'DISTRIBUTION'");
        assertThat(sql).contains("'EXPORT'", "'IMPORT'", "'MIXED'");
        assertThat(sql).contains("NULL, NULL, NULL, true");
        assertThat(sql).contains("system_type_id");
        assertThat(sql).contains("fk_hmr022_pipeline_system_type");
        assertThat(sql).contains("unknown PipelineSystem system_type values");
        assertThat(sql).contains("DROP COLUMN system_type");
    }

    private static CreatePipelineSystemCommand command(String systemTypeCode) {
        return new CreatePipelineSystemCommand(
                "SYS-1",
                null,
                "Système 1",
                "System 1",
                systemTypeCode,
                null
        );
    }

    private static final class StubRepository implements PipelineSystemRepositoryPort {
        private PipelineSystem saved;

        @Override
        public PipelineSystem save(PipelineSystem model) {
            saved = model;
            return model;
        }

        @Override
        public Optional<PipelineSystem> findById(String id) {
            return Optional.empty();
        }

        @Override
        public Optional<PipelineSystemType> findTypeByCode(String code) {
            return "TRANSPORT".equals(code)
                    ? Optional.of(PipelineSystemType.TRANSPORT)
                    : Optional.empty();
        }
    }
}
