/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseClosure
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.hse.domain.exception.InvalidHseValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidHseValueException("HseClosure id must not be blank.");
        }
        // HRA-051 required: hseCaseId
        if (hseCaseId == null || hseCaseId.isBlank()) {
            throw new InvalidHseValueException("HseClosure hse case id must not be blank.");
        }
        // HRA-051 required: closedByActorId
        if (closedByActorId == null || closedByActorId.isBlank()) {
            throw new InvalidHseValueException("HseClosure closed by actor id must not be blank.");
        }
        // HRA-051 required: closedAt
        if (closedAt == null) {
            throw new InvalidHseValueException("HseClosure closed at must not be null.");
        }

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
