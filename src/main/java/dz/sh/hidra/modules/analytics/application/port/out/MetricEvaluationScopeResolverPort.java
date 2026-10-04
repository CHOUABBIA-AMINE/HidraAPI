/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MetricEvaluationScopeResolverPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.application.port.out
 *
 * @Description : Neutral Analytics scope-resolution contract for metric evaluation runs.
 *
 */
package dz.sh.hidra.modules.analytics.application.port.out;

/**
 * Resolves a typed analytical scope through its authoritative owner.
 */
public interface MetricEvaluationScopeResolverPort {

    Resolution resolve(String scopeType, String scopeId);

    record Resolution(
            boolean supported,
            boolean identifierRequired,
            boolean exists
    ) {
        public static Resolution unsupported() {
            return new Resolution(false, true, false);
        }

        public static Resolution ownerBacked(boolean exists) {
            return new Resolution(true, true, exists);
        }
    }
}
