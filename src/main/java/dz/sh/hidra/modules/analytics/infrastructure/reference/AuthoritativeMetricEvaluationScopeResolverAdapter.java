/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthoritativeMetricEvaluationScopeResolverAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.reference
 *
 * @Description : Analytics-neutral adapter routing metric scopes to authoritative owner contracts.
 *
 */
package dz.sh.hidra.modules.analytics.infrastructure.reference;

import dz.sh.hidra.modules.analytics.application.port.out.MetricEvaluationScopeResolverPort;
import dz.sh.hidra.modules.organization.application.contract.analytics.AnalyticsOrganizationScopeContract;
import dz.sh.hidra.modules.topology.application.contract.analytics.AnalyticsTopologyScopeContract;
import java.util.Objects;
import org.springframework.stereotype.Component;

/**
 * Routes Analytics scope validation only through deliberate owner contracts.
 */
@Component
public class AuthoritativeMetricEvaluationScopeResolverAdapter
        implements MetricEvaluationScopeResolverPort {

    private final AnalyticsTopologyScopeContract topologyScopeContract;
    private final AnalyticsOrganizationScopeContract organizationScopeContract;

    public AuthoritativeMetricEvaluationScopeResolverAdapter(
            AnalyticsTopologyScopeContract topologyScopeContract,
            AnalyticsOrganizationScopeContract organizationScopeContract
    ) {
        this.topologyScopeContract = Objects.requireNonNull(
                topologyScopeContract,
                "AnalyticsTopologyScopeContract must not be null."
        );
        this.organizationScopeContract = Objects.requireNonNull(
                organizationScopeContract,
                "AnalyticsOrganizationScopeContract must not be null."
        );
    }

    @Override
    public Resolution resolve(String scopeType, String scopeId) {
        var topology = topologyScopeContract.resolve(scopeType, scopeId);
        if (topology.supported()) {
            return Resolution.ownerBacked(topology.exists());
        }

        var organization = organizationScopeContract.resolve(scopeType, scopeId);
        if (organization.supported()) {
            return Resolution.ownerBacked(organization.exists());
        }

        return Resolution.unsupported();
    }
}
