/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DefectMeasurement
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.domain.model
 *
 * @Description : Defect measurement.
 *
 */
package dz.sh.hidra.modules.integrity.domain.model;

import dz.sh.hidra.modules.integrity.domain.exception.InvalidIntegrityValueException;
import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Defect measurement.
     *
         * @param id id
     * @param defectId defectId
     * @param measurementTypeId measurementTypeId
     * @param measurementValue measurementValue
     * @param unitId unitId
     * @param measurementMethodId measurementMethodId
     * @param measuredByActorId measuredByActorId
     * @param measuredAt measuredAt
     * @param notes notes
     */
    public record DefectMeasurement(
            String id,
        String defectId,
        String measurementTypeId,
        BigDecimal measurementValue,
        String unitId,
        String measurementMethodId,
        String measuredByActorId,
        Instant measuredAt,
        String notes
    ) {

        public DefectMeasurement {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidIntegrityValueException("DefectMeasurement id must not be blank.");
        }
        // HRA-051 required: defectId
        if (defectId == null || defectId.isBlank()) {
            throw new InvalidIntegrityValueException("DefectMeasurement defect id must not be blank.");
        }
        // HRA-051 required: measurementTypeId
        if (measurementTypeId == null || measurementTypeId.isBlank()) {
            throw new InvalidIntegrityValueException("DefectMeasurement measurement type id must not be blank.");
        }
        // HRA-051 required: measurementValue
        if (measurementValue == null) {
            throw new InvalidIntegrityValueException("DefectMeasurement measurement value must not be null.");
        }
        // HRA-051 required: unitId
        if (unitId == null || unitId.isBlank()) {
            throw new InvalidIntegrityValueException("DefectMeasurement unit id must not be blank.");
        }
        // HRA-051 required: measuredAt
        if (measuredAt == null) {
            throw new InvalidIntegrityValueException("DefectMeasurement measured at must not be null.");
        }

        id = normalize(id);
        defectId = normalize(defectId);
        measurementTypeId = normalize(measurementTypeId);
        unitId = normalize(unitId);
        measurementMethodId = normalize(measurementMethodId);
        measuredByActorId = normalize(measuredByActorId);
        notes = normalize(notes);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
