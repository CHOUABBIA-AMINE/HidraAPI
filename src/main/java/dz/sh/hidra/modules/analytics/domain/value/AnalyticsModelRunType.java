/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsModelRunType
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.domain.value
 *
 * @Description : Defines AnalyticsModelRunType values.
 *
 */
package dz.sh.hidra.modules.analytics.domain.value;

/**
 * Defines AnalyticsModelRunType values.
 */
public enum AnalyticsModelRunType {
    TRAINING, VALIDATION, SCORING, BACKTEST, BATCH_INFERENCE, FEATURE_EXTRACTION
}
