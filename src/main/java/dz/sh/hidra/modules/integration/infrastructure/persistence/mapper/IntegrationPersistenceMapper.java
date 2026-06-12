/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationPersistenceMapper
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.persistence.mapper
 *
 * @Description : Maps integration domain models to JPA entities.
 *
 */
package dz.sh.hidra.modules.integration.infrastructure.persistence.mapper;

import dz.sh.hidra.modules.integration.domain.model.*;
import dz.sh.hidra.modules.integration.infrastructure.persistence.entity.*;

/**
 * Maps integration domain models to JPA entities.
 */
public final class IntegrationPersistenceMapper {

    private IntegrationPersistenceMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }


        public static ExternalSystemJpaEntity toEntity(ExternalSystem model) {
            return new ExternalSystemJpaEntity(
                        model.id(),
                        model.code(),
                        model.nameAr(),
                        model.nameFr(),
                        model.nameEn(),
                        model.systemTypeId(),
                        model.ownerOrganizationUnitId(),
                        model.environment(),
                        model.criticality(),
                        model.status(),
                        model.description(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static ExternalSystem toDomain(ExternalSystemJpaEntity entity) {
            return new ExternalSystem(
                        entity.id(),
                        entity.code(),
                        entity.nameAr(),
                        entity.nameFr(),
                        entity.nameEn(),
                        entity.systemTypeId(),
                        entity.ownerOrganizationUnitId(),
                        entity.environment(),
                        entity.criticality(),
                        entity.status(),
                        entity.description(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static ExternalEndpointJpaEntity toEntity(ExternalEndpoint model) {
            return new ExternalEndpointJpaEntity(
                        model.id(),
                        model.externalSystemId(),
                        model.code(),
                        model.endpointTypeId(),
                        model.direction(),
                        model.endpointUri(),
                        model.host(),
                        model.port(),
                        model.pathOrTopic(),
                        model.protocolId(),
                        model.pollingIntervalSeconds(),
                        model.timeoutSeconds(),
                        model.credentialReference(),
                        model.tlsRequired(),
                        model.active(),
                        model.validFrom(),
                        model.validTo(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static ExternalEndpoint toDomain(ExternalEndpointJpaEntity entity) {
            return new ExternalEndpoint(
                        entity.id(),
                        entity.externalSystemId(),
                        entity.code(),
                        entity.endpointTypeId(),
                        entity.direction(),
                        entity.endpointUri(),
                        entity.host(),
                        entity.port(),
                        entity.pathOrTopic(),
                        entity.protocolId(),
                        entity.pollingIntervalSeconds(),
                        entity.timeoutSeconds(),
                        entity.credentialReference(),
                        entity.tlsRequired(),
                        entity.active(),
                        entity.validFrom(),
                        entity.validTo(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static ConnectorInstanceJpaEntity toEntity(ConnectorInstance model) {
            return new ConnectorInstanceJpaEntity(
                        model.id(),
                        model.externalSystemId(),
                        model.endpointId(),
                        model.code(),
                        model.connectorTypeId(),
                        model.connectorImplementation(),
                        model.direction(),
                        model.configurationJson(),
                        model.maxConcurrency(),
                        model.active(),
                        model.healthStatus(),
                        model.lastHealthCheckAt(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static ConnectorInstance toDomain(ConnectorInstanceJpaEntity entity) {
            return new ConnectorInstance(
                        entity.id(),
                        entity.externalSystemId(),
                        entity.endpointId(),
                        entity.code(),
                        entity.connectorTypeId(),
                        entity.connectorImplementation(),
                        entity.direction(),
                        entity.configurationJson(),
                        entity.maxConcurrency(),
                        entity.active(),
                        entity.healthStatus(),
                        entity.lastHealthCheckAt(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static IntegrationDataContractJpaEntity toEntity(IntegrationDataContract model) {
            return new IntegrationDataContractJpaEntity(
                        model.id(),
                        model.code(),
                        model.nameFr(),
                        model.nameAr(),
                        model.nameEn(),
                        model.contractTypeId(),
                        model.payloadFormatId(),
                        model.owningTargetModule(),
                        model.description(),
                        model.status(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static IntegrationDataContract toDomain(IntegrationDataContractJpaEntity entity) {
            return new IntegrationDataContract(
                        entity.id(),
                        entity.code(),
                        entity.nameFr(),
                        entity.nameAr(),
                        entity.nameEn(),
                        entity.contractTypeId(),
                        entity.payloadFormatId(),
                        entity.owningTargetModule(),
                        entity.description(),
                        entity.status(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static IntegrationSchemaVersionJpaEntity toEntity(IntegrationSchemaVersion model) {
            return new IntegrationSchemaVersionJpaEntity(
                        model.id(),
                        model.dataContractId(),
                        model.versionNumber(),
                        model.schemaDefinition(),
                        model.checksum(),
                        model.status(),
                        model.effectiveFrom(),
                        model.effectiveTo(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static IntegrationSchemaVersion toDomain(IntegrationSchemaVersionJpaEntity entity) {
            return new IntegrationSchemaVersion(
                        entity.id(),
                        entity.dataContractId(),
                        entity.versionNumber(),
                        entity.schemaDefinition(),
                        entity.checksum(),
                        entity.status(),
                        entity.effectiveFrom(),
                        entity.effectiveTo(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static IntegrationMappingProfileJpaEntity toEntity(IntegrationMappingProfile model) {
            return new IntegrationMappingProfileJpaEntity(
                        model.id(),
                        model.code(),
                        model.externalSystemId(),
                        model.dataContractId(),
                        model.schemaVersionId(),
                        model.targetModule(),
                        model.targetTypeCode(),
                        model.direction(),
                        model.status(),
                        model.validationMode(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static IntegrationMappingProfile toDomain(IntegrationMappingProfileJpaEntity entity) {
            return new IntegrationMappingProfile(
                        entity.id(),
                        entity.code(),
                        entity.externalSystemId(),
                        entity.dataContractId(),
                        entity.schemaVersionId(),
                        entity.targetModule(),
                        entity.targetTypeCode(),
                        entity.direction(),
                        entity.status(),
                        entity.validationMode(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static IntegrationFieldMappingJpaEntity toEntity(IntegrationFieldMapping model) {
            return new IntegrationFieldMappingJpaEntity(
                        model.id(),
                        model.mappingProfileId(),
                        model.sourcePath(),
                        model.targetPath(),
                        model.dataType(),
                        model.required(),
                        model.defaultValue(),
                        model.unitCode(),
                        model.transformationRuleId(),
                        model.displayOrder(),
                        model.active()
            );
        }

        public static IntegrationFieldMapping toDomain(IntegrationFieldMappingJpaEntity entity) {
            return new IntegrationFieldMapping(
                        entity.id(),
                        entity.mappingProfileId(),
                        entity.sourcePath(),
                        entity.targetPath(),
                        entity.dataType(),
                        entity.required(),
                        entity.defaultValue(),
                        entity.unitCode(),
                        entity.transformationRuleId(),
                        entity.displayOrder(),
                        entity.active()
            );
        }

        public static IntegrationTransformationRuleJpaEntity toEntity(IntegrationTransformationRule model) {
            return new IntegrationTransformationRuleJpaEntity(
                        model.id(),
                        model.mappingProfileId(),
                        model.code(),
                        model.ruleTypeId(),
                        model.expression(),
                        model.configurationJson(),
                        model.active(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static IntegrationTransformationRule toDomain(IntegrationTransformationRuleJpaEntity entity) {
            return new IntegrationTransformationRule(
                        entity.id(),
                        entity.mappingProfileId(),
                        entity.code(),
                        entity.ruleTypeId(),
                        entity.expression(),
                        entity.configurationJson(),
                        entity.active(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static ExternalObjectReferenceJpaEntity toEntity(ExternalObjectReference model) {
            return new ExternalObjectReferenceJpaEntity(
                        model.id(),
                        model.externalSystemId(),
                        model.externalObjectType(),
                        model.externalObjectId(),
                        model.externalObjectCode(),
                        model.targetModule(),
                        model.targetTypeCode(),
                        model.targetId(),
                        model.targetCodeSnapshot(),
                        model.targetLabelSnapshot(),
                        model.confidenceLevel(),
                        model.status(),
                        model.validFrom(),
                        model.validTo(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static ExternalObjectReference toDomain(ExternalObjectReferenceJpaEntity entity) {
            return new ExternalObjectReference(
                        entity.id(),
                        entity.externalSystemId(),
                        entity.externalObjectType(),
                        entity.externalObjectId(),
                        entity.externalObjectCode(),
                        entity.targetModule(),
                        entity.targetTypeCode(),
                        entity.targetId(),
                        entity.targetCodeSnapshot(),
                        entity.targetLabelSnapshot(),
                        entity.confidenceLevel(),
                        entity.status(),
                        entity.validFrom(),
                        entity.validTo(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static IntegrationJobDefinitionJpaEntity toEntity(IntegrationJobDefinition model) {
            return new IntegrationJobDefinitionJpaEntity(
                        model.id(),
                        model.code(),
                        model.nameFr(),
                        model.nameAr(),
                        model.nameEn(),
                        model.connectorInstanceId(),
                        model.mappingProfileId(),
                        model.jobTypeId(),
                        model.direction(),
                        model.targetModule(),
                        model.scheduleExpression(),
                        model.manualRunAllowed(),
                        model.retryPolicyId(),
                        model.active(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static IntegrationJobDefinition toDomain(IntegrationJobDefinitionJpaEntity entity) {
            return new IntegrationJobDefinition(
                        entity.id(),
                        entity.code(),
                        entity.nameFr(),
                        entity.nameAr(),
                        entity.nameEn(),
                        entity.connectorInstanceId(),
                        entity.mappingProfileId(),
                        entity.jobTypeId(),
                        entity.direction(),
                        entity.targetModule(),
                        entity.scheduleExpression(),
                        entity.manualRunAllowed(),
                        entity.retryPolicyId(),
                        entity.active(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static IntegrationJobRunJpaEntity toEntity(IntegrationJobRun model) {
            return new IntegrationJobRunJpaEntity(
                        model.id(),
                        model.jobDefinitionId(),
                        model.runNumber(),
                        model.triggerType(),
                        model.triggeredByActorId(),
                        model.status(),
                        model.correlationId(),
                        model.startedAt(),
                        model.completedAt(),
                        model.receivedCount(),
                        model.mappedCount(),
                        model.acceptedCount(),
                        model.rejectedCount(),
                        model.deadLetterCount(),
                        model.retryCount(),
                        model.failureReason(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static IntegrationJobRun toDomain(IntegrationJobRunJpaEntity entity) {
            return new IntegrationJobRun(
                        entity.id(),
                        entity.jobDefinitionId(),
                        entity.runNumber(),
                        entity.triggerType(),
                        entity.triggeredByActorId(),
                        entity.status(),
                        entity.correlationId(),
                        entity.startedAt(),
                        entity.completedAt(),
                        entity.receivedCount(),
                        entity.mappedCount(),
                        entity.acceptedCount(),
                        entity.rejectedCount(),
                        entity.deadLetterCount(),
                        entity.retryCount(),
                        entity.failureReason(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static IntegrationJobRunStepJpaEntity toEntity(IntegrationJobRunStep model) {
            return new IntegrationJobRunStepJpaEntity(
                        model.id(),
                        model.jobRunId(),
                        model.stepName(),
                        model.status(),
                        model.startedAt(),
                        model.completedAt(),
                        model.processedCount(),
                        model.errorCount(),
                        model.detailsJson()
            );
        }

        public static IntegrationJobRunStep toDomain(IntegrationJobRunStepJpaEntity entity) {
            return new IntegrationJobRunStep(
                        entity.id(),
                        entity.jobRunId(),
                        entity.stepName(),
                        entity.status(),
                        entity.startedAt(),
                        entity.completedAt(),
                        entity.processedCount(),
                        entity.errorCount(),
                        entity.detailsJson()
            );
        }

        public static IntegrationExchangeMessageJpaEntity toEntity(IntegrationExchangeMessage model) {
            return new IntegrationExchangeMessageJpaEntity(
                        model.id(),
                        model.jobRunId(),
                        model.externalSystemId(),
                        model.endpointId(),
                        model.direction(),
                        model.messageTypeId(),
                        model.externalMessageId(),
                        model.payloadFormatId(),
                        model.payloadStorageMode(),
                        model.payloadSanitized(),
                        model.payloadReference(),
                        model.payloadHash(),
                        model.contentLengthBytes(),
                        model.receivedOrSentAt(),
                        model.correlationId(),
                        model.status(),
                        model.createdAt()
            );
        }

        public static IntegrationExchangeMessage toDomain(IntegrationExchangeMessageJpaEntity entity) {
            return new IntegrationExchangeMessage(
                        entity.id(),
                        entity.jobRunId(),
                        entity.externalSystemId(),
                        entity.endpointId(),
                        entity.direction(),
                        entity.messageTypeId(),
                        entity.externalMessageId(),
                        entity.payloadFormatId(),
                        entity.payloadStorageMode(),
                        entity.payloadSanitized(),
                        entity.payloadReference(),
                        entity.payloadHash(),
                        entity.contentLengthBytes(),
                        entity.receivedOrSentAt(),
                        entity.correlationId(),
                        entity.status(),
                        entity.createdAt()
            );
        }

        public static IntegrationInboundRecordJpaEntity toEntity(IntegrationInboundRecord model) {
            return new IntegrationInboundRecordJpaEntity(
                        model.id(),
                        model.exchangeMessageId(),
                        model.jobRunId(),
                        model.recordSequence(),
                        model.mappingProfileId(),
                        model.targetModule(),
                        model.targetTypeCode(),
                        model.targetId(),
                        model.targetCodeSnapshot(),
                        model.mappedPayload(),
                        model.validationStatus(),
                        model.submissionStatus(),
                        model.targetResponseCode(),
                        model.targetResponseMessage(),
                        model.errorCode(),
                        model.errorMessage(),
                        model.createdAt(),
                        model.submittedAt(),
                        model.completedAt()
            );
        }

        public static IntegrationInboundRecord toDomain(IntegrationInboundRecordJpaEntity entity) {
            return new IntegrationInboundRecord(
                        entity.id(),
                        entity.exchangeMessageId(),
                        entity.jobRunId(),
                        entity.recordSequence(),
                        entity.mappingProfileId(),
                        entity.targetModule(),
                        entity.targetTypeCode(),
                        entity.targetId(),
                        entity.targetCodeSnapshot(),
                        entity.mappedPayload(),
                        entity.validationStatus(),
                        entity.submissionStatus(),
                        entity.targetResponseCode(),
                        entity.targetResponseMessage(),
                        entity.errorCode(),
                        entity.errorMessage(),
                        entity.createdAt(),
                        entity.submittedAt(),
                        entity.completedAt()
            );
        }

        public static IntegrationOutboundRecordJpaEntity toEntity(IntegrationOutboundRecord model) {
            return new IntegrationOutboundRecordJpaEntity(
                        model.id(),
                        model.exchangeMessageId(),
                        model.jobRunId(),
                        model.sourceModule(),
                        model.sourceTypeCode(),
                        model.sourceId(),
                        model.sourceCodeSnapshot(),
                        model.sourceLabelSnapshot(),
                        model.mappingProfileId(),
                        model.outboundPayload(),
                        model.externalSystemId(),
                        model.externalObjectId(),
                        model.status(),
                        model.errorCode(),
                        model.errorMessage(),
                        model.createdAt(),
                        model.sentAt(),
                        model.acknowledgedAt()
            );
        }

        public static IntegrationOutboundRecord toDomain(IntegrationOutboundRecordJpaEntity entity) {
            return new IntegrationOutboundRecord(
                        entity.id(),
                        entity.exchangeMessageId(),
                        entity.jobRunId(),
                        entity.sourceModule(),
                        entity.sourceTypeCode(),
                        entity.sourceId(),
                        entity.sourceCodeSnapshot(),
                        entity.sourceLabelSnapshot(),
                        entity.mappingProfileId(),
                        entity.outboundPayload(),
                        entity.externalSystemId(),
                        entity.externalObjectId(),
                        entity.status(),
                        entity.errorCode(),
                        entity.errorMessage(),
                        entity.createdAt(),
                        entity.sentAt(),
                        entity.acknowledgedAt()
            );
        }

        public static IntegrationRetryPolicyJpaEntity toEntity(IntegrationRetryPolicy model) {
            return new IntegrationRetryPolicyJpaEntity(
                        model.id(),
                        model.code(),
                        model.maxAttempts(),
                        model.initialDelaySeconds(),
                        model.maxDelaySeconds(),
                        model.backoffStrategy(),
                        model.retryableErrorCodes(),
                        model.active(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static IntegrationRetryPolicy toDomain(IntegrationRetryPolicyJpaEntity entity) {
            return new IntegrationRetryPolicy(
                        entity.id(),
                        entity.code(),
                        entity.maxAttempts(),
                        entity.initialDelaySeconds(),
                        entity.maxDelaySeconds(),
                        entity.backoffStrategy(),
                        entity.retryableErrorCodes(),
                        entity.active(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static IntegrationRetryAttemptJpaEntity toEntity(IntegrationRetryAttempt model) {
            return new IntegrationRetryAttemptJpaEntity(
                        model.id(),
                        model.retryPolicyId(),
                        model.targetRecordType(),
                        model.targetRecordId(),
                        model.attemptNumber(),
                        model.status(),
                        model.scheduledAt(),
                        model.startedAt(),
                        model.completedAt(),
                        model.errorCode(),
                        model.errorMessage(),
                        model.createdAt()
            );
        }

        public static IntegrationRetryAttempt toDomain(IntegrationRetryAttemptJpaEntity entity) {
            return new IntegrationRetryAttempt(
                        entity.id(),
                        entity.retryPolicyId(),
                        entity.targetRecordType(),
                        entity.targetRecordId(),
                        entity.attemptNumber(),
                        entity.status(),
                        entity.scheduledAt(),
                        entity.startedAt(),
                        entity.completedAt(),
                        entity.errorCode(),
                        entity.errorMessage(),
                        entity.createdAt()
            );
        }

        public static IntegrationDeadLetterRecordJpaEntity toEntity(IntegrationDeadLetterRecord model) {
            return new IntegrationDeadLetterRecordJpaEntity(
                        model.id(),
                        model.externalSystemId(),
                        model.jobRunId(),
                        model.exchangeMessageId(),
                        model.inboundRecordId(),
                        model.outboundRecordId(),
                        model.targetModule(),
                        model.failureStage(),
                        model.reasonCode(),
                        model.reasonMessage(),
                        model.payloadHash(),
                        model.sanitizedPayload(),
                        model.status(),
                        model.resolvedByActorId(),
                        model.resolvedAt(),
                        model.resolutionComment(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static IntegrationDeadLetterRecord toDomain(IntegrationDeadLetterRecordJpaEntity entity) {
            return new IntegrationDeadLetterRecord(
                        entity.id(),
                        entity.externalSystemId(),
                        entity.jobRunId(),
                        entity.exchangeMessageId(),
                        entity.inboundRecordId(),
                        entity.outboundRecordId(),
                        entity.targetModule(),
                        entity.failureStage(),
                        entity.reasonCode(),
                        entity.reasonMessage(),
                        entity.payloadHash(),
                        entity.sanitizedPayload(),
                        entity.status(),
                        entity.resolvedByActorId(),
                        entity.resolvedAt(),
                        entity.resolutionComment(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static IntegrationSyncCursorJpaEntity toEntity(IntegrationSyncCursor model) {
            return new IntegrationSyncCursorJpaEntity(
                        model.id(),
                        model.jobDefinitionId(),
                        model.externalSystemId(),
                        model.cursorName(),
                        model.cursorValue(),
                        model.cursorPayload(),
                        model.lastSuccessfulRunId(),
                        model.lastSuccessfulAt(),
                        model.status(),
                        model.updatedAt()
            );
        }

        public static IntegrationSyncCursor toDomain(IntegrationSyncCursorJpaEntity entity) {
            return new IntegrationSyncCursor(
                        entity.id(),
                        entity.jobDefinitionId(),
                        entity.externalSystemId(),
                        entity.cursorName(),
                        entity.cursorValue(),
                        entity.cursorPayload(),
                        entity.lastSuccessfulRunId(),
                        entity.lastSuccessfulAt(),
                        entity.status(),
                        entity.updatedAt()
            );
        }

        public static IntegrationReconciliationRunJpaEntity toEntity(IntegrationReconciliationRun model) {
            return new IntegrationReconciliationRunJpaEntity(
                        model.id(),
                        model.externalSystemId(),
                        model.jobDefinitionId(),
                        model.targetModule(),
                        model.targetTypeCode(),
                        model.reconciliationPeriodStart(),
                        model.reconciliationPeriodEnd(),
                        model.status(),
                        model.hidraCount(),
                        model.externalCount(),
                        model.matchedCount(),
                        model.missingInHidraCount(),
                        model.missingExternallyCount(),
                        model.mismatchCount(),
                        model.startedAt(),
                        model.completedAt(),
                        model.createdAt()
            );
        }

        public static IntegrationReconciliationRun toDomain(IntegrationReconciliationRunJpaEntity entity) {
            return new IntegrationReconciliationRun(
                        entity.id(),
                        entity.externalSystemId(),
                        entity.jobDefinitionId(),
                        entity.targetModule(),
                        entity.targetTypeCode(),
                        entity.reconciliationPeriodStart(),
                        entity.reconciliationPeriodEnd(),
                        entity.status(),
                        entity.hidraCount(),
                        entity.externalCount(),
                        entity.matchedCount(),
                        entity.missingInHidraCount(),
                        entity.missingExternallyCount(),
                        entity.mismatchCount(),
                        entity.startedAt(),
                        entity.completedAt(),
                        entity.createdAt()
            );
        }

        public static IntegrationReconciliationIssueJpaEntity toEntity(IntegrationReconciliationIssue model) {
            return new IntegrationReconciliationIssueJpaEntity(
                        model.id(),
                        model.reconciliationRunId(),
                        model.issueType(),
                        model.externalObjectType(),
                        model.externalObjectId(),
                        model.targetModule(),
                        model.targetTypeCode(),
                        model.targetId(),
                        model.fieldPath(),
                        model.hidraValueSnapshot(),
                        model.externalValueSnapshot(),
                        model.severity(),
                        model.status(),
                        model.resolutionComment(),
                        model.createdAt(),
                        model.resolvedAt()
            );
        }

        public static IntegrationReconciliationIssue toDomain(IntegrationReconciliationIssueJpaEntity entity) {
            return new IntegrationReconciliationIssue(
                        entity.id(),
                        entity.reconciliationRunId(),
                        entity.issueType(),
                        entity.externalObjectType(),
                        entity.externalObjectId(),
                        entity.targetModule(),
                        entity.targetTypeCode(),
                        entity.targetId(),
                        entity.fieldPath(),
                        entity.hidraValueSnapshot(),
                        entity.externalValueSnapshot(),
                        entity.severity(),
                        entity.status(),
                        entity.resolutionComment(),
                        entity.createdAt(),
                        entity.resolvedAt()
            );
        }

        public static IntegrationHealthSnapshotJpaEntity toEntity(IntegrationHealthSnapshot model) {
            return new IntegrationHealthSnapshotJpaEntity(
                        model.id(),
                        model.externalSystemId(),
                        model.endpointId(),
                        model.connectorInstanceId(),
                        model.jobDefinitionId(),
                        model.healthStatus(),
                        model.latencyMs(),
                        model.lastSuccessAt(),
                        model.lastFailureAt(),
                        model.errorCode(),
                        model.errorMessage(),
                        model.capturedAt()
            );
        }

        public static IntegrationHealthSnapshot toDomain(IntegrationHealthSnapshotJpaEntity entity) {
            return new IntegrationHealthSnapshot(
                        entity.id(),
                        entity.externalSystemId(),
                        entity.endpointId(),
                        entity.connectorInstanceId(),
                        entity.jobDefinitionId(),
                        entity.healthStatus(),
                        entity.latencyMs(),
                        entity.lastSuccessAt(),
                        entity.lastFailureAt(),
                        entity.errorCode(),
                        entity.errorMessage(),
                        entity.capturedAt()
            );
        }

        public static IntegrationCatalogEntryJpaEntity toEntity(IntegrationCatalogEntry model) {
            return new IntegrationCatalogEntryJpaEntity(
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

        public static IntegrationCatalogEntry toDomain(IntegrationCatalogEntryJpaEntity entity) {
            return new IntegrationCatalogEntry(
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

        public static IntegrationCatalogTranslationJpaEntity toEntity(IntegrationCatalogTranslation model) {
            return new IntegrationCatalogTranslationJpaEntity(
                        model.id(),
                        model.catalogEntryId(),
                        model.locale(),
                        model.name(),
                        model.description(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static IntegrationCatalogTranslation toDomain(IntegrationCatalogTranslationJpaEntity entity) {
            return new IntegrationCatalogTranslation(
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
