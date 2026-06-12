/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsSourceFeedGateway
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.integration
 *
 * @Description : Gateway contract for analytics read-side source feeds.
 *
 */
package dz.sh.hidra.modules.analytics.infrastructure.integration;

/**
 * Gateway contract for analytics read-side source feeds.
 */
public interface AnalyticsSourceFeedGateway {

    boolean sourceAvailable(String sourceModule, String sourceType, String sourceName);

    boolean snapshotAvailable(String sourceModule, String sourceSnapshotId);
}
