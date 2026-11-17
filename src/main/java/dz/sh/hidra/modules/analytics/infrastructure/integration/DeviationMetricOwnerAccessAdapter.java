/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DeviationMetricOwnerAccessAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.integration
 *
 * @Description : Connects Analytics to exact authorized Monitoring and Identity public exports.
 *
 */
package dz.sh.hidra.modules.analytics.infrastructure.integration;

import dz.sh.hidra.modules.analytics.application.port.in.DeviationMetricRunUseCase.Source;
import dz.sh.hidra.modules.analytics.application.port.out.DeviationMetricSourcePort;
import dz.sh.hidra.modules.analytics.application.port.out.DeviationMetricAuthorizationPort;
import dz.sh.hidra.modules.identity.application.contract.analytics.AnalyticsMetricScopedAuthorizationContract;
import dz.sh.hidra.modules.monitoring.application.contract.analytics.AnalyticsDeviationSourceContract;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class DeviationMetricOwnerAccessAdapter
        implements DeviationMetricSourcePort, DeviationMetricAuthorizationPort {
    private final AnalyticsDeviationSourceContract monitoring;
    private final AnalyticsMetricScopedAuthorizationContract identity;
    public DeviationMetricOwnerAccessAdapter(AnalyticsDeviationSourceContract monitoring,
            AnalyticsMetricScopedAuthorizationContract identity) {
        this.monitoring=Objects.requireNonNull(monitoring);
        this.identity=Objects.requireNonNull(identity);
    }
    @Override
    public void require(String permission,String company,String facility) {
        identity.require(permission,company,facility);
    }
    @Override
    public String currentActor() { return identity.currentActor(); }

    @Override
    public Source authorized(String id,String company,String facility) {
        var e=monitoring.authorized(id);
        if(!"FACILITY".equals(e.assetType())
                || !Objects.equals(company,e.companyId())
                || !Objects.equals(facility,e.assetId()))
            throw new NoSuchElementException("Protected Monitoring evidence unavailable.");
        return new Source(e.evaluationId(),e.ruleId(),e.revision(),e.companyId(),
                e.assetId(),e.telemetryPointId(),e.unitId(),e.outcome(),
                e.actual(),e.expected(),e.targetId(),e.targetRevisionId(),
                e.approvalId(),e.trustedReadingId(),e.rawReadingId(),
                e.assessmentId(),e.bindingId(),e.evaluatedAt(),e.recordedAt());
    }
}
