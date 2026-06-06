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

import java.time.Instant;
import java.util.List;

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
import dz.sh.hidra.modules.topology.application.dto.TopologyCatalogDto;
import dz.sh.hidra.modules.topology.application.port.in.ResolveTopologyCatalogTypeUseCase;
import dz.sh.hidra.modules.topology.application.query.ListFacilitiesQuery;
import dz.sh.hidra.modules.topology.application.query.ListPipelineAppurtenancesQuery;
import dz.sh.hidra.modules.topology.application.query.ListPipelineSystemsQuery;
import dz.sh.hidra.modules.topology.application.query.ListPipelinesQuery;
import dz.sh.hidra.modules.topology.application.query.ListTopologyConnectionsQuery;
import dz.sh.hidra.modules.topology.application.query.ListTopologyNodesQuery;

/**
 * Unit tests for TopologyRestMapper after catalog type localization.
 */
class TopologyRestMapperTest {

    private final TopologyRestMapper mapper = new TopologyRestMapper(catalogResolver());

    @Test
    void shouldMapRestRequestsToApplicationCommandsWithCatalogReferences() {
        CreatePipelineSystemCommand pipelineSystemCommand = mapper.toCommand(TopologyRestTestData.createPipelineSystemRequest(), "fr");
        CreatePipelineCommand pipelineCommand = mapper.toCommand(TopologyRestTestData.createPipelineRequest(), "fr");
        CreatePipelineSegmentCommand segmentCommand = mapper.toCommand(TopologyRestTestData.createPipelineSegmentRequest());
        CreatePipelineAppurtenanceCommand appurtenanceCommand = mapper.toCommand(TopologyRestTestData.createPipelineAppurtenanceRequest(), "fr");
        CreateTopologyConnectionCommand connectionCommand = mapper.toCommand(TopologyRestTestData.createTopologyConnectionRequest(), "fr");
        RegisterEquipmentCommand equipmentCommand = mapper.toCommand(TopologyRestTestData.registerEquipmentRequest(), "fr");

        assertEquals("GZ1", pipelineSystemCommand.code().value());
        assertEquals("GAS", pipelineSystemCommand.productType().name());
        assertEquals("GAS", pipelineSystemCommand.productType().id());
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
        CreatePipelineAppurtenanceCommand command = mapper.toCommand(TopologyRestTestData.createInjectionPointRequest(), "en");

        assertEquals("INJECTION_POINT", command.appurtenanceType().name());
        assertNull(command.valveType());
    }

    @Test
    void shouldMapListQueryParametersToCatalogReferenceQueries() {
        ListPipelineSystemsQuery systemsQuery = mapper.toListPipelineSystemsQuery("gas", "gas", "planned", 1, 25, "fr");
        ListPipelinesQuery pipelinesQuery = mapper.toListPipelinesQuery("line", "ps-1", "gas", "active", 0, 20, "fr");
        ListFacilitiesQuery facilitiesQuery = mapper.toListFacilitiesQuery("station", "terminal", "gas", "planned", 0, 10, "fr");
        ListTopologyNodesQuery nodesQuery = mapper.toListTopologyNodesQuery("node", "injection_point", "fac-1", null, "planned", 0, 10, "fr");
        ListPipelineAppurtenancesQuery appurtenancesQuery = mapper.toListPipelineAppurtenancesQuery("app", "pipe-1", "purge_point", null, "planned", 0, 10, "fr");
        ListTopologyConnectionsQuery connectionsQuery = mapper.toListTopologyConnectionsQuery("conn", "node-1", "node-2", "pipeline_segment", "segment", "planned", 0, 10, "fr");

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
    void shouldMapApplicationDtosToLocalizedRestResponses() {
        PipelineSystemResponse pipelineSystem = mapper.toResponse(TopologyRestTestData.pipelineSystemDto(), "fr");
        PipelineResponse pipeline = mapper.toResponse(TopologyRestTestData.pipelineDto(), "fr");
        FacilityResponse facility = mapper.toResponse(TopologyRestTestData.facilityDto(), "fr");
        TopologyNodeResponse node = mapper.toResponse(TopologyRestTestData.topologyNodeDto(), "fr");
        PipelineSegmentResponse segment = mapper.toResponse(TopologyRestTestData.pipelineSegmentDto());
        PipelineAppurtenanceResponse appurtenance = mapper.toResponse(TopologyRestTestData.pipelineAppurtenanceDto(), "fr");
        TopologyConnectionResponse connection = mapper.toResponse(TopologyRestTestData.topologyConnectionDto(), "fr");
        EquipmentResponse equipment = mapper.toResponse(TopologyRestTestData.equipmentDto(), "fr");

        assertEquals("GZ1", pipelineSystem.code());
        assertEquals("GAS", pipelineSystem.productType().code());
        assertEquals("fr:GAS", pipelineSystem.productType().label());
        assertEquals("TRC-OPS-EAST-CS-01", pipelineSystem.operationalOwnerReference().ownerCode());
        assertEquals("GZ1-LINE-A", pipeline.code());
        assertEquals("COMPRESSION_STATION", facility.facilityType().code());
        assertEquals("TRC-OPS-EAST-CS-01", facility.organizationUnitReference().referenceCode());
        assertEquals("FACILITY_INLET", node.nodeType().code());
        assertEquals("GZ1-SEG-001", segment.code());
        assertEquals("VALVE", appurtenance.appurtenanceType().code());
        assertEquals("BLOCK_VALVE", appurtenance.valveType().code());
        assertEquals("PIPELINE_SEGMENT", connection.connectionType().code());
        assertEquals("COMPRESSOR", equipment.equipmentType().code());
    }

    @Test
    void shouldMapApplicationPagesToLocalizedRestResponsePages() {
        PageResult<PipelineSystemResponse> systems = mapper.toPipelineSystemResponsePage(TopologyRestTestData.pipelineSystemPage(), "ar");
        PageResult<PipelineResponse> pipelines = mapper.toPipelineResponsePage(TopologyRestTestData.pipelinePage(), "ar");
        PageResult<FacilityResponse> facilities = mapper.toFacilityResponsePage(TopologyRestTestData.facilityPage(), "ar");
        PageResult<TopologyNodeResponse> nodes = mapper.toTopologyNodeResponsePage(TopologyRestTestData.topologyNodePage(), "ar");
        PageResult<PipelineSegmentResponse> segments = mapper.toPipelineSegmentResponsePage(TopologyRestTestData.pipelineSegmentPage());
        PageResult<PipelineAppurtenanceResponse> appurtenances = mapper.toPipelineAppurtenanceResponsePage(TopologyRestTestData.pipelineAppurtenancePage(), "ar");
        PageResult<TopologyConnectionResponse> connections = mapper.toTopologyConnectionResponsePage(TopologyRestTestData.topologyConnectionPage(), "ar");

        assertEquals(1L, systems.totalElements());
        assertEquals("ar:GAS", systems.items().get(0).productType().label());
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

    private static ResolveTopologyCatalogTypeUseCase catalogResolver() {
        return query -> new TopologyCatalogDto(
                query.code().value(),
                query.catalogName(),
                query.code().value(),
                "ACTIVE",
                0,
                true,
                query.locale() == null ? "en" : query.locale(),
                (query.locale() == null ? "en" : query.locale()) + ":" + query.code().value(),
                null,
                List.of(),
                Instant.EPOCH,
                Instant.EPOCH);
    }
}
