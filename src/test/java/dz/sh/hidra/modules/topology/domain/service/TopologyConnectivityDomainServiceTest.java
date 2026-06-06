/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyConnectivityDomainServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.service
 *
 * @Description : Unit tests for topology connectivity domain service.
 *
 */
package dz.sh.hidra.modules.topology.domain.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.modules.topology.domain.TopologyDomainTestData;
import dz.sh.hidra.modules.topology.domain.exception.TopologyValidationException;
import dz.sh.hidra.modules.topology.domain.model.Pipeline;
import dz.sh.hidra.modules.topology.domain.model.PipelineSegment;
import dz.sh.hidra.modules.topology.domain.model.PipelineSystem;
import dz.sh.hidra.modules.topology.domain.model.TopologyConnection;
import dz.sh.hidra.modules.topology.domain.model.TopologyNode;
import dz.sh.hidra.modules.topology.domain.value.ConnectionType;
import dz.sh.hidra.modules.topology.domain.value.NodeType;
import dz.sh.hidra.modules.topology.domain.value.TopologyAssetType;

/**
 * Unit tests for topology connectivity domain service.
 */
class TopologyConnectivityDomainServiceTest {

    private final TopologyConnectivityDomainService service = new TopologyConnectivityDomainService();

    @Test
    void shouldValidateSegmentConnectivityAgainstEndpointNodes() {
        PipelineSystem pipelineSystem = TopologyDomainTestData.pipelineSystem();
        Pipeline pipeline = TopologyDomainTestData.pipeline(pipelineSystem);
        TopologyNode fromNode = TopologyDomainTestData.freeNode(NodeType.PIPELINE_JUNCTION, "SEG-FROM");
        TopologyNode toNode = TopologyDomainTestData.freeNode(NodeType.PIPELINE_JUNCTION, "SEG-TO");
        PipelineSegment segment = TopologyDomainTestData.segment(pipeline, fromNode, toNode);

        assertDoesNotThrow(() -> service.validateSegmentConnectivity(segment, fromNode, toNode));
    }

    @Test
    void shouldRejectSegmentConnectivityWhenEndpointNodeDoesNotMatch() {
        Pipeline pipeline = TopologyDomainTestData.pipeline(TopologyDomainTestData.pipelineSystem());
        TopologyNode fromNode = TopologyDomainTestData.freeNode(NodeType.PIPELINE_JUNCTION, "SEG-FROM-MISMATCH");
        TopologyNode toNode = TopologyDomainTestData.freeNode(NodeType.PIPELINE_JUNCTION, "SEG-TO-MISMATCH");
        TopologyNode otherNode = TopologyDomainTestData.freeNode(NodeType.PIPELINE_JUNCTION, "SEG-OTHER-MISMATCH");
        PipelineSegment segment = TopologyDomainTestData.segment(pipeline, fromNode, toNode);

        assertThrows(TopologyValidationException.class, () -> service.validateSegmentConnectivity(segment, otherNode, toNode));
    }

    @Test
    void shouldRejectDuplicateNodeIdentifiers() {
        TopologyNode node = TopologyDomainTestData.freeNode(NodeType.PIPELINE_JUNCTION, "DUP");
        TopologyNode duplicateNode = TopologyDomainTestData.duplicateOf(node);

        assertThrows(TopologyValidationException.class, () -> service.ensureUniqueNodeIds(List.of(node, duplicateNode)));
    }

    @Test
    void shouldValidateConnectionConnectivityWhenLinkedAssetTypeMatchesConnectionType() {
        Pipeline pipeline = TopologyDomainTestData.pipeline(TopologyDomainTestData.pipelineSystem());
        TopologyNode fromNode = TopologyDomainTestData.freeNode(NodeType.PIPELINE_JUNCTION, "CONN-FROM");
        TopologyNode toNode = TopologyDomainTestData.freeNode(NodeType.PIPELINE_JUNCTION, "CONN-TO");
        PipelineSegment segment = TopologyDomainTestData.segment(pipeline, fromNode, toNode);
        TopologyConnection connection = TopologyDomainTestData.connection(fromNode, toNode, segment);

        assertDoesNotThrow(() -> service.validateConnectionConnectivity(connection, fromNode, toNode));
    }

    @Test
    void shouldRejectConnectionWhenLinkedAssetTypeIsInconsistentWithConnectionType() {
        TopologyNode fromNode = TopologyDomainTestData.freeNode(NodeType.PIPELINE_JUNCTION, "CONN-BAD-FROM");
        TopologyNode toNode = TopologyDomainTestData.freeNode(NodeType.PIPELINE_JUNCTION, "CONN-BAD-TO");
        TopologyConnection connection = TopologyDomainTestData.restoredConnection(
                fromNode,
                toNode,
                ConnectionType.PIPELINE_SEGMENT,
                TopologyAssetType.PIPELINE);

        assertThrows(TopologyValidationException.class, () -> service.validateConnectionConnectivity(connection, fromNode, toNode));
    }
}
