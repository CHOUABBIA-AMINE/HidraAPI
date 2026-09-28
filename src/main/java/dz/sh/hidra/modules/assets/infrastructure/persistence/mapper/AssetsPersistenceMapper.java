/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetsPersistenceMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.infrastructure.persistence.mapper
 *
 * @Description : Maps assets domain models to JPA entities.
 *
 */
package dz.sh.hidra.modules.assets.infrastructure.persistence.mapper;

import dz.sh.hidra.modules.assets.domain.model.*;
import dz.sh.hidra.modules.assets.infrastructure.persistence.entity.*;

/**
 * Maps assets domain models to JPA entities.
 */
public final class AssetsPersistenceMapper {

    private AssetsPersistenceMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }


        public static MaintainableAssetJpaEntity toEntity(MaintainableAsset model) {
            return new MaintainableAssetJpaEntity(
                        model.id(),
                        model.assetNumber(),
                        model.assetCode(),
                        model.assetName(),
                        model.assetTypeId(),
                        model.topologyAssetTypeCode(),
                        model.topologyAssetId(),
                        model.topologyAssetCodeSnapshot(),
                        model.topologyAssetNameSnapshot(),
                        model.parentAssetId(),
                        model.status(),
                        model.criticalityId(),
                        model.ownerOrganizationUnitId(),
                        model.ownerOrganizationUnitNameSnapshot(),
                        model.manufacturerPartyId(),
                        model.manufacturerNameSnapshot(),
                        model.modelId(),
                        model.serialIdentityId(),
                        model.registeredAt(),
                        model.installedAt(),
                        model.commissionedAt(),
                        model.retiredAt(),
                        model.createdByActorId(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static MaintainableAsset toDomain(MaintainableAssetJpaEntity entity) {
            return new MaintainableAsset(
                        entity.id(),
                        entity.assetNumber(),
                        entity.assetCode(),
                        entity.assetName(),
                        entity.assetTypeId(),
                        entity.topologyAssetTypeCode(),
                        entity.topologyAssetId(),
                        entity.topologyAssetCodeSnapshot(),
                        entity.topologyAssetNameSnapshot(),
                        entity.parentAssetId(),
                        entity.status(),
                        entity.criticalityId(),
                        entity.ownerOrganizationUnitId(),
                        entity.ownerOrganizationUnitNameSnapshot(),
                        entity.manufacturerPartyId(),
                        entity.manufacturerNameSnapshot(),
                        entity.modelId(),
                        entity.serialIdentityId(),
                        entity.registeredAt(),
                        entity.installedAt(),
                        entity.commissionedAt(),
                        entity.retiredAt(),
                        entity.createdByActorId(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }
        public static MaintenanceWorkOrderJpaEntity toEntity(MaintenanceWorkOrder model) {
            return new MaintenanceWorkOrderJpaEntity(
                        model.id(),
                        model.workOrderNumber(),
                        model.maintainableAssetId(),
                        model.maintenancePlanId(),
                        model.sourceRecommendationId(),
                        model.workOrderTypeId(),
                        model.priorityId(),
                        model.status(),
                        model.title(),
                        model.description(),
                        model.assignedOrganizationUnitId(),
                        model.assignedActorId(),
                        model.plannedStartAt(),
                        model.plannedEndAt(),
                        model.startedAt(),
                        model.completedAt(),
                        model.workflowInstanceId(),
                        model.createdByActorId(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static MaintenanceWorkOrder toDomain(MaintenanceWorkOrderJpaEntity entity) {
            return new MaintenanceWorkOrder(
                        entity.id(),
                        entity.workOrderNumber(),
                        entity.maintainableAssetId(),
                        entity.maintenancePlanId(),
                        entity.sourceRecommendationId(),
                        entity.workOrderTypeId(),
                        entity.priorityId(),
                        entity.status(),
                        entity.title(),
                        entity.description(),
                        entity.assignedOrganizationUnitId(),
                        entity.assignedActorId(),
                        entity.plannedStartAt(),
                        entity.plannedEndAt(),
                        entity.startedAt(),
                        entity.completedAt(),
                        entity.workflowInstanceId(),
                        entity.createdByActorId(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }
        public static AssetConditionRecordJpaEntity toEntity(AssetConditionRecord model) {
            return new AssetConditionRecordJpaEntity(
                        model.id(),
                        model.maintainableAssetId(),
                        model.conditionStatus(),
                        model.conditionTypeId(),
                        model.sourceModule(),
                        model.sourceReferenceId(),
                        model.summary(),
                        model.conditionScore(),
                        model.observedAt(),
                        model.observedByActorId(),
                        model.createdAt()
            );
        }

        public static AssetConditionRecord toDomain(AssetConditionRecordJpaEntity entity) {
            return new AssetConditionRecord(
                        entity.id(),
                        entity.maintainableAssetId(),
                        entity.conditionStatus(),
                        entity.conditionTypeId(),
                        entity.sourceModule(),
                        entity.sourceReferenceId(),
                        entity.summary(),
                        entity.conditionScore(),
                        entity.observedAt(),
                        entity.observedByActorId(),
                        entity.createdAt()
            );
        }
}
