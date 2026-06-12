/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : GetAnalyticsInsightQuery
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.application.query
 *
 * @Description : Query to find analytics insight by ID.
 *
 */
package dz.sh.hidra.modules.analytics.application.query;

/**
 * Query to find analytics insight by ID.
 */
public record GetAnalyticsInsightQuery(String analyticsInsightId) {
}
