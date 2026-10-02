/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsLineageStatus
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.domain.value
 *
 * @Description : Defines AnalyticsLineageStatus values.
 *
 */
package dz.sh.hidra.modules.analytics.domain.value;

/**
 * Defines AnalyticsLineageStatus values.
 */
public enum AnalyticsLineageStatus {
    DRAFT, COMPLETE, PARTIAL, BROKEN, UNKNOWN
}
