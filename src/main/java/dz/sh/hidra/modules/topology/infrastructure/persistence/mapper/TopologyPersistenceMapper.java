/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyPersistenceMapper
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.mapper
 *
 * @Description : Maps topology domain models to JPA entities.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.mapper;

import dz.sh.hidra.modules.topology.domain.model.*;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.*;
public final class TopologyPersistenceMapper {
    private TopologyPersistenceMapper() { throw new UnsupportedOperationException("Utility class must not be instantiated."); }
    public static PipelineSystemJpaEntity toEntity(PipelineSystem model) { return new PipelineSystemJpaEntity(
                model.id(),
                model.code(),
                model.nameAr(),
                model.nameFr(),
                model.nameEn(),
                model.systemType(),
                model.status(),
                model.description(),
                model.commissionedAt(),
                model.retiredAt(),
                model.createdAt(),
                model.updatedAt()
        ); }
    public static PipelineSystem toDomain(PipelineSystemJpaEntity entity) { return new PipelineSystem(
                entity.id(),
                entity.code(),
                entity.nameAr(),
                entity.nameFr(),
                entity.nameEn(),
                entity.systemType(),
                entity.status(),
                entity.description(),
                entity.commissionedAt(),
                entity.retiredAt(),
                entity.createdAt(),
                entity.updatedAt()
        ); }
    public static PipelineJpaEntity toEntity(Pipeline model) { return new PipelineJpaEntity(
                model.id(),
                model.pipelineSystemId(),
                model.code(),
                model.nameAr(),
                model.nameFr(),
                model.nameEn(),
                model.pipelineType(),
                model.nominalDiameter(),
                model.diameterUnitCode(),
                model.designPressure(),
                model.pressureUnitCode(),
                model.totalLengthKm(),
                model.status(),
                model.createdAt(),
                model.updatedAt()
        ); }
    public static Pipeline toDomain(PipelineJpaEntity entity) { return new Pipeline(
                entity.id(),
                entity.pipelineSystemId(),
                entity.code(),
                entity.nameAr(),
                entity.nameFr(),
                entity.nameEn(),
                entity.pipelineType(),
                entity.nominalDiameter(),
                entity.diameterUnitCode(),
                entity.designPressure(),
                entity.pressureUnitCode(),
                entity.totalLengthKm(),
                entity.status(),
                entity.createdAt(),
                entity.updatedAt()
        ); }
    public static PipelineSegmentJpaEntity toEntity(PipelineSegment model) { return new PipelineSegmentJpaEntity(
                model.id(),
                model.pipelineId(),
                model.code(),
                model.segmentType(),
                model.fromNodeId(),
                model.toNodeId(),
                model.startKilometerPoint(),
                model.endKilometerPoint(),
                model.lengthKm(),
                model.flowDirection(),
                model.status(),
                model.createdAt(),
                model.updatedAt()
        ); }
    public static PipelineSegment toDomain(PipelineSegmentJpaEntity entity) { return new PipelineSegment(
                entity.id(),
                entity.pipelineId(),
                entity.code(),
                entity.segmentType(),
                entity.fromNodeId(),
                entity.toNodeId(),
                entity.startKilometerPoint(),
                entity.endKilometerPoint(),
                entity.lengthKm(),
                entity.flowDirection(),
                entity.status(),
                entity.createdAt(),
                entity.updatedAt()
        ); }
    public static TopologyNodeJpaEntity toEntity(TopologyNode model) { return new TopologyNodeJpaEntity(
                model.id(),
                model.code(),
                model.name(),
                model.nodeType(),
                model.facilityId(),
                model.latitude(),
                model.longitude(),
                model.elevationMeters(),
                model.status(),
                model.createdAt(),
                model.updatedAt()
        ); }
    public static TopologyNode toDomain(TopologyNodeJpaEntity entity) { return new TopologyNode(
                entity.id(),
                entity.code(),
                entity.name(),
                entity.nodeType(),
                entity.facilityId(),
                entity.latitude(),
                entity.longitude(),
                entity.elevationMeters(),
                entity.status(),
                entity.createdAt(),
                entity.updatedAt()
        ); }
    public static TopologyConnectionJpaEntity toEntity(TopologyConnection model) { return new TopologyConnectionJpaEntity(
                model.id(),
                model.code(),
                model.fromNodeId(),
                model.toNodeId(),
                model.connectionType(),
                model.flowDirection(),
                model.pipelineSegmentId(),
                model.nominalCapacity(),
                model.capacityUnitCode(),
                model.status(),
                model.createdAt(),
                model.updatedAt()
        ); }
    public static TopologyConnection toDomain(TopologyConnectionJpaEntity entity) { return new TopologyConnection(
                entity.id(),
                entity.code(),
                entity.fromNodeId(),
                entity.toNodeId(),
                entity.connectionType(),
                entity.flowDirection(),
                entity.pipelineSegmentId(),
                entity.nominalCapacity(),
                entity.capacityUnitCode(),
                entity.status(),
                entity.createdAt(),
                entity.updatedAt()
        ); }
    public static FacilityJpaEntity toEntity(Facility model) { return new FacilityJpaEntity(
                model.id(),
                model.code(),
                model.nameAr(),
                model.nameFr(),
                model.nameEn(),
                model.facilityTypeId(),
                model.facilityKind(),
                model.ownerPartyId(),
                model.ownerPartyCodeSnapshot(),
                model.ownerPartyNameSnapshot(),
                model.latitude(),
                model.longitude(),
                model.elevationMeters(),
                model.status(),
                model.commissionedAt(),
                model.retiredAt(),
                model.createdAt(),
                model.updatedAt()
        ); }
    public static Facility toDomain(FacilityJpaEntity entity) { return new Facility(
                entity.id(),
                entity.code(),
                entity.nameAr(),
                entity.nameFr(),
                entity.nameEn(),
                entity.facilityTypeId(),
                entity.facilityKind(),
                entity.ownerPartyId(),
                entity.ownerPartyCodeSnapshot(),
                entity.ownerPartyNameSnapshot(),
                entity.latitude(),
                entity.longitude(),
                entity.elevationMeters(),
                entity.status(),
                entity.commissionedAt(),
                entity.retiredAt(),
                entity.createdAt(),
                entity.updatedAt()
        ); }
    public static FacilityTypeJpaEntity toEntity(FacilityType model) { return new FacilityTypeJpaEntity(
                model.id(),
                model.code(),
                model.name(),
                model.facilityKind(),
                model.description(),
                model.status(),
                model.createdAt(),
                model.updatedAt()
        ); }
    public static FacilityType toDomain(FacilityTypeJpaEntity entity) { return new FacilityType(
                entity.id(),
                entity.code(),
                entity.name(),
                entity.facilityKind(),
                entity.description(),
                entity.status(),
                entity.createdAt(),
                entity.updatedAt()
        ); }
    public static FacilityTypeVersionJpaEntity toEntity(FacilityTypeVersion model) { return new FacilityTypeVersionJpaEntity(
                model.id(),
                model.facilityTypeId(),
                model.versionNumber(),
                model.definitionPayload(),
                model.status(),
                model.effectiveFrom(),
                model.effectiveTo(),
                model.createdAt(),
                model.updatedAt()
        ); }
    public static FacilityTypeVersion toDomain(FacilityTypeVersionJpaEntity entity) { return new FacilityTypeVersion(
                entity.id(),
                entity.facilityTypeId(),
                entity.versionNumber(),
                entity.definitionPayload(),
                entity.status(),
                entity.effectiveFrom(),
                entity.effectiveTo(),
                entity.createdAt(),
                entity.updatedAt()
        ); }
    public static FacilityAttributeDefinitionJpaEntity toEntity(FacilityAttributeDefinition model) { return new FacilityAttributeDefinitionJpaEntity(
                model.id(),
                model.facilityTypeVersionId(),
                model.attributeCode(),
                model.label(),
                model.dataType(),
                model.unitCode(),
                model.required(),
                model.status(),
                model.createdAt(),
                model.updatedAt()
        ); }
    public static FacilityAttributeDefinition toDomain(FacilityAttributeDefinitionJpaEntity entity) { return new FacilityAttributeDefinition(
                entity.id(),
                entity.facilityTypeVersionId(),
                entity.attributeCode(),
                entity.label(),
                entity.dataType(),
                entity.unitCode(),
                entity.required(),
                entity.status(),
                entity.createdAt(),
                entity.updatedAt()
        ); }
    public static FacilityAttributeValueJpaEntity toEntity(FacilityAttributeValue model) { return new FacilityAttributeValueJpaEntity(
                model.id(),
                model.facilityId(),
                model.attributeDefinitionId(),
                model.valueText(),
                model.valueNumber(),
                model.valueJson(),
                model.validFrom(),
                model.validTo(),
                model.createdAt(),
                model.updatedAt()
        ); }
    public static FacilityAttributeValue toDomain(FacilityAttributeValueJpaEntity entity) { return new FacilityAttributeValue(
                entity.id(),
                entity.facilityId(),
                entity.attributeDefinitionId(),
                entity.valueText(),
                entity.valueNumber(),
                entity.valueJson(),
                entity.validFrom(),
                entity.validTo(),
                entity.createdAt(),
                entity.updatedAt()
        ); }
    public static PipelineSystemFacilityJpaEntity toEntity(PipelineSystemFacility model) { return new PipelineSystemFacilityJpaEntity(
                model.id(),
                model.pipelineSystemId(),
                model.facilityId(),
                model.relationshipCode(),
                model.validFrom(),
                model.validTo(),
                model.status(),
                model.createdAt(),
                model.updatedAt()
        ); }
    public static PipelineSystemFacility toDomain(PipelineSystemFacilityJpaEntity entity) { return new PipelineSystemFacility(
                entity.id(),
                entity.pipelineSystemId(),
                entity.facilityId(),
                entity.relationshipCode(),
                entity.validFrom(),
                entity.validTo(),
                entity.status(),
                entity.createdAt(),
                entity.updatedAt()
        ); }
    public static FacilityNodeBindingJpaEntity toEntity(FacilityNodeBinding model) { return new FacilityNodeBindingJpaEntity(
                model.id(),
                model.facilityId(),
                model.nodeId(),
                model.bindingRoleCode(),
                model.primaryBinding(),
                model.status(),
                model.createdAt(),
                model.updatedAt()
        ); }
    public static FacilityNodeBinding toDomain(FacilityNodeBindingJpaEntity entity) { return new FacilityNodeBinding(
                entity.id(),
                entity.facilityId(),
                entity.nodeId(),
                entity.bindingRoleCode(),
                entity.primaryBinding(),
                entity.status(),
                entity.createdAt(),
                entity.updatedAt()
        ); }
    public static EquipmentJpaEntity toEntity(Equipment model) { return new EquipmentJpaEntity(
                model.id(),
                model.code(),
                model.name(),
                model.facilityId(),
                model.nodeId(),
                model.pipelineSegmentId(),
                model.equipmentTypeId(),
                model.equipmentKind(),
                model.manufacturerPartyId(),
                model.manufacturerPartyCodeSnapshot(),
                model.manufacturerPartyNameSnapshot(),
                model.status(),
                model.installedAt(),
                model.retiredAt(),
                model.createdAt(),
                model.updatedAt()
        ); }
    public static Equipment toDomain(EquipmentJpaEntity entity) { return new Equipment(
                entity.id(),
                entity.code(),
                entity.name(),
                entity.facilityId(),
                entity.nodeId(),
                entity.pipelineSegmentId(),
                entity.equipmentTypeId(),
                entity.equipmentKind(),
                entity.manufacturerPartyId(),
                entity.manufacturerPartyCodeSnapshot(),
                entity.manufacturerPartyNameSnapshot(),
                entity.status(),
                entity.installedAt(),
                entity.retiredAt(),
                entity.createdAt(),
                entity.updatedAt()
        ); }
    public static EquipmentTypeJpaEntity toEntity(EquipmentType model) { return new EquipmentTypeJpaEntity(
                model.id(),
                model.code(),
                model.name(),
                model.equipmentKind(),
                model.description(),
                model.status(),
                model.createdAt(),
                model.updatedAt()
        ); }
    public static EquipmentType toDomain(EquipmentTypeJpaEntity entity) { return new EquipmentType(
                entity.id(),
                entity.code(),
                entity.name(),
                entity.equipmentKind(),
                entity.description(),
                entity.status(),
                entity.createdAt(),
                entity.updatedAt()
        ); }
    public static EquipmentTypeVersionJpaEntity toEntity(EquipmentTypeVersion model) { return new EquipmentTypeVersionJpaEntity(
                model.id(),
                model.equipmentTypeId(),
                model.versionNumber(),
                model.definitionPayload(),
                model.status(),
                model.effectiveFrom(),
                model.effectiveTo(),
                model.createdAt(),
                model.updatedAt()
        ); }
    public static EquipmentTypeVersion toDomain(EquipmentTypeVersionJpaEntity entity) { return new EquipmentTypeVersion(
                entity.id(),
                entity.equipmentTypeId(),
                entity.versionNumber(),
                entity.definitionPayload(),
                entity.status(),
                entity.effectiveFrom(),
                entity.effectiveTo(),
                entity.createdAt(),
                entity.updatedAt()
        ); }
    public static EquipmentAttributeDefinitionJpaEntity toEntity(EquipmentAttributeDefinition model) { return new EquipmentAttributeDefinitionJpaEntity(
                model.id(),
                model.equipmentTypeVersionId(),
                model.attributeCode(),
                model.label(),
                model.dataType(),
                model.unitCode(),
                model.required(),
                model.status(),
                model.createdAt(),
                model.updatedAt()
        ); }
    public static EquipmentAttributeDefinition toDomain(EquipmentAttributeDefinitionJpaEntity entity) { return new EquipmentAttributeDefinition(
                entity.id(),
                entity.equipmentTypeVersionId(),
                entity.attributeCode(),
                entity.label(),
                entity.dataType(),
                entity.unitCode(),
                entity.required(),
                entity.status(),
                entity.createdAt(),
                entity.updatedAt()
        ); }
    public static EquipmentAttributeValueJpaEntity toEntity(EquipmentAttributeValue model) { return new EquipmentAttributeValueJpaEntity(
                model.id(),
                model.equipmentId(),
                model.attributeDefinitionId(),
                model.valueText(),
                model.valueNumber(),
                model.valueJson(),
                model.validFrom(),
                model.validTo(),
                model.createdAt(),
                model.updatedAt()
        ); }
    public static EquipmentAttributeValue toDomain(EquipmentAttributeValueJpaEntity entity) { return new EquipmentAttributeValue(
                entity.id(),
                entity.equipmentId(),
                entity.attributeDefinitionId(),
                entity.valueText(),
                entity.valueNumber(),
                entity.valueJson(),
                entity.validFrom(),
                entity.validTo(),
                entity.createdAt(),
                entity.updatedAt()
        ); }
    public static MeasurementLocationJpaEntity toEntity(MeasurementLocation model) { return new MeasurementLocationJpaEntity(
                model.id(),
                model.code(),
                model.measurementLocationType(),
                model.pipelineId(),
                model.pipelineSegmentId(),
                model.facilityId(),
                model.nodeId(),
                model.equipmentId(),
                model.kilometerPoint(),
                model.description(),
                model.status(),
                model.createdAt(),
                model.updatedAt()
        ); }
    public static MeasurementLocation toDomain(MeasurementLocationJpaEntity entity) { return new MeasurementLocation(
                entity.id(),
                entity.code(),
                entity.measurementLocationType(),
                entity.pipelineId(),
                entity.pipelineSegmentId(),
                entity.facilityId(),
                entity.nodeId(),
                entity.equipmentId(),
                entity.kilometerPoint(),
                entity.description(),
                entity.status(),
                entity.createdAt(),
                entity.updatedAt()
        ); }
    public static TopologySnapshotJpaEntity toEntity(TopologySnapshot model) { return new TopologySnapshotJpaEntity(
                model.id(),
                model.snapshotCode(),
                model.versionNumber(),
                model.status(),
                model.snapshotPayload(),
                model.approvedByWorkflowId(),
                model.capturedAt(),
                model.approvedAt(),
                model.createdAt()
        ); }
    public static TopologySnapshot toDomain(TopologySnapshotJpaEntity entity) { return new TopologySnapshot(
                entity.id(),
                entity.snapshotCode(),
                entity.versionNumber(),
                entity.status(),
                entity.snapshotPayload(),
                entity.approvedByWorkflowId(),
                entity.capturedAt(),
                entity.approvedAt(),
                entity.createdAt()
        ); }

}
