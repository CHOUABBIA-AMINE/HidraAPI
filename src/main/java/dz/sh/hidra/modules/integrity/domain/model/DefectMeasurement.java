/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DefectMeasurement
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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
