/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyMeasurementPeriod
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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
