/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationPersistenceMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
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
}
