/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DeviationMetricAuthorizationPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.application.port.out
 *
 * @Description : Current exact Analytics permission and actor boundary.
 *
 */
package dz.sh.hidra.modules.analytics.application.port.out;

public interface DeviationMetricAuthorizationPort {
    void require(String permission, String companyId, String facilityId);
    String currentActor();
}
