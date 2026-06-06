/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyConnectionApplicationServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Unit tests for topology connection application service.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.topology.application.command.CreateTopologyConnectionCommand;
import dz.sh.hidra.modules.topology.application.dto.TopologyConnectionDto;
import dz.sh.hidra.modules.topology.application.query.ListTopologyConnectionsQuery;
import dz.sh.hidra.modules.topology.domain.TopologyDomainTestData;
import dz.sh.hidra.modules.topology.domain.model.Pipeline;
import dz.sh.hidra.modules.topology.domain.model.PipelineSegment;
import dz.sh.hidra.modules.topology.domain.model.TopologyNode;
import dz.sh.hidra.modules.topology.domain.policy.FacilityTopologyPolicy;
import dz.sh.hidra.modules.topology.domain.policy.PipelineAppurtenancePolicy;
import dz.sh.hidra.modules.topology.domain.policy.TopologyAssetStatusPolicy;
import dz.sh.hidra.modules.topology.domain.policy.TopologyConnectivityPolicy;
import dz.sh.hidra.modules.topology.domain.service.TopologyRegistrationDomainService;
import dz.sh.hidra.modules.topology.domain.value.ConnectionType;
import dz.sh.hidra.modules.topology.domain.value.NodeType;
import dz.sh.hidra.modules.topology.domain.value.TopologyAssetType;
import dz.sh.hidra.modules.topology.domain.value.TopologyStatus;

/**
 * Unit tests for TopologyConnectionApplicationService.
 */
class TopologyConnectionApplicationServiceTest {

    @Test
    void shouldCreateTopologyConnectionWhenEndpointNodesExist() {
        Fixture fixture = fixture();
        TopologyConnectionApplicationService service = new TopologyConnectionApplicationService(
                fixture.connections,
                fixture.nodes,
                registrationDomainService());

        TopologyConnectionDto dto = service.createTopologyConnection(new CreateTopologyConnectionCommand(
                TopologyDomainTestData.code("APP-CONN"),
                TopologyDomainTestData.name("Application Connection"),
                fixture.fromNode.id(),
                fixture.toNode.id(),
                ConnectionType.PIPELINE_SEGMENT,
                TopologyAssetType.SEGMENT,
                fixture.segment.id().value()));

        assertEquals("APP-CONN", dto.code());
        assertEquals(fixture.fromNode.id().value(), dto.fromNodeId());
        assertEquals(fixture.toNode.id().value(), dto.toNodeId());
        assertEquals("PIPELINE_SEGMENT", dto.connectionType());
        assertEquals("SEGMENT", dto.linkedAssetType());
    }

    @Test
    void shouldRejectTopologyConnectionWhenEndpointNodeIsMissing() {
        Fixture fixture = fixture();
        InMemoryTopologyRepositoryPorts.TopologyNodes onlyFromNode = new InMemoryTopologyRepositoryPorts.TopologyNodes();
        onlyFromNode.save(fixture.fromNode);
        TopologyConnectionApplicationService service = new TopologyConnectionApplicationService(
                fixture.connections,
                onlyFromNode,
                registrationDomainService());

        assertThrows(BusinessRuleViolationException.class, () -> service.createTopologyConnection(new CreateTopologyConnectionCommand(
                TopologyDomainTestData.code("APP-CONN-MISSING"),
                TopologyDomainTestData.name("Connection Missing Node"),
                fixture.fromNode.id(),
                fixture.toNode.id(),
                ConnectionType.PIPELINE_SEGMENT,
                TopologyAssetType.SEGMENT,
                fixture.segment.id().value())));
    }

    @Test
    void shouldRejectDuplicateTopologyConnectionCode() {
        Fixture fixture = fixture();
        fixture.connections.save(TopologyDomainTestData.connection(fixture.fromNode, fixture.toNode, fixture.segment));
        TopologyConnectionApplicationService service = new TopologyConnectionApplicationService(
                fixture.connections,
                fixture.nodes,
                registrationDomainService());

        assertThrows(BusinessRuleViolationException.class, () -> service.createTopologyConnection(new CreateTopologyConnectionCommand(
                TopologyDomainTestData.connection(fixture.fromNode, fixture.toNode, fixture.segment).code(),
                TopologyDomainTestData.name("Duplicate Connection"),
                fixture.fromNode.id(),
                fixture.toNode.id(),
                ConnectionType.PIPELINE_SEGMENT,
                TopologyAssetType.SEGMENT,
                fixture.segment.id().value())));
    }

    @Test
    void shouldListTopologyConnections() {
        Fixture fixture = fixture();
        fixture.connections.save(TopologyDomainTestData.connection(fixture.fromNode, fixture.toNode, fixture.segment));
        TopologyConnectionApplicationService service = new TopologyConnectionApplicationService(
                fixture.connections,
                fixture.nodes,
                registrationDomainService());

        PageResult<TopologyConnectionDto> page = service.listTopologyConnections(new ListTopologyConnectionsQuery(
                null,
                fixture.fromNode.id(),
                fixture.toNode.id(),
                ConnectionType.PIPELINE_SEGMENT,
                TopologyAssetType.SEGMENT,
                TopologyStatus.PLANNED,
                PageRequest.of(0, 10)));

        assertEquals(1L, page.totalElements());
        assertEquals("TOP-CONN", page.items().get(0).code());
    }

    private static Fixture fixture() {
        Pipeline pipeline = TopologyDomainTestData.pipeline(TopologyDomainTestData.pipelineSystem());
        TopologyNode fromNode = TopologyDomainTestData.freeNode(NodeType.PIPELINE_JUNCTION, "APP-CONN-FROM");
        TopologyNode toNode = TopologyDomainTestData.freeNode(NodeType.PIPELINE_JUNCTION, "APP-CONN-TO");
        PipelineSegment segment = TopologyDomainTestData.segment(pipeline, fromNode, toNode);

        InMemoryTopologyRepositoryPorts.TopologyNodes nodes = new InMemoryTopologyRepositoryPorts.TopologyNodes();
        nodes.save(fromNode);
        nodes.save(toNode);

        return new Fixture(fromNode, toNode, segment, nodes, new InMemoryTopologyRepositoryPorts.TopologyConnections());
    }

    private record Fixture(
            TopologyNode fromNode,
            TopologyNode toNode,
            PipelineSegment segment,
            InMemoryTopologyRepositoryPorts.TopologyNodes nodes,
            InMemoryTopologyRepositoryPorts.TopologyConnections connections) {
    }

    private static TopologyRegistrationDomainService registrationDomainService() {
        return new TopologyRegistrationDomainService(
                new TopologyAssetStatusPolicy(),
                new TopologyConnectivityPolicy(),
                new FacilityTopologyPolicy(),
                new PipelineAppurtenancePolicy());
    }


}
