/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyRestMapperTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.mapper
 *
 * @Description : Unit tests for topology REST mapper.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.modules.topology.api.rest.TopologyRestTestData;
import dz.sh.hidra.modules.topology.api.rest.response.EquipmentResponse;
import dz.sh.hidra.modules.topology.api.rest.response.FacilityResponse;
import dz.sh.hidra.modules.topology.api.rest.response.PipelineAppurtenanceResponse;
import dz.sh.hidra.modules.topology.api.rest.response.PipelineResponse;
import dz.sh.hidra.modules.topology.api.rest.response.PipelineSegmentResponse;
import dz.sh.hidra.modules.topology.api.rest.response.PipelineSystemResponse;
import dz.sh.hidra.modules.topology.api.rest.response.TopologyConnectionResponse;
import dz.sh.hidra.modules.topology.api.rest.response.TopologyNodeResponse;
import dz.sh.hidra.modules.topology.application.command.CreatePipelineAppurtenanceCommand;
import dz.sh.hidra.modules.topology.application.command.CreatePipelineCommand;
import dz.sh.hidra.modules.topology.application.command.CreatePipelineSegmentCommand;
import dz.sh.hidra.modules.topology.application.command.CreatePipelineSystemCommand;
import dz.sh.hidra.modules.topology.application.command.CreateTopologyConnectionCommand;
import dz.sh.hidra.modules.topology.application.command.RegisterEquipmentCommand;
import dz.sh.hidra.modules.topology.application.query.ListFacilitiesQuery;
import dz.sh.hidra.modules.topology.application.query.ListPipelineAppurtenancesQuery;
import dz.sh.hidra.modules.topology.application.query.ListPipelineSystemsQuery;
import dz.sh.hidra.modules.topology.application.query.ListPipelinesQuery;
import dz.sh.hidra.modules.topology.application.query.ListTopologyConnectionsQuery;
import dz.sh.hidra.modules.topology.application.query.ListTopologyNodesQuery;
import dz.sh.hidra.modules.topology.domain.value.PipelineAppurtenanceType;

/**
 * Unit tests for TopologyRestMapper.
 *
 * <p>Business role:
 * Verifies REST request strings are converted to topology commands/queries and application DTOs are
 * converted to REST response contracts.
 *
 * <p>Architecture role:
 * API-layer mapper unit test. It does not use controllers, Spring, repositories, or persistence.
 *
 * <p>Validation:
 * Checks enum normalization, nested coordinate/reference mapping, PageResult mapping, and all
 * topology REST response groups.
 */
class TopologyRestMapperTest {

    private final TopologyRestMapper mapper = new TopologyRestMapper();

    @Test
    void shouldMapRestRequestsToApplicationCommands() {
        CreatePipelineSystemCommand pipelineSystemCommand = mapper.toCommand(TopologyRestTestData.createPipelineSystemRequest());
        CreatePipelineCommand pipelineCommand = mapper.toCommand(TopologyRestTestData.createPipelineRequest());
        CreatePipelineSegmentCommand segmentCommand = mapper.toCommand(TopologyRestTestData.createPipelineSegmentRequest());
        CreatePipelineAppurtenanceCommand appurtenanceCommand = mapper.toCommand(TopologyRestTestData.createPipelineAppurtenanceRequest());
        CreateTopologyConnectionCommand connectionCommand = mapper.toCommand(TopologyRestTestData.createTopologyConnectionRequest());
        RegisterEquipmentCommand equipmentCommand = mapper.toCommand(TopologyRestTestData.registerEquipmentRequest());

        assertEquals("GZ1", pipelineSystemCommand.code().value());
        assertEquals("GAS", pipelineSystemCommand.productType().name());
        assertEquals("TRC-OPS-EAST", pipelineSystemCommand.operationalOwnerReference().ownerCode());
        assertEquals("ps-1", pipelineCommand.pipelineSystemId().value());
        assertEquals("GZ1-SEG-001", segmentCommand.code().value());
        assertEquals("VALVE", appurtenanceCommand.appurtenanceType().name());
        assertEquals("BLOCK_VALVE", appurtenanceCommand.valveType().name());
        assertEquals("PIPELINE_SEGMENT", connectionCommand.connectionType().name());
        assertEquals("SEGMENT", connectionCommand.linkedAssetType().name());
        assertEquals("COMPRESSOR", equipmentCommand.equipmentType().name());
        assertEquals("FACILITY", equipmentCommand.parentAssetType().name());
    }

    @Test
    void shouldMapInjectionPointRequestWithoutValveType() {
        CreatePipelineAppurtenanceCommand command = mapper.toCommand(TopologyRestTestData.createInjectionPointRequest());

        assertEquals(PipelineAppurtenanceType.INJECTION_POINT, command.appurtenanceType());
        assertNull(command.valveType());
    }

    @Test
    void shouldMapListQueryParametersToApplicationQueries() {
        ListPipelineSystemsQuery systemsQuery = mapper.toListPipelineSystemsQuery("gas", "gas", "planned", 1, 25);
        ListPipelinesQuery pipelinesQuery = mapper.toListPipelinesQuery("line", "ps-1", "gas", "active", 0, 20);
        ListFacilitiesQuery facilitiesQuery = mapper.toListFacilitiesQuery("station", "terminal", "gas", "planned", 0, 10);
        ListTopologyNodesQuery nodesQuery = mapper.toListTopologyNodesQuery("node", "injection_point", "fac-1", null, "planned", 0, 10);
        ListPipelineAppurtenancesQuery appurtenancesQuery = mapper.toListPipelineAppurtenancesQuery("app", "pipe-1", "purge_point", null, "planned", 0, 10);
        ListTopologyConnectionsQuery connectionsQuery = mapper.toListTopologyConnectionsQuery("conn", "node-1", "node-2", "pipeline_segment", "segment", "planned", 0, 10);

        assertEquals("gas", systemsQuery.searchText());
        assertEquals("GAS", systemsQuery.productType().name());
        assertEquals("PLANNED", systemsQuery.status().name());
        assertEquals(1, systemsQuery.pageRequest().page());
        assertEquals(25, systemsQuery.pageRequest().size());
        assertEquals("ps-1", pipelinesQuery.pipelineSystemId().value());
        assertEquals("TERMINAL", facilitiesQuery.facilityType().name());
        assertEquals("INJECTION_POINT", nodesQuery.nodeType().name());
        assertEquals("PURGE_POINT", appurtenancesQuery.appurtenanceType().name());
        assertEquals("PIPELINE_SEGMENT", connectionsQuery.connectionType().name());
    }

    @Test
    void shouldMapApplicationDtosToRestResponses() {
        PipelineSystemResponse pipelineSystem = mapper.toResponse(TopologyRestTestData.pipelineSystemDto());
        PipelineResponse pipeline = mapper.toResponse(TopologyRestTestData.pipelineDto());
        FacilityResponse facility = mapper.toResponse(TopologyRestTestData.facilityDto());
        TopologyNodeResponse node = mapper.toResponse(TopologyRestTestData.topologyNodeDto());
        PipelineSegmentResponse segment = mapper.toResponse(TopologyRestTestData.pipelineSegmentDto());
        PipelineAppurtenanceResponse appurtenance = mapper.toResponse(TopologyRestTestData.pipelineAppurtenanceDto());
        TopologyConnectionResponse connection = mapper.toResponse(TopologyRestTestData.topologyConnectionDto());
        EquipmentResponse equipment = mapper.toResponse(TopologyRestTestData.equipmentDto());

        assertEquals("GZ1", pipelineSystem.code());
        assertEquals("TRC-OPS-EAST-CS-01", pipelineSystem.operationalOwnerReference().ownerCode());
        assertEquals("GZ1-LINE-A", pipeline.code());
        assertEquals("COMPRESSION_STATION", facility.facilityType());
        assertEquals("TRC-OPS-EAST-CS-01", facility.organizationUnitReference().referenceCode());
        assertEquals("FACILITY_INLET", node.nodeType());
        assertEquals("GZ1-SEG-001", segment.code());
        assertEquals("VALVE", appurtenance.appurtenanceType());
        assertEquals("BLOCK_VALVE", appurtenance.valveType());
        assertEquals("PIPELINE_SEGMENT", connection.connectionType());
        assertEquals("COMPRESSOR", equipment.equipmentType());
    }

    @Test
    void shouldMapApplicationPagesToRestResponsePages() {
        PageResult<PipelineSystemResponse> systems = mapper.toPipelineSystemResponsePage(TopologyRestTestData.pipelineSystemPage());
        PageResult<PipelineResponse> pipelines = mapper.toPipelineResponsePage(TopologyRestTestData.pipelinePage());
        PageResult<FacilityResponse> facilities = mapper.toFacilityResponsePage(TopologyRestTestData.facilityPage());
        PageResult<TopologyNodeResponse> nodes = mapper.toTopologyNodeResponsePage(TopologyRestTestData.topologyNodePage());
        PageResult<PipelineSegmentResponse> segments = mapper.toPipelineSegmentResponsePage(TopologyRestTestData.pipelineSegmentPage());
        PageResult<PipelineAppurtenanceResponse> appurtenances = mapper.toPipelineAppurtenanceResponsePage(TopologyRestTestData.pipelineAppurtenancePage());
        PageResult<TopologyConnectionResponse> connections = mapper.toTopologyConnectionResponsePage(TopologyRestTestData.topologyConnectionPage());

        assertEquals(1L, systems.totalElements());
        assertEquals("GZ1", systems.items().get(0).code());
        assertEquals("GZ1-LINE-A", pipelines.items().get(0).code());
        assertEquals("CS-EAST-01", facilities.items().get(0).code());
        assertEquals("NODE-CS-EAST-01-IN", nodes.items().get(0).code());
        assertEquals("GZ1-SEG-001", segments.items().get(0).code());
        assertEquals("GZ1-BV-001", appurtenances.items().get(0).code());
        assertEquals("CONN-GZ1-001", connections.items().get(0).code());
    }

    @Test
    void shouldMapNullOptionalNestedDtosToNullResponses() {
        assertNull(mapper.toResponse((dz.sh.hidra.modules.topology.application.dto.GeoCoordinateDto) null));
        assertNull(mapper.toResponse((dz.sh.hidra.modules.topology.application.dto.OrganizationUnitReferenceDto) null));
        assertNull(mapper.toOperationalOwnerReferenceResponse(null));
    }
}
