/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseClosure
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.domain.model
 *
 * @Description : HSE case closure record.
 *
 */
package dz.sh.hidra.modules.hse.domain.model;

import java.time.Instant;

    /**
     * HSE case closure record.
     *
         * @param id id
     * @param hseCaseId hseCaseId
     * @param closureSummary closureSummary
     * @param impactAssessed impactAssessed
     * @param capaCompleted capaCompleted
     * @param evidenceReviewed evidenceReviewed
     * @param regulatoryReviewed regulatoryReviewed
     * @param closedByActorId closedByActorId
     * @param closedByDisplayNameSnapshot closedByDisplayNameSnapshot
     * @param closedAt closedAt
     * @param workflowInstanceId workflowInstanceId
     */
    public record HseClosure(
            String id,
        String hseCaseId,
        String closureSummary,
        boolean impactAssessed,
        boolean capaCompleted,
        boolean evidenceReviewed,
        boolean regulatoryReviewed,
        String closedByActorId,
        String closedByDisplayNameSnapshot,
        Instant closedAt,
        String workflowInstanceId
    ) {

        public HseClosure {
        id = normalize(id);
        hseCaseId = normalize(hseCaseId);
        closureSummary = normalize(closureSummary);
        closedByActorId = normalize(closedByActorId);
        closedByDisplayNameSnapshot = normalize(closedByDisplayNameSnapshot);
        workflowInstanceId = normalize(workflowInstanceId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
