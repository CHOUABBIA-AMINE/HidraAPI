/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NoopAnalyticsSourceFeedGateway
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.integration
 *
 * @Description : No-op analytics source feed gateway.
 *
 */
package dz.sh.hidra.modules.analytics.infrastructure.integration;

/**
 * No-op analytics source feed gateway.
 */
public class NoopAnalyticsSourceFeedGateway implements AnalyticsSourceFeedGateway {

    @Override
    public boolean sourceAvailable(String sourceModule, String sourceType, String sourceName) {
        return true;
    }

    @Override
    public boolean snapshotAvailable(String sourceModule, String sourceSnapshotId) {
        return true;
    }
}
