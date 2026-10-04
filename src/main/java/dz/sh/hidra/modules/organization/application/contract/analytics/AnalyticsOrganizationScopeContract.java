/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsOrganizationScopeContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Interface
 * @Layer       : Application Contract
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.contract.analytics
 *
 * @Description : Organization-owned public scope lookup contract for Analytics metric evaluation.
 *
 */
package dz.sh.hidra.modules.organization.application.contract.analytics;

/**
 * Resolves Organization-owned analytical scopes.
 */
public interface AnalyticsOrganizationScopeContract {

    Resolution resolve(String scopeType, String scopeId);

    record Resolution(boolean supported, boolean exists) {
        public static Resolution unsupported() {
            return new Resolution(false, false);
        }

        public static Resolution supported(boolean exists) {
            return new Resolution(true, exists);
        }
    }
}
