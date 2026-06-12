/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FindConfigurationValueByIdQuery
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.application.query
 *
 * @Description : Query to find configuration value by ID.
 *
 */
package dz.sh.hidra.modules.configuration.application.query;

/**
 * Query to find configuration value by ID.
 */
public record FindConfigurationValueByIdQuery(String configurationValueId) {
}
