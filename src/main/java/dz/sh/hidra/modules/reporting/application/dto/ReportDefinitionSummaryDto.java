/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportDefinitionSummaryDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.application.dto
 *
 * @Description : Report definition summary DTO.
 *
 */
package dz.sh.hidra.modules.reporting.application.dto;

/**
 * Report definition summary DTO.
 */
public record ReportDefinitionSummaryDto(
        String id,
        String code,
        String nameFr,
        String reportCategoryId,
        String ownerModule,
        boolean active,
        String currentTemplateVersionId,
        boolean requiresApproval,
        boolean restricted
) {
}
