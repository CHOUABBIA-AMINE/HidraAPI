/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyRestControllersTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.controller
 *
 * @Description : Unit tests for topology REST controllers.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.modules.topology.api.rest.TopologyRestTestData;
import dz.sh.hidra.modules.topology.api.rest.mapper.TopologyRestMapper;
import dz.sh.hidra.modules.topology.api.rest.response.EquipmentResponse;
import dz.sh.hidra.modules.topology.api.rest.response.FacilityResponse;
import dz.sh.hidra.modules.topology.api.rest.response.PipelineAppurtenanceResponse;
import dz.sh.hidra.modules.topology.api.rest.response.PipelineResponse;
import dz.sh.hidra.modules.topology.api.rest.response.PipelineSegmentResponse;
import dz.sh.hidra.modules.topology.api.rest.response.PipelineSystemResponse;
import dz.sh.hidra.modules.topology.api.rest.response.TopologyConnectionResponse;
import dz.sh.hidra.modules.topology.api.rest.response.TopologyNodeResponse;
import dz.sh.hidra.modules.topology.application.command.CreateFacilityCommand;
import dz.sh.hidra.modules.topology.application.command.CreatePipelineAppurtenanceCommand;
import dz.sh.hidra.modules.topology.application.command.CreatePipelineCommand;
import dz.sh.hidra.modules.topology.application.command.CreatePipelineSegmentCommand;
import dz.sh.hidra.modules.topology.application.command.CreatePipelineSystemCommand;
import dz.sh.hidra.modules.topology.application.command.CreateTopologyConnectionCommand;
import dz.sh.hidra.modules.topology.application.command.CreateTopologyNodeCommand;
import dz.sh.hidra.modules.topology.application.command.RegisterEquipmentCommand;
import dz.sh.hidra.modules.topology.application.dto.EquipmentDto;
import dz.sh.hidra.modules.topology.application.dto.FacilityDto;
import dz.sh.hidra.modules.topology.application.dto.PipelineAppurtenanceDto;
import dz.sh.hidra.modules.topology.application.dto.PipelineDto;
import dz.sh.hidra.modules.topology.application.dto.PipelineSegmentDto;
import dz.sh.hidra.modules.topology.application.dto.PipelineSystemDto;
import dz.sh.hidra.modules.topology.application.dto.TopologyConnectionDto;
import dz.sh.hidra.modules.topology.application.dto.TopologyNodeDto;
import dz.sh.hidra.modules.topology.application.port.in.CreateFacilityUseCase;
import dz.sh.hidra.modules.topology.application.port.in.CreatePipelineAppurtenanceUseCase;
import dz.sh.hidra.modules.topology.application.port.in.CreatePipelineSegmentUseCase;
import dz.sh.hidra.modules.topology.application.port.in.CreatePipelineSystemUseCase;
import dz.sh.hidra.modules.topology.application.port.in.CreatePipelineUseCase;
import dz.sh.hidra.modules.topology.application.port.in.CreateTopologyConnectionUseCase;
import dz.sh.hidra.modules.topology.application.port.in.CreateTopologyNodeUseCase;
import dz.sh.hidra.modules.topology.application.port.in.GetFacilityUseCase;
import dz.sh.hidra.modules.topology.application.port.in.GetPipelineAppurtenanceUseCase;
import dz.sh.hidra.modules.topology.application.port.in.GetPipelineSystemUseCase;
import dz.sh.hidra.modules.topology.application.port.in.GetPipelineUseCase;
import dz.sh.hidra.modules.topology.application.port.in.GetTopologyNodeUseCase;
import dz.sh.hidra.modules.topology.application.port.in.ListFacilitiesUseCase;
import dz.sh.hidra.modules.topology.application.port.in.ListPipelineAppurtenancesUseCase;
import dz.sh.hidra.modules.topology.application.port.in.ListPipelineSegmentsUseCase;
import dz.sh.hidra.modules.topology.application.port.in.ListPipelineSystemsUseCase;
import dz.sh.hidra.modules.topology.application.port.in.ListPipelinesUseCase;
import dz.sh.hidra.modules.topology.application.port.in.ListTopologyConnectionsUseCase;
import dz.sh.hidra.modules.topology.application.port.in.ListTopologyNodesUseCase;
import dz.sh.hidra.modules.topology.application.port.in.RegisterEquipmentUseCase;
import dz.sh.hidra.modules.topology.application.query.GetFacilityByIdQuery;
import dz.sh.hidra.modules.topology.application.query.GetPipelineAppurtenanceByIdQuery;
import dz.sh.hidra.modules.topology.application.query.GetPipelineByIdQuery;
import dz.sh.hidra.modules.topology.application.query.GetPipelineSystemByIdQuery;
import dz.sh.hidra.modules.topology.application.query.GetTopologyNodeByIdQuery;
import dz.sh.hidra.modules.topology.application.query.ListFacilitiesQuery;
import dz.sh.hidra.modules.topology.application.query.ListPipelineAppurtenancesQuery;
import dz.sh.hidra.modules.topology.application.query.ListPipelineSegmentsQuery;
import dz.sh.hidra.modules.topology.application.query.ListPipelineSystemsQuery;
import dz.sh.hidra.modules.topology.application.query.ListPipelinesQuery;
import dz.sh.hidra.modules.topology.application.query.ListTopologyConnectionsQuery;
import dz.sh.hidra.modules.topology.application.query.ListTopologyNodesQuery;

/**
 * Unit tests for topology REST controllers.
 *
 * <p>Business role:
 * Verifies direct HTTP controller orchestration for topology asset groups.
 *
 * <p>Architecture role:
 * Controllers are instantiated directly with fake inbound ports. No Spring context, MockMvc, server,
 * database, persistence adapter, or application service implementation is required.
 *
 * <p>Validation:
 * Tests verify create/get/list delegation, response mapping, CREATED status for commands, and the
 * endpoint boundary where pipeline segments/connections are list-only and equipment is register-only.
 */
class TopologyRestControllersTest {

    private final TopologyRestMapper mapper = new TopologyRestMapper();

    @Test
    void shouldDelegatePipelineSystemControllerOperations() {
        PipelineSystemUseCases useCases = new PipelineSystemUseCases();
        PipelineSystemController controller = new PipelineSystemController(useCases, useCases, useCases, mapper);

        ResponseEntity<PipelineSystemResponse> created = controller.createPipelineSystem(TopologyRestTestData.createPipelineSystemRequest());
        ResponseEntity<PipelineSystemResponse> found = controller.getPipelineSystem("ps-1");
        PageResult<PipelineSystemResponse> page = controller.listPipelineSystems("gas", "gas", "planned", 0, 20);

        assertEquals(HttpStatus.CREATED, created.getStatusCode());
        assertEquals("GZ1", created.getBody().code());
        assertEquals("ps-1", found.getBody().pipelineSystemId());
        assertEquals(1L, page.totalElements());
        assertEquals("GZ1", useCases.createdCommand.code().value());
        assertEquals("ps-1", useCases.getQuery.id().value());
        assertEquals("GAS", useCases.listQuery.productType().name());
    }

    @Test
    void shouldDelegatePipelineControllerOperations() {
        PipelineUseCases useCases = new PipelineUseCases();
        PipelineController controller = new PipelineController(useCases, useCases, useCases, mapper);

        ResponseEntity<PipelineResponse> created = controller.createPipeline(TopologyRestTestData.createPipelineRequest());
        ResponseEntity<PipelineResponse> found = controller.getPipeline("pipe-1");
        PageResult<PipelineResponse> page = controller.listPipelines("line", "ps-1", "gas", "planned", 0, 20);

        assertEquals(HttpStatus.CREATED, created.getStatusCode());
        assertEquals("GZ1-LINE-A", created.getBody().code());
        assertEquals("pipe-1", found.getBody().pipelineId());
        assertEquals(1L, page.totalElements());
        assertEquals("ps-1", useCases.createdCommand.pipelineSystemId().value());
        assertEquals("pipe-1", useCases.getQuery.id().value());
        assertEquals("ps-1", useCases.listQuery.pipelineSystemId().value());
    }

    @Test
    void shouldDelegateFacilityControllerOperations() {
        FacilityUseCases useCases = new FacilityUseCases();
        FacilityController controller = new FacilityController(useCases, useCases, useCases, mapper);

        ResponseEntity<FacilityResponse> created = controller.createFacility(TopologyRestTestData.createFacilityRequest());
        ResponseEntity<FacilityResponse> found = controller.getFacility("fac-1");
        PageResult<FacilityResponse> page = controller.listFacilities("station", "compression_station", "gas", "planned", 0, 20);

        assertEquals(HttpStatus.CREATED, created.getStatusCode());
        assertEquals("CS-EAST-01", created.getBody().code());
        assertEquals("fac-1", found.getBody().facilityId());
        assertEquals(1L, page.totalElements());
        assertEquals("COMPRESSION_STATION", useCases.createdCommand.facilityType().name());
        assertEquals("fac-1", useCases.getQuery.id().value());
        assertEquals("GAS", useCases.listQuery.productType().name());
    }

    @Test
    void shouldDelegateTopologyNodeControllerOperations() {
        TopologyNodeUseCases useCases = new TopologyNodeUseCases();
        TopologyNodeController controller = new TopologyNodeController(useCases, useCases, useCases, mapper);

        ResponseEntity<TopologyNodeResponse> created = controller.createTopologyNode(TopologyRestTestData.createTopologyNodeRequest());
        ResponseEntity<TopologyNodeResponse> found = controller.getTopologyNode("node-1");
        PageResult<TopologyNodeResponse> page = controller.listTopologyNodes("node", "facility_inlet", "fac-1", null, "planned", 0, 20);

        assertEquals(HttpStatus.CREATED, created.getStatusCode());
        assertEquals("NODE-CS-EAST-01-IN", created.getBody().code());
        assertEquals("node-1", found.getBody().topologyNodeId());
        assertEquals(1L, page.totalElements());
        assertEquals("FACILITY_INLET", useCases.createdCommand.nodeType().name());
        assertEquals("node-1", useCases.getQuery.id().value());
        assertEquals("fac-1", useCases.listQuery.facilityId().value());
    }

    @Test
    void shouldDelegatePipelineSegmentControllerOperationsWithoutGetEndpoint() {
        PipelineSegmentUseCases useCases = new PipelineSegmentUseCases();
        PipelineSegmentController controller = new PipelineSegmentController(useCases, useCases, mapper);

        ResponseEntity<PipelineSegmentResponse> created = controller.createPipelineSegment(TopologyRestTestData.createPipelineSegmentRequest());
        PageResult<PipelineSegmentResponse> page = controller.listPipelineSegments("seg", "pipe-1", "node-1", "node-2", "planned", 0, 20);

        assertEquals(HttpStatus.CREATED, created.getStatusCode());
        assertEquals("GZ1-SEG-001", created.getBody().code());
        assertEquals(1L, page.totalElements());
        assertEquals("pipe-1", useCases.createdCommand.pipelineId().value());
        assertEquals("node-1", useCases.listQuery.fromNodeId().value());
    }

    @Test
    void shouldDelegatePipelineAppurtenanceControllerOperations() {
        PipelineAppurtenanceUseCases useCases = new PipelineAppurtenanceUseCases();
        PipelineAppurtenanceController controller = new PipelineAppurtenanceController(useCases, useCases, useCases, mapper);

        ResponseEntity<PipelineAppurtenanceResponse> created = controller.createPipelineAppurtenance(TopologyRestTestData.createPipelineAppurtenanceRequest());
        ResponseEntity<PipelineAppurtenanceResponse> found = controller.getPipelineAppurtenance("app-1");
        PageResult<PipelineAppurtenanceResponse> page = controller.listPipelineAppurtenances("valve", "pipe-1", "valve", "block_valve", "planned", 0, 20);

        assertEquals(HttpStatus.CREATED, created.getStatusCode());
        assertEquals("GZ1-BV-001", created.getBody().code());
        assertEquals("app-1", found.getBody().pipelineAppurtenanceId());
        assertEquals(1L, page.totalElements());
        assertEquals("VALVE", useCases.createdCommand.appurtenanceType().name());
        assertEquals("app-1", useCases.getQuery.id().value());
        assertEquals("BLOCK_VALVE", useCases.listQuery.valveType().name());
    }

    @Test
    void shouldDelegateTopologyConnectionControllerOperationsWithoutGetEndpoint() {
        TopologyConnectionUseCases useCases = new TopologyConnectionUseCases();
        TopologyConnectionController controller = new TopologyConnectionController(useCases, useCases, mapper);

        ResponseEntity<TopologyConnectionResponse> created = controller.createTopologyConnection(TopologyRestTestData.createTopologyConnectionRequest());
        PageResult<TopologyConnectionResponse> page = controller.listTopologyConnections("conn", "node-1", "node-2", "pipeline_segment", "segment", "planned", 0, 20);

        assertEquals(HttpStatus.CREATED, created.getStatusCode());
        assertEquals("CONN-GZ1-001", created.getBody().code());
        assertEquals(1L, page.totalElements());
        assertEquals("PIPELINE_SEGMENT", useCases.createdCommand.connectionType().name());
        assertEquals("SEGMENT", useCases.listQuery.linkedAssetType().name());
    }

    @Test
    void shouldDelegateEquipmentControllerRegisterOnlyOperation() {
        EquipmentUseCases useCases = new EquipmentUseCases();
        EquipmentController controller = new EquipmentController(useCases, mapper);

        ResponseEntity<EquipmentResponse> created = controller.registerEquipment(TopologyRestTestData.registerEquipmentRequest());

        assertEquals(HttpStatus.CREATED, created.getStatusCode());
        assertEquals("CMP-CS-EAST-01-A", created.getBody().code());
        assertEquals("COMPRESSOR", useCases.registerCommand.equipmentType().name());
        assertEquals("FACILITY", useCases.registerCommand.parentAssetType().name());
        assertNotNull(created.getBody().createdAt());
    }

    private static final class PipelineSystemUseCases implements
            CreatePipelineSystemUseCase,
            GetPipelineSystemUseCase,
            ListPipelineSystemsUseCase {

        private CreatePipelineSystemCommand createdCommand;
        private GetPipelineSystemByIdQuery getQuery;
        private ListPipelineSystemsQuery listQuery;

        @Override
        public PipelineSystemDto createPipelineSystem(CreatePipelineSystemCommand command) {
            this.createdCommand = command;
            return TopologyRestTestData.pipelineSystemDto();
        }

        @Override
        public PipelineSystemDto getPipelineSystem(GetPipelineSystemByIdQuery query) {
            this.getQuery = query;
            return TopologyRestTestData.pipelineSystemDto();
        }

        @Override
        public PageResult<PipelineSystemDto> listPipelineSystems(ListPipelineSystemsQuery query) {
            this.listQuery = query;
            return TopologyRestTestData.pipelineSystemPage();
        }
    }

    private static final class PipelineUseCases implements
            CreatePipelineUseCase,
            GetPipelineUseCase,
            ListPipelinesUseCase {

        private CreatePipelineCommand createdCommand;
        private GetPipelineByIdQuery getQuery;
        private ListPipelinesQuery listQuery;

        @Override
        public PipelineDto createPipeline(CreatePipelineCommand command) {
            this.createdCommand = command;
            return TopologyRestTestData.pipelineDto();
        }

        @Override
        public PipelineDto getPipeline(GetPipelineByIdQuery query) {
            this.getQuery = query;
            return TopologyRestTestData.pipelineDto();
        }

        @Override
        public PageResult<PipelineDto> listPipelines(ListPipelinesQuery query) {
            this.listQuery = query;
            return TopologyRestTestData.pipelinePage();
        }
    }

    private static final class FacilityUseCases implements
            CreateFacilityUseCase,
            GetFacilityUseCase,
            ListFacilitiesUseCase {

        private CreateFacilityCommand createdCommand;
        private GetFacilityByIdQuery getQuery;
        private ListFacilitiesQuery listQuery;

        @Override
        public FacilityDto createFacility(CreateFacilityCommand command) {
            this.createdCommand = command;
            return TopologyRestTestData.facilityDto();
        }

        @Override
        public FacilityDto getFacility(GetFacilityByIdQuery query) {
            this.getQuery = query;
            return TopologyRestTestData.facilityDto();
        }

        @Override
        public PageResult<FacilityDto> listFacilities(ListFacilitiesQuery query) {
            this.listQuery = query;
            return TopologyRestTestData.facilityPage();
        }
    }

    private static final class TopologyNodeUseCases implements
            CreateTopologyNodeUseCase,
            GetTopologyNodeUseCase,
            ListTopologyNodesUseCase {

        private CreateTopologyNodeCommand createdCommand;
        private GetTopologyNodeByIdQuery getQuery;
        private ListTopologyNodesQuery listQuery;

        @Override
        public TopologyNodeDto createTopologyNode(CreateTopologyNodeCommand command) {
            this.createdCommand = command;
            return TopologyRestTestData.topologyNodeDto();
        }

        @Override
        public TopologyNodeDto getTopologyNode(GetTopologyNodeByIdQuery query) {
            this.getQuery = query;
            return TopologyRestTestData.topologyNodeDto();
        }

        @Override
        public PageResult<TopologyNodeDto> listTopologyNodes(ListTopologyNodesQuery query) {
            this.listQuery = query;
            return TopologyRestTestData.topologyNodePage();
        }
    }

    private static final class PipelineSegmentUseCases implements
            CreatePipelineSegmentUseCase,
            ListPipelineSegmentsUseCase {

        private CreatePipelineSegmentCommand createdCommand;
        private ListPipelineSegmentsQuery listQuery;

        @Override
        public PipelineSegmentDto createPipelineSegment(CreatePipelineSegmentCommand command) {
            this.createdCommand = command;
            return TopologyRestTestData.pipelineSegmentDto();
        }

        @Override
        public PageResult<PipelineSegmentDto> listPipelineSegments(ListPipelineSegmentsQuery query) {
            this.listQuery = query;
            return TopologyRestTestData.pipelineSegmentPage();
        }
    }

    private static final class PipelineAppurtenanceUseCases implements
            CreatePipelineAppurtenanceUseCase,
            GetPipelineAppurtenanceUseCase,
            ListPipelineAppurtenancesUseCase {

        private CreatePipelineAppurtenanceCommand createdCommand;
        private GetPipelineAppurtenanceByIdQuery getQuery;
        private ListPipelineAppurtenancesQuery listQuery;

        @Override
        public PipelineAppurtenanceDto createPipelineAppurtenance(CreatePipelineAppurtenanceCommand command) {
            this.createdCommand = command;
            return TopologyRestTestData.pipelineAppurtenanceDto();
        }

        @Override
        public PipelineAppurtenanceDto getPipelineAppurtenance(GetPipelineAppurtenanceByIdQuery query) {
            this.getQuery = query;
            return TopologyRestTestData.pipelineAppurtenanceDto();
        }

        @Override
        public PageResult<PipelineAppurtenanceDto> listPipelineAppurtenances(ListPipelineAppurtenancesQuery query) {
            this.listQuery = query;
            return TopologyRestTestData.pipelineAppurtenancePage();
        }
    }

    private static final class TopologyConnectionUseCases implements
            CreateTopologyConnectionUseCase,
            ListTopologyConnectionsUseCase {

        private CreateTopologyConnectionCommand createdCommand;
        private ListTopologyConnectionsQuery listQuery;

        @Override
        public TopologyConnectionDto createTopologyConnection(CreateTopologyConnectionCommand command) {
            this.createdCommand = command;
            return TopologyRestTestData.topologyConnectionDto();
        }

        @Override
        public PageResult<TopologyConnectionDto> listTopologyConnections(ListTopologyConnectionsQuery query) {
            this.listQuery = query;
            return TopologyRestTestData.topologyConnectionPage();
        }
    }

    private static final class EquipmentUseCases implements RegisterEquipmentUseCase {

        private RegisterEquipmentCommand registerCommand;

        @Override
        public EquipmentDto registerEquipment(RegisterEquipmentCommand command) {
            this.registerCommand = command;
            return TopologyRestTestData.equipmentDto();
        }
    }
}
