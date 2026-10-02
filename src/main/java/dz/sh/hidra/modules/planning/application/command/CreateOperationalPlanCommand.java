/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateOperationalPlanCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.application.command
 *
 * @Description : Command to create operational plan.
 *
 */
package dz.sh.hidra.modules.planning.application.command;

/**
 * Command to create operational plan.
 */
public record CreateOperationalPlanCommand(
        String periodId,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String planTypeId,
        String productTypeId,
        String topologyScopeType,
        String topologyScopeId,
        String topologyScopeCode,
        String topologyScopeNameSnapshot,
        String responsibleOrganizationUnitId,
        String createdByActorId
) {
}
