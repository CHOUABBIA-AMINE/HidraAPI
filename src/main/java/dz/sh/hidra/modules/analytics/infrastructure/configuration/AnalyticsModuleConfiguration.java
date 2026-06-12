/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsModuleConfiguration
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.configuration
 *
 * @Description : Analytics infrastructure configuration.
 *
 */
package dz.sh.hidra.modules.analytics.infrastructure.configuration;

/**
 * Analytics infrastructure configuration.
 */
public record AnalyticsModuleConfiguration(
        boolean sourceOfTruthMutationBlocked,
        boolean foreignAggregateImportsBlocked,
        boolean insightAdvisoryOnly,
        boolean reportingExportDelegated
) {

    public static AnalyticsModuleConfiguration defaults() {
        return new AnalyticsModuleConfiguration(true, true, true, true);
    }
}
