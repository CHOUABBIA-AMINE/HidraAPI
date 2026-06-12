/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportDefinitionResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.api.rest.response
 *
 * @Description : REST response for report definition.
 *
 */
package dz.sh.hidra.modules.reporting.api.rest.response;

/**
 * REST response for report definition.
 */
public record ReportDefinitionResponse(
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
