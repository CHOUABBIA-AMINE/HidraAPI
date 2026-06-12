/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsRunStatus
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.domain.value
 *
 * @Description : Defines AnalyticsRunStatus values.
 *
 */
package dz.sh.hidra.modules.analytics.domain.value;

/**
 * Defines AnalyticsRunStatus values.
 */
public enum AnalyticsRunStatus {
    PENDING, RUNNING, COMPLETED, COMPLETED_WITH_WARNINGS, FAILED, CANCELLED
}
