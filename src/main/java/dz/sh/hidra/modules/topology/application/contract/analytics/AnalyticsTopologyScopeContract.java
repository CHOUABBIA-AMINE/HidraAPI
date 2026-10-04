/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsTopologyScopeContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Interface
 * @Layer       : Application Contract
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.contract.analytics
 *
 * @Description : Topology-owned public scope lookup contract for Analytics metric evaluation.
 *
 */
package dz.sh.hidra.modules.topology.application.contract.analytics;

/**
 * Resolves the Topology scope types explicitly authorized for Analytics.
 */
public interface AnalyticsTopologyScopeContract {

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
