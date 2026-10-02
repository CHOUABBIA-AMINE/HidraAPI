/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsAccessMode
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.domain.value
 *
 * @Description : Defines AnalyticsAccessMode values.
 *
 */
package dz.sh.hidra.modules.analytics.domain.value;

/**
 * Defines AnalyticsAccessMode values.
 */
public enum AnalyticsAccessMode {
    APPLICATION_PORT, PROJECTION_TABLE, OUTBOX_EVENT, EVENT_STREAM, EXTERNAL_EXPORT, READ_MODEL_API
}
