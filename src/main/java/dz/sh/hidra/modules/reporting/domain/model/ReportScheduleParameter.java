/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportScheduleParameter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.domain.model
 *
 * @Description : Default parameter for scheduled reports.
 *
 */
package dz.sh.hidra.modules.reporting.domain.model;

import dz.sh.hidra.modules.reporting.domain.value.*;
import java.time.Instant;

    /**
     * Default parameter for scheduled reports.
     *
         * @param id id
     * @param reportScheduleId reportScheduleId
     * @param parameterDefinitionId parameterDefinitionId
     * @param parameterCode parameterCode
     * @param valueType valueType
     * @param valueJson valueJson
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record ReportScheduleParameter(
            String id,
        String reportScheduleId,
        String parameterDefinitionId,
        String parameterCode,
        ReportValueType valueType,
        String valueJson,
        Instant createdAt,
        Instant updatedAt
    ) {

        public ReportScheduleParameter {
        id = normalize(id);
        reportScheduleId = normalize(reportScheduleId);
        parameterDefinitionId = normalize(parameterDefinitionId);
        parameterCode = normalize(parameterCode);
        valueJson = normalize(valueJson);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
