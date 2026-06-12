/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportDataSourceBinding
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.domain.model
 *
 * @Description : Source projection, API, or read model used by a report.
 *
 */
package dz.sh.hidra.modules.reporting.domain.model;

import dz.sh.hidra.modules.reporting.domain.value.*;
import java.time.Instant;

    /**
     * Source projection, API, or read model used by a report.
     *
         * @param id id
     * @param reportDefinitionId reportDefinitionId
     * @param sourceModule sourceModule
     * @param sourceType sourceType
     * @param sourceName sourceName
     * @param sourceContractVersion sourceContractVersion
     * @param required required
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record ReportDataSourceBinding(
            String id,
        String reportDefinitionId,
        String sourceModule,
        ReportSourceType sourceType,
        String sourceName,
        String sourceContractVersion,
        boolean required,
        Instant createdAt,
        Instant updatedAt
    ) {

        public ReportDataSourceBinding {
        id = normalize(id);
        reportDefinitionId = normalize(reportDefinitionId);
        sourceModule = normalize(sourceModule);
        sourceName = normalize(sourceName);
        sourceContractVersion = normalize(sourceContractVersion);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
