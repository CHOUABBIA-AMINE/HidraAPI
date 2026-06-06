/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineAppurtenanceApplicationServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Unit tests for pipeline appurtenance application service.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.topology.application.command.CreatePipelineAppurtenanceCommand;
import dz.sh.hidra.modules.topology.application.dto.PipelineAppurtenanceDto;
import dz.sh.hidra.modules.topology.application.query.GetPipelineAppurtenanceByIdQuery;
import dz.sh.hidra.modules.topology.application.query.ListPipelineAppurtenancesQuery;
import dz.sh.hidra.modules.topology.domain.TopologyDomainTestData;
import dz.sh.hidra.modules.topology.domain.model.Pipeline;
import dz.sh.hidra.modules.topology.domain.model.TopologyNode;
import dz.sh.hidra.modules.topology.domain.policy.FacilityTopologyPolicy;
import dz.sh.hidra.modules.topology.domain.policy.PipelineAppurtenancePolicy;
import dz.sh.hidra.modules.topology.domain.policy.TopologyAssetStatusPolicy;
import dz.sh.hidra.modules.topology.domain.policy.TopologyConnectivityPolicy;
import dz.sh.hidra.modules.topology.domain.service.PipelineAppurtenanceDomainService;
import dz.sh.hidra.modules.topology.domain.service.TopologyRegistrationDomainService;
import dz.sh.hidra.modules.topology.domain.value.GeoCoordinate;
import dz.sh.hidra.modules.topology.domain.value.NodeType;
import dz.sh.hidra.modules.topology.domain.value.PipelineAppurtenanceId;
import dz.sh.hidra.modules.topology.domain.value.PipelineAppurtenanceType;
import dz.sh.hidra.modules.topology.domain.value.PipelineKilometerPoint;
import dz.sh.hidra.modules.topology.domain.value.TopologyStatus;
import dz.sh.hidra.modules.topology.domain.value.ValveType;

/**
 * Unit tests for PipelineAppurtenanceApplicationService.
 */
class PipelineAppurtenanceApplicationServiceTest {

    @Test
    void shouldCreateValveAppurtenanceWhenPipelineAndNodeExist() {
        Fixture fixture = fixture(NodeType.PIPELINE_VALVE_POINT);
        PipelineAppurtenanceApplicationService service = new PipelineAppurtenanceApplicationService(
                fixture.appurtenances,
                fixture.pipelines,
                fixture.nodes,
                appurtenanceDomainService());

        PipelineAppurtenanceDto dto = service.createPipelineAppurtenance(new CreatePipelineAppurtenanceCommand(
                fixture.pipeline.id(),
                fixture.node.id(),
                TopologyDomainTestData.code("APP-VALVE"),
                TopologyDomainTestData.name("Application Valve"),
                PipelineAppurtenanceType.VALVE,
                ValveType.BLOCK_VALVE,
                PipelineKilometerPoint.of(25.0),
                GeoCoordinate.of(31.7000, 2.3000),
                " Application valve "));

        assertEquals("APP-VALVE", dto.code());
        assertEquals("VALVE", dto.appurtenanceType());
        assertEquals("BLOCK_VALVE", dto.valveType());
        assertEquals(fixture.pipeline.id().value(), dto.pipelineId());
        assertEquals(fixture.node.id().value(), dto.nodeId());
    }

    @Test
    void shouldCreateInjectionExtractionAndPurgeAppurtenances() {
        assertCreateSpecializedAppurtenance(NodeType.INJECTION_POINT, PipelineAppurtenanceType.INJECTION_POINT, "APP-INJ");
        assertCreateSpecializedAppurtenance(NodeType.EXTRACTION_POINT, PipelineAppurtenanceType.EXTRACTION_POINT, "APP-EXT");
        assertCreateSpecializedAppurtenance(NodeType.PURGE_POINT, PipelineAppurtenanceType.PURGE_POINT, "APP-PURGE");
    }

    @Test
    void shouldRejectAppurtenanceWhenPipelineIsMissing() {
        Fixture fixture = fixture(NodeType.PIPELINE_VALVE_POINT);
        PipelineAppurtenanceApplicationService service = new PipelineAppurtenanceApplicationService(
                fixture.appurtenances,
                new InMemoryTopologyRepositoryPorts.Pipelines(),
                fixture.nodes,
                appurtenanceDomainService());

        assertThrows(BusinessRuleViolationException.class, () -> service.createPipelineAppurtenance(valveCommand(fixture)));
    }

    @Test
    void shouldRejectAppurtenanceWhenNodeIsMissing() {
        Fixture fixture = fixture(NodeType.PIPELINE_VALVE_POINT);
        PipelineAppurtenanceApplicationService service = new PipelineAppurtenanceApplicationService(
                fixture.appurtenances,
                fixture.pipelines,
                new InMemoryTopologyRepositoryPorts.TopologyNodes(),
                appurtenanceDomainService());

        assertThrows(BusinessRuleViolationException.class, () -> service.createPipelineAppurtenance(valveCommand(fixture)));
    }

    @Test
    void shouldRejectDuplicateAppurtenanceCode() {
        Fixture fixture = fixture(NodeType.PIPELINE_VALVE_POINT);
        fixture.appurtenances.save(TopologyDomainTestData.valve(fixture.pipeline, fixture.node));
        PipelineAppurtenanceApplicationService service = new PipelineAppurtenanceApplicationService(
                fixture.appurtenances,
                fixture.pipelines,
                fixture.nodes,
                appurtenanceDomainService());

        assertThrows(BusinessRuleViolationException.class, () -> service.createPipelineAppurtenance(new CreatePipelineAppurtenanceCommand(
                fixture.pipeline.id(),
                fixture.node.id(),
                TopologyDomainTestData.valve(fixture.pipeline, fixture.node).code(),
                TopologyDomainTestData.name("Duplicate Appurtenance"),
                PipelineAppurtenanceType.VALVE,
                ValveType.BLOCK_VALVE,
                PipelineKilometerPoint.of(25.0),
                null,
                null)));
    }

    @Test
    void shouldGetAndListPipelineAppurtenances() {
        Fixture fixture = fixture(NodeType.PIPELINE_VALVE_POINT);
        fixture.appurtenances.save(TopologyDomainTestData.valve(fixture.pipeline, fixture.node));
        PipelineAppurtenanceApplicationService service = new PipelineAppurtenanceApplicationService(
                fixture.appurtenances,
                fixture.pipelines,
                fixture.nodes,
                appurtenanceDomainService());

        PipelineAppurtenanceDto found = service.getPipelineAppurtenance(new GetPipelineAppurtenanceByIdQuery(
                TopologyDomainTestData.valve(fixture.pipeline, fixture.node).id()));
        assertEquals("TOP-APP-VALVE", found.code());

        PageResult<PipelineAppurtenanceDto> page = service.listPipelineAppurtenances(new ListPipelineAppurtenancesQuery(
                null,
                fixture.pipeline.id(),
                PipelineAppurtenanceType.VALVE,
                ValveType.BLOCK_VALVE,
                TopologyStatus.PLANNED,
                PageRequest.of(0, 10)));

        assertEquals(1L, page.totalElements());
    }

    @Test
    void shouldRejectUnknownPipelineAppurtenanceId() {
        PipelineAppurtenanceApplicationService service = new PipelineAppurtenanceApplicationService(
                new InMemoryTopologyRepositoryPorts.PipelineAppurtenances(),
                new InMemoryTopologyRepositoryPorts.Pipelines(),
                new InMemoryTopologyRepositoryPorts.TopologyNodes(),
                appurtenanceDomainService());

        assertThrows(BusinessRuleViolationException.class, () -> service.getPipelineAppurtenance(
                new GetPipelineAppurtenanceByIdQuery(PipelineAppurtenanceId.newId())));
    }

    private static void assertCreateSpecializedAppurtenance(
            NodeType nodeType,
            PipelineAppurtenanceType appurtenanceType,
            String suffix) {

        Fixture fixture = fixture(nodeType);
        PipelineAppurtenanceApplicationService service = new PipelineAppurtenanceApplicationService(
                fixture.appurtenances,
                fixture.pipelines,
                fixture.nodes,
                appurtenanceDomainService());

        PipelineAppurtenanceDto dto = service.createPipelineAppurtenance(new CreatePipelineAppurtenanceCommand(
                fixture.pipeline.id(),
                fixture.node.id(),
                TopologyDomainTestData.code(suffix),
                TopologyDomainTestData.name(suffix),
                appurtenanceType,
                null,
                PipelineKilometerPoint.of(30.0),
                null,
                null));

        assertEquals(appurtenanceType.name(), dto.appurtenanceType());
    }

    private static CreatePipelineAppurtenanceCommand valveCommand(Fixture fixture) {
        return new CreatePipelineAppurtenanceCommand(
                fixture.pipeline.id(),
                fixture.node.id(),
                TopologyDomainTestData.code("APP-VALVE-MISSING"),
                TopologyDomainTestData.name("Valve Missing"),
                PipelineAppurtenanceType.VALVE,
                ValveType.BLOCK_VALVE,
                PipelineKilometerPoint.of(25.0),
                null,
                null);
    }

    private static Fixture fixture(NodeType nodeType) {
        Pipeline pipeline = TopologyDomainTestData.pipeline(TopologyDomainTestData.pipelineSystem());
        TopologyNode node = TopologyDomainTestData.freeNode(nodeType, "APP-" + nodeType.name());

        InMemoryTopologyRepositoryPorts.Pipelines pipelines = new InMemoryTopologyRepositoryPorts.Pipelines();
        pipelines.save(pipeline);
        InMemoryTopologyRepositoryPorts.TopologyNodes nodes = new InMemoryTopologyRepositoryPorts.TopologyNodes();
        nodes.save(node);

        return new Fixture(pipeline, node, pipelines, nodes, new InMemoryTopologyRepositoryPorts.PipelineAppurtenances());
    }

    private record Fixture(
            Pipeline pipeline,
            TopologyNode node,
            InMemoryTopologyRepositoryPorts.Pipelines pipelines,
            InMemoryTopologyRepositoryPorts.TopologyNodes nodes,
            InMemoryTopologyRepositoryPorts.PipelineAppurtenances appurtenances) {
    }

    private static TopologyRegistrationDomainService registrationDomainService() {
        return new TopologyRegistrationDomainService(
                new TopologyAssetStatusPolicy(),
                new TopologyConnectivityPolicy(),
                new FacilityTopologyPolicy(),
                new PipelineAppurtenancePolicy());
    }

    private static PipelineAppurtenanceDomainService appurtenanceDomainService() {
        return new PipelineAppurtenanceDomainService(
                new PipelineAppurtenancePolicy(),
                new TopologyConnectivityPolicy(),
                new TopologyAssetStatusPolicy());
    }

}
