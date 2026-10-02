/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateOperationalPlanRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.api.rest.request
 *
 * @Description : REST request to create operational plan.
 *
 */
package dz.sh.hidra.modules.planning.api.rest.request;

/**
 * REST request to create operational plan.
 */
public record CreateOperationalPlanRequest(
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
