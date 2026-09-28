/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseInspection
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.hse.domain.exception.InvalidHseValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidHseValueException("HseInspection id must not be blank.");
        }
        // HRA-051 required: inspectionNumber
        if (inspectionNumber == null || inspectionNumber.isBlank()) {
            throw new InvalidHseValueException("HseInspection inspection number must not be blank.");
        }
        // HRA-051 required: inspectionTypeId
        if (inspectionTypeId == null || inspectionTypeId.isBlank()) {
            throw new InvalidHseValueException("HseInspection inspection type id must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidHseValueException("HseInspection status must not be null.");
        }

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
