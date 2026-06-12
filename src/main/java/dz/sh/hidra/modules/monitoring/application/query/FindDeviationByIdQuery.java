/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FindDeviationByIdQuery
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.application.query
 *
 * @Description : Query to find a monitoring deviation by ID.
 *
 */
package dz.sh.hidra.modules.monitoring.application.query;

/**
 * Query to find a monitoring deviation by ID.
 */
public record FindDeviationByIdQuery(String deviationId) {
}
