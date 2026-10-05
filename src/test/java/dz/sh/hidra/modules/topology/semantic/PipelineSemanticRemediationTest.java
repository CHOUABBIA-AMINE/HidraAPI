/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Class
 * @Layer       : Topology Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.semantic
 *
 * @Description : Verifies HMR-042 Pipeline classification catalog remediation.
 *
 */
package dz.sh.hidra.modules.topology.semantic;

import dz.sh.hidra.modules.topology.domain.value.PipelineType;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PipelineSemanticRemediationTest {

    @Test
    void pipelineTypeIsOpenCatalogReferenceNotClosedEnum() {
        PipelineType custom = new PipelineType(
                "CUSTOM-ID",
                "CUSTOM_PIPELINE_CLASS",
                null,
                "Classe personnalisée",
                null
        );

        assertThat(PipelineType.class.isEnum()).isFalse();
        assertThat(custom.id()).isEqualTo("CUSTOM-ID");
        assertThat(custom.code()).isEqualTo("CUSTOM_PIPELINE_CLASS");
        assertThat(custom.nameFr()).isEqualTo("Classe personnalisée");
        assertThat(PipelineType.NATURAL_GAS.code()).isEqualTo("NATURAL_GAS");
    }

    @Test
    void migrationSeedsFormerValuesAndReplacesLegacyColumnWithCatalogForeignKey() throws Exception {
        String sql = Files.readString(Path.of(
                "src/main/resources/db/migration/"
                        + "V20261004_042__hmr_042_topology_pipeline.sql"
        ));

        assertThat(sql)
                .contains("CREATE TABLE hidra_topology_pipeline_type")
                .contains("'CRUDE_OIL'", "'CONDENSATE'", "'NATURAL_GAS'", "'LPG'")
                .contains("'MULTI_PRODUCT'", "'WATER'", "'OTHER'")
                .contains("ADD COLUMN pipeline_type_id")
                .contains("FOREIGN KEY (pipeline_type_id)")
                .contains("REFERENCES hidra_topology_pipeline_type (id)")
                .contains("DROP COLUMN pipeline_type");
    }
}
