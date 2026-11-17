/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsMetricScopedAuthorizationContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.contract.analytics
 *
 * @Description : Analytics-only current paired-scope authorization and native LOCAL actor export.
 *
 */
package dz.sh.hidra.modules.identity.application.contract.analytics;

public interface AnalyticsMetricScopedAuthorizationContract {
    String currentActor();
    void require(String permission,String companyId,String facilityId);
}
