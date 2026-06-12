/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportParameterDefinition
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.domain.model
 *
 * @Description : Parameter expected by a report definition.
 *
 */
package dz.sh.hidra.modules.reporting.domain.model;

import dz.sh.hidra.modules.reporting.domain.value.*;
import java.time.Instant;

    /**
     * Parameter expected by a report definition.
     *
         * @param id id
     * @param reportDefinitionId reportDefinitionId
     * @param code code
     * @param labelAr labelAr
     * @param labelFr labelFr
     * @param labelEn labelEn
     * @param parameterType parameterType
     * @param required required
     * @param defaultValue defaultValue
     * @param allowedValuesReference allowedValuesReference
     * @param validationExpression validationExpression
     * @param sortOrder sortOrder
     * @param active active
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record ReportParameterDefinition(
            String id,
        String reportDefinitionId,
        String code,
        String labelAr,
        String labelFr,
        String labelEn,
        ReportParameterType parameterType,
        boolean required,
        String defaultValue,
        String allowedValuesReference,
        String validationExpression,
        int sortOrder,
        boolean active,
        Instant createdAt,
        Instant updatedAt
    ) {

        public ReportParameterDefinition {
        id = normalize(id);
        reportDefinitionId = normalize(reportDefinitionId);
        code = normalize(code);
        labelAr = normalize(labelAr);
        labelFr = normalize(labelFr);
        labelEn = normalize(labelEn);
        defaultValue = normalize(defaultValue);
        allowedValuesReference = normalize(allowedValuesReference);
        validationExpression = normalize(validationExpression);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
