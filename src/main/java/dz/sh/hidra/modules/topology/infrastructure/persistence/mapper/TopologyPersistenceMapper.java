/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyPersistenceMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.mapper
 *
 * @Description : Maps topology domain models to JPA entities.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.mapper;

import dz.sh.hidra.modules.topology.domain.model.*;
import dz.sh.hidra.modules.topology.domain.value.ConnectionTypeReference;
import dz.sh.hidra.modules.topology.domain.value.PipelineSystemType;
import dz.sh.hidra.modules.topology.domain.value.PipelineType;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.*;

public final class TopologyPersistenceMapper {
    private TopologyPersistenceMapper() { throw new UnsupportedOperationException("Utility class must not be instantiated."); }

    public static PipelineSystemJpaEntity toEntity(
            PipelineSystem model,
            PipelineSystemTypeJpaEntity systemType
    ) {
        return new PipelineSystemJpaEntity(
                model.id(),
                model.code(),
                model.nameAr(),
                model.nameFr(),
                model.nameEn(),
                systemType,
                model.status(),
                model.description(),
                model.commissionedAt(),
                model.retiredAt(),
                model.createdAt(),
                model.updatedAt()
        );
    }

    public static PipelineSystem toDomain(PipelineSystemJpaEntity entity) { return new PipelineSystem(
                entity.id(),
                entity.code(),
                entity.nameAr(),
                entity.nameFr(),
                entity.nameEn(),
                toDomain(entity.systemType()),
                entity.status(),
                entity.description(),
                entity.commissionedAt(),
                entity.retiredAt(),
                entity.createdAt(),
                entity.updatedAt()
        ); }

    public static PipelineSystemType toDomain(PipelineSystemTypeJpaEntity entity) {
        return new PipelineSystemType(
                entity.id(),
                entity.code(),
                entity.nameAr(),
                entity.nameFr(),
                entity.nameEn()
        );
    }

    public static PipelineJpaEntity toEntity(
            Pipeline model,
            PipelineTypeJpaEntity pipelineType
    ) { return new PipelineJpaEntity(
                model.id(),
                model.pipelineSystemId(),
                model.code(),
                model.nameAr(),
                model.nameFr(),
                model.nameEn(),
                pipelineType,
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
                toDomain(entity.pipelineType()),
                entity.nominalDiameter(),
                entity.diameterUnitCode(),
                entity.designPressure(),
                entity.pressureUnitCode(),
                entity.totalLengthKm(),
                entity.status(),
                entity.createdAt(),
                entity.updatedAt()
        ); }
    public static PipelineType toDomain(PipelineTypeJpaEntity entity) {
        return new PipelineType(
                entity.id(),
                entity.code(),
                entity.nameAr(),
                entity.nameFr(),
                entity.nameEn()
        );
    }

    public static TopologyConnectionJpaEntity toEntity(
            TopologyConnection model,
            ConnectionTypeJpaEntity connectionType
    ) {
        return new TopologyConnectionJpaEntity(
                model.id(),
                model.code(),
                model.fromNodeId(),
                model.toNodeId(),
                connectionType,
                model.flowDirection(),
                model.pipelineSegmentId(),
                model.nominalCapacity(),
                model.capacityUnitCode(),
                model.status(),
                model.createdAt(),
                model.updatedAt()
        );
    }

    public static TopologyConnection toDomain(TopologyConnectionJpaEntity entity) {
        return new TopologyConnection(
                entity.id(),
                entity.code(),
                entity.fromNodeId(),
                entity.toNodeId(),
                toDomain(entity.connectionType()),
                entity.flowDirection(),
                entity.pipelineSegmentId(),
                entity.nominalCapacity(),
                entity.capacityUnitCode(),
                entity.status(),
                entity.createdAt(),
                entity.updatedAt()
        );
    }

    public static ConnectionTypeReference toDomain(ConnectionTypeJpaEntity entity) {
        return new ConnectionTypeReference(
                entity.id(),
                entity.code(),
                entity.nameAr(),
                entity.nameFr(),
                entity.nameEn()
        );
    }
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
    public static EquipmentJpaEntity toEntity(Equipment model) { return new EquipmentJpaEntity(
                model.id(),
                model.code(),
                model.name(),
                model.facilityId(),
                model.nodeId(),
                model.pipelineSegmentId(),
                model.equipmentTypeId(),
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
                entity.manufacturerPartyId(),
                entity.manufacturerPartyCodeSnapshot(),
                entity.manufacturerPartyNameSnapshot(),
                entity.status(),
                entity.installedAt(),
                entity.retiredAt(),
                entity.createdAt(),
                entity.updatedAt()
        ); }
}
