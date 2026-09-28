/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyMeasurementPeriod
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.domain.model
 *
 * @Description : Fiscal/official measurement period.
 *
 */
package dz.sh.hidra.modules.custody.domain.model;

import dz.sh.hidra.modules.custody.domain.exception.InvalidCustodyValueException;
import dz.sh.hidra.modules.custody.domain.value.*;
import java.time.Instant;

    /**
     * Fiscal/official measurement period.
     *
         * @param id id
     * @param periodCode periodCode
     * @param agreementId agreementId
     * @param transferPointId transferPointId
     * @param periodStart periodStart
     * @param periodEnd periodEnd
     * @param status status
     * @param lockedByActorId lockedByActorId
     * @param lockedAt lockedAt
     * @param approvedByActorId approvedByActorId
     * @param approvedAt approvedAt
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record CustodyMeasurementPeriod(
            String id,
        String periodCode,
        String agreementId,
        String transferPointId,
        Instant periodStart,
        Instant periodEnd,
        CustodyPeriodStatus status,
        String lockedByActorId,
        Instant lockedAt,
        String approvedByActorId,
        Instant approvedAt,
        Instant createdAt,
        Instant updatedAt
    ) {

        public CustodyMeasurementPeriod {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidCustodyValueException("CustodyMeasurementPeriod id must not be blank.");
        }
        // HRA-051 required: periodCode
        if (periodCode == null || periodCode.isBlank()) {
            throw new InvalidCustodyValueException("CustodyMeasurementPeriod period code must not be blank.");
        }
        // HRA-051 required: agreementId
        if (agreementId == null || agreementId.isBlank()) {
            throw new InvalidCustodyValueException("CustodyMeasurementPeriod agreement id must not be blank.");
        }
        // HRA-051 required: transferPointId
        if (transferPointId == null || transferPointId.isBlank()) {
            throw new InvalidCustodyValueException("CustodyMeasurementPeriod transfer point id must not be blank.");
        }
        // HRA-051 required: periodStart
        if (periodStart == null) {
            throw new InvalidCustodyValueException("CustodyMeasurementPeriod period start must not be null.");
        }
        // HRA-051 required: periodEnd
        if (periodEnd == null) {
            throw new InvalidCustodyValueException("CustodyMeasurementPeriod period end must not be null.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidCustodyValueException("CustodyMeasurementPeriod status must not be null.");
        }
        // HRA-051 order: periodStart <= periodEnd
        if (periodStart != null && periodEnd != null && periodEnd.isBefore(periodStart)) {
            throw new InvalidCustodyValueException("CustodyMeasurementPeriod period end must not be before period start.");
        }

        id = normalize(id);
        periodCode = normalize(periodCode);
        agreementId = normalize(agreementId);
        transferPointId = normalize(transferPointId);
        lockedByActorId = normalize(lockedByActorId);
        approvedByActorId = normalize(approvedByActorId);
        }
        public boolean closedLifecycle() {
            return status == CustodyPeriodStatus.CLOSED
                    || status == CustodyPeriodStatus.CANCELLED;
        }
        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
