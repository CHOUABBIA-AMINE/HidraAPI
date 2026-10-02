/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateSimulationModelRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.api.rest.request
 *
 * @Description : REST request to create simulation model.
 *
 */
package dz.sh.hidra.modules.simulation.api.rest.request;

/**
 * REST request to create simulation model.
 */
public record CreateSimulationModelRequest(
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String modelTypeId,
        String topologyScopeType,
        String topologyScopeId,
        String description
) {
}
