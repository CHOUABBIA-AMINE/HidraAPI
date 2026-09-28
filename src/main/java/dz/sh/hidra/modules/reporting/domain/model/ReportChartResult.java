/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportChartResult
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.domain.model
 *
 * @Description : Generated chart metadata.
 *
 */
package dz.sh.hidra.modules.reporting.domain.model;

import dz.sh.hidra.modules.reporting.domain.exception.InvalidReportingValueException;
import dz.sh.hidra.modules.reporting.domain.value.*;
import java.time.Instant;

    /**
     * Generated chart metadata.
     *
         * @param id id
     * @param reportSectionResultId reportSectionResultId
     * @param chartType chartType
     * @param seriesSchemaJson seriesSchemaJson
     * @param imageReference imageReference
     * @param interactiveSpecReference interactiveSpecReference
     * @param createdAt createdAt
     */
    public record ReportChartResult(
            String id,
        String reportSectionResultId,
        ReportChartType chartType,
        String seriesSchemaJson,
        String imageReference,
        String interactiveSpecReference,
        Instant createdAt
    ) {

        public ReportChartResult {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidReportingValueException("ReportChartResult id must not be blank.");
        }
        // HRA-051 required: reportSectionResultId
        if (reportSectionResultId == null || reportSectionResultId.isBlank()) {
            throw new InvalidReportingValueException("ReportChartResult report section result id must not be blank.");
        }
        // HRA-051 required: chartType
        if (chartType == null) {
            throw new InvalidReportingValueException("ReportChartResult chart type must not be null.");
        }

        id = normalize(id);
        reportSectionResultId = normalize(reportSectionResultId);
        seriesSchemaJson = normalize(seriesSchemaJson);
        imageReference = normalize(imageReference);
        interactiveSpecReference = normalize(interactiveSpecReference);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
