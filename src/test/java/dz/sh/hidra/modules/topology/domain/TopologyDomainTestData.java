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
 * @Description : Deterministic test data factory for topology domain unit tests.
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
import dz.sh.hidra.modules.topology.domain.value.ConnectionTypeReference;
import dz.sh.hidra.modules.topology.domain.value.DiameterInInches;
import dz.sh.hidra.modules.topology.domain.value.EquipmentId;
import dz.sh.hidra.modules.topology.domain.value.EquipmentType;
import dz.sh.hidra.modules.topology.domain.value.EquipmentTypeReference;
import dz.sh.hidra.modules.topology.domain.value.FacilityId;
import dz.sh.hidra.modules.topology.domain.value.FacilityType;
import dz.sh.hidra.modules.topology.domain.value.FacilityTypeReference;
import dz.sh.hidra.modules.topology.domain.value.GeoCoordinate;
import dz.sh.hidra.modules.topology.domain.value.LengthInKilometers;
import dz.sh.hidra.modules.topology.domain.value.NodeType;
import dz.sh.hidra.modules.topology.domain.value.NodeTypeReference;
import dz.sh.hidra.modules.topology.domain.value.OperationalOwnerReference;
import dz.sh.hidra.modules.topology.domain.value.OrganizationUnitReference;
import dz.sh.hidra.modules.topology.domain.value.PipelineAppurtenanceId;
import dz.sh.hidra.modules.topology.domain.value.PipelineAppurtenanceType;
import dz.sh.hidra.modules.topology.domain.value.PipelineAppurtenanceTypeReference;
import dz.sh.hidra.modules.topology.domain.value.PipelineId;
import dz.sh.hidra.modules.topology.domain.value.PipelineKilometerPoint;
import dz.sh.hidra.modules.topology.domain.value.PipelineSegmentId;
import dz.sh.hidra.modules.topology.domain.value.PipelineSystemId;
import dz.sh.hidra.modules.topology.domain.value.ProductTypeReference;
import dz.sh.hidra.modules.topology.domain.value.TopologyAssetType;
import dz.sh.hidra.modules.topology.domain.value.TopologyCode;
import dz.sh.hidra.modules.topology.domain.value.TopologyConnectionId;
import dz.sh.hidra.modules.topology.domain.value.TopologyName;
import dz.sh.hidra.modules.topology.domain.value.TopologyNodeId;
import dz.sh.hidra.modules.topology.domain.value.TopologyStatus;
import dz.sh.hidra.modules.topology.domain.value.ValveType;
import dz.sh.hidra.modules.topology.domain.value.ValveTypeReference;

/**
 * Test data factory for pure topology domain tests.
 *
 * <p>Business role:
 * Provides deterministic topology objects for pipeline systems, pipelines, facilities, nodes,
 * segments, appurtenances, connections, and equipment.
 *
 * <p>Architecture role:
 * This class exists in test source only and depends only on topology domain types.
 *
 * <p>Validation:
 * Factory methods deliberately restore stable identifiers so tests do not accidentally save one
 * generated object and query another object created by a second factory call.
 */
public final class TopologyDomainTestData {

    public static final Instant CREATED_AT = Instant.parse("2026-01-01T00:00:00Z");
    public static final Instant UPDATED_AT = Instant.parse("2026-01-02T00:00:00Z");

    private TopologyDomainTestData() {
    }

    public static TopologyCode code(String suffix) {
        return TopologyCode.of("TOP-" + suffix);
    }

    public static TopologyName name(String suffix) {
        return TopologyName.of("Topology " + suffix);
    }

    public static ProductTypeReference gasProductType() {
        return ProductTypeReference.of("GAS", "GAS");
    }

    public static FacilityTypeReference compressionStationType() {
        return FacilityTypeReference.of("COMPRESSION_STATION", "COMPRESSION_STATION");
    }

    public static NodeTypeReference nodeType(NodeType nodeType) {
        return NodeTypeReference.from(nodeType);
    }

    public static PipelineAppurtenanceTypeReference appurtenanceType(PipelineAppurtenanceType appurtenanceType) {
        return PipelineAppurtenanceTypeReference.from(appurtenanceType);
    }

    public static ValveTypeReference valveType(ValveType valveType) {
        return ValveTypeReference.from(valveType);
    }

    public static ConnectionTypeReference connectionType(ConnectionType connectionType) {
        return ConnectionTypeReference.from(connectionType);
    }

    public static EquipmentTypeReference equipmentType(EquipmentType equipmentType) {
        return EquipmentTypeReference.from(equipmentType);
    }

    public static OperationalOwnerReference operationalOwnerReference() {
        return OperationalOwnerReference.of("ORGANIZATION_UNIT", "ou-east", "TRC-OPS-EAST", "Operational East Region");
    }

    public static OrganizationUnitReference organizationUnitReference() {
        return OrganizationUnitReference.of("ORGANIZATION_UNIT", "ou-station", "TRC-OPS-EAST-CS-01", "Compression Station East 01");
    }

    public static PipelineSystem pipelineSystem() {
        return PipelineSystem.restore(
                PipelineSystemId.of("ps-test-main"),
                code("PS"),
                name("Pipeline System"),
                "Main system",
                gasProductType(),
                TopologyStatus.PLANNED,
                operationalOwnerReference(),
                CREATED_AT,
                UPDATED_AT);
    }

    public static PipelineSystem retiredPipelineSystem() {
        return PipelineSystem.restore(
                PipelineSystemId.of("ps-test-retired"),
                code("PS-RETIRED"),
                name("Retired Pipeline System"),
                "Retired system",
                gasProductType(),
                TopologyStatus.RETIRED,
                operationalOwnerReference(),
                CREATED_AT,
                UPDATED_AT);
    }

    public static Pipeline pipeline(PipelineSystem pipelineSystem) {
        return Pipeline.restore(
                PipelineId.of("pipe-test-main"),
                pipelineSystem.id(),
                code("PIPE"),
                name("Pipeline"),
                "Main pipeline",
                gasProductType(),
                DiameterInInches.of(new BigDecimal("42.000")),
                LengthInKilometers.of(new BigDecimal("512.300")),
                TopologyStatus.PLANNED,
                CREATED_AT,
                UPDATED_AT);
    }

    public static Pipeline activePipeline(PipelineSystem pipelineSystem) {
        return Pipeline.restore(
                PipelineId.of("pipe-test-active"),
                pipelineSystem.id(),
                code("PIPE-ACTIVE"),
                name("Active Pipeline"),
                "Active pipeline",
                gasProductType(),
                DiameterInInches.of(new BigDecimal("42.000")),
                LengthInKilometers.of(new BigDecimal("512.300")),
                TopologyStatus.ACTIVE,
                CREATED_AT,
                UPDATED_AT);
    }

    public static Facility facility() {
        return Facility.restore(
                FacilityId.of("fac-test-main"),
                code("FAC"),
                name("Facility"),
                compressionStationType(),
                gasProductType(),
                TopologyStatus.PLANNED,
                GeoCoordinate.of(31.6167, 2.2167),
                organizationUnitReference(),
                CREATED_AT,
                UPDATED_AT);
    }

    public static TopologyNode facilityNode(Facility facility, NodeType nodeType) {
        return TopologyNode.restore(
                TopologyNodeId.of("node-test-fac-" + nodeType.name().toLowerCase()),
                code("NODE-FAC-" + nodeType.name()),
                name("Facility Node " + nodeType.name()),
                nodeType(nodeType),
                facility.id(),
                null,
                GeoCoordinate.of(31.6168, 2.2168),
                new BigDecimal("725.300"),
                TopologyStatus.PLANNED,
                CREATED_AT,
                UPDATED_AT);
    }

    public static TopologyNode freeNode(NodeType nodeType, String suffix) {
        return TopologyNode.restore(
                TopologyNodeId.of("node-test-" + suffix.toLowerCase().replace('_', '-')),
                code("NODE-" + suffix),
                name("Node " + suffix),
                nodeType(nodeType),
                null,
                null,
                GeoCoordinate.of(31.5000, 2.1000),
                new BigDecimal("700.000"),
                TopologyStatus.PLANNED,
                CREATED_AT,
                UPDATED_AT);
    }

    public static PipelineSegment segment(Pipeline pipeline, TopologyNode fromNode, TopologyNode toNode) {
        return PipelineSegment.restore(
                PipelineSegmentId.of("seg-test-main"),
                pipeline.id(),
                code("SEG"),
                name("Segment"),
                fromNode.id(),
                toNode.id(),
                LengthInKilometers.of(new BigDecimal("25.000")),
                DiameterInInches.of(new BigDecimal("42.000")),
                TopologyStatus.PLANNED,
                CREATED_AT,
                UPDATED_AT);
    }

    public static PipelineSegment segmentWithPipelineId(PipelineId pipelineId, TopologyNode fromNode, TopologyNode toNode) {
        return PipelineSegment.restore(
                PipelineSegmentId.of("seg-test-other"),
                pipelineId,
                code("SEG-OTHER"),
                name("Other Segment"),
                fromNode.id(),
                toNode.id(),
                LengthInKilometers.of(new BigDecimal("25.000")),
                DiameterInInches.of(new BigDecimal("42.000")),
                TopologyStatus.PLANNED,
                CREATED_AT,
                UPDATED_AT);
    }

    public static PipelineAppurtenance valve(Pipeline pipeline, TopologyNode node) {
        return appurtenance(pipeline, node, PipelineAppurtenanceType.VALVE, ValveType.BLOCK_VALVE, "VALVE");
    }

    public static PipelineAppurtenance appurtenance(
            Pipeline pipeline,
            TopologyNode node,
            PipelineAppurtenanceType appurtenanceType,
            ValveType valveType,
            String suffix) {

        return PipelineAppurtenance.restore(
                PipelineAppurtenanceId.of("app-test-" + suffix.toLowerCase().replace('_', '-')),
                pipeline.id(),
                node.id(),
                code("APP-" + suffix),
                name("Appurtenance " + suffix),
                appurtenanceType(appurtenanceType),
                valveType(valveType),
                PipelineKilometerPoint.of(new BigDecimal("30.000")),
                TopologyStatus.PLANNED,
                GeoCoordinate.of(31.7100, 2.3100),
                "Appurtenance",
                CREATED_AT,
                UPDATED_AT);
    }

    public static TopologyConnection connection(TopologyNode fromNode, TopologyNode toNode, PipelineSegment segment) {
        return TopologyConnection.restore(
                TopologyConnectionId.of("conn-test-main"),
                code("CONN"),
                name("Connection"),
                fromNode.id(),
                toNode.id(),
                connectionType(ConnectionType.PIPELINE_SEGMENT),
                TopologyAssetType.SEGMENT,
                segment.id().value(),
                TopologyStatus.PLANNED,
                CREATED_AT,
                UPDATED_AT);
    }

    public static Equipment equipment(Facility facility) {
        return Equipment.restore(
                EquipmentId.of("eqp-test-main"),
                code("EQP"),
                name("Equipment"),
                equipmentType(EquipmentType.COMPRESSOR),
                TopologyAssetType.FACILITY,
                facility.id().value(),
                TopologyStatus.PLANNED,
                CREATED_AT,
                UPDATED_AT);
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
        return PipelineSegment.restore(
                PipelineSegmentId.of("seg-test-same-node"),
                pipeline.id(),
                code("SEG-SAME"),
                name("Same Node Segment"),
                node.id(),
                node.id(),
                LengthInKilometers.of(new BigDecimal("10.000")),
                DiameterInInches.of(new BigDecimal("42.000")),
                TopologyStatus.PLANNED,
                CREATED_AT,
                UPDATED_AT);
    }

    public static TopologyConnection restoredConnection(
            TopologyNode fromNode,
            TopologyNode toNode,
            ConnectionType connectionType,
            TopologyAssetType linkedAssetType) {

        return TopologyConnection.restore(
                TopologyConnectionId.of("conn-test-restored"),
                code("CONN-RESTORED"),
                name("Restored Connection"),
                fromNode.id(),
                toNode.id(),
                connectionType(connectionType),
                linkedAssetType,
                "linked-asset-id",
                TopologyStatus.PLANNED,
                CREATED_AT,
                UPDATED_AT);
    }

    public static Facility restoredFacilityWithIdAsOrganizationUnitReference() {
        FacilityId facilityId = FacilityId.of("fac-test-reused-id");

        return Facility.restore(
                facilityId,
                code("FAC-REUSED-ID"),
                name("Facility Reused Id"),
                compressionStationType(),
                gasProductType(),
                TopologyStatus.PLANNED,
                GeoCoordinate.of(31.6167, 2.2167),
                OrganizationUnitReference.of("ORGANIZATION_UNIT", facilityId.value(), "TRC-OPS-EAST-CS-01", "Compression Station East 01"),
                CREATED_AT,
                UPDATED_AT);
    }

    public static Equipment equipmentWithEquipmentParent() {
        return Equipment.restore(
                EquipmentId.of("eqp-test-parent-eqp"),
                code("EQP-PARENT-EQP"),
                name("Equipment With Equipment Parent"),
                equipmentType(EquipmentType.METER),
                TopologyAssetType.EQUIPMENT,
                "eqp-parent",
                TopologyStatus.PLANNED,
                CREATED_AT,
                UPDATED_AT);
    }

    public static Pipeline pipelineRestoredWithSystemId(PipelineSystemId pipelineSystemId) {
        return Pipeline.restore(
                PipelineId.of("pipe-test-restored"),
                pipelineSystemId,
                code("PIPE-RESTORED"),
                name("Restored Pipeline"),
                "Restored pipeline",
                gasProductType(),
                DiameterInInches.of(new BigDecimal("42.000")),
                LengthInKilometers.of(new BigDecimal("20.000")),
                TopologyStatus.PLANNED,
                CREATED_AT,
                UPDATED_AT);
    }
}
