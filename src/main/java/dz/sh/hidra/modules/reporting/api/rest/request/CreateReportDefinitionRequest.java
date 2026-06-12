/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateReportDefinitionRequest
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.api.rest.request
 *
 * @Description : REST request to create report definition.
 *
 */
package dz.sh.hidra.modules.reporting.api.rest.request;

/**
 * REST request to create report definition.
 */
public record CreateReportDefinitionRequest(
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String reportCategoryId,
        String ownerModule,
        String description,
        boolean requiresApproval,
        boolean restricted
) {
}
