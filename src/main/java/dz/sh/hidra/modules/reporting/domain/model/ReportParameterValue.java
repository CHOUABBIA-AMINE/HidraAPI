/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportParameterValue
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.domain.model
 *
 * @Description : Concrete parameter value for a report request.
 *
 */
package dz.sh.hidra.modules.reporting.domain.model;

import dz.sh.hidra.modules.reporting.domain.value.*;
import java.time.Instant;
import java.math.BigDecimal;
import java.time.LocalDate;

    /**
     * Concrete parameter value for a report request.
     *
         * @param id id
     * @param reportRequestId reportRequestId
     * @param parameterDefinitionId parameterDefinitionId
     * @param parameterCode parameterCode
     * @param valueType valueType
     * @param valueText valueText
     * @param valueNumber valueNumber
     * @param valueBoolean valueBoolean
     * @param valueDate valueDate
     * @param valueDateTime valueDateTime
     * @param valueJson valueJson
     * @param createdAt createdAt
     */
    public record ReportParameterValue(
            String id,
        String reportRequestId,
        String parameterDefinitionId,
        String parameterCode,
        ReportValueType valueType,
        String valueText,
        BigDecimal valueNumber,
        Boolean valueBoolean,
        LocalDate valueDate,
        Instant valueDateTime,
        String valueJson,
        Instant createdAt
    ) {

        public ReportParameterValue {
        id = normalize(id);
        reportRequestId = normalize(reportRequestId);
        parameterDefinitionId = normalize(parameterDefinitionId);
        parameterCode = normalize(parameterCode);
        valueText = normalize(valueText);
        valueJson = normalize(valueJson);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
