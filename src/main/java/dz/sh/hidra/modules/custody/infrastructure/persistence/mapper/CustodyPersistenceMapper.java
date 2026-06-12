/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyPersistenceMapper
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence.mapper
 *
 * @Description : Maps custody domain models to JPA entities.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.persistence.mapper;

import dz.sh.hidra.modules.custody.domain.model.*;
import dz.sh.hidra.modules.custody.infrastructure.persistence.entity.*;

/**
 * Maps custody domain models to JPA entities.
 */
public final class CustodyPersistenceMapper {

    private CustodyPersistenceMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }


        public static CustodyTransferPointJpaEntity toEntity(CustodyTransferPoint model) {
            return new CustodyTransferPointJpaEntity(
                        model.id(),
                        model.code(),
                        model.nameAr(),
                        model.nameFr(),
                        model.nameEn(),
                        model.topologyAssetTypeCode(),
                        model.topologyAssetId(),
                        model.topologyAssetCodeSnapshot(),
                        model.topologyAssetNameSnapshot(),
                        model.direction(),
                        model.productTypeId(),
                        model.measurementLocationId(),
                        model.status(),
                        model.effectiveFrom(),
                        model.effectiveTo(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static CustodyTransferPoint toDomain(CustodyTransferPointJpaEntity entity) {
            return new CustodyTransferPoint(
                        entity.id(),
                        entity.code(),
                        entity.nameAr(),
                        entity.nameFr(),
                        entity.nameEn(),
                        entity.topologyAssetTypeCode(),
                        entity.topologyAssetId(),
                        entity.topologyAssetCodeSnapshot(),
                        entity.topologyAssetNameSnapshot(),
                        entity.direction(),
                        entity.productTypeId(),
                        entity.measurementLocationId(),
                        entity.status(),
                        entity.effectiveFrom(),
                        entity.effectiveTo(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static CustodyAgreementJpaEntity toEntity(CustodyAgreement model) {
            return new CustodyAgreementJpaEntity(
                        model.id(),
                        model.agreementNumber(),
                        model.agreementTypeId(),
                        model.title(),
                        model.description(),
                        model.transferPointId(),
                        model.status(),
                        model.validFrom(),
                        model.validTo(),
                        model.termsSnapshotJson(),
                        model.documentReferenceId(),
                        model.createdByActorId(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static CustodyAgreement toDomain(CustodyAgreementJpaEntity entity) {
            return new CustodyAgreement(
                        entity.id(),
                        entity.agreementNumber(),
                        entity.agreementTypeId(),
                        entity.title(),
                        entity.description(),
                        entity.transferPointId(),
                        entity.status(),
                        entity.validFrom(),
                        entity.validTo(),
                        entity.termsSnapshotJson(),
                        entity.documentReferenceId(),
                        entity.createdByActorId(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static CustodyAgreementPartyJpaEntity toEntity(CustodyAgreementParty model) {
            return new CustodyAgreementPartyJpaEntity(
                        model.id(),
                        model.agreementId(),
                        model.partyRoleId(),
                        model.partyId(),
                        model.partyCodeSnapshot(),
                        model.partyNameSnapshot(),
                        model.partyRoleCodeSnapshot(),
                        model.ownershipSharePercent(),
                        model.effectiveFrom(),
                        model.effectiveTo(),
                        model.createdAt()
            );
        }

        public static CustodyAgreementParty toDomain(CustodyAgreementPartyJpaEntity entity) {
            return new CustodyAgreementParty(
                        entity.id(),
                        entity.agreementId(),
                        entity.partyRoleId(),
                        entity.partyId(),
                        entity.partyCodeSnapshot(),
                        entity.partyNameSnapshot(),
                        entity.partyRoleCodeSnapshot(),
                        entity.ownershipSharePercent(),
                        entity.effectiveFrom(),
                        entity.effectiveTo(),
                        entity.createdAt()
            );
        }

        public static CustodyMeasurementPeriodJpaEntity toEntity(CustodyMeasurementPeriod model) {
            return new CustodyMeasurementPeriodJpaEntity(
                        model.id(),
                        model.periodCode(),
                        model.agreementId(),
                        model.transferPointId(),
                        model.periodStart(),
                        model.periodEnd(),
                        model.status(),
                        model.lockedByActorId(),
                        model.lockedAt(),
                        model.approvedByActorId(),
                        model.approvedAt(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static CustodyMeasurementPeriod toDomain(CustodyMeasurementPeriodJpaEntity entity) {
            return new CustodyMeasurementPeriod(
                        entity.id(),
                        entity.periodCode(),
                        entity.agreementId(),
                        entity.transferPointId(),
                        entity.periodStart(),
                        entity.periodEnd(),
                        entity.status(),
                        entity.lockedByActorId(),
                        entity.lockedAt(),
                        entity.approvedByActorId(),
                        entity.approvedAt(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static CustodyBatchJpaEntity toEntity(CustodyBatch model) {
            return new CustodyBatchJpaEntity(
                        model.id(),
                        model.batchNumber(),
                        model.measurementPeriodId(),
                        model.agreementId(),
                        model.productTypeId(),
                        model.status(),
                        model.batchStart(),
                        model.batchEnd(),
                        model.expectedQuantity(),
                        model.expectedQuantityUnitId(),
                        model.sourcePlanTargetId(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static CustodyBatch toDomain(CustodyBatchJpaEntity entity) {
            return new CustodyBatch(
                        entity.id(),
                        entity.batchNumber(),
                        entity.measurementPeriodId(),
                        entity.agreementId(),
                        entity.productTypeId(),
                        entity.status(),
                        entity.batchStart(),
                        entity.batchEnd(),
                        entity.expectedQuantity(),
                        entity.expectedQuantityUnitId(),
                        entity.sourcePlanTargetId(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static CustodyMeteringSystemJpaEntity toEntity(CustodyMeteringSystem model) {
            return new CustodyMeteringSystemJpaEntity(
                        model.id(),
                        model.meteringSystemCode(),
                        model.name(),
                        model.transferPointId(),
                        model.topologyAssetTypeCode(),
                        model.topologyAssetId(),
                        model.measurementStandardId(),
                        model.calibrationCertificateId(),
                        model.active(),
                        model.effectiveFrom(),
                        model.effectiveTo(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static CustodyMeteringSystem toDomain(CustodyMeteringSystemJpaEntity entity) {
            return new CustodyMeteringSystem(
                        entity.id(),
                        entity.meteringSystemCode(),
                        entity.name(),
                        entity.transferPointId(),
                        entity.topologyAssetTypeCode(),
                        entity.topologyAssetId(),
                        entity.measurementStandardId(),
                        entity.calibrationCertificateId(),
                        entity.active(),
                        entity.effectiveFrom(),
                        entity.effectiveTo(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static CustodyMeterRunSnapshotJpaEntity toEntity(CustodyMeterRunSnapshot model) {
            return new CustodyMeterRunSnapshotJpaEntity(
                        model.id(),
                        model.measurementPeriodId(),
                        model.meteringSystemId(),
                        model.meterRunCodeSnapshot(),
                        model.meterSerialSnapshot(),
                        model.calibrationCertificateSnapshot(),
                        model.configurationSnapshotJson(),
                        model.snapshotAt(),
                        model.snapshotByActorId(),
                        model.createdAt()
            );
        }

        public static CustodyMeterRunSnapshot toDomain(CustodyMeterRunSnapshotJpaEntity entity) {
            return new CustodyMeterRunSnapshot(
                        entity.id(),
                        entity.measurementPeriodId(),
                        entity.meteringSystemId(),
                        entity.meterRunCodeSnapshot(),
                        entity.meterSerialSnapshot(),
                        entity.calibrationCertificateSnapshot(),
                        entity.configurationSnapshotJson(),
                        entity.snapshotAt(),
                        entity.snapshotByActorId(),
                        entity.createdAt()
            );
        }

        public static CustodyMeasurementSnapshotJpaEntity toEntity(CustodyMeasurementSnapshot model) {
            return new CustodyMeasurementSnapshotJpaEntity(
                        model.id(),
                        model.measurementPeriodId(),
                        model.batchId(),
                        model.meterRunSnapshotId(),
                        model.telemetryReadingReferenceId(),
                        model.telemetryPointReferenceId(),
                        model.measurementTypeId(),
                        model.observedValue(),
                        model.observedUnitId(),
                        model.standardValue(),
                        model.standardUnitId(),
                        model.measuredAt(),
                        model.acceptedForCustody(),
                        model.qualityFlagSnapshot(),
                        model.createdAt()
            );
        }

        public static CustodyMeasurementSnapshot toDomain(CustodyMeasurementSnapshotJpaEntity entity) {
            return new CustodyMeasurementSnapshot(
                        entity.id(),
                        entity.measurementPeriodId(),
                        entity.batchId(),
                        entity.meterRunSnapshotId(),
                        entity.telemetryReadingReferenceId(),
                        entity.telemetryPointReferenceId(),
                        entity.measurementTypeId(),
                        entity.observedValue(),
                        entity.observedUnitId(),
                        entity.standardValue(),
                        entity.standardUnitId(),
                        entity.measuredAt(),
                        entity.acceptedForCustody(),
                        entity.qualityFlagSnapshot(),
                        entity.createdAt()
            );
        }

        public static CustodyQualitySampleJpaEntity toEntity(CustodyQualitySample model) {
            return new CustodyQualitySampleJpaEntity(
                        model.id(),
                        model.sampleNumber(),
                        model.measurementPeriodId(),
                        model.batchId(),
                        model.sampleTypeId(),
                        model.productTypeId(),
                        model.sampledAt(),
                        model.sampledByActorId(),
                        model.laboratoryPartyId(),
                        model.laboratoryNameSnapshot(),
                        model.resultSummary(),
                        model.certificateId(),
                        model.createdAt()
            );
        }

        public static CustodyQualitySample toDomain(CustodyQualitySampleJpaEntity entity) {
            return new CustodyQualitySample(
                        entity.id(),
                        entity.sampleNumber(),
                        entity.measurementPeriodId(),
                        entity.batchId(),
                        entity.sampleTypeId(),
                        entity.productTypeId(),
                        entity.sampledAt(),
                        entity.sampledByActorId(),
                        entity.laboratoryPartyId(),
                        entity.laboratoryNameSnapshot(),
                        entity.resultSummary(),
                        entity.certificateId(),
                        entity.createdAt()
            );
        }

        public static CustodyQualityCertificateJpaEntity toEntity(CustodyQualityCertificate model) {
            return new CustodyQualityCertificateJpaEntity(
                        model.id(),
                        model.certificateNumber(),
                        model.qualitySampleId(),
                        model.documentReferenceId(),
                        model.issuedByPartyId(),
                        model.issuedByNameSnapshot(),
                        model.issuedAt(),
                        model.certificateSummaryJson(),
                        model.status(),
                        model.createdAt()
            );
        }

        public static CustodyQualityCertificate toDomain(CustodyQualityCertificateJpaEntity entity) {
            return new CustodyQualityCertificate(
                        entity.id(),
                        entity.certificateNumber(),
                        entity.qualitySampleId(),
                        entity.documentReferenceId(),
                        entity.issuedByPartyId(),
                        entity.issuedByNameSnapshot(),
                        entity.issuedAt(),
                        entity.certificateSummaryJson(),
                        entity.status(),
                        entity.createdAt()
            );
        }

        public static CustodyQuantityCalculationJpaEntity toEntity(CustodyQuantityCalculation model) {
            return new CustodyQuantityCalculationJpaEntity(
                        model.id(),
                        model.calculationNumber(),
                        model.measurementPeriodId(),
                        model.batchId(),
                        model.quantityBasis(),
                        model.grossObservedQuantity(),
                        model.grossStandardQuantity(),
                        model.netStandardQuantity(),
                        model.massQuantity(),
                        model.quantityUnitId(),
                        model.calculationMethodId(),
                        model.calculationDetailsJson(),
                        model.calculatedByActorId(),
                        model.calculatedAt(),
                        model.official(),
                        model.createdAt()
            );
        }

        public static CustodyQuantityCalculation toDomain(CustodyQuantityCalculationJpaEntity entity) {
            return new CustodyQuantityCalculation(
                        entity.id(),
                        entity.calculationNumber(),
                        entity.measurementPeriodId(),
                        entity.batchId(),
                        entity.quantityBasis(),
                        entity.grossObservedQuantity(),
                        entity.grossStandardQuantity(),
                        entity.netStandardQuantity(),
                        entity.massQuantity(),
                        entity.quantityUnitId(),
                        entity.calculationMethodId(),
                        entity.calculationDetailsJson(),
                        entity.calculatedByActorId(),
                        entity.calculatedAt(),
                        entity.official(),
                        entity.createdAt()
            );
        }

        public static CustodyCorrectionFactorJpaEntity toEntity(CustodyCorrectionFactor model) {
            return new CustodyCorrectionFactorJpaEntity(
                        model.id(),
                        model.quantityCalculationId(),
                        model.factorTypeId(),
                        model.factorCode(),
                        model.factorValue(),
                        model.basisDescription(),
                        model.sourceReferenceId(),
                        model.appliedAt(),
                        model.createdAt()
            );
        }

        public static CustodyCorrectionFactor toDomain(CustodyCorrectionFactorJpaEntity entity) {
            return new CustodyCorrectionFactor(
                        entity.id(),
                        entity.quantityCalculationId(),
                        entity.factorTypeId(),
                        entity.factorCode(),
                        entity.factorValue(),
                        entity.basisDescription(),
                        entity.sourceReferenceId(),
                        entity.appliedAt(),
                        entity.createdAt()
            );
        }

        public static CustodyTransferTicketJpaEntity toEntity(CustodyTransferTicket model) {
            return new CustodyTransferTicketJpaEntity(
                        model.id(),
                        model.ticketNumber(),
                        model.measurementPeriodId(),
                        model.agreementId(),
                        model.transferPointId(),
                        model.batchId(),
                        model.quantityCalculationId(),
                        model.status(),
                        model.ticketDate(),
                        model.issuedByActorId(),
                        model.approvedByActorId(),
                        model.approvedAt(),
                        model.workflowInstanceId(),
                        model.auditReferenceId(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static CustodyTransferTicket toDomain(CustodyTransferTicketJpaEntity entity) {
            return new CustodyTransferTicket(
                        entity.id(),
                        entity.ticketNumber(),
                        entity.measurementPeriodId(),
                        entity.agreementId(),
                        entity.transferPointId(),
                        entity.batchId(),
                        entity.quantityCalculationId(),
                        entity.status(),
                        entity.ticketDate(),
                        entity.issuedByActorId(),
                        entity.approvedByActorId(),
                        entity.approvedAt(),
                        entity.workflowInstanceId(),
                        entity.auditReferenceId(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static CustodyTicketLineJpaEntity toEntity(CustodyTicketLine model) {
            return new CustodyTicketLineJpaEntity(
                        model.id(),
                        model.transferTicketId(),
                        model.lineNumber(),
                        model.lineTypeId(),
                        model.productTypeId(),
                        model.quantity(),
                        model.quantityUnitId(),
                        model.qualityValue(),
                        model.qualityUnitId(),
                        model.description(),
                        model.createdAt()
            );
        }

        public static CustodyTicketLine toDomain(CustodyTicketLineJpaEntity entity) {
            return new CustodyTicketLine(
                        entity.id(),
                        entity.transferTicketId(),
                        entity.lineNumber(),
                        entity.lineTypeId(),
                        entity.productTypeId(),
                        entity.quantity(),
                        entity.quantityUnitId(),
                        entity.qualityValue(),
                        entity.qualityUnitId(),
                        entity.description(),
                        entity.createdAt()
            );
        }

        public static CustodyReconciliationJpaEntity toEntity(CustodyReconciliation model) {
            return new CustodyReconciliationJpaEntity(
                        model.id(),
                        model.reconciliationNumber(),
                        model.measurementPeriodId(),
                        model.agreementId(),
                        model.status(),
                        model.totalTicketQuantity(),
                        model.totalMeasuredQuantity(),
                        model.differenceQuantity(),
                        model.quantityUnitId(),
                        model.differencePercent(),
                        model.reconciledByActorId(),
                        model.reconciledAt(),
                        model.approvedByActorId(),
                        model.approvedAt(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static CustodyReconciliation toDomain(CustodyReconciliationJpaEntity entity) {
            return new CustodyReconciliation(
                        entity.id(),
                        entity.reconciliationNumber(),
                        entity.measurementPeriodId(),
                        entity.agreementId(),
                        entity.status(),
                        entity.totalTicketQuantity(),
                        entity.totalMeasuredQuantity(),
                        entity.differenceQuantity(),
                        entity.quantityUnitId(),
                        entity.differencePercent(),
                        entity.reconciledByActorId(),
                        entity.reconciledAt(),
                        entity.approvedByActorId(),
                        entity.approvedAt(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static CustodyDiscrepancyJpaEntity toEntity(CustodyDiscrepancy model) {
            return new CustodyDiscrepancyJpaEntity(
                        model.id(),
                        model.discrepancyNumber(),
                        model.reconciliationId(),
                        model.discrepancyTypeId(),
                        model.status(),
                        model.differenceQuantity(),
                        model.quantityUnitId(),
                        model.description(),
                        model.rootCauseText(),
                        model.resolutionText(),
                        model.assignedActorId(),
                        model.openedAt(),
                        model.resolvedAt(),
                        model.closedAt(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static CustodyDiscrepancy toDomain(CustodyDiscrepancyJpaEntity entity) {
            return new CustodyDiscrepancy(
                        entity.id(),
                        entity.discrepancyNumber(),
                        entity.reconciliationId(),
                        entity.discrepancyTypeId(),
                        entity.status(),
                        entity.differenceQuantity(),
                        entity.quantityUnitId(),
                        entity.description(),
                        entity.rootCauseText(),
                        entity.resolutionText(),
                        entity.assignedActorId(),
                        entity.openedAt(),
                        entity.resolvedAt(),
                        entity.closedAt(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static CustodyApprovalReferenceJpaEntity toEntity(CustodyApprovalReference model) {
            return new CustodyApprovalReferenceJpaEntity(
                        model.id(),
                        model.targetType(),
                        model.targetId(),
                        model.workflowInstanceId(),
                        model.workflowTaskId(),
                        model.approvedByActorId(),
                        model.approvalStatus(),
                        model.approvalComment(),
                        model.approvedAt(),
                        model.createdAt()
            );
        }

        public static CustodyApprovalReference toDomain(CustodyApprovalReferenceJpaEntity entity) {
            return new CustodyApprovalReference(
                        entity.id(),
                        entity.targetType(),
                        entity.targetId(),
                        entity.workflowInstanceId(),
                        entity.workflowTaskId(),
                        entity.approvedByActorId(),
                        entity.approvalStatus(),
                        entity.approvalComment(),
                        entity.approvedAt(),
                        entity.createdAt()
            );
        }

        public static CustodyDocumentReferenceJpaEntity toEntity(CustodyDocumentReference model) {
            return new CustodyDocumentReferenceJpaEntity(
                        model.id(),
                        model.targetType(),
                        model.targetId(),
                        model.documentType(),
                        model.documentReferenceId(),
                        model.documentCodeSnapshot(),
                        model.documentTitleSnapshot(),
                        model.attachedAt(),
                        model.attachedByActorId()
            );
        }

        public static CustodyDocumentReference toDomain(CustodyDocumentReferenceJpaEntity entity) {
            return new CustodyDocumentReference(
                        entity.id(),
                        entity.targetType(),
                        entity.targetId(),
                        entity.documentType(),
                        entity.documentReferenceId(),
                        entity.documentCodeSnapshot(),
                        entity.documentTitleSnapshot(),
                        entity.attachedAt(),
                        entity.attachedByActorId()
            );
        }

        public static CustodyCatalogEntryJpaEntity toEntity(CustodyCatalogEntry model) {
            return new CustodyCatalogEntryJpaEntity(
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

        public static CustodyCatalogEntry toDomain(CustodyCatalogEntryJpaEntity entity) {
            return new CustodyCatalogEntry(
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

        public static CustodyCatalogTranslationJpaEntity toEntity(CustodyCatalogTranslation model) {
            return new CustodyCatalogTranslationJpaEntity(
                        model.id(),
                        model.catalogEntryId(),
                        model.locale(),
                        model.name(),
                        model.description(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static CustodyCatalogTranslation toDomain(CustodyCatalogTranslationJpaEntity entity) {
            return new CustodyCatalogTranslation(
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
