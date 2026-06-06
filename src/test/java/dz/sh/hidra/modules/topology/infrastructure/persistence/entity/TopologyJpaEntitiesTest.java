/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyJpaEntitiesTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.entity
 *
 * @Description : Unit tests for topology JPA entity constructors and accessors.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.Instant;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for topology JPA entity constructors and accessors.
 *
 * <p>Business role:
 * Verifies that topology persistence entities can be instantiated by JPA and can hold the physical
 * topology state required by migrations and repository adapters.
 *
 * <p>Architecture role:
 * These tests are infrastructure-only and do not require Spring, a database, Flyway, REST, or the
 * application layer.
 *
 * <p>Validation:
 * The tests intentionally check public no-argument constructors and representative accessors because
 * JPA requires visible constructors and mapper/adapter code depends on those accessors.
 */
class TopologyJpaEntitiesTest {

    @Test
    void shouldCreatePipelineSystemJpaEntityAndUseAccessors() {
        PipelineSystemJpaEntity entity = new PipelineSystemJpaEntity();
        Instant now = Instant.parse("2026-01-01T00:00:00Z");

        entity.setId("ps-1");
        entity.setCode("GZ1");
        entity.setName("Gas System");
        entity.setDescription("Main gas system");
        entity.setProductType("GAS");
        entity.setStatus("ACTIVE");
        entity.setOperationalOwnerReferenceType("ORGANIZATION_UNIT");
        entity.setOperationalOwnerReferenceId("ou-east");
        entity.setOperationalOwnerReferenceCode("TRC-OPS-EAST");
        entity.setOperationalOwnerReferenceName("Operational East Region");
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);

        assertEquals("ps-1", entity.getId());
        assertEquals("GZ1", entity.getCode());
        assertEquals("Gas System", entity.getName());
        assertEquals("Main gas system", entity.getDescription());
        assertEquals("GAS", entity.getProductType());
        assertEquals("ACTIVE", entity.getStatus());
        assertEquals("TRC-OPS-EAST", entity.getOperationalOwnerReferenceCode());
        assertEquals(now, entity.getCreatedAt());
        assertEquals(now, entity.getUpdatedAt());
    }

    @Test
    void shouldCreatePipelineJpaEntityAndUseAccessors() {
        PipelineJpaEntity entity = new PipelineJpaEntity();

        entity.setId("pipe-1");
        entity.setPipelineSystemId("ps-1");
        entity.setCode("GZ1-LINE-A");
        entity.setName("Line A");
        entity.setDescription("Main line");
        entity.setProductType("GAS");
        entity.setNominalDiameterInches(new BigDecimal("42.000"));
        entity.setDesignLengthKm(new BigDecimal("512.300"));
        entity.setStatus("PLANNED");

        assertEquals("pipe-1", entity.getId());
        assertEquals("ps-1", entity.getPipelineSystemId());
        assertEquals("GZ1-LINE-A", entity.getCode());
        assertEquals(new BigDecimal("42.000"), entity.getNominalDiameterInches());
        assertEquals(new BigDecimal("512.300"), entity.getDesignLengthKm());
    }

    @Test
    void shouldCreateFacilityJpaEntityAndUseAccessors() {
        FacilityJpaEntity entity = new FacilityJpaEntity();

        entity.setId("fac-1");
        entity.setCode("CS-EAST-01");
        entity.setName("Compression Station East 01");
        entity.setFacilityType("COMPRESSION_STATION");
        entity.setProductType("GAS");
        entity.setStatus("ACTIVE");
        entity.setLatitude(new BigDecimal("31.6167000"));
        entity.setLongitude(new BigDecimal("2.2167000"));
        entity.setOrganizationUnitReferenceType("ORGANIZATION_UNIT");
        entity.setOrganizationUnitReferenceId("ou-station");
        entity.setOrganizationUnitReferenceCode("TRC-OPS-EAST-CS-01");
        entity.setOrganizationUnitReferenceName("Compression Station East 01");

        assertEquals("fac-1", entity.getId());
        assertEquals("CS-EAST-01", entity.getCode());
        assertEquals("COMPRESSION_STATION", entity.getFacilityType());
        assertEquals(new BigDecimal("31.6167000"), entity.getLatitude());
        assertEquals("TRC-OPS-EAST-CS-01", entity.getOrganizationUnitReferenceCode());
    }

    @Test
    void shouldCreateNodeSegmentAppurtenanceConnectionAndEquipmentJpaEntities() {
        TopologyNodeJpaEntity node = new TopologyNodeJpaEntity();
        node.setId("node-1");
        node.setCode("NODE-1");
        node.setName("Node 1");
        node.setNodeType("INJECTION_POINT");
        node.setFacilityId("fac-1");
        node.setPipelineAppurtenanceId("app-1");
        node.setLatitude(new BigDecimal("31.6000000"));
        node.setLongitude(new BigDecimal("2.2000000"));
        node.setElevationMeters(new BigDecimal("725.300"));
        node.setStatus("ACTIVE");

        PipelineSegmentJpaEntity segment = new PipelineSegmentJpaEntity();
        segment.setId("seg-1");
        segment.setPipelineId("pipe-1");
        segment.setCode("SEG-1");
        segment.setName("Segment 1");
        segment.setFromNodeId("node-1");
        segment.setToNodeId("node-2");
        segment.setLengthKm(new BigDecimal("25.000"));
        segment.setDiameterInches(new BigDecimal("42.000"));
        segment.setStatus("ACTIVE");

        PipelineAppurtenanceJpaEntity appurtenance = new PipelineAppurtenanceJpaEntity();
        appurtenance.setId("app-1");
        appurtenance.setPipelineId("pipe-1");
        appurtenance.setNodeId("node-1");
        appurtenance.setCode("APP-1");
        appurtenance.setName("Block Valve");
        appurtenance.setAppurtenanceType("VALVE");
        appurtenance.setValveType("BLOCK_VALVE");
        appurtenance.setPipelineKilometerPoint(new BigDecimal("25.000"));
        appurtenance.setStatus("ACTIVE");

        TopologyConnectionJpaEntity connection = new TopologyConnectionJpaEntity();
        connection.setId("conn-1");
        connection.setCode("CONN-1");
        connection.setName("Connection 1");
        connection.setFromNodeId("node-1");
        connection.setToNodeId("node-2");
        connection.setConnectionType("PIPELINE_SEGMENT");
        connection.setLinkedAssetType("SEGMENT");
        connection.setLinkedAssetId("seg-1");
        connection.setStatus("ACTIVE");

        EquipmentJpaEntity equipment = new EquipmentJpaEntity();
        equipment.setId("eqp-1");
        equipment.setCode("EQP-1");
        equipment.setName("Compressor A");
        equipment.setEquipmentType("COMPRESSOR");
        equipment.setParentAssetType("FACILITY");
        equipment.setParentAssetId("fac-1");
        equipment.setStatus("ACTIVE");

        assertEquals("INJECTION_POINT", node.getNodeType());
        assertEquals("node-1", segment.getFromNodeId());
        assertEquals("BLOCK_VALVE", appurtenance.getValveType());
        assertEquals("SEGMENT", connection.getLinkedAssetType());
        assertEquals("FACILITY", equipment.getParentAssetType());
        assertNotNull(node);
        assertNotNull(segment);
        assertNotNull(appurtenance);
        assertNotNull(connection);
        assertNotNull(equipment);
    }
}
