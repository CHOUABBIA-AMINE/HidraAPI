/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SpringAnalyticsController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.api.rest.controller
 *
 * @Description : Spring MVC adapter exposing analytics REST endpoints.
 *
 */
package dz.sh.hidra.modules.analytics.api.rest.controller;
import dz.sh.hidra.modules.analytics.api.rest.mapper.AnalyticsRestMapper;
import dz.sh.hidra.modules.analytics.api.rest.request.CreateAnalyticsDatasetRequest;
import dz.sh.hidra.modules.analytics.api.rest.request.CreateAnalyticsInsightRequest;
import dz.sh.hidra.modules.analytics.api.rest.request.RunMetricEvaluationRequest;
import dz.sh.hidra.modules.analytics.api.rest.request.RunProjectionRequest;
import dz.sh.hidra.modules.analytics.api.rest.response.AnalyticsDatasetResponse;
import dz.sh.hidra.modules.analytics.api.rest.response.AnalyticsInsightResponse;
import dz.sh.hidra.modules.analytics.api.rest.response.AnalyticsProjectionRunResponse;
import dz.sh.hidra.modules.analytics.api.rest.response.MetricEvaluationRunResponse;
import dz.sh.hidra.modules.analytics.application.port.in.AnalyticsDatasetUseCase;
import dz.sh.hidra.modules.analytics.application.port.in.AnalyticsInsightUseCase;
import dz.sh.hidra.modules.analytics.application.port.in.AnalyticsProjectionUseCase;
import dz.sh.hidra.modules.analytics.application.port.in.MetricEvaluationUseCase;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring MVC adapter exposing analytics REST endpoints.
 */
@RestController
@Validated
@RequestMapping("/api/v1/analytics")
public class SpringAnalyticsController implements AnalyticsController {

    private final AnalyticsDatasetUseCase analyticsDatasetUseCase;
    private final AnalyticsInsightUseCase analyticsInsightUseCase;
    private final AnalyticsProjectionUseCase analyticsProjectionUseCase;
    private final MetricEvaluationUseCase metricEvaluationUseCase;

    public SpringAnalyticsController(
            AnalyticsDatasetUseCase analyticsDatasetUseCase,
            AnalyticsInsightUseCase analyticsInsightUseCase,
            AnalyticsProjectionUseCase analyticsProjectionUseCase,
            MetricEvaluationUseCase metricEvaluationUseCase
    ) {
        this.analyticsDatasetUseCase = Objects.requireNonNull(analyticsDatasetUseCase, "AnalyticsDatasetUseCase must not be null.");
        this.analyticsInsightUseCase = Objects.requireNonNull(analyticsInsightUseCase, "AnalyticsInsightUseCase must not be null.");
        this.analyticsProjectionUseCase = Objects.requireNonNull(analyticsProjectionUseCase, "AnalyticsProjectionUseCase must not be null.");
        this.metricEvaluationUseCase = Objects.requireNonNull(metricEvaluationUseCase, "MetricEvaluationUseCase must not be null.");
    }

    @GetMapping("/capabilities")
    public Map<String, Object> capabilities() {
        return Map.of(
                "module", "analytics",
                "mission", "Transform operational, risk, and simulation data into decision-ready intelligence.",
                "objectives", List.of(
                "Register analytics datasets used by Hidra intelligence flows.",
                "Create insights for operational and risk decision support.",
                "Run metric evaluations and projections for proactive pipeline management."
        ),
                "operations", List.of(
                "createAnalyticsDataset",
                "createAnalyticsInsight",
                "runProjection",
                "runMetricEvaluation"
        ),
                "resourceEndpoints", List.of(
                "POST /api/v1/analytics/datasets",
                "POST /api/v1/analytics/insights",
                "POST /api/v1/analytics/projections/runs",
                "POST /api/v1/analytics/metrics/evaluations"
        )
        );
    }

    @Override
    @PostMapping({"/create-analytics-dataset", "/datasets"})
    public AnalyticsDatasetResponse createAnalyticsDataset(@Valid @RequestBody CreateAnalyticsDatasetRequest request) {
        Objects.requireNonNull(request, "CreateAnalyticsDatasetRequest must not be null.");
        return AnalyticsRestMapper.toResponse(analyticsDatasetUseCase.createAnalyticsDataset(AnalyticsRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping({"/create-analytics-insight", "/insights"})
    public AnalyticsInsightResponse createAnalyticsInsight(@Valid @RequestBody CreateAnalyticsInsightRequest request) {
        Objects.requireNonNull(request, "CreateAnalyticsInsightRequest must not be null.");
        return AnalyticsRestMapper.toResponse(analyticsInsightUseCase.createAnalyticsInsight(AnalyticsRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping({"/run-projection", "/projections/runs"})
    public AnalyticsProjectionRunResponse runProjection(@Valid @RequestBody RunProjectionRequest request) {
        Objects.requireNonNull(request, "RunProjectionRequest must not be null.");
        return AnalyticsRestMapper.toResponse(analyticsProjectionUseCase.runProjection(AnalyticsRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping({"/run-metric-evaluation", "/metrics/evaluations"})
    public MetricEvaluationRunResponse runMetricEvaluation(@Valid @RequestBody RunMetricEvaluationRequest request) {
        Objects.requireNonNull(request, "RunMetricEvaluationRequest must not be null.");
        return AnalyticsRestMapper.toResponse(metricEvaluationUseCase.runMetricEvaluation(AnalyticsRestMapper.toCommand(request)));
    }

}
