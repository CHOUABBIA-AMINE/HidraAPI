/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : ReportingDeviationMetricQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Class
 * @Layer       : Application
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.application.service
 * @Description : Scoped, versioned T25 report delivery.
 */
package dz.sh.hidra.modules.analytics.application.service;
import dz.sh.hidra.modules.analytics.application.contract.reporting.ReportingDeviationMetricContract;
import dz.sh.hidra.modules.analytics.application.port.in.DeviationMetricRunUseCase;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReportingDeviationMetricQueryService implements ReportingDeviationMetricContract {
    private final DeviationMetricRunUseCase runs;
    public ReportingDeviationMetricQueryService(DeviationMetricRunUseCase runs) { this.runs = runs; }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Metric completed(String company, String facility, String runId) {
        var run = runs.read(company, facility, runId);
        if (!"COMPLETED".equals(run.status()) || !"ABSOLUTE_DEVIATION".equals(run.metricCode())
                || !company.equals(run.companyId()) || !facility.equals(run.facilityId())
                || run.source()==null || run.value()==null)
            throw new NoSuchElementException("Protected metric unavailable.");
        var s = run.source();
        return new Metric(run.runId(),run.definitionVersionId(),run.methodVersion(),run.value(),
                run.unitId(),s.evaluationId(),s.ruleId(),s.ruleRevision(),s.targetId(),s.targetRevisionId(),
                s.approvalId(),s.trustedReadingId(),s.rawReadingId(),s.assessmentId(),s.bindingId(),
                s.evaluatedAt(),s.recordedAt());
    }
}
