/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyConnectionSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Topology Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.semantic
 *
 * @Description : Verifies HMR-031 connection-type catalog, self-loop, and PipelineSegment semantics.
 *
 */
package dz.sh.hidra.modules.topology.semantic;

import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyConnectionException;
import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import dz.sh.hidra.modules.topology.domain.model.TopologyConnection;
import dz.sh.hidra.modules.topology.domain.value.ConnectionTypeReference;
import dz.sh.hidra.modules.topology.domain.value.FlowDirection;
import dz.sh.hidra.modules.topology.domain.value.TopologyStatus;
import dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.JpaTopologyConnectionRepositoryAdapter;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.ConnectionTypeJpaEntity;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.ConnectionTypeJpaRepository;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.TopologyConnectionJpaRepository;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TopologyConnectionSemanticRemediationTest {

    private static final Instant NOW = Instant.parse("2026-10-04T00:00:00Z");

    @Test
    void connectionTypeIsCatalogBackedAndMultilingualLabelsRemainOptional() {
        ConnectionTypeReference type = ConnectionTypeReference.PIPELINE_SEGMENT;

        assertThat(type.id()).isEqualTo("PIPELINE_SEGMENT");
        assertThat(type.code()).isEqualTo("PIPELINE_SEGMENT");
        assertThat(type.nameAr()).isNull();
        assertThat(type.nameFr()).isNull();
        assertThat(type.nameEn()).isNull();
    }

    @Test
    void aggregateRejectsSelfConnection() {
        assertThatThrownBy(() -> connection("node-1", "node-1", null))
                .isInstanceOf(InvalidTopologyConnectionException.class)
                .hasMessageContaining("must not connect a node to itself");
    }

    @Test
    void adapterRejectsUnknownCatalogReferenceAndDanglingSegment() {
        TopologyConnectionJpaRepository repository =
                mock(TopologyConnectionJpaRepository.class);
        ConnectionTypeJpaRepository typeRepository =
                mock(ConnectionTypeJpaRepository.class);
        JpaTopologyConnectionRepositoryAdapter adapter =
                new JpaTopologyConnectionRepositoryAdapter(repository, typeRepository);

        TopologyConnection model = connection("node-1", "node-2", "segment-1");

        when(typeRepository.findById("PIPELINE_SEGMENT")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> adapter.save(model))
                .isInstanceOf(InvalidTopologyValueException.class)
                .hasMessageContaining("active catalog entry");

        when(typeRepository.findById("PIPELINE_SEGMENT"))
                .thenReturn(Optional.of(typeEntity("PIPELINE_SEGMENT")));
        when(repository.existsPipelineSegment("segment-1")).thenReturn(false);

        assertThatThrownBy(() -> adapter.save(model))
                .isInstanceOf(InvalidTopologyValueException.class)
                .hasMessageContaining("pipeline segment does not exist");
    }

    @Test
    void migrationProtectsCatalogSelfLoopAndOptionalSegmentReference() throws Exception {
        String sql = Files.readString(Path.of(
                "src/main/resources/db/migration/"
                        + "V20261004_031__hmr_031_topology_topology_connection.sql"
        ));

        assertThat(sql).contains("CREATE TABLE hidra_topology_connection_type");
        assertThat(sql).contains("'PIPELINE_SEGMENT'", "'DIRECT_LINK'", "'VIRTUAL_LINK'");
        assertThat(sql).contains("'TRANSFER_LINK'", "'MEASUREMENT_LINK'");
        assertThat(sql).contains("connection_type_id");
        assertThat(sql).contains("fk_hmr031_topology_connection_type");
        assertThat(sql).contains("ck_hmr031_topology_connection_no_self_loop");
        assertThat(sql).contains("from_node_id <> to_node_id");
        assertThat(sql).contains("fk_hmr031_topology_connection_pipeline_segment");
        assertThat(sql).contains("DROP COLUMN connection_type");
    }

    private static TopologyConnection connection(
            String fromNodeId,
            String toNodeId,
            String pipelineSegmentId
    ) {
        return new TopologyConnection(
                "connection-1",
                "CONN-1",
                fromNodeId,
                toNodeId,
                ConnectionTypeReference.PIPELINE_SEGMENT,
                FlowDirection.DIRECTED,
                pipelineSegmentId,
                null,
                null,
                TopologyStatus.DRAFT,
                NOW,
                NOW
        );
    }

    private static ConnectionTypeJpaEntity typeEntity(String code) {
        return new ConnectionTypeJpaEntity(
                code,
                code,
                null,
                null,
                null,
                true,
                NOW,
                NOW
        );
    }
}
