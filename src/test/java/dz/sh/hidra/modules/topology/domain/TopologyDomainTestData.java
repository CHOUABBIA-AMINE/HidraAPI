/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyDomainTestData
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain
 *
 * @Description : Test data factory for topology domain unit tests.
 *
 */
package dz.sh.hidra.modules.topology.domain;

import java.math.BigDecimal;
import java.time.Instant;

import dz.sh.hidra.modules.topology.domain.model.Equipment;
import dz.sh.hidra.modules.topology.domain.model.Facility;
import dz.sh.hidra.modules.topology.domain.model.Pipeline;
import dz.sh.hidra.modules.topology.domain.model.PipelineAppurtenance;
import dz.sh.hidra.modules.topology.domain.model.PipelineSegment;
import dz.sh.hidra.modules.topology.domain.model.PipelineSystem;
import dz.sh.hidra.modules.topology.domain.model.TopologyConnection;
import dz.sh.hidra.modules.topology.domain.model.TopologyNode;
import dz.sh.hidra.modules.topology.domain.value.ConnectionType;
import dz.sh.hidra.modules.topology.domain.value.DiameterInInches;
import dz.sh.hidra.modules.topology.domain.value.EquipmentType;
import dz.sh.hidra.modules.topology.domain.value.FacilityId;
import dz.sh.hidra.modules.topology.domain.value.FacilityType;
import dz.sh.hidra.modules.topology.domain.value.GeoCoordinate;
import dz.sh.hidra.modules.topology.domain.value.LengthInKilometers;
import dz.sh.hidra.modules.topology.domain.value.NodeType;
import dz.sh.hidra.modules.topology.domain.value.OperationalOwnerReference;
import dz.sh.hidra.modules.topology.domain.value.OrganizationUnitReference;
import dz.sh.hidra.modules.topology.domain.value.PipelineAppurtenanceType;
import dz.sh.hidra.modules.topology.domain.value.PipelineId;
import dz.sh.hidra.modules.topology.domain.value.PipelineKilometerPoint;
import dz.sh.hidra.modules.topology.domain.value.PipelineSegmentId;
import dz.sh.hidra.modules.topology.domain.value.PipelineSystemId;
import dz.sh.hidra.modules.topology.domain.value.ProductType;
import dz.sh.hidra.modules.topology.domain.value.TopologyAssetType;
import dz.sh.hidra.modules.topology.domain.value.TopologyCode;
import dz.sh.hidra.modules.topology.domain.value.TopologyConnectionId;
import dz.sh.hidra.modules.topology.domain.value.TopologyName;
import dz.sh.hidra.modules.topology.domain.value.TopologyStatus;
import dz.sh.hidra.modules.topology.domain.value.ValveType;

/**
 * Test data factory for pure topology domain tests.
 *
 * <p>Business role:
 * Provides representative topology objects for pipeline systems, pipelines, facilities, nodes,
 * segments, appurtenances, connections, and equipment.
 *
 * <p>Architecture role:
 * This class exists in test source only and depends only on topology domain types.
 *
 * <p>Validation:
 * Test data uses valid defaults and intentionally exposes factory methods for invalid scenarios.
 */
public final class TopologyDomainTestData {

    private TopologyDomainTestData() {
    }

    public static TopologyCode code(String suffix) {
        return TopologyCode.of("TOP-" + suffix);
    }

    public static TopologyName name(String suffix) {
        return TopologyName.of("Topology " + suffix);
    }

    public static OperationalOwnerReference operationalOwnerReference() {
        return OperationalOwnerReference.of("ORGANIZATION_UNIT", "ou-east", "TRC-OPS-EAST", "Operational East Region");
    }

    public static OrganizationUnitReference organizationUnitReference() {
        return OrganizationUnitReference.of("ORGANIZATION_UNIT", "ou-station", "TRC-OPS-EAST-CS-01", "Compression Station East 01");
    }

    public static PipelineSystem pipelineSystem() {
        return PipelineSystem.create(code("PS"), name("Pipeline System"), " Main system ", ProductType.GAS, operationalOwnerReference());
    }

    public static PipelineSystem retiredPipelineSystem() {
        return pipelineSystem().retire();
    }

    public static Pipeline pipeline(PipelineSystem pipelineSystem) {
        return Pipeline.create(
                pipelineSystem.id(),
                code("PIPE"),
                name("Pipeline"),
                " Main pipeline ",
                ProductType.GAS,
                DiameterInInches.of(new BigDecimal("42.000")),
                LengthInKilometers.of(new BigDecimal("512.300")));
    }

    public static Pipeline activePipeline(PipelineSystem pipelineSystem) {
        return pipeline(pipelineSystem).activate();
    }

    public static Facility facility() {
        return Facility.create(
                code("FAC"),
                name("Facility"),
                FacilityType.COMPRESSION_STATION,
                ProductType.GAS,
                GeoCoordinate.of(31.6167, 2.2167),
                organizationUnitReference());
    }

    public static TopologyNode facilityNode(Facility facility, NodeType nodeType) {
        return TopologyNode.create(
                code("NODE-FAC-" + nodeType.name()),
                name("Facility Node " + nodeType.name()),
                nodeType,
                facility.id(),
                null,
                GeoCoordinate.of(31.6168, 2.2168),
                new BigDecimal("725.300"));
    }

    public static TopologyNode freeNode(NodeType nodeType, String suffix) {
        return TopologyNode.create(
                code("NODE-" + suffix),
                name("Node " + suffix),
                nodeType,
                null,
                null,
                GeoCoordinate.of(31.5000, 2.1000),
                new BigDecimal("700.000"));
    }

    public static PipelineSegment segment(Pipeline pipeline, TopologyNode fromNode, TopologyNode toNode) {
        return PipelineSegment.create(
                pipeline.id(),
                code("SEG"),
                name("Segment"),
                fromNode.id(),
                toNode.id(),
                LengthInKilometers.of(new BigDecimal("25.000")),
                DiameterInInches.of(new BigDecimal("42.000")));
    }

    public static PipelineSegment segmentWithPipelineId(PipelineId pipelineId, TopologyNode fromNode, TopologyNode toNode) {
        return PipelineSegment.create(
                pipelineId,
                code("SEG-OTHER"),
                name("Other Segment"),
                fromNode.id(),
                toNode.id(),
                LengthInKilometers.of(new BigDecimal("25.000")),
                DiameterInInches.of(new BigDecimal("42.000")));
    }

    public static PipelineAppurtenance valve(Pipeline pipeline, TopologyNode node) {
        return PipelineAppurtenance.create(
                pipeline.id(),
                node.id(),
                code("APP-VALVE"),
                name("Valve Appurtenance"),
                PipelineAppurtenanceType.VALVE,
                ValveType.BLOCK_VALVE,
                PipelineKilometerPoint.of(new BigDecimal("25.000")),
                GeoCoordinate.of(31.7000, 2.3000),
                " Block valve ");
    }

    public static PipelineAppurtenance appurtenance(
            Pipeline pipeline,
            TopologyNode node,
            PipelineAppurtenanceType appurtenanceType,
            ValveType valveType,
            String suffix) {

        return PipelineAppurtenance.create(
                pipeline.id(),
                node.id(),
                code("APP-" + suffix),
                name("Appurtenance " + suffix),
                appurtenanceType,
                valveType,
                PipelineKilometerPoint.of(new BigDecimal("30.000")),
                GeoCoordinate.of(31.7100, 2.3100),
                " Appurtenance ");
    }

    public static TopologyConnection connection(TopologyNode fromNode, TopologyNode toNode, PipelineSegment segment) {
        return TopologyConnection.create(
                code("CONN"),
                name("Connection"),
                fromNode.id(),
                toNode.id(),
                ConnectionType.PIPELINE_SEGMENT,
                TopologyAssetType.SEGMENT,
                segment.id().value());
    }

    public static Equipment equipment(Facility facility) {
        return Equipment.create(
                code("EQP"),
                name("Equipment"),
                EquipmentType.COMPRESSOR,
                TopologyAssetType.FACILITY,
                facility.id().value());
    }

    public static TopologyNode duplicateOf(TopologyNode node) {
        return TopologyNode.restore(
                node.id(),
                code("NODE-DUP"),
                name("Duplicate Node"),
                node.nodeType(),
                node.facilityId(),
                node.pipelineAppurtenanceId(),
                node.coordinate(),
                node.elevationMeters(),
                node.status(),
                node.createdAt(),
                node.updatedAt());
    }

    public static PipelineSegment restoredSegmentWithSameNodes(Pipeline pipeline, TopologyNode node) {
        Instant createdAt = Instant.parse("2026-01-01T00:00:00Z");
        return PipelineSegment.restore(
                PipelineSegmentId.newId(),
                pipeline.id(),
                code("SEG-SAME"),
                name("Same Node Segment"),
                node.id(),
                node.id(),
                LengthInKilometers.of(new BigDecimal("10.000")),
                DiameterInInches.of(new BigDecimal("42.000")),
                TopologyStatus.PLANNED,
                createdAt,
                createdAt);
    }

    public static TopologyConnection restoredConnection(
            TopologyNode fromNode,
            TopologyNode toNode,
            ConnectionType connectionType,
            TopologyAssetType linkedAssetType) {

        Instant createdAt = Instant.parse("2026-01-01T00:00:00Z");
        return TopologyConnection.restore(
                TopologyConnectionId.newId(),
                code("CONN-RESTORED"),
                name("Restored Connection"),
                fromNode.id(),
                toNode.id(),
                connectionType,
                linkedAssetType,
                "linked-asset-id",
                TopologyStatus.PLANNED,
                createdAt,
                createdAt);
    }

    public static Facility restoredFacilityWithIdAsOrganizationUnitReference() {
        FacilityId facilityId = FacilityId.newId();
        Instant createdAt = Instant.parse("2026-01-01T00:00:00Z");

        return Facility.restore(
                facilityId,
                code("FAC-REUSED-ID"),
                name("Facility Reused Id"),
                FacilityType.COMPRESSION_STATION,
                ProductType.GAS,
                TopologyStatus.PLANNED,
                GeoCoordinate.of(31.6167, 2.2167),
                OrganizationUnitReference.of("ORGANIZATION_UNIT", facilityId.value(), "TRC-OPS-EAST-CS-01", "Compression Station East 01"),
                createdAt,
                createdAt);
    }

    public static Equipment equipmentWithEquipmentParent() {
        return Equipment.create(
                code("EQP-PARENT-EQP"),
                name("Equipment With Equipment Parent"),
                EquipmentType.METER,
                TopologyAssetType.EQUIPMENT,
                "eqp-parent");
    }

    public static Pipeline pipelineRestoredWithSystemId(PipelineSystemId pipelineSystemId) {
        Instant createdAt = Instant.parse("2026-01-01T00:00:00Z");
        return Pipeline.restore(
                PipelineId.newId(),
                pipelineSystemId,
                code("PIPE-RESTORED"),
                name("Restored Pipeline"),
                "Restored pipeline",
                ProductType.GAS,
                DiameterInInches.of(new BigDecimal("42.000")),
                LengthInKilometers.of(new BigDecimal("20.000")),
                TopologyStatus.PLANNED,
                createdAt,
                createdAt);
    }
}
