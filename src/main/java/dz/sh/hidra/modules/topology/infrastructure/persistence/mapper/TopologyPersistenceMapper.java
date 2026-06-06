/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyPersistenceMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-06
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.mapper
 *
 * @Description : Maps topology domain objects to persistence entities and back.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.mapper;

import java.math.BigDecimal;
import java.util.Objects;

import dz.sh.hidra.modules.topology.domain.model.Equipment;
import dz.sh.hidra.modules.topology.domain.model.Facility;
import dz.sh.hidra.modules.topology.domain.model.Pipeline;
import dz.sh.hidra.modules.topology.domain.model.PipelineAppurtenance;
import dz.sh.hidra.modules.topology.domain.model.PipelineSegment;
import dz.sh.hidra.modules.topology.domain.model.PipelineSystem;
import dz.sh.hidra.modules.topology.domain.model.TopologyConnection;
import dz.sh.hidra.modules.topology.domain.model.TopologyNode;
import dz.sh.hidra.modules.topology.domain.value.ConnectionTypeReference;
import dz.sh.hidra.modules.topology.domain.value.DiameterInInches;
import dz.sh.hidra.modules.topology.domain.value.EquipmentId;
import dz.sh.hidra.modules.topology.domain.value.EquipmentTypeReference;
import dz.sh.hidra.modules.topology.domain.value.FacilityId;
import dz.sh.hidra.modules.topology.domain.value.FacilityTypeReference;
import dz.sh.hidra.modules.topology.domain.value.GeoCoordinate;
import dz.sh.hidra.modules.topology.domain.value.LengthInKilometers;
import dz.sh.hidra.modules.topology.domain.value.NodeTypeReference;
import dz.sh.hidra.modules.topology.domain.value.OperationalOwnerReference;
import dz.sh.hidra.modules.topology.domain.value.OrganizationUnitReference;
import dz.sh.hidra.modules.topology.domain.value.PipelineAppurtenanceId;
import dz.sh.hidra.modules.topology.domain.value.PipelineAppurtenanceTypeReference;
import dz.sh.hidra.modules.topology.domain.value.PipelineId;
import dz.sh.hidra.modules.topology.domain.value.PipelineKilometerPoint;
import dz.sh.hidra.modules.topology.domain.value.PipelineSegmentId;
import dz.sh.hidra.modules.topology.domain.value.PipelineSystemId;
import dz.sh.hidra.modules.topology.domain.value.ProductTypeReference;
import dz.sh.hidra.modules.topology.domain.value.TopologyAssetType;
import dz.sh.hidra.modules.topology.domain.value.TopologyCode;
import dz.sh.hidra.modules.topology.domain.value.TopologyConnectionId;
import dz.sh.hidra.modules.topology.domain.value.TopologyMultilingualDescription;
import dz.sh.hidra.modules.topology.domain.value.TopologyMultilingualName;
import dz.sh.hidra.modules.topology.domain.value.TopologyName;
import dz.sh.hidra.modules.topology.domain.value.TopologyNodeId;
import dz.sh.hidra.modules.topology.domain.value.TopologyStatus;
import dz.sh.hidra.modules.topology.domain.value.ValveTypeReference;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.EquipmentJpaEntity;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.FacilityJpaEntity;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.PipelineAppurtenanceJpaEntity;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.PipelineJpaEntity;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.PipelineSegmentJpaEntity;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.PipelineSystemJpaEntity;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.TopologyConnectionJpaEntity;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.TopologyNodeJpaEntity;

/**
 * Maps topology domain objects and persistence entities.
 */
public final class TopologyPersistenceMapper {

    public PipelineSystemJpaEntity toEntity(PipelineSystem pipelineSystem) {
        Objects.requireNonNull(pipelineSystem, "Pipeline system must not be null.");
        PipelineSystemJpaEntity entity = new PipelineSystemJpaEntity();
        OperationalOwnerReference owner = pipelineSystem.operationalOwnerReference();
        ProductTypeReference productType = pipelineSystem.productType();
        entity.setId(pipelineSystem.id().value());
        entity.setCode(pipelineSystem.code().value());
        entity.setName(pipelineSystem.name().value());
        entity.setDescription(pipelineSystem.description());
        entity.setProductType(productType.name());
        entity.setProductTypeId(productType.id());
        entity.setStatus(pipelineSystem.status().name());
        entity.setOperationalOwnerReferenceType(owner == null ? null : owner.ownerType());
        entity.setOperationalOwnerReferenceId(owner == null ? null : owner.ownerId());
        entity.setOperationalOwnerReferenceCode(owner == null ? null : owner.ownerCode());
        entity.setOperationalOwnerReferenceName(owner == null ? null : owner.ownerName());
        entity.setCreatedAt(pipelineSystem.createdAt());
        entity.setUpdatedAt(pipelineSystem.updatedAt());
        return entity;
    }

    public PipelineSystem toDomain(PipelineSystemJpaEntity entity) {
        Objects.requireNonNull(entity, "Pipeline system entity must not be null.");
        return PipelineSystem.restore(
                PipelineSystemId.of(entity.getId()),
                TopologyCode.of(entity.getCode()),
                TopologyName.of(entity.getName()),
                entity.getDescription(),
                ProductTypeReference.of(idOrCode(entity.getProductTypeId(), entity.getProductType()), entity.getProductType()),
                TopologyStatus.valueOf(entity.getStatus()),
                toOperationalOwnerReference(entity.getOperationalOwnerReferenceType(), entity.getOperationalOwnerReferenceId(), entity.getOperationalOwnerReferenceCode(), entity.getOperationalOwnerReferenceName()),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    public PipelineJpaEntity toEntity(Pipeline pipeline) {
        Objects.requireNonNull(pipeline, "Pipeline must not be null.");
        PipelineJpaEntity entity = new PipelineJpaEntity();
        ProductTypeReference productType = pipeline.productType();
        TopologyMultilingualDescription description = pipeline.description();
        entity.setId(pipeline.id().value());
        entity.setPipelineSystemId(pipeline.pipelineSystemId().value());
        entity.setCode(pipeline.code().value());
        entity.setNameAr(pipeline.name().nameAr());
        entity.setNameFr(pipeline.name().nameFr());
        entity.setNameEn(pipeline.name().nameEn());
        entity.setDescriptionAr(description == null ? null : description.descriptionAr());
        entity.setDescriptionFr(description == null ? null : description.descriptionFr());
        entity.setDescriptionEn(description == null ? null : description.descriptionEn());
        entity.setProductType(productType.name());
        entity.setProductTypeId(productType.id());
        entity.setNominalDiameterInches(pipeline.nominalDiameter().value());
        entity.setDesignLengthKm(pipeline.designLength().value());
        entity.setStatus(pipeline.status().name());
        entity.setCreatedAt(pipeline.createdAt());
        entity.setUpdatedAt(pipeline.updatedAt());
        return entity;
    }

    public Pipeline toDomain(PipelineJpaEntity entity) {
        Objects.requireNonNull(entity, "Pipeline entity must not be null.");
        return Pipeline.restore(
                PipelineId.of(entity.getId()),
                PipelineSystemId.of(entity.getPipelineSystemId()),
                TopologyCode.of(entity.getCode()),
                TopologyMultilingualName.of(entity.getNameAr(), entity.getNameFr(), entity.getNameEn()),
                TopologyMultilingualDescription.of(entity.getDescriptionAr(), entity.getDescriptionFr(), entity.getDescriptionEn()),
                ProductTypeReference.of(idOrCode(entity.getProductTypeId(), entity.getProductType()), entity.getProductType()),
                DiameterInInches.of(entity.getNominalDiameterInches()),
                LengthInKilometers.of(entity.getDesignLengthKm()),
                TopologyStatus.valueOf(entity.getStatus()),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    public FacilityJpaEntity toEntity(Facility facility) {
        Objects.requireNonNull(facility, "Facility must not be null.");
        FacilityJpaEntity entity = new FacilityJpaEntity();
        OrganizationUnitReference reference = facility.organizationUnitReference();
        GeoCoordinate coordinate = facility.coordinate();
        FacilityTypeReference facilityType = facility.facilityType();
        ProductTypeReference productType = facility.productType();
        entity.setId(facility.id().value());
        entity.setCode(facility.code().value());
        entity.setName(facility.name().value());
        entity.setFacilityType(facilityType.name());
        entity.setFacilityTypeId(facilityType.id());
        entity.setProductType(productType.name());
        entity.setProductTypeId(productType.id());
        entity.setStatus(facility.status().name());
        entity.setLatitude(toLatitude(coordinate));
        entity.setLongitude(toLongitude(coordinate));
        entity.setOrganizationUnitReferenceType(reference == null ? null : reference.referenceType());
        entity.setOrganizationUnitReferenceId(reference == null ? null : reference.referenceId());
        entity.setOrganizationUnitReferenceCode(reference == null ? null : reference.referenceCode());
        entity.setOrganizationUnitReferenceName(reference == null ? null : reference.referenceName());
        entity.setCreatedAt(facility.createdAt());
        entity.setUpdatedAt(facility.updatedAt());
        return entity;
    }

    public Facility toDomain(FacilityJpaEntity entity) {
        Objects.requireNonNull(entity, "Facility entity must not be null.");
        return Facility.restore(FacilityId.of(entity.getId()), TopologyCode.of(entity.getCode()), TopologyName.of(entity.getName()), FacilityTypeReference.of(idOrCode(entity.getFacilityTypeId(), entity.getFacilityType()), entity.getFacilityType()), ProductTypeReference.of(idOrCode(entity.getProductTypeId(), entity.getProductType()), entity.getProductType()), TopologyStatus.valueOf(entity.getStatus()), toGeoCoordinate(entity.getLatitude(), entity.getLongitude()), toOrganizationUnitReference(entity.getOrganizationUnitReferenceType(), entity.getOrganizationUnitReferenceId(), entity.getOrganizationUnitReferenceCode(), entity.getOrganizationUnitReferenceName()), entity.getCreatedAt(), entity.getUpdatedAt());
    }

    public TopologyNodeJpaEntity toEntity(TopologyNode node) {
        Objects.requireNonNull(node, "Topology node must not be null.");
        TopologyNodeJpaEntity entity = new TopologyNodeJpaEntity();
        GeoCoordinate coordinate = node.coordinate();
        NodeTypeReference nodeType = node.nodeType();
        entity.setId(node.id().value());
        entity.setCode(node.code().value());
        entity.setName(node.name().value());
        entity.setNodeType(nodeType.name());
        entity.setNodeTypeId(nodeType.id());
        entity.setFacilityId(node.facilityId() == null ? null : node.facilityId().value());
        entity.setPipelineAppurtenanceId(node.pipelineAppurtenanceId() == null ? null : node.pipelineAppurtenanceId().value());
        entity.setLatitude(toLatitude(coordinate));
        entity.setLongitude(toLongitude(coordinate));
        entity.setElevationMeters(node.elevationMeters());
        entity.setStatus(node.status().name());
        entity.setCreatedAt(node.createdAt());
        entity.setUpdatedAt(node.updatedAt());
        return entity;
    }

    public TopologyNode toDomain(TopologyNodeJpaEntity entity) {
        Objects.requireNonNull(entity, "Topology node entity must not be null.");
        return TopologyNode.restore(TopologyNodeId.of(entity.getId()), TopologyCode.of(entity.getCode()), TopologyName.of(entity.getName()), NodeTypeReference.of(idOrCode(entity.getNodeTypeId(), entity.getNodeType()), entity.getNodeType()), entity.getFacilityId() == null ? null : FacilityId.of(entity.getFacilityId()), entity.getPipelineAppurtenanceId() == null ? null : PipelineAppurtenanceId.of(entity.getPipelineAppurtenanceId()), toGeoCoordinate(entity.getLatitude(), entity.getLongitude()), entity.getElevationMeters(), TopologyStatus.valueOf(entity.getStatus()), entity.getCreatedAt(), entity.getUpdatedAt());
    }

    public PipelineSegmentJpaEntity toEntity(PipelineSegment segment) {
        Objects.requireNonNull(segment, "Pipeline segment must not be null.");
        PipelineSegmentJpaEntity entity = new PipelineSegmentJpaEntity();
        entity.setId(segment.id().value()); entity.setPipelineId(segment.pipelineId().value()); entity.setCode(segment.code().value()); entity.setName(segment.name().value()); entity.setFromNodeId(segment.fromNodeId().value()); entity.setToNodeId(segment.toNodeId().value()); entity.setLengthKm(segment.length().value()); entity.setDiameterInches(segment.diameter().value()); entity.setStatus(segment.status().name()); entity.setCreatedAt(segment.createdAt()); entity.setUpdatedAt(segment.updatedAt());
        return entity;
    }

    public PipelineSegment toDomain(PipelineSegmentJpaEntity entity) {
        Objects.requireNonNull(entity, "Pipeline segment entity must not be null.");
        return PipelineSegment.restore(PipelineSegmentId.of(entity.getId()), PipelineId.of(entity.getPipelineId()), TopologyCode.of(entity.getCode()), TopologyName.of(entity.getName()), TopologyNodeId.of(entity.getFromNodeId()), TopologyNodeId.of(entity.getToNodeId()), LengthInKilometers.of(entity.getLengthKm()), DiameterInInches.of(entity.getDiameterInches()), TopologyStatus.valueOf(entity.getStatus()), entity.getCreatedAt(), entity.getUpdatedAt());
    }

    public PipelineAppurtenanceJpaEntity toEntity(PipelineAppurtenance appurtenance) {
        Objects.requireNonNull(appurtenance, "Pipeline appurtenance must not be null.");
        PipelineAppurtenanceJpaEntity entity = new PipelineAppurtenanceJpaEntity();
        GeoCoordinate coordinate = appurtenance.coordinate(); PipelineAppurtenanceTypeReference appurtenanceType = appurtenance.appurtenanceType(); ValveTypeReference valveType = appurtenance.valveType();
        entity.setId(appurtenance.id().value()); entity.setPipelineId(appurtenance.pipelineId().value()); entity.setNodeId(appurtenance.nodeId().value()); entity.setCode(appurtenance.code().value()); entity.setName(appurtenance.name().value()); entity.setAppurtenanceType(appurtenanceType.name()); entity.setAppurtenanceTypeId(appurtenanceType.id()); entity.setValveType(valveType == null ? null : valveType.name()); entity.setValveTypeId(valveType == null ? null : valveType.id()); entity.setPipelineKilometerPoint(appurtenance.pipelineKilometerPoint().value()); entity.setStatus(appurtenance.status().name()); entity.setLatitude(toLatitude(coordinate)); entity.setLongitude(toLongitude(coordinate)); entity.setDescription(appurtenance.description()); entity.setCreatedAt(appurtenance.createdAt()); entity.setUpdatedAt(appurtenance.updatedAt());
        return entity;
    }

    public PipelineAppurtenance toDomain(PipelineAppurtenanceJpaEntity entity) {
        Objects.requireNonNull(entity, "Pipeline appurtenance entity must not be null.");
        return PipelineAppurtenance.restore(PipelineAppurtenanceId.of(entity.getId()), PipelineId.of(entity.getPipelineId()), TopologyNodeId.of(entity.getNodeId()), TopologyCode.of(entity.getCode()), TopologyName.of(entity.getName()), PipelineAppurtenanceTypeReference.of(idOrCode(entity.getAppurtenanceTypeId(), entity.getAppurtenanceType()), entity.getAppurtenanceType()), entity.getValveType() == null ? null : ValveTypeReference.of(idOrCode(entity.getValveTypeId(), entity.getValveType()), entity.getValveType()), PipelineKilometerPoint.of(entity.getPipelineKilometerPoint()), TopologyStatus.valueOf(entity.getStatus()), toGeoCoordinate(entity.getLatitude(), entity.getLongitude()), entity.getDescription(), entity.getCreatedAt(), entity.getUpdatedAt());
    }

    public TopologyConnectionJpaEntity toEntity(TopologyConnection connection) {
        Objects.requireNonNull(connection, "Topology connection must not be null.");
        TopologyConnectionJpaEntity entity = new TopologyConnectionJpaEntity(); ConnectionTypeReference connectionType = connection.connectionType();
        entity.setId(connection.id().value()); entity.setCode(connection.code().value()); entity.setName(connection.name().value()); entity.setFromNodeId(connection.fromNodeId().value()); entity.setToNodeId(connection.toNodeId().value()); entity.setConnectionType(connectionType.name()); entity.setConnectionTypeId(connectionType.id()); entity.setLinkedAssetType(connection.linkedAssetType().name()); entity.setLinkedAssetId(connection.linkedAssetId()); entity.setStatus(connection.status().name()); entity.setCreatedAt(connection.createdAt()); entity.setUpdatedAt(connection.updatedAt());
        return entity;
    }

    public TopologyConnection toDomain(TopologyConnectionJpaEntity entity) {
        Objects.requireNonNull(entity, "Topology connection entity must not be null.");
        return TopologyConnection.restore(TopologyConnectionId.of(entity.getId()), TopologyCode.of(entity.getCode()), TopologyName.of(entity.getName()), TopologyNodeId.of(entity.getFromNodeId()), TopologyNodeId.of(entity.getToNodeId()), ConnectionTypeReference.of(idOrCode(entity.getConnectionTypeId(), entity.getConnectionType()), entity.getConnectionType()), TopologyAssetType.valueOf(entity.getLinkedAssetType()), entity.getLinkedAssetId(), TopologyStatus.valueOf(entity.getStatus()), entity.getCreatedAt(), entity.getUpdatedAt());
    }

    public EquipmentJpaEntity toEntity(Equipment equipment) {
        Objects.requireNonNull(equipment, "Equipment must not be null.");
        EquipmentJpaEntity entity = new EquipmentJpaEntity(); EquipmentTypeReference equipmentType = equipment.equipmentType();
        entity.setId(equipment.id().value()); entity.setCode(equipment.code().value()); entity.setName(equipment.name().value()); entity.setEquipmentType(equipmentType.name()); entity.setEquipmentTypeId(equipmentType.id()); entity.setParentAssetType(equipment.parentAssetType().name()); entity.setParentAssetId(equipment.parentAssetId()); entity.setStatus(equipment.status().name()); entity.setCreatedAt(equipment.createdAt()); entity.setUpdatedAt(equipment.updatedAt());
        return entity;
    }

    public Equipment toDomain(EquipmentJpaEntity entity) {
        Objects.requireNonNull(entity, "Equipment entity must not be null.");
        return Equipment.restore(EquipmentId.of(entity.getId()), TopologyCode.of(entity.getCode()), TopologyName.of(entity.getName()), EquipmentTypeReference.of(idOrCode(entity.getEquipmentTypeId(), entity.getEquipmentType()), entity.getEquipmentType()), TopologyAssetType.valueOf(entity.getParentAssetType()), entity.getParentAssetId(), TopologyStatus.valueOf(entity.getStatus()), entity.getCreatedAt(), entity.getUpdatedAt());
    }

    private static String idOrCode(String id, String code) { return id == null || id.isBlank() ? code : id; }
    private static BigDecimal toLatitude(GeoCoordinate coordinate) { return coordinate == null ? null : BigDecimal.valueOf(coordinate.latitude()).stripTrailingZeros(); }
    private static BigDecimal toLongitude(GeoCoordinate coordinate) { return coordinate == null ? null : BigDecimal.valueOf(coordinate.longitude()).stripTrailingZeros(); }
    private static GeoCoordinate toGeoCoordinate(BigDecimal latitude, BigDecimal longitude) { return latitude == null || longitude == null ? null : GeoCoordinate.of(latitude.doubleValue(), longitude.doubleValue()); }

    private static OperationalOwnerReference toOperationalOwnerReference(String type, String id, String code, String name) {
        if (type == null || id == null || code == null || name == null) { return null; }
        return OperationalOwnerReference.of(type, id, code, name);
    }

    private static OrganizationUnitReference toOrganizationUnitReference(String type, String id, String code, String name) {
        if (type == null || id == null || code == null || name == null) { return null; }
        return OrganizationUnitReference.of(type, id, code, name);
    }
}
