/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineAppurtenanceDomainServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.service
 *
 * @Description : Unit tests for pipeline appurtenance domain service.
 *
 */
package dz.sh.hidra.modules.topology.domain.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.modules.topology.domain.TopologyDomainTestData;
import dz.sh.hidra.modules.topology.domain.exception.TopologyValidationException;
import dz.sh.hidra.modules.topology.domain.model.Pipeline;
import dz.sh.hidra.modules.topology.domain.model.PipelineAppurtenance;
import dz.sh.hidra.modules.topology.domain.model.TopologyNode;
import dz.sh.hidra.modules.topology.domain.policy.PipelineAppurtenancePolicy;
import dz.sh.hidra.modules.topology.domain.policy.TopologyAssetStatusPolicy;
import dz.sh.hidra.modules.topology.domain.policy.TopologyConnectivityPolicy;
import dz.sh.hidra.modules.topology.domain.value.NodeType;
import dz.sh.hidra.modules.topology.domain.value.PipelineAppurtenanceType;

/**
 * Unit tests for pipeline appurtenance domain service.
 */
class PipelineAppurtenanceDomainServiceTest {

    private final PipelineAppurtenanceDomainService service = new PipelineAppurtenanceDomainService(
            new PipelineAppurtenancePolicy(),
            new TopologyConnectivityPolicy(),
            new TopologyAssetStatusPolicy());

    @Test
    void shouldValidateValveRegistrationWithCompatibleNode() {
        Pipeline pipeline = TopologyDomainTestData.pipeline(TopologyDomainTestData.pipelineSystem());
        TopologyNode node = TopologyDomainTestData.freeNode(NodeType.PIPELINE_VALVE_POINT, "SVC-VALVE");
        PipelineAppurtenance valve = TopologyDomainTestData.valve(pipeline, node);

        assertDoesNotThrow(() -> service.validateRegistration(pipeline, valve, node));
    }

    @Test
    void shouldRejectAppurtenanceRegistrationAgainstWrongPipeline() {
        Pipeline expectedPipeline = TopologyDomainTestData.pipeline(TopologyDomainTestData.pipelineSystem());
        Pipeline otherPipeline = TopologyDomainTestData.pipeline(TopologyDomainTestData.pipelineSystem());
        TopologyNode node = TopologyDomainTestData.freeNode(NodeType.PIPELINE_VALVE_POINT, "SVC-WRONG-PIPE");
        PipelineAppurtenance valve = TopologyDomainTestData.valve(otherPipeline, node);

        assertThrows(TopologyValidationException.class, () -> service.validateRegistration(expectedPipeline, valve, node));
    }

    @Test
    void shouldRequireValveInjectionExtractionAndPurgeSpecializations() {
        Pipeline pipeline = TopologyDomainTestData.pipeline(TopologyDomainTestData.pipelineSystem());

        TopologyNode valveNode = TopologyDomainTestData.freeNode(NodeType.PIPELINE_VALVE_POINT, "SVC-REQ-VALVE");
        TopologyNode injectionNode = TopologyDomainTestData.freeNode(NodeType.INJECTION_POINT, "SVC-REQ-INJ");
        TopologyNode extractionNode = TopologyDomainTestData.freeNode(NodeType.EXTRACTION_POINT, "SVC-REQ-EXT");
        TopologyNode purgeNode = TopologyDomainTestData.freeNode(NodeType.PURGE_POINT, "SVC-REQ-PURGE");

        assertDoesNotThrow(() -> service.requireValveAppurtenance(TopologyDomainTestData.valve(pipeline, valveNode)));
        assertDoesNotThrow(() -> service.requireInjectionPoint(TopologyDomainTestData.appurtenance(
                pipeline,
                injectionNode,
                PipelineAppurtenanceType.INJECTION_POINT,
                null,
                "SVC-REQ-INJ")));
        assertDoesNotThrow(() -> service.requireExtractionPoint(TopologyDomainTestData.appurtenance(
                pipeline,
                extractionNode,
                PipelineAppurtenanceType.EXTRACTION_POINT,
                null,
                "SVC-REQ-EXT")));
        assertDoesNotThrow(() -> service.requirePurgePoint(TopologyDomainTestData.appurtenance(
                pipeline,
                purgeNode,
                PipelineAppurtenanceType.PURGE_POINT,
                null,
                "SVC-REQ-PURGE")));
    }

    @Test
    void shouldRejectWrongSpecializedAppurtenanceType() {
        Pipeline pipeline = TopologyDomainTestData.pipeline(TopologyDomainTestData.pipelineSystem());
        TopologyNode valveNode = TopologyDomainTestData.freeNode(NodeType.PIPELINE_VALVE_POINT, "SVC-WRONG-TYPE");
        PipelineAppurtenance valve = TopologyDomainTestData.valve(pipeline, valveNode);

        assertThrows(TopologyValidationException.class, () -> service.requireInjectionPoint(valve));
    }
}
