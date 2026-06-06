/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyRepositoryAdaptersTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Unit tests for topology persistence repository adapters.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.modules.topology.application.query.ListFacilitiesQuery;
import dz.sh.hidra.modules.topology.application.query.ListPipelineAppurtenancesQuery;
import dz.sh.hidra.modules.topology.application.query.ListPipelineSegmentsQuery;
import dz.sh.hidra.modules.topology.application.query.ListPipelineSystemsQuery;
import dz.sh.hidra.modules.topology.application.query.ListPipelinesQuery;
import dz.sh.hidra.modules.topology.application.query.ListTopologyConnectionsQuery;
import dz.sh.hidra.modules.topology.application.query.ListTopologyNodesQuery;
import dz.sh.hidra.modules.topology.domain.TopologyDomainTestData;
import dz.sh.hidra.modules.topology.domain.model.Equipment;
import dz.sh.hidra.modules.topology.domain.model.Facility;
import dz.sh.hidra.modules.topology.domain.model.Pipeline;
import dz.sh.hidra.modules.topology.domain.model.PipelineAppurtenance;
import dz.sh.hidra.modules.topology.domain.model.PipelineSegment;
import dz.sh.hidra.modules.topology.domain.model.PipelineSystem;
import dz.sh.hidra.modules.topology.domain.model.TopologyConnection;
import dz.sh.hidra.modules.topology.domain.model.TopologyNode;
import dz.sh.hidra.modules.topology.domain.value.ConnectionType;
import dz.sh.hidra.modules.topology.domain.value.NodeType;
import dz.sh.hidra.modules.topology.domain.value.PipelineAppurtenanceType;
import dz.sh.hidra.modules.topology.domain.value.ProductType;
import dz.sh.hidra.modules.topology.domain.value.TopologyAssetType;
import dz.sh.hidra.modules.topology.domain.value.TopologyStatus;
import dz.sh.hidra.modules.topology.domain.value.ValveType;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.EquipmentJpaEntity;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.FacilityJpaEntity;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.PipelineAppurtenanceJpaEntity;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.PipelineJpaEntity;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.PipelineSegmentJpaEntity;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.PipelineSystemJpaEntity;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.TopologyConnectionJpaEntity;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.TopologyNodeJpaEntity;
import dz.sh.hidra.modules.topology.infrastructure.persistence.mapper.TopologyPersistenceMapper;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.EquipmentJpaRepository;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.FacilityJpaRepository;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.PipelineAppurtenanceJpaRepository;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.PipelineJpaRepository;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.PipelineSegmentJpaRepository;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.PipelineSystemJpaRepository;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.TopologyConnectionJpaRepository;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.TopologyNodeJpaRepository;

/**
 * Unit tests for topology persistence repository adapters.
 *
 * <p>Business role:
 * Verifies that topology adapters save, load, check code uniqueness, and page mapped domain models
 * correctly.
 *
 * <p>Architecture role:
 * These tests exercise infrastructure adapters without Spring, PostgreSQL, H2, Testcontainers,
 * Flyway, REST, or application services.
 *
 * <p>Validation:
 * Dynamic in-memory Spring Data repository proxies return JPA entities so adapter mapping behavior is
 * still exercised.
 */
class TopologyRepositoryAdaptersTest {

    private final TopologyPersistenceMapper mapper = new TopologyPersistenceMapper();

    @Test
    void shouldPersistAndQueryPipelineSystemsThroughAdapter() {
        PipelineSystem pipelineSystem = TopologyDomainTestData.pipelineSystem();
        PipelineSystemRepositoryAdapter adapter = new PipelineSystemRepositoryAdapter(
                repository(PipelineSystemJpaRepository.class, PipelineSystemJpaEntity::getId, PipelineSystemJpaEntity::getCode),
                mapper);

        PipelineSystem saved = adapter.save(pipelineSystem);
        PageResult<PipelineSystem> page = adapter.findAll(new ListPipelineSystemsQuery(
                "PS",
                ProductType.GAS,
                TopologyStatus.PLANNED,
                PageRequest.of(0, 10)));

        assertEquals(pipelineSystem.code(), saved.code());
        assertTrue(adapter.findById(pipelineSystem.id()).isPresent());
        assertTrue(adapter.findByCode(pipelineSystem.code()).isPresent());
        assertTrue(adapter.existsByCode(pipelineSystem.code()));
        assertEquals(1L, page.totalElements());
    }

    @Test
    void shouldPersistAndQueryPipelinesThroughAdapter() {
        PipelineSystem pipelineSystem = TopologyDomainTestData.pipelineSystem();
        Pipeline pipeline = TopologyDomainTestData.pipeline(pipelineSystem);
        PipelineRepositoryAdapter adapter = new PipelineRepositoryAdapter(
                repository(PipelineJpaRepository.class, PipelineJpaEntity::getId, PipelineJpaEntity::getCode),
                mapper);

        Pipeline saved = adapter.save(pipeline);
        PageResult<Pipeline> page = adapter.findAll(new ListPipelinesQuery(
                "PIPE",
                pipelineSystem.id(),
                ProductType.GAS,
                TopologyStatus.PLANNED,
                PageRequest.of(0, 10)));

        assertEquals(pipeline.code(), saved.code());
        assertTrue(adapter.findById(pipeline.id()).isPresent());
        assertTrue(adapter.findByCode(pipeline.code()).isPresent());
        assertTrue(adapter.existsByCode(pipeline.code()));
        assertEquals(1L, page.totalElements());
    }

    @Test
    void shouldPersistAndQueryFacilitiesThroughAdapter() {
        Facility facility = TopologyDomainTestData.facility();
        FacilityRepositoryAdapter adapter = new FacilityRepositoryAdapter(
                repository(FacilityJpaRepository.class, FacilityJpaEntity::getId, FacilityJpaEntity::getCode),
                mapper);

        Facility saved = adapter.save(facility);
        PageResult<Facility> page = adapter.findAll(new ListFacilitiesQuery(
                "FAC",
                facility.facilityType(),
                ProductType.GAS,
                TopologyStatus.PLANNED,
                PageRequest.of(0, 10)));

        assertEquals(facility.code(), saved.code());
        assertTrue(adapter.findById(facility.id()).isPresent());
        assertTrue(adapter.findByCode(facility.code()).isPresent());
        assertTrue(adapter.existsByCode(facility.code()));
        assertEquals(1L, page.totalElements());
    }

    @Test
    void shouldPersistAndQueryTopologyNodesThroughAdapter() {
        Facility facility = TopologyDomainTestData.facility();
        TopologyNode node = TopologyDomainTestData.facilityNode(facility, NodeType.FACILITY_INLET);
        TopologyNodeRepositoryAdapter adapter = new TopologyNodeRepositoryAdapter(
                repository(TopologyNodeJpaRepository.class, TopologyNodeJpaEntity::getId, TopologyNodeJpaEntity::getCode),
                mapper);

        TopologyNode saved = adapter.save(node);
        PageResult<TopologyNode> page = adapter.findAll(new ListTopologyNodesQuery(
                "NODE",
                NodeType.FACILITY_INLET,
                facility.id(),
                null,
                TopologyStatus.PLANNED,
                PageRequest.of(0, 10)));

        assertEquals(node.code(), saved.code());
        assertTrue(adapter.findById(node.id()).isPresent());
        assertTrue(adapter.findByCode(node.code()).isPresent());
        assertTrue(adapter.existsByCode(node.code()));
        assertEquals(1L, page.totalElements());
    }

    @Test
    void shouldPersistAndQueryPipelineSegmentsThroughAdapter() {
        Pipeline pipeline = TopologyDomainTestData.pipeline(TopologyDomainTestData.pipelineSystem());
        TopologyNode fromNode = TopologyDomainTestData.freeNode(NodeType.PIPELINE_JUNCTION, "ADAPTER-SEG-FROM");
        TopologyNode toNode = TopologyDomainTestData.freeNode(NodeType.PIPELINE_JUNCTION, "ADAPTER-SEG-TO");
        PipelineSegment segment = TopologyDomainTestData.segment(pipeline, fromNode, toNode);
        PipelineSegmentRepositoryAdapter adapter = new PipelineSegmentRepositoryAdapter(
                repository(PipelineSegmentJpaRepository.class, PipelineSegmentJpaEntity::getId, PipelineSegmentJpaEntity::getCode),
                mapper);

        PipelineSegment saved = adapter.save(segment);
        PageResult<PipelineSegment> page = adapter.findAll(new ListPipelineSegmentsQuery(
                "SEG",
                pipeline.id(),
                fromNode.id(),
                toNode.id(),
                TopologyStatus.PLANNED,
                PageRequest.of(0, 10)));

        assertEquals(segment.code(), saved.code());
        assertTrue(adapter.findById(segment.id()).isPresent());
        assertTrue(adapter.findByCode(segment.code()).isPresent());
        assertTrue(adapter.existsByCode(segment.code()));
        assertEquals(1L, page.totalElements());
    }

    @Test
    void shouldPersistAndQueryPipelineAppurtenancesThroughAdapter() {
        Pipeline pipeline = TopologyDomainTestData.pipeline(TopologyDomainTestData.pipelineSystem());
        TopologyNode node = TopologyDomainTestData.freeNode(NodeType.PIPELINE_VALVE_POINT, "ADAPTER-APP");
        PipelineAppurtenance appurtenance = TopologyDomainTestData.valve(pipeline, node);
        PipelineAppurtenanceRepositoryAdapter adapter = new PipelineAppurtenanceRepositoryAdapter(
                repository(PipelineAppurtenanceJpaRepository.class, PipelineAppurtenanceJpaEntity::getId, PipelineAppurtenanceJpaEntity::getCode),
                mapper);

        PipelineAppurtenance saved = adapter.save(appurtenance);
        PageResult<PipelineAppurtenance> page = adapter.findAll(new ListPipelineAppurtenancesQuery(
                "APP",
                pipeline.id(),
                PipelineAppurtenanceType.VALVE,
                ValveType.BLOCK_VALVE,
                TopologyStatus.PLANNED,
                PageRequest.of(0, 10)));

        assertEquals(appurtenance.code(), saved.code());
        assertTrue(adapter.findById(appurtenance.id()).isPresent());
        assertTrue(adapter.findByCode(appurtenance.code()).isPresent());
        assertTrue(adapter.existsByCode(appurtenance.code()));
        assertEquals(1L, page.totalElements());
    }

    @Test
    void shouldPersistAndQueryTopologyConnectionsThroughAdapter() {
        Pipeline pipeline = TopologyDomainTestData.pipeline(TopologyDomainTestData.pipelineSystem());
        TopologyNode fromNode = TopologyDomainTestData.freeNode(NodeType.PIPELINE_JUNCTION, "ADAPTER-CONN-FROM");
        TopologyNode toNode = TopologyDomainTestData.freeNode(NodeType.PIPELINE_JUNCTION, "ADAPTER-CONN-TO");
        PipelineSegment segment = TopologyDomainTestData.segment(pipeline, fromNode, toNode);
        TopologyConnection connection = TopologyDomainTestData.connection(fromNode, toNode, segment);
        TopologyConnectionRepositoryAdapter adapter = new TopologyConnectionRepositoryAdapter(
                repository(TopologyConnectionJpaRepository.class, TopologyConnectionJpaEntity::getId, TopologyConnectionJpaEntity::getCode),
                mapper);

        TopologyConnection saved = adapter.save(connection);
        PageResult<TopologyConnection> page = adapter.findAll(new ListTopologyConnectionsQuery(
                "CONN",
                fromNode.id(),
                toNode.id(),
                ConnectionType.PIPELINE_SEGMENT,
                TopologyAssetType.SEGMENT,
                TopologyStatus.PLANNED,
                PageRequest.of(0, 10)));

        assertEquals(connection.code(), saved.code());
        assertTrue(adapter.findById(connection.id()).isPresent());
        assertTrue(adapter.findByCode(connection.code()).isPresent());
        assertTrue(adapter.existsByCode(connection.code()));
        assertEquals(1L, page.totalElements());
    }

    @Test
    void shouldPersistAndQueryEquipmentThroughAdapter() {
        Equipment equipment = TopologyDomainTestData.equipment(TopologyDomainTestData.facility());
        EquipmentRepositoryAdapter adapter = new EquipmentRepositoryAdapter(
                repository(EquipmentJpaRepository.class, EquipmentJpaEntity::getId, EquipmentJpaEntity::getCode),
                mapper);

        Equipment saved = adapter.save(equipment);

        assertEquals(equipment.code(), saved.code());
        assertTrue(adapter.findById(equipment.id()).isPresent());
        assertTrue(adapter.findByCode(equipment.code()).isPresent());
        assertTrue(adapter.existsByCode(equipment.code()));
    }

    private static <R, E> R repository(
            Class<R> repositoryType,
            java.util.function.Function<E, String> idExtractor,
            java.util.function.Function<E, String> codeExtractor) {

        return DynamicTopologyJpaRepositoryFakes.repository(repositoryType, idExtractor, codeExtractor);
    }
}
