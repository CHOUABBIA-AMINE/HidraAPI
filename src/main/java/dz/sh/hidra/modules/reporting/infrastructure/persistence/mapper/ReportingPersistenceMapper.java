/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingPersistenceMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.infrastructure.persistence.mapper
 *
 * @Description : Maps reporting domain models to JPA entities.
 *
 */
package dz.sh.hidra.modules.reporting.infrastructure.persistence.mapper;

import dz.sh.hidra.modules.reporting.domain.model.*;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.entity.*;

/**
 * Maps reporting domain models to JPA entities.
 */
public final class ReportingPersistenceMapper {

    private ReportingPersistenceMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }


        public static ReportDefinitionJpaEntity toEntity(ReportDefinition model) {
            return new ReportDefinitionJpaEntity(
                        model.id(),
                        model.code(),
                        model.nameAr(),
                        model.nameFr(),
                        model.nameEn(),
                        model.reportCategoryId(),
                        model.ownerModule(),
                        model.description(),
                        model.active(),
                        model.currentTemplateVersionId(),
                        model.requiresApproval(),
                        model.restricted(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static ReportDefinition toDomain(ReportDefinitionJpaEntity entity) {
            return new ReportDefinition(
                        entity.id(),
                        entity.code(),
                        entity.nameAr(),
                        entity.nameFr(),
                        entity.nameEn(),
                        entity.reportCategoryId(),
                        entity.ownerModule(),
                        entity.description(),
                        entity.active(),
                        entity.currentTemplateVersionId(),
                        entity.requiresApproval(),
                        entity.restricted(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static ReportTemplateJpaEntity toEntity(ReportTemplate model) {
            return new ReportTemplateJpaEntity(
                        model.id(),
                        model.reportDefinitionId(),
                        model.code(),
                        model.nameAr(),
                        model.nameFr(),
                        model.nameEn(),
                        model.templateEngine(),
                        model.active(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static ReportTemplate toDomain(ReportTemplateJpaEntity entity) {
            return new ReportTemplate(
                        entity.id(),
                        entity.reportDefinitionId(),
                        entity.code(),
                        entity.nameAr(),
                        entity.nameFr(),
                        entity.nameEn(),
                        entity.templateEngine(),
                        entity.active(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static ReportTemplateVersionJpaEntity toEntity(ReportTemplateVersion model) {
            return new ReportTemplateVersionJpaEntity(
                        model.id(),
                        model.reportTemplateId(),
                        model.versionNumber(),
                        model.status(),
                        model.layoutContentReference(),
                        model.styleReference(),
                        model.checksum(),
                        model.createdByActorId(),
                        model.createdByDisplayNameSnapshot(),
                        model.createdAt(),
                        model.activatedAt(),
                        model.retiredAt()
            );
        }

        public static ReportTemplateVersion toDomain(ReportTemplateVersionJpaEntity entity) {
            return new ReportTemplateVersion(
                        entity.id(),
                        entity.reportTemplateId(),
                        entity.versionNumber(),
                        entity.status(),
                        entity.layoutContentReference(),
                        entity.styleReference(),
                        entity.checksum(),
                        entity.createdByActorId(),
                        entity.createdByDisplayNameSnapshot(),
                        entity.createdAt(),
                        entity.activatedAt(),
                        entity.retiredAt()
            );
        }

        public static ReportParameterDefinitionJpaEntity toEntity(ReportParameterDefinition model) {
            return new ReportParameterDefinitionJpaEntity(
                        model.id(),
                        model.reportDefinitionId(),
                        model.code(),
                        model.labelAr(),
                        model.labelFr(),
                        model.labelEn(),
                        model.parameterType(),
                        model.required(),
                        model.defaultValue(),
                        model.allowedValuesReference(),
                        model.validationExpression(),
                        model.sortOrder(),
                        model.active(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static ReportParameterDefinition toDomain(ReportParameterDefinitionJpaEntity entity) {
            return new ReportParameterDefinition(
                        entity.id(),
                        entity.reportDefinitionId(),
                        entity.code(),
                        entity.labelAr(),
                        entity.labelFr(),
                        entity.labelEn(),
                        entity.parameterType(),
                        entity.required(),
                        entity.defaultValue(),
                        entity.allowedValuesReference(),
                        entity.validationExpression(),
                        entity.sortOrder(),
                        entity.active(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static ReportRequestJpaEntity toEntity(ReportRequest model) {
            return new ReportRequestJpaEntity(
                        model.id(),
                        model.reportDefinitionId(),
                        model.requestedByActorId(),
                        model.requestedByUsernameSnapshot(),
                        model.requestedByDisplayNameSnapshot(),
                        model.requestedByRoleCodeSnapshot(),
                        model.organizationUnitId(),
                        model.organizationUnitNameSnapshot(),
                        model.requestedAt(),
                        model.purpose(),
                        model.status(),
                        model.correlationId(),
                        model.workflowReferenceId(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static ReportRequest toDomain(ReportRequestJpaEntity entity) {
            return new ReportRequest(
                        entity.id(),
                        entity.reportDefinitionId(),
                        entity.requestedByActorId(),
                        entity.requestedByUsernameSnapshot(),
                        entity.requestedByDisplayNameSnapshot(),
                        entity.requestedByRoleCodeSnapshot(),
                        entity.organizationUnitId(),
                        entity.organizationUnitNameSnapshot(),
                        entity.requestedAt(),
                        entity.purpose(),
                        entity.status(),
                        entity.correlationId(),
                        entity.workflowReferenceId(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static ReportParameterValueJpaEntity toEntity(ReportParameterValue model) {
            return new ReportParameterValueJpaEntity(
                        model.id(),
                        model.reportRequestId(),
                        model.parameterDefinitionId(),
                        model.parameterCode(),
                        model.valueType(),
                        model.valueText(),
                        model.valueNumber(),
                        model.valueBoolean(),
                        model.valueDate(),
                        model.valueDateTime(),
                        model.valueJson(),
                        model.createdAt()
            );
        }

        public static ReportParameterValue toDomain(ReportParameterValueJpaEntity entity) {
            return new ReportParameterValue(
                        entity.id(),
                        entity.reportRequestId(),
                        entity.parameterDefinitionId(),
                        entity.parameterCode(),
                        entity.valueType(),
                        entity.valueText(),
                        entity.valueNumber(),
                        entity.valueBoolean(),
                        entity.valueDate(),
                        entity.valueDateTime(),
                        entity.valueJson(),
                        entity.createdAt()
            );
        }

        public static ReportDataSourceBindingJpaEntity toEntity(ReportDataSourceBinding model) {
            return new ReportDataSourceBindingJpaEntity(
                        model.id(),
                        model.reportDefinitionId(),
                        model.sourceModule(),
                        model.sourceType(),
                        model.sourceName(),
                        model.sourceContractVersion(),
                        model.required(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static ReportDataSourceBinding toDomain(ReportDataSourceBindingJpaEntity entity) {
            return new ReportDataSourceBinding(
                        entity.id(),
                        entity.reportDefinitionId(),
                        entity.sourceModule(),
                        entity.sourceType(),
                        entity.sourceName(),
                        entity.sourceContractVersion(),
                        entity.required(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static ReportInputSnapshotJpaEntity toEntity(ReportInputSnapshot model) {
            return new ReportInputSnapshotJpaEntity(
                        model.id(),
                        model.reportRunId(),
                        model.snapshotType(),
                        model.sourceModule(),
                        model.sourceReferenceId(),
                        model.sourceReferenceCode(),
                        model.sourceReferenceLabel(),
                        model.sourceVersion(),
                        model.snapshotHash(),
                        model.capturedAt(),
                        model.metadataJson()
            );
        }

        public static ReportInputSnapshot toDomain(ReportInputSnapshotJpaEntity entity) {
            return new ReportInputSnapshot(
                        entity.id(),
                        entity.reportRunId(),
                        entity.snapshotType(),
                        entity.sourceModule(),
                        entity.sourceReferenceId(),
                        entity.sourceReferenceCode(),
                        entity.sourceReferenceLabel(),
                        entity.sourceVersion(),
                        entity.snapshotHash(),
                        entity.capturedAt(),
                        entity.metadataJson()
            );
        }

        public static ReportRunJpaEntity toEntity(ReportRun model) {
            return new ReportRunJpaEntity(
                        model.id(),
                        model.reportRequestId(),
                        model.reportDefinitionId(),
                        model.templateVersionId(),
                        model.status(),
                        model.runMode(),
                        model.queuedAt(),
                        model.startedAt(),
                        model.completedAt(),
                        model.failedAt(),
                        model.failureReason(),
                        model.recordCount(),
                        model.outputCount(),
                        model.executionDurationMs(),
                        model.correlationId(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static ReportRun toDomain(ReportRunJpaEntity entity) {
            return new ReportRun(
                        entity.id(),
                        entity.reportRequestId(),
                        entity.reportDefinitionId(),
                        entity.templateVersionId(),
                        entity.status(),
                        entity.runMode(),
                        entity.queuedAt(),
                        entity.startedAt(),
                        entity.completedAt(),
                        entity.failedAt(),
                        entity.failureReason(),
                        entity.recordCount(),
                        entity.outputCount(),
                        entity.executionDurationMs(),
                        entity.correlationId(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static ReportSectionDefinitionJpaEntity toEntity(ReportSectionDefinition model) {
            return new ReportSectionDefinitionJpaEntity(
                        model.id(),
                        model.reportDefinitionId(),
                        model.code(),
                        model.titleAr(),
                        model.titleFr(),
                        model.titleEn(),
                        model.sectionType(),
                        model.sortOrder(),
                        model.visibleByDefault(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static ReportSectionDefinition toDomain(ReportSectionDefinitionJpaEntity entity) {
            return new ReportSectionDefinition(
                        entity.id(),
                        entity.reportDefinitionId(),
                        entity.code(),
                        entity.titleAr(),
                        entity.titleFr(),
                        entity.titleEn(),
                        entity.sectionType(),
                        entity.sortOrder(),
                        entity.visibleByDefault(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static ReportSectionResultJpaEntity toEntity(ReportSectionResult model) {
            return new ReportSectionResultJpaEntity(
                        model.id(),
                        model.reportRunId(),
                        model.sectionDefinitionId(),
                        model.sectionCode(),
                        model.status(),
                        model.resultReference(),
                        model.rowCount(),
                        model.warningCount(),
                        model.createdAt()
            );
        }

        public static ReportSectionResult toDomain(ReportSectionResultJpaEntity entity) {
            return new ReportSectionResult(
                        entity.id(),
                        entity.reportRunId(),
                        entity.sectionDefinitionId(),
                        entity.sectionCode(),
                        entity.status(),
                        entity.resultReference(),
                        entity.rowCount(),
                        entity.warningCount(),
                        entity.createdAt()
            );
        }

        public static ReportTableResultJpaEntity toEntity(ReportTableResult model) {
            return new ReportTableResultJpaEntity(
                        model.id(),
                        model.reportSectionResultId(),
                        model.columnSchemaJson(),
                        model.rowCount(),
                        model.contentReference(),
                        model.checksum(),
                        model.createdAt()
            );
        }

        public static ReportTableResult toDomain(ReportTableResultJpaEntity entity) {
            return new ReportTableResult(
                        entity.id(),
                        entity.reportSectionResultId(),
                        entity.columnSchemaJson(),
                        entity.rowCount(),
                        entity.contentReference(),
                        entity.checksum(),
                        entity.createdAt()
            );
        }

        public static ReportChartResultJpaEntity toEntity(ReportChartResult model) {
            return new ReportChartResultJpaEntity(
                        model.id(),
                        model.reportSectionResultId(),
                        model.chartType(),
                        model.seriesSchemaJson(),
                        model.imageReference(),
                        model.interactiveSpecReference(),
                        model.createdAt()
            );
        }

        public static ReportChartResult toDomain(ReportChartResultJpaEntity entity) {
            return new ReportChartResult(
                        entity.id(),
                        entity.reportSectionResultId(),
                        entity.chartType(),
                        entity.seriesSchemaJson(),
                        entity.imageReference(),
                        entity.interactiveSpecReference(),
                        entity.createdAt()
            );
        }

        public static ReportOutputArtifactJpaEntity toEntity(ReportOutputArtifact model) {
            return new ReportOutputArtifactJpaEntity(
                        model.id(),
                        model.reportRunId(),
                        model.artifactType(),
                        model.format(),
                        model.fileName(),
                        model.mimeType(),
                        model.storageObjectReferenceId(),
                        model.documentReferenceId(),
                        model.checksum(),
                        model.sizeBytes(),
                        model.generatedAt(),
                        model.expiresAt(),
                        model.createdAt()
            );
        }

        public static ReportOutputArtifact toDomain(ReportOutputArtifactJpaEntity entity) {
            return new ReportOutputArtifact(
                        entity.id(),
                        entity.reportRunId(),
                        entity.artifactType(),
                        entity.format(),
                        entity.fileName(),
                        entity.mimeType(),
                        entity.storageObjectReferenceId(),
                        entity.documentReferenceId(),
                        entity.checksum(),
                        entity.sizeBytes(),
                        entity.generatedAt(),
                        entity.expiresAt(),
                        entity.createdAt()
            );
        }

        public static ReportScheduleJpaEntity toEntity(ReportSchedule model) {
            return new ReportScheduleJpaEntity(
                        model.id(),
                        model.reportDefinitionId(),
                        model.code(),
                        model.nameAr(),
                        model.nameFr(),
                        model.nameEn(),
                        model.cronExpression(),
                        model.timezone(),
                        model.active(),
                        model.nextRunAt(),
                        model.lastRunAt(),
                        model.createdByActorId(),
                        model.createdByDisplayNameSnapshot(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static ReportSchedule toDomain(ReportScheduleJpaEntity entity) {
            return new ReportSchedule(
                        entity.id(),
                        entity.reportDefinitionId(),
                        entity.code(),
                        entity.nameAr(),
                        entity.nameFr(),
                        entity.nameEn(),
                        entity.cronExpression(),
                        entity.timezone(),
                        entity.active(),
                        entity.nextRunAt(),
                        entity.lastRunAt(),
                        entity.createdByActorId(),
                        entity.createdByDisplayNameSnapshot(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static ReportScheduleParameterJpaEntity toEntity(ReportScheduleParameter model) {
            return new ReportScheduleParameterJpaEntity(
                        model.id(),
                        model.reportScheduleId(),
                        model.parameterDefinitionId(),
                        model.parameterCode(),
                        model.valueType(),
                        model.valueJson(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static ReportScheduleParameter toDomain(ReportScheduleParameterJpaEntity entity) {
            return new ReportScheduleParameter(
                        entity.id(),
                        entity.reportScheduleId(),
                        entity.parameterDefinitionId(),
                        entity.parameterCode(),
                        entity.valueType(),
                        entity.valueJson(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static ReportPublicationJpaEntity toEntity(ReportPublication model) {
            return new ReportPublicationJpaEntity(
                        model.id(),
                        model.reportRunId(),
                        model.publicationStatus(),
                        model.publishedByActorId(),
                        model.publishedByDisplayNameSnapshot(),
                        model.publishedAt(),
                        model.publicationNote(),
                        model.workflowReferenceId(),
                        model.auditReferenceId(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static ReportPublication toDomain(ReportPublicationJpaEntity entity) {
            return new ReportPublication(
                        entity.id(),
                        entity.reportRunId(),
                        entity.publicationStatus(),
                        entity.publishedByActorId(),
                        entity.publishedByDisplayNameSnapshot(),
                        entity.publishedAt(),
                        entity.publicationNote(),
                        entity.workflowReferenceId(),
                        entity.auditReferenceId(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static ReportDistributionTargetJpaEntity toEntity(ReportDistributionTarget model) {
            return new ReportDistributionTargetJpaEntity(
                        model.id(),
                        model.reportDefinitionId(),
                        model.targetType(),
                        model.targetReference(),
                        model.channelId(),
                        model.active(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static ReportDistributionTarget toDomain(ReportDistributionTargetJpaEntity entity) {
            return new ReportDistributionTarget(
                        entity.id(),
                        entity.reportDefinitionId(),
                        entity.targetType(),
                        entity.targetReference(),
                        entity.channelId(),
                        entity.active(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static ReportDistributionRecordJpaEntity toEntity(ReportDistributionRecord model) {
            return new ReportDistributionRecordJpaEntity(
                        model.id(),
                        model.reportPublicationId(),
                        model.reportOutputArtifactId(),
                        model.targetType(),
                        model.targetReference(),
                        model.notificationRequestId(),
                        model.integrationOutboundRecordId(),
                        model.status(),
                        model.requestedAt(),
                        model.completedAt(),
                        model.failureReason(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static ReportDistributionRecord toDomain(ReportDistributionRecordJpaEntity entity) {
            return new ReportDistributionRecord(
                        entity.id(),
                        entity.reportPublicationId(),
                        entity.reportOutputArtifactId(),
                        entity.targetType(),
                        entity.targetReference(),
                        entity.notificationRequestId(),
                        entity.integrationOutboundRecordId(),
                        entity.status(),
                        entity.requestedAt(),
                        entity.completedAt(),
                        entity.failureReason(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static ReportAccessPolicyJpaEntity toEntity(ReportAccessPolicy model) {
            return new ReportAccessPolicyJpaEntity(
                        model.id(),
                        model.reportDefinitionId(),
                        model.scopeType(),
                        model.scopeReferenceId(),
                        model.permissionCode(),
                        model.restricted(),
                        model.maskSensitiveValues(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static ReportAccessPolicy toDomain(ReportAccessPolicyJpaEntity entity) {
            return new ReportAccessPolicy(
                        entity.id(),
                        entity.reportDefinitionId(),
                        entity.scopeType(),
                        entity.scopeReferenceId(),
                        entity.permissionCode(),
                        entity.restricted(),
                        entity.maskSensitiveValues(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static ReportCatalogEntryJpaEntity toEntity(ReportCatalogEntry model) {
            return new ReportCatalogEntryJpaEntity(
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

        public static ReportCatalogEntry toDomain(ReportCatalogEntryJpaEntity entity) {
            return new ReportCatalogEntry(
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

        public static ReportCatalogTranslationJpaEntity toEntity(ReportCatalogTranslation model) {
            return new ReportCatalogTranslationJpaEntity(
                        model.id(),
                        model.catalogEntryId(),
                        model.locale(),
                        model.name(),
                        model.description(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static ReportCatalogTranslation toDomain(ReportCatalogTranslationJpaEntity entity) {
            return new ReportCatalogTranslation(
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
