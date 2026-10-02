/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsRunMode
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.domain.value
 *
 * @Description : Defines AnalyticsRunMode values.
 *
 */
package dz.sh.hidra.modules.analytics.domain.value;

/**
 * Defines AnalyticsRunMode values.
 */
public enum AnalyticsRunMode {
    FULL_REBUILD, INCREMENTAL, BACKFILL, MANUAL_RECOMPUTE, SCHEDULED
}
