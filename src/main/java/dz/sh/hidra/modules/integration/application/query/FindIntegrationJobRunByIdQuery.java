/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FindIntegrationJobRunByIdQuery
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.application.query
 *
 * @Description : Query to find integration job run by ID.
 *
 */
package dz.sh.hidra.modules.integration.application.query;

/**
 * Query to find integration job run by ID.
 */
public record FindIntegrationJobRunByIdQuery(String jobRunId) {
}
