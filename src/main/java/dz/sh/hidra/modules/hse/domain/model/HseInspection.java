/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseInspection
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.domain.model
 *
 * @Description : HSE inspection.
 *
 */
package dz.sh.hidra.modules.hse.domain.model;

import dz.sh.hidra.modules.hse.domain.value.*;
import java.time.Instant;

    /**
     * HSE inspection.
     *
         * @param id id
     * @param inspectionNumber inspectionNumber
     * @param inspectionTypeId inspectionTypeId
     * @param title title
     * @param targetModule targetModule
     * @param targetTypeCode targetTypeCode
     * @param targetId targetId
     * @param inspectorActorId inspectorActorId
     * @param plannedAt plannedAt
     * @param startedAt startedAt
     * @param completedAt completedAt
     * @param status status
     * @param findingSummary findingSummary
     * @param linkedHseCaseId linkedHseCaseId
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record HseInspection(
            String id,
        String inspectionNumber,
        String inspectionTypeId,
        String title,
        String targetModule,
        String targetTypeCode,
        String targetId,
        String inspectorActorId,
        Instant plannedAt,
        Instant startedAt,
        Instant completedAt,
        InspectionStatus status,
        String findingSummary,
        String linkedHseCaseId,
        Instant createdAt,
        Instant updatedAt
    ) {

        public HseInspection {
        id = normalize(id);
        inspectionNumber = normalize(inspectionNumber);
        inspectionTypeId = normalize(inspectionTypeId);
        title = normalize(title);
        targetModule = normalize(targetModule);
        targetTypeCode = normalize(targetTypeCode);
        targetId = normalize(targetId);
        inspectorActorId = normalize(inspectorActorId);
        findingSummary = normalize(findingSummary);
        linkedHseCaseId = normalize(linkedHseCaseId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
