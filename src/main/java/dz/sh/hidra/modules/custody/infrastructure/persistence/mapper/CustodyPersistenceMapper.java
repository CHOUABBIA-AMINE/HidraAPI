/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyPersistenceMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
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
}
