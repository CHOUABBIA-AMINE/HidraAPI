/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PermitToWork
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.domain.model
 *
 * @Description : Permit to work.
 *
 */
package dz.sh.hidra.modules.hse.domain.model;

import dz.sh.hidra.modules.hse.domain.value.*;
import java.time.Instant;

    /**
     * Permit to work.
     *
         * @param id id
     * @param permitNumber permitNumber
     * @param permitTypeId permitTypeId
     * @param title title
     * @param description description
     * @param targetModule targetModule
     * @param targetTypeCode targetTypeCode
     * @param targetId targetId
     * @param requestedByActorId requestedByActorId
     * @param approvedByActorId approvedByActorId
     * @param validFrom validFrom
     * @param validTo validTo
     * @param status status
     * @param workflowInstanceId workflowInstanceId
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record PermitToWork(
            String id,
        String permitNumber,
        String permitTypeId,
        String title,
        String description,
        String targetModule,
        String targetTypeCode,
        String targetId,
        String requestedByActorId,
        String approvedByActorId,
        Instant validFrom,
        Instant validTo,
        PermitStatus status,
        String workflowInstanceId,
        Instant createdAt,
        Instant updatedAt
    ) {

        public PermitToWork {
        id = normalize(id);
        permitNumber = normalize(permitNumber);
        permitTypeId = normalize(permitTypeId);
        title = normalize(title);
        description = normalize(description);
        targetModule = normalize(targetModule);
        targetTypeCode = normalize(targetTypeCode);
        targetId = normalize(targetId);
        requestedByActorId = normalize(requestedByActorId);
        approvedByActorId = normalize(approvedByActorId);
        workflowInstanceId = normalize(workflowInstanceId);
        }
        public boolean activePermit() {
            return status == PermitStatus.ACTIVE;
        }
        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
