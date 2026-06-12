/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsSourceGuard
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.domain.service
 *
 * @Description : Guards analytics against operational mutation and foreign aggregate coupling.
 *
 */
package dz.sh.hidra.modules.analytics.domain.service;

import dz.sh.hidra.modules.analytics.domain.exception.AnalyticsBoundaryViolationException;
import dz.sh.hidra.modules.analytics.domain.policy.AnalyticsBoundaryPolicy;

/**
 * Guards analytics against operational mutation and foreign aggregate coupling.
 */
public class AnalyticsSourceGuard {

    public void ensureReadOnlyOperation(String operationName) {
        if (AnalyticsBoundaryPolicy.isForbiddenOperationalMutation(operationName)) {
            throw new AnalyticsBoundaryViolationException("Analytics must not modify source-of-truth operational state.");
        }
    }

    public void ensureNoForeignAggregatePayload(String sourceName) {
        if (AnalyticsBoundaryPolicy.isForeignAggregatePayload(sourceName)) {
            throw new AnalyticsBoundaryViolationException("Analytics outbound ports must return read models or snapshots, not foreign aggregates or JPA entities.");
        }
    }
}
