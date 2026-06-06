/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineSegmentApplicationServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Unit tests for pipeline segment application service.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.topology.application.command.CreatePipelineSegmentCommand;
import dz.sh.hidra.modules.topology.application.dto.PipelineSegmentDto;
import dz.sh.hidra.modules.topology.application.query.ListPipelineSegmentsQuery;
import dz.sh.hidra.modules.topology.domain.TopologyDomainTestData;
import dz.sh.hidra.modules.topology.domain.model.Pipeline;
import dz.sh.hidra.modules.topology.domain.model.PipelineSystem;
import dz.sh.hidra.modules.topology.domain.model.TopologyNode;
import dz.sh.hidra.modules.topology.domain.policy.FacilityTopologyPolicy;
import dz.sh.hidra.modules.topology.domain.policy.PipelineAppurtenancePolicy;
import dz.sh.hidra.modules.topology.domain.policy.TopologyAssetStatusPolicy;
import dz.sh.hidra.modules.topology.domain.policy.TopologyConnectivityPolicy;
import dz.sh.hidra.modules.topology.domain.service.TopologyRegistrationDomainService;
import dz.sh.hidra.modules.topology.domain.value.DiameterInInches;
import dz.sh.hidra.modules.topology.domain.value.LengthInKilometers;
import dz.sh.hidra.modules.topology.domain.value.NodeType;
import dz.sh.hidra.modules.topology.domain.value.TopologyStatus;

/**
 * Unit tests for PipelineSegmentApplicationService.
 */
class PipelineSegmentApplicationServiceTest {

    @Test
    void shouldCreatePipelineSegmentWhenPipelineAndNodesExist() {
        Fixture fixture = fixture();
        PipelineSegmentApplicationService service = new PipelineSegmentApplicationService(
                fixture.segments,
                fixture.pipelines,
                fixture.nodes,
                registrationDomainService());

        PipelineSegmentDto dto = service.createPipelineSegment(new CreatePipelineSegmentCommand(
                fixture.pipeline.id(),
                TopologyDomainTestData.code("APP-SEG"),
                TopologyDomainTestData.name("Application Segment"),
                fixture.fromNode.id(),
                fixture.toNode.id(),
                LengthInKilometers.of(25.0),
                DiameterInInches.of(42.0)));

        assertEquals("APP-SEG", dto.code());
        assertEquals(fixture.pipeline.id().value(), dto.pipelineId());
        assertEquals(fixture.fromNode.id().value(), dto.fromNodeId());
        assertEquals(fixture.toNode.id().value(), dto.toNodeId());
    }

    @Test
    void shouldRejectPipelineSegmentWhenPipelineIsMissing() {
        Fixture fixture = fixture();
        PipelineSegmentApplicationService service = new PipelineSegmentApplicationService(
                fixture.segments,
                new InMemoryTopologyRepositoryPorts.Pipelines(),
                fixture.nodes,
                registrationDomainService());

        assertThrows(BusinessRuleViolationException.class, () -> service.createPipelineSegment(new CreatePipelineSegmentCommand(
                fixture.pipeline.id(),
                TopologyDomainTestData.code("APP-SEG-MISSING-PIPE"),
                TopologyDomainTestData.name("Missing Pipeline Segment"),
                fixture.fromNode.id(),
                fixture.toNode.id(),
                LengthInKilometers.of(25.0),
                DiameterInInches.of(42.0))));
    }

    @Test
    void shouldRejectPipelineSegmentWhenEndpointNodeIsMissing() {
        Fixture fixture = fixture();
        InMemoryTopologyRepositoryPorts.TopologyNodes onlyFromNode = new InMemoryTopologyRepositoryPorts.TopologyNodes();
        onlyFromNode.save(fixture.fromNode);
        PipelineSegmentApplicationService service = new PipelineSegmentApplicationService(
                fixture.segments,
                fixture.pipelines,
                onlyFromNode,
                registrationDomainService());

        assertThrows(BusinessRuleViolationException.class, () -> service.createPipelineSegment(new CreatePipelineSegmentCommand(
                fixture.pipeline.id(),
                TopologyDomainTestData.code("APP-SEG-MISSING-NODE"),
                TopologyDomainTestData.name("Missing Node Segment"),
                fixture.fromNode.id(),
                fixture.toNode.id(),
                LengthInKilometers.of(25.0),
                DiameterInInches.of(42.0))));
    }

    @Test
    void shouldRejectDuplicatePipelineSegmentCode() {
        Fixture fixture = fixture();
        fixture.segments.save(TopologyDomainTestData.segment(fixture.pipeline, fixture.fromNode, fixture.toNode));
        PipelineSegmentApplicationService service = new PipelineSegmentApplicationService(
                fixture.segments,
                fixture.pipelines,
                fixture.nodes,
                registrationDomainService());

        assertThrows(BusinessRuleViolationException.class, () -> service.createPipelineSegment(new CreatePipelineSegmentCommand(
                TopologyDomainTestData.segment(fixture.pipeline, fixture.fromNode, fixture.toNode).pipelineId(),
                TopologyDomainTestData.segment(fixture.pipeline, fixture.fromNode, fixture.toNode).code(),
                TopologyDomainTestData.name("Duplicate Segment"),
                fixture.fromNode.id(),
                fixture.toNode.id(),
                LengthInKilometers.of(25.0),
                DiameterInInches.of(42.0))));
    }

    @Test
    void shouldListPipelineSegments() {
        Fixture fixture = fixture();
        fixture.segments.save(TopologyDomainTestData.segment(fixture.pipeline, fixture.fromNode, fixture.toNode));
        PipelineSegmentApplicationService service = new PipelineSegmentApplicationService(
                fixture.segments,
                fixture.pipelines,
                fixture.nodes,
                registrationDomainService());

        PageResult<PipelineSegmentDto> page = service.listPipelineSegments(new ListPipelineSegmentsQuery(
                null,
                fixture.pipeline.id(),
                fixture.fromNode.id(),
                fixture.toNode.id(),
                TopologyStatus.PLANNED,
                PageRequest.of(0, 10)));

        assertEquals(1L, page.totalElements());
        assertEquals("TOP-SEG", page.items().get(0).code());
    }

    private static Fixture fixture() {
        PipelineSystem pipelineSystem = TopologyDomainTestData.pipelineSystem();
        Pipeline pipeline = TopologyDomainTestData.pipeline(pipelineSystem);
        TopologyNode fromNode = TopologyDomainTestData.freeNode(NodeType.PIPELINE_JUNCTION, "APP-SEG-FROM");
        TopologyNode toNode = TopologyDomainTestData.freeNode(NodeType.PIPELINE_JUNCTION, "APP-SEG-TO");

        InMemoryTopologyRepositoryPorts.Pipelines pipelines = new InMemoryTopologyRepositoryPorts.Pipelines();
        pipelines.save(pipeline);
        InMemoryTopologyRepositoryPorts.TopologyNodes nodes = new InMemoryTopologyRepositoryPorts.TopologyNodes();
        nodes.save(fromNode);
        nodes.save(toNode);

        return new Fixture(pipeline, fromNode, toNode, pipelines, nodes, new InMemoryTopologyRepositoryPorts.PipelineSegments());
    }

    private record Fixture(
            Pipeline pipeline,
            TopologyNode fromNode,
            TopologyNode toNode,
            InMemoryTopologyRepositoryPorts.Pipelines pipelines,
            InMemoryTopologyRepositoryPorts.TopologyNodes nodes,
            InMemoryTopologyRepositoryPorts.PipelineSegments segments) {
    }

    private static TopologyRegistrationDomainService registrationDomainService() {
        return new TopologyRegistrationDomainService(
                new TopologyAssetStatusPolicy(),
                new TopologyConnectivityPolicy(),
                new FacilityTopologyPolicy(),
                new PipelineAppurtenancePolicy());
    }


}
