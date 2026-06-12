/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetsPersistenceMapper
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
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

        public static AssetTypeJpaEntity toEntity(AssetType model) {
            return new AssetTypeJpaEntity(
                        model.id(),
                        model.code(),
                        model.parentTypeId(),
                        model.active(),
                        model.sortOrder(),
                        model.systemDefined(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static AssetType toDomain(AssetTypeJpaEntity entity) {
            return new AssetType(
                        entity.id(),
                        entity.code(),
                        entity.parentTypeId(),
                        entity.active(),
                        entity.sortOrder(),
                        entity.systemDefined(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static AssetTypeTranslationJpaEntity toEntity(AssetTypeTranslation model) {
            return new AssetTypeTranslationJpaEntity(
                        model.id(),
                        model.assetTypeId(),
                        model.locale(),
                        model.name(),
                        model.description(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static AssetTypeTranslation toDomain(AssetTypeTranslationJpaEntity entity) {
            return new AssetTypeTranslation(
                        entity.id(),
                        entity.assetTypeId(),
                        entity.locale(),
                        entity.name(),
                        entity.description(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static AssetTechnicalAttributeDefinitionJpaEntity toEntity(AssetTechnicalAttributeDefinition model) {
            return new AssetTechnicalAttributeDefinitionJpaEntity(
                        model.id(),
                        model.assetTypeId(),
                        model.code(),
                        model.name(),
                        model.dataType(),
                        model.unitId(),
                        model.required(),
                        model.active(),
                        model.sortOrder(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static AssetTechnicalAttributeDefinition toDomain(AssetTechnicalAttributeDefinitionJpaEntity entity) {
            return new AssetTechnicalAttributeDefinition(
                        entity.id(),
                        entity.assetTypeId(),
                        entity.code(),
                        entity.name(),
                        entity.dataType(),
                        entity.unitId(),
                        entity.required(),
                        entity.active(),
                        entity.sortOrder(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static AssetTechnicalAttributeValueJpaEntity toEntity(AssetTechnicalAttributeValue model) {
            return new AssetTechnicalAttributeValueJpaEntity(
                        model.id(),
                        model.maintainableAssetId(),
                        model.attributeDefinitionId(),
                        model.textValue(),
                        model.numericValue(),
                        model.booleanValue(),
                        model.dateValue(),
                        model.catalogValueId(),
                        model.unitId(),
                        model.effectiveFrom(),
                        model.effectiveTo(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static AssetTechnicalAttributeValue toDomain(AssetTechnicalAttributeValueJpaEntity entity) {
            return new AssetTechnicalAttributeValue(
                        entity.id(),
                        entity.maintainableAssetId(),
                        entity.attributeDefinitionId(),
                        entity.textValue(),
                        entity.numericValue(),
                        entity.booleanValue(),
                        entity.dateValue(),
                        entity.catalogValueId(),
                        entity.unitId(),
                        entity.effectiveFrom(),
                        entity.effectiveTo(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static AssetInstallationJpaEntity toEntity(AssetInstallation model) {
            return new AssetInstallationJpaEntity(
                        model.id(),
                        model.maintainableAssetId(),
                        model.installationNumber(),
                        model.topologyAssetTypeCode(),
                        model.topologyAssetId(),
                        model.topologyAssetCodeSnapshot(),
                        model.installedAt(),
                        model.commissionedAt(),
                        model.installedByPartyId(),
                        model.installedByNameSnapshot(),
                        model.commissioningDocumentId(),
                        model.notes(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static AssetInstallation toDomain(AssetInstallationJpaEntity entity) {
            return new AssetInstallation(
                        entity.id(),
                        entity.maintainableAssetId(),
                        entity.installationNumber(),
                        entity.topologyAssetTypeCode(),
                        entity.topologyAssetId(),
                        entity.topologyAssetCodeSnapshot(),
                        entity.installedAt(),
                        entity.commissionedAt(),
                        entity.installedByPartyId(),
                        entity.installedByNameSnapshot(),
                        entity.commissioningDocumentId(),
                        entity.notes(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static AssetManufacturerReferenceJpaEntity toEntity(AssetManufacturerReference model) {
            return new AssetManufacturerReferenceJpaEntity(
                        model.id(),
                        model.maintainableAssetId(),
                        model.manufacturerPartyId(),
                        model.manufacturerCodeSnapshot(),
                        model.manufacturerNameSnapshot(),
                        model.manufacturerRoleCodeSnapshot(),
                        model.manufacturerReferenceNumber(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static AssetManufacturerReference toDomain(AssetManufacturerReferenceJpaEntity entity) {
            return new AssetManufacturerReference(
                        entity.id(),
                        entity.maintainableAssetId(),
                        entity.manufacturerPartyId(),
                        entity.manufacturerCodeSnapshot(),
                        entity.manufacturerNameSnapshot(),
                        entity.manufacturerRoleCodeSnapshot(),
                        entity.manufacturerReferenceNumber(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static AssetModelJpaEntity toEntity(AssetModel model) {
            return new AssetModelJpaEntity(
                        model.id(),
                        model.modelCode(),
                        model.modelName(),
                        model.assetTypeId(),
                        model.manufacturerPartyId(),
                        model.manufacturerNameSnapshot(),
                        model.technicalDescription(),
                        model.active(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static AssetModel toDomain(AssetModelJpaEntity entity) {
            return new AssetModel(
                        entity.id(),
                        entity.modelCode(),
                        entity.modelName(),
                        entity.assetTypeId(),
                        entity.manufacturerPartyId(),
                        entity.manufacturerNameSnapshot(),
                        entity.technicalDescription(),
                        entity.active(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static AssetSerialIdentityJpaEntity toEntity(AssetSerialIdentity model) {
            return new AssetSerialIdentityJpaEntity(
                        model.id(),
                        model.maintainableAssetId(),
                        model.serialNumber(),
                        model.batchNumber(),
                        model.manufacturerPartNumber(),
                        model.nameplateDataJson(),
                        model.manufacturedAt(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static AssetSerialIdentity toDomain(AssetSerialIdentityJpaEntity entity) {
            return new AssetSerialIdentity(
                        entity.id(),
                        entity.maintainableAssetId(),
                        entity.serialNumber(),
                        entity.batchNumber(),
                        entity.manufacturerPartNumber(),
                        entity.nameplateDataJson(),
                        entity.manufacturedAt(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static AssetLifecycleEventJpaEntity toEntity(AssetLifecycleEvent model) {
            return new AssetLifecycleEventJpaEntity(
                        model.id(),
                        model.maintainableAssetId(),
                        model.eventType(),
                        model.oldStatus(),
                        model.newStatus(),
                        model.eventReasonId(),
                        model.eventComment(),
                        model.actorId(),
                        model.eventAt(),
                        model.correlationId(),
                        model.createdAt()
            );
        }

        public static AssetLifecycleEvent toDomain(AssetLifecycleEventJpaEntity entity) {
            return new AssetLifecycleEvent(
                        entity.id(),
                        entity.maintainableAssetId(),
                        entity.eventType(),
                        entity.oldStatus(),
                        entity.newStatus(),
                        entity.eventReasonId(),
                        entity.eventComment(),
                        entity.actorId(),
                        entity.eventAt(),
                        entity.correlationId(),
                        entity.createdAt()
            );
        }

        public static MaintenanceStrategyJpaEntity toEntity(MaintenanceStrategy model) {
            return new MaintenanceStrategyJpaEntity(
                        model.id(),
                        model.strategyCode(),
                        model.name(),
                        model.strategyTypeId(),
                        model.assetTypeId(),
                        model.description(),
                        model.status(),
                        model.effectiveFrom(),
                        model.effectiveTo(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static MaintenanceStrategy toDomain(MaintenanceStrategyJpaEntity entity) {
            return new MaintenanceStrategy(
                        entity.id(),
                        entity.strategyCode(),
                        entity.name(),
                        entity.strategyTypeId(),
                        entity.assetTypeId(),
                        entity.description(),
                        entity.status(),
                        entity.effectiveFrom(),
                        entity.effectiveTo(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static MaintenancePlanJpaEntity toEntity(MaintenancePlan model) {
            return new MaintenancePlanJpaEntity(
                        model.id(),
                        model.planCode(),
                        model.name(),
                        model.maintainableAssetId(),
                        model.maintenanceStrategyId(),
                        model.frequencyId(),
                        model.nextDueAt(),
                        model.lastExecutedAt(),
                        model.status(),
                        model.createdByActorId(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static MaintenancePlan toDomain(MaintenancePlanJpaEntity entity) {
            return new MaintenancePlan(
                        entity.id(),
                        entity.planCode(),
                        entity.name(),
                        entity.maintainableAssetId(),
                        entity.maintenanceStrategyId(),
                        entity.frequencyId(),
                        entity.nextDueAt(),
                        entity.lastExecutedAt(),
                        entity.status(),
                        entity.createdByActorId(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static MaintenanceTaskTemplateJpaEntity toEntity(MaintenanceTaskTemplate model) {
            return new MaintenanceTaskTemplateJpaEntity(
                        model.id(),
                        model.templateCode(),
                        model.name(),
                        model.assetTypeId(),
                        model.maintenanceStrategyId(),
                        model.taskTypeId(),
                        model.instructions(),
                        model.estimatedDurationMinutes(),
                        model.active(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static MaintenanceTaskTemplate toDomain(MaintenanceTaskTemplateJpaEntity entity) {
            return new MaintenanceTaskTemplate(
                        entity.id(),
                        entity.templateCode(),
                        entity.name(),
                        entity.assetTypeId(),
                        entity.maintenanceStrategyId(),
                        entity.taskTypeId(),
                        entity.instructions(),
                        entity.estimatedDurationMinutes(),
                        entity.active(),
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

        public static MaintenanceWorkOrderTaskJpaEntity toEntity(MaintenanceWorkOrderTask model) {
            return new MaintenanceWorkOrderTaskJpaEntity(
                        model.id(),
                        model.workOrderId(),
                        model.taskTemplateId(),
                        model.taskNumber(),
                        model.taskTypeId(),
                        model.description(),
                        model.status(),
                        model.sequenceNumber(),
                        model.assignedActorId(),
                        model.startedAt(),
                        model.completedAt(),
                        model.resultSummary(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static MaintenanceWorkOrderTask toDomain(MaintenanceWorkOrderTaskJpaEntity entity) {
            return new MaintenanceWorkOrderTask(
                        entity.id(),
                        entity.workOrderId(),
                        entity.taskTemplateId(),
                        entity.taskNumber(),
                        entity.taskTypeId(),
                        entity.description(),
                        entity.status(),
                        entity.sequenceNumber(),
                        entity.assignedActorId(),
                        entity.startedAt(),
                        entity.completedAt(),
                        entity.resultSummary(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static MaintenanceExecutionRecordJpaEntity toEntity(MaintenanceExecutionRecord model) {
            return new MaintenanceExecutionRecordJpaEntity(
                        model.id(),
                        model.workOrderId(),
                        model.taskId(),
                        model.executionResult(),
                        model.performedByActorId(),
                        model.executedAt(),
                        model.durationMinutes(),
                        model.resultSummary(),
                        model.measurementJson(),
                        model.followUpRecommendationId(),
                        model.createdAt()
            );
        }

        public static MaintenanceExecutionRecord toDomain(MaintenanceExecutionRecordJpaEntity entity) {
            return new MaintenanceExecutionRecord(
                        entity.id(),
                        entity.workOrderId(),
                        entity.taskId(),
                        entity.executionResult(),
                        entity.performedByActorId(),
                        entity.executedAt(),
                        entity.durationMinutes(),
                        entity.resultSummary(),
                        entity.measurementJson(),
                        entity.followUpRecommendationId(),
                        entity.createdAt()
            );
        }

        public static SparePartJpaEntity toEntity(SparePart model) {
            return new SparePartJpaEntity(
                        model.id(),
                        model.partNumber(),
                        model.name(),
                        model.description(),
                        model.manufacturerPartyId(),
                        model.manufacturerNameSnapshot(),
                        model.unitId(),
                        model.categoryId(),
                        model.active(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static SparePart toDomain(SparePartJpaEntity entity) {
            return new SparePart(
                        entity.id(),
                        entity.partNumber(),
                        entity.name(),
                        entity.description(),
                        entity.manufacturerPartyId(),
                        entity.manufacturerNameSnapshot(),
                        entity.unitId(),
                        entity.categoryId(),
                        entity.active(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static AssetSparePartCompatibilityJpaEntity toEntity(AssetSparePartCompatibility model) {
            return new AssetSparePartCompatibilityJpaEntity(
                        model.id(),
                        model.maintainableAssetId(),
                        model.assetTypeId(),
                        model.assetModelId(),
                        model.sparePartId(),
                        model.compatibilityRule(),
                        model.status(),
                        model.effectiveFrom(),
                        model.effectiveTo(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static AssetSparePartCompatibility toDomain(AssetSparePartCompatibilityJpaEntity entity) {
            return new AssetSparePartCompatibility(
                        entity.id(),
                        entity.maintainableAssetId(),
                        entity.assetTypeId(),
                        entity.assetModelId(),
                        entity.sparePartId(),
                        entity.compatibilityRule(),
                        entity.status(),
                        entity.effectiveFrom(),
                        entity.effectiveTo(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static AssetDocumentReferenceJpaEntity toEntity(AssetDocumentReference model) {
            return new AssetDocumentReferenceJpaEntity(
                        model.id(),
                        model.maintainableAssetId(),
                        model.documentType(),
                        model.documentReferenceId(),
                        model.documentCodeSnapshot(),
                        model.documentTitleSnapshot(),
                        model.attachedAt(),
                        model.attachedByActorId()
            );
        }

        public static AssetDocumentReference toDomain(AssetDocumentReferenceJpaEntity entity) {
            return new AssetDocumentReference(
                        entity.id(),
                        entity.maintainableAssetId(),
                        entity.documentType(),
                        entity.documentReferenceId(),
                        entity.documentCodeSnapshot(),
                        entity.documentTitleSnapshot(),
                        entity.attachedAt(),
                        entity.attachedByActorId()
            );
        }

        public static AssetWarrantyJpaEntity toEntity(AssetWarranty model) {
            return new AssetWarrantyJpaEntity(
                        model.id(),
                        model.maintainableAssetId(),
                        model.warrantyNumber(),
                        model.providerPartyId(),
                        model.providerNameSnapshot(),
                        model.validFrom(),
                        model.validTo(),
                        model.status(),
                        model.termsSummary(),
                        model.documentReferenceId(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static AssetWarranty toDomain(AssetWarrantyJpaEntity entity) {
            return new AssetWarranty(
                        entity.id(),
                        entity.maintainableAssetId(),
                        entity.warrantyNumber(),
                        entity.providerPartyId(),
                        entity.providerNameSnapshot(),
                        entity.validFrom(),
                        entity.validTo(),
                        entity.status(),
                        entity.termsSummary(),
                        entity.documentReferenceId(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static AssetServiceContractReferenceJpaEntity toEntity(AssetServiceContractReference model) {
            return new AssetServiceContractReferenceJpaEntity(
                        model.id(),
                        model.maintainableAssetId(),
                        model.contractReferenceId(),
                        model.contractCodeSnapshot(),
                        model.serviceProviderPartyId(),
                        model.serviceProviderNameSnapshot(),
                        model.validFrom(),
                        model.validTo(),
                        model.active(),
                        model.createdAt()
            );
        }

        public static AssetServiceContractReference toDomain(AssetServiceContractReferenceJpaEntity entity) {
            return new AssetServiceContractReference(
                        entity.id(),
                        entity.maintainableAssetId(),
                        entity.contractReferenceId(),
                        entity.contractCodeSnapshot(),
                        entity.serviceProviderPartyId(),
                        entity.serviceProviderNameSnapshot(),
                        entity.validFrom(),
                        entity.validTo(),
                        entity.active(),
                        entity.createdAt()
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

        public static AssetMeterReadingReferenceJpaEntity toEntity(AssetMeterReadingReference model) {
            return new AssetMeterReadingReferenceJpaEntity(
                        model.id(),
                        model.maintainableAssetId(),
                        model.readingType(),
                        model.readingReferenceId(),
                        model.readingCodeSnapshot(),
                        model.readingValueSnapshot(),
                        model.unitId(),
                        model.readingAt(),
                        model.createdAt()
            );
        }

        public static AssetMeterReadingReference toDomain(AssetMeterReadingReferenceJpaEntity entity) {
            return new AssetMeterReadingReference(
                        entity.id(),
                        entity.maintainableAssetId(),
                        entity.readingType(),
                        entity.readingReferenceId(),
                        entity.readingCodeSnapshot(),
                        entity.readingValueSnapshot(),
                        entity.unitId(),
                        entity.readingAt(),
                        entity.createdAt()
            );
        }

        public static AssetCatalogEntryJpaEntity toEntity(AssetCatalogEntry model) {
            return new AssetCatalogEntryJpaEntity(
                        model.id(),
                        model.catalogName(),
                        model.code(),
                        model.active(),
                        model.sortOrder(),
                        model.systemDefined(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static AssetCatalogEntry toDomain(AssetCatalogEntryJpaEntity entity) {
            return new AssetCatalogEntry(
                        entity.id(),
                        entity.catalogName(),
                        entity.code(),
                        entity.active(),
                        entity.sortOrder(),
                        entity.systemDefined(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static AssetCatalogTranslationJpaEntity toEntity(AssetCatalogTranslation model) {
            return new AssetCatalogTranslationJpaEntity(
                        model.id(),
                        model.catalogEntryId(),
                        model.locale(),
                        model.name(),
                        model.description(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static AssetCatalogTranslation toDomain(AssetCatalogTranslationJpaEntity entity) {
            return new AssetCatalogTranslation(
                        entity.id(),
                        entity.catalogEntryId(),
                        entity.locale(),
                        entity.name(),
                        entity.description(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

}
