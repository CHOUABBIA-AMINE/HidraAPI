/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DeviationMetricRunApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.application.service
 *
 * @Description : Coordinates scoped deterministic metric execution and durable replay.
 *
 */
package dz.sh.hidra.modules.analytics.application.service;

import dz.sh.hidra.modules.analytics.application.port.in.DeviationMetricRunUseCase;
import dz.sh.hidra.modules.analytics.application.port.out.*;
import dz.sh.hidra.modules.analytics.domain.model.MetricEvaluationRun;
import dz.sh.hidra.modules.analytics.domain.model.MetricValue;
import dz.sh.hidra.modules.analytics.domain.service.AbsoluteDeviationMetricV1;
import dz.sh.hidra.modules.analytics.domain.value.AnalyticsId;
import dz.sh.hidra.modules.analytics.domain.value.AnalyticsQualityStatus;
import dz.sh.hidra.modules.analytics.domain.value.AnalyticsRunStatus;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeviationMetricRunApplicationService implements DeviationMetricRunUseCase {
    private final DeviationMetricAuthorizationPort authorization;
    private final DeviationMetricSourcePort sources;
    private final DeviationMetricReceiptPort receipts;
    private final MetricEvaluationRunRepositoryPort runs;
    private final MetricValueRepositoryPort values;

    public DeviationMetricRunApplicationService(DeviationMetricAuthorizationPort authorization,
            DeviationMetricSourcePort sources, DeviationMetricReceiptPort receipts,
            MetricEvaluationRunRepositoryPort runs, MetricValueRepositoryPort values) {
        this.authorization=Objects.requireNonNull(authorization);
        this.sources=Objects.requireNonNull(sources);
        this.receipts=Objects.requireNonNull(receipts);
        this.runs=Objects.requireNonNull(runs);
        this.values=Objects.requireNonNull(values);
    }

    @Override
    @Transactional
    public Result execute(String companyId,String facilityId,String versionId,String evaluationId) {
        companyId=id(companyId,96);facilityId=id(facilityId,80);
        versionId=id(versionId,80);evaluationId=id(evaluationId,80);
        authorization.require("analytics:deviation-metric:execute",companyId,facilityId);
        authorization.require("analytics:deviation-metric:read",companyId,facilityId);
        var source=protectedSource(evaluationId,companyId,facilityId);
        receipts.lock(companyId,facilityId,versionId,evaluationId);
        var previous=receipts.byKey(companyId,facilityId,versionId,evaluationId);
        if(previous.isPresent()) return replay(previous.orElseThrow());
        var definition=receipts.eligible(versionId,source.unitId(),source.evaluatedAt());
        var outcome=AbsoluteDeviationMetricV1.evaluate(source.outcome(),source.actual(),source.expected());
        Instant now=Instant.now().truncatedTo(ChronoUnit.MICROS);
        String runId=AnalyticsId.newId().value();
        String valueId=outcome.successful()?AnalyticsId.newId().value():null;
        runs.save(new MetricEvaluationRun(runId,versionId,
                outcome.successful()?AnalyticsRunStatus.COMPLETED:AnalyticsRunStatus.FAILED,
                source.evaluatedAt(),source.evaluatedAt(),"FACILITY",facilityId,
                now,now,1L,outcome.successful()?1L:0L,
                outcome.errorCode(),null,null,now));
        if(outcome.successful()) {
            values.save(new MetricValue(valueId,runId,definition.definitionId(),versionId,
                    "FACILITY",facilityId,source.evaluatedAt(),source.evaluatedAt(),
                    outcome.value(),null,source.unitId(),AnalyticsQualityStatus.READY,now));
        }
        receipts.seal(runId,valueId,companyId,facilityId,versionId,evaluationId,
                authorization.currentActor(),source);
        return receipts.byRun(companyId,facilityId,runId).orElseThrow(
                () -> new IllegalStateException("Committed metric receipt missing."));
    }

    @Override
    @Transactional
    public Result read(String companyId,String facilityId,String runId) {
        companyId=id(companyId,96);facilityId=id(facilityId,80);runId=id(runId,80);
        authorization.require("analytics:deviation-metric:read",companyId,facilityId);
        var stored=receipts.byRun(companyId,facilityId,runId)
                .orElseThrow(DeviationMetricRunApplicationService::unavailable);
        protectedSource(stored.source().evaluationId(),companyId,facilityId);
        return stored;
    }

    private Source protectedSource(String id,String company,String facility) {
        try { return sources.authorized(id,company,facility); }
        catch (org.springframework.security.access.AccessDeniedException | NoSuchElementException rejected) {
            throw unavailable();
        }
    }
    private static String id(String id,int length) {
        if(id==null||id.isBlank()||id.length()>length||!id.equals(id.strip()))
            throw new IllegalArgumentException("Invalid metric identifier.");
        return id;
    }
    private static Result replay(Result value) {
        return new Result(value.runId(),value.status(),value.errorCode(),value.companyId(),
                value.facilityId(),value.metricCode(),value.definitionVersionId(),
                value.methodVersion(),value.unitId(),value.value(),value.startedAt(),
                value.completedAt(),value.source(),true);
    }
    private static NoSuchElementException unavailable() {
        return new NoSuchElementException("Protected metric result unavailable.");
    }
}
