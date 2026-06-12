/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsModule
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics
 *
 * @Description : Defines analytics module constants.
 *
 */
package dz.sh.hidra.modules.analytics;

/**
 * Analytics module constants.
 */
public final class AnalyticsModule {

    public static final String MODULE_NAME = "analytics";
    public static final String TABLE_PREFIX = "hidra_analytics_";

    private AnalyticsModule() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
