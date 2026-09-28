/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportScheduleParameter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.reporting.domain.exception.InvalidReportingValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidReportingValueException("ReportScheduleParameter id must not be blank.");
        }
        // HRA-051 required: reportScheduleId
        if (reportScheduleId == null || reportScheduleId.isBlank()) {
            throw new InvalidReportingValueException("ReportScheduleParameter report schedule id must not be blank.");
        }
        // HRA-051 required: parameterDefinitionId
        if (parameterDefinitionId == null || parameterDefinitionId.isBlank()) {
            throw new InvalidReportingValueException("ReportScheduleParameter parameter definition id must not be blank.");
        }
        // HRA-051 required: parameterCode
        if (parameterCode == null || parameterCode.isBlank()) {
            throw new InvalidReportingValueException("ReportScheduleParameter parameter code must not be blank.");
        }
        // HRA-051 required: valueType
        if (valueType == null) {
            throw new InvalidReportingValueException("ReportScheduleParameter value type must not be null.");
        }

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
