/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateReportDefinitionCommand
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.application.command
 *
 * @Description : Command to create a report definition.
 *
 */
package dz.sh.hidra.modules.reporting.application.command;

/**
 * Command to create a report definition.
 */
public record CreateReportDefinitionCommand(
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
