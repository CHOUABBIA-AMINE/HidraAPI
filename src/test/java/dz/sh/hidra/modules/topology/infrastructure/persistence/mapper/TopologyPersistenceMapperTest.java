/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyPersistenceMapperTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.mapper
 *
 * @Description : Unit tests for topology persistence mapper round trips.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.modules.topology.domain.TopologyDomainTestData;
import dz.sh.hidra.modules.topology.domain.model.Equipment;
import dz.sh.hidra.modules.topology.domain.model.Facility;
import dz.sh.hidra.modules.topology.domain.model.Pipeline;
import dz.sh.hidra.modules.topology.domain.model.PipelineAppurtenance;
import dz.sh.hidra.modules.topology.domain.model.PipelineSegment;
import dz.sh.hidra.modules.topology.domain.model.PipelineSystem;
import dz.sh.hidra.modules.topology.domain.model.TopologyConnection;
import dz.sh.hidra.modules.topology.domain.model.TopologyNode;
import dz.sh.hidra.modules.topology.domain.value.NodeType;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.EquipmentJpaEntity;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.FacilityJpaEntity;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.PipelineAppurtenanceJpaEntity;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.PipelineJpaEntity;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.PipelineSegmentJpaEntity;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.PipelineSystemJpaEntity;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.TopologyConnectionJpaEntity;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.TopologyNodeJpaEntity;

/**
 * Unit tests for topology persistence mapper round trips.
 *
 * <p>Business role:
 * Verifies that physical topology master data keeps its business identity and key fields when mapped
 * to and from JPA entities.
 *
 * <p>Architecture role:
 * These tests isolate mapper behavior from Spring Data repositories and databases.
 *
 * <p>Validation:
 * Domain restore methods re-validate mapped values during entity-to-domain conversion.
 */
class TopologyPersistenceMapperTest {

    private final TopologyPersistenceMapper mapper = new TopologyPersistenceMapper();

    @Test
    void shouldMapPipelineSystemToEntityAndBack() {
        PipelineSystem pipelineSystem = TopologyDomainTestData.pipelineSystem();

        PipelineSystemJpaEntity entity = mapper.toEntity(pipelineSystem);
        PipelineSystem restored = mapper.toDomain(entity);

        assertEquals(pipelineSystem.id(), restored.id());
        assertEquals(pipelineSystem.code(), restored.code());
        assertEquals(pipelineSystem.name(), restored.name());
        assertEquals(pipelineSystem.description(), restored.description());
        assertEquals(pipelineSystem.productType(), restored.productType());
        assertEquals(pipelineSystem.status(), restored.status());
        assertEquals("TRC-OPS-EAST", restored.operationalOwnerReference().ownerCode());
    }

    @Test
    void shouldMapPipelineToEntityAndBack() {
        PipelineSystem pipelineSystem = TopologyDomainTestData.pipelineSystem();
        Pipeline pipeline = TopologyDomainTestData.pipeline(pipelineSystem);

        PipelineJpaEntity entity = mapper.toEntity(pipeline);
        Pipeline restored = mapper.toDomain(entity);

        assertEquals(pipeline.id(), restored.id());
        assertEquals(pipeline.pipelineSystemId(), restored.pipelineSystemId());
        assertEquals(pipeline.code(), restored.code());
        assertEquals(pipeline.nominalDiameter(), restored.nominalDiameter());
        assertEquals(pipeline.designLength(), restored.designLength());
    }

    @Test
    void shouldMapFacilityToEntityAndBack() {
        Facility facility = TopologyDomainTestData.facility();

        FacilityJpaEntity entity = mapper.toEntity(facility);
        Facility restored = mapper.toDomain(entity);

        assertEquals(facility.id(), restored.id());
        assertEquals(facility.code(), restored.code());
        assertEquals(facility.facilityType(), restored.facilityType());
        assertEquals(facility.productType(), restored.productType());
        assertEquals(facility.coordinate().latitude(), restored.coordinate().latitude());
        assertEquals("TRC-OPS-EAST-CS-01", restored.organizationUnitReference().referenceCode());
    }

    @Test
    void shouldMapTopologyNodeAndSegmentToEntityAndBack() {
        Pipeline pipeline = TopologyDomainTestData.pipeline(TopologyDomainTestData.pipelineSystem());
        TopologyNode fromNode = TopologyDomainTestData.freeNode(NodeType.PIPELINE_JUNCTION, "MAP-FROM");
        TopologyNode toNode = TopologyDomainTestData.freeNode(NodeType.PIPELINE_JUNCTION, "MAP-TO");
        PipelineSegment segment = TopologyDomainTestData.segment(pipeline, fromNode, toNode);

        TopologyNodeJpaEntity nodeEntity = mapper.toEntity(fromNode);
        TopologyNode restoredNode = mapper.toDomain(nodeEntity);
        PipelineSegmentJpaEntity segmentEntity = mapper.toEntity(segment);
        PipelineSegment restoredSegment = mapper.toDomain(segmentEntity);

        assertEquals(fromNode.id(), restoredNode.id());
        assertEquals(fromNode.nodeType(), restoredNode.nodeType());
        assertEquals(segment.id(), restoredSegment.id());
        assertEquals(fromNode.id(), restoredSegment.fromNodeId());
        assertEquals(toNode.id(), restoredSegment.toNodeId());
    }

    @Test
    void shouldMapPipelineAppurtenanceConnectionAndEquipmentToEntityAndBack() {
        Pipeline pipeline = TopologyDomainTestData.pipeline(TopologyDomainTestData.pipelineSystem());
        TopologyNode fromNode = TopologyDomainTestData.freeNode(NodeType.PIPELINE_VALVE_POINT, "MAP-VALVE");
        TopologyNode toNode = TopologyDomainTestData.freeNode(NodeType.PIPELINE_JUNCTION, "MAP-CONN-TO");
        PipelineSegment segment = TopologyDomainTestData.segment(pipeline, fromNode, toNode);
        PipelineAppurtenance appurtenance = TopologyDomainTestData.valve(pipeline, fromNode);
        TopologyConnection connection = TopologyDomainTestData.connection(fromNode, toNode, segment);
        Equipment equipment = TopologyDomainTestData.equipment(TopologyDomainTestData.facility());

        PipelineAppurtenanceJpaEntity appurtenanceEntity = mapper.toEntity(appurtenance);
        TopologyConnectionJpaEntity connectionEntity = mapper.toEntity(connection);
        EquipmentJpaEntity equipmentEntity = mapper.toEntity(equipment);

        PipelineAppurtenance restoredAppurtenance = mapper.toDomain(appurtenanceEntity);
        TopologyConnection restoredConnection = mapper.toDomain(connectionEntity);
        Equipment restoredEquipment = mapper.toDomain(equipmentEntity);

        assertEquals(appurtenance.id(), restoredAppurtenance.id());
        assertEquals(appurtenance.appurtenanceType(), restoredAppurtenance.appurtenanceType());
        assertEquals(appurtenance.valveType(), restoredAppurtenance.valveType());
        assertEquals(connection.id(), restoredConnection.id());
        assertEquals(connection.linkedAssetType(), restoredConnection.linkedAssetType());
        assertEquals(equipment.id(), restoredEquipment.id());
        assertEquals(equipment.parentAssetType(), restoredEquipment.parentAssetType());
    }

    @Test
    void shouldMapNullOptionalReferencesAndCoordinatesAsNull() {
        PipelineSystem pipelineSystem = PipelineSystem.create(
                TopologyDomainTestData.code("MAP-NO-OWNER"),
                TopologyDomainTestData.name("No Owner"),
                null,
                dz.sh.hidra.modules.topology.domain.value.ProductType.GAS,
                null);
        TopologyNode node = TopologyNode.create(
                TopologyDomainTestData.code("MAP-NO-COORD"),
                TopologyDomainTestData.name("No Coordinate"),
                NodeType.PIPELINE_JUNCTION,
                null,
                null,
                null,
                null);

        PipelineSystemJpaEntity pipelineSystemEntity = mapper.toEntity(pipelineSystem);
        TopologyNodeJpaEntity nodeEntity = mapper.toEntity(node);

        assertNull(pipelineSystemEntity.getOperationalOwnerReferenceCode());
        assertNull(mapper.toDomain(pipelineSystemEntity).operationalOwnerReference());
        assertNull(nodeEntity.getLatitude());
        assertNull(nodeEntity.getLongitude());
        assertNull(mapper.toDomain(nodeEntity).coordinate());
    }
}
