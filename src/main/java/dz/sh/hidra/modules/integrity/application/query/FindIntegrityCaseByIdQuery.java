/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FindIntegrityCaseByIdQuery
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.application.query
 *
 * @Description : Query to find integrity case by ID.
 *
 */
package dz.sh.hidra.modules.integrity.application.query;

/**
 * Query to find integrity case by ID.
 */
public record FindIntegrityCaseByIdQuery(String integrityCaseId) {
}
