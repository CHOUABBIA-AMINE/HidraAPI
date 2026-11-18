/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : ScopedReportAuthorizationContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.contract.reporting
 * @Description : Scoped, versioned T25 report delivery.
 */
package dz.sh.hidra.modules.identity.application.contract.reporting;
public interface ScopedReportAuthorizationContract {
    String require(String permission, String company, String facility);
}
