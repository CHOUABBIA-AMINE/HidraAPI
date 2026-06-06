/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineAppurtenancePolicyTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.policy
 *
 * @Description : Unit tests for pipeline appurtenance policy.
 *
 */
package dz.sh.hidra.modules.topology.domain.policy;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.modules.topology.domain.TopologyDomainTestData;
import dz.sh.hidra.modules.topology.domain.exception.TopologyValidationException;
import dz.sh.hidra.modules.topology.domain.model.Pipeline;
import dz.sh.hidra.modules.topology.domain.model.PipelineAppurtenance;
import dz.sh.hidra.modules.topology.domain.model.PipelineSystem;
import dz.sh.hidra.modules.topology.domain.model.TopologyNode;
import dz.sh.hidra.modules.topology.domain.value.NodeType;
import dz.sh.hidra.modules.topology.domain.value.PipelineAppurtenanceType;
import dz.sh.hidra.modules.topology.domain.value.PipelineId;
import dz.sh.hidra.modules.topology.domain.value.ValveType;

/**
 * Unit tests for pipeline appurtenance policy.
 */
class PipelineAppurtenancePolicyTest {

    private final PipelineAppurtenancePolicy policy = new PipelineAppurtenancePolicy();

    @Test
    void shouldValidateValveTypeConsistency() {
        assertDoesNotThrow(() -> policy.validateValveTypeConsistency(PipelineAppurtenanceType.VALVE, ValveType.BLOCK_VALVE));
        assertDoesNotThrow(() -> policy.validateValveTypeConsistency(PipelineAppurtenanceType.INJECTION_POINT, null));

        assertThrows(TopologyValidationException.class, () -> policy.validateValveTypeConsistency(PipelineAppurtenanceType.VALVE, null));
        assertThrows(TopologyValidationException.class, () -> policy.validateValveTypeConsistency(PipelineAppurtenanceType.PURGE_POINT, ValveType.BLOCK_VALVE));
    }

    @Test
    void shouldValidateThatAppurtenanceBelongsToExpectedPipeline() {
        PipelineSystem pipelineSystem = TopologyDomainTestData.pipelineSystem();
        Pipeline pipeline = TopologyDomainTestData.pipeline(pipelineSystem);
        TopologyNode node = TopologyDomainTestData.freeNode(NodeType.PIPELINE_VALVE_POINT, "APP-POLICY-VALVE");
        PipelineAppurtenance appurtenance = TopologyDomainTestData.valve(pipeline, node);

        assertDoesNotThrow(() -> policy.validateBelongsToPipeline(appurtenance, pipeline));
    }

    @Test
    void shouldRejectAppurtenanceBelongingToDifferentPipeline() {
        PipelineSystem pipelineSystem = TopologyDomainTestData.pipelineSystem();
        Pipeline expectedPipeline = TopologyDomainTestData.pipeline(pipelineSystem);
        Pipeline otherPipeline = TopologyDomainTestData.pipelineRestoredWithSystemId(pipelineSystem.id());
        TopologyNode node = TopologyDomainTestData.freeNode(NodeType.PIPELINE_VALVE_POINT, "APP-POLICY-WRONG");
        PipelineAppurtenance appurtenance = TopologyDomainTestData.valve(otherPipeline, node);

        assertThrows(TopologyValidationException.class, () -> policy.validateBelongsToPipeline(appurtenance, expectedPipeline));
    }

    @Test
    void shouldValidateCompatibleNodesForValveInjectionExtractionAndPurgeAppurtenances() {
        Pipeline pipeline = TopologyDomainTestData.pipeline(TopologyDomainTestData.pipelineSystem());

        TopologyNode valveNode = TopologyDomainTestData.freeNode(NodeType.PIPELINE_VALVE_POINT, "POL-VALVE");
        TopologyNode injectionNode = TopologyDomainTestData.freeNode(NodeType.INJECTION_POINT, "POL-INJ");
        TopologyNode extractionNode = TopologyDomainTestData.freeNode(NodeType.EXTRACTION_POINT, "POL-EXT");
        TopologyNode purgeNode = TopologyDomainTestData.freeNode(NodeType.PURGE_POINT, "POL-PURGE");

        assertDoesNotThrow(() -> policy.validateNodeCompatibility(
                TopologyDomainTestData.valve(pipeline, valveNode),
                valveNode));
        assertDoesNotThrow(() -> policy.validateNodeCompatibility(
                TopologyDomainTestData.appurtenance(pipeline, injectionNode, PipelineAppurtenanceType.INJECTION_POINT, null, "POL-INJ"),
                injectionNode));
        assertDoesNotThrow(() -> policy.validateNodeCompatibility(
                TopologyDomainTestData.appurtenance(pipeline, extractionNode, PipelineAppurtenanceType.EXTRACTION_POINT, null, "POL-EXT"),
                extractionNode));
        assertDoesNotThrow(() -> policy.validateNodeCompatibility(
                TopologyDomainTestData.appurtenance(pipeline, purgeNode, PipelineAppurtenanceType.PURGE_POINT, null, "POL-PURGE"),
                purgeNode));
    }

    @Test
    void shouldRejectNodeTypeInconsistentWithAppurtenanceType() {
        Pipeline pipeline = TopologyDomainTestData.pipeline(TopologyDomainTestData.pipelineSystem());
        TopologyNode valveNode = TopologyDomainTestData.freeNode(NodeType.PIPELINE_VALVE_POINT, "POL-BAD-NODE");
        PipelineAppurtenance injectionPoint = TopologyDomainTestData.appurtenance(
                pipeline,
                valveNode,
                PipelineAppurtenanceType.INJECTION_POINT,
                null,
                "POL-BAD-INJ");

        assertThrows(TopologyValidationException.class, () -> policy.validateNodeCompatibility(injectionPoint, valveNode));
    }

    @Test
    void shouldRejectMismatchedNodeIdentifier() {
        Pipeline pipeline = TopologyDomainTestData.pipeline(TopologyDomainTestData.pipelineSystem());
        TopologyNode appurtenanceNode = TopologyDomainTestData.freeNode(NodeType.PIPELINE_VALVE_POINT, "POL-MATCHED");
        TopologyNode otherNode = TopologyDomainTestData.freeNode(NodeType.PIPELINE_VALVE_POINT, "POL-OTHER");
        PipelineAppurtenance appurtenance = TopologyDomainTestData.valve(pipeline, appurtenanceNode);

        assertThrows(TopologyValidationException.class, () -> policy.validateNodeCompatibility(appurtenance, otherNode));
    }
}
