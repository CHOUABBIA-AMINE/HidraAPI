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
import dz.sh.hidra.modules.analytics.api.rest.response.AnalyticsDatasetResponse;
import dz.sh.hidra.modules.analytics.api.rest.response.AnalyticsInsightResponse;
import dz.sh.hidra.modules.analytics.application.port.in.AnalyticsDatasetUseCase;
import dz.sh.hidra.modules.analytics.application.port.in.AnalyticsInsightUseCase;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Objects;

/**
 * Spring MVC adapter exposing analytics REST endpoints.
 */
@RestController
@Validated
@RequestMapping("/api/v1/analytics")
public class SpringAnalyticsController implements AnalyticsController {

    private final AnalyticsDatasetUseCase analyticsDatasetUseCase;
    private final AnalyticsInsightUseCase analyticsInsightUseCase;

    public SpringAnalyticsController(
            AnalyticsDatasetUseCase analyticsDatasetUseCase,
            AnalyticsInsightUseCase analyticsInsightUseCase
    ) {
        this.analyticsDatasetUseCase = Objects.requireNonNull(analyticsDatasetUseCase, "AnalyticsDatasetUseCase must not be null.");
        this.analyticsInsightUseCase = Objects.requireNonNull(analyticsInsightUseCase, "AnalyticsInsightUseCase must not be null.");
    }


    @Override
    @PostMapping("/create-analytics-dataset")
    public AnalyticsDatasetResponse createAnalyticsDataset(@Valid @RequestBody CreateAnalyticsDatasetRequest request) {
        Objects.requireNonNull(request, "CreateAnalyticsDatasetRequest must not be null.");
        return AnalyticsRestMapper.toResponse(analyticsDatasetUseCase.createAnalyticsDataset(AnalyticsRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping("/create-analytics-insight")
    public AnalyticsInsightResponse createAnalyticsInsight(@Valid @RequestBody CreateAnalyticsInsightRequest request) {
        Objects.requireNonNull(request, "CreateAnalyticsInsightRequest must not be null.");
        return AnalyticsRestMapper.toResponse(analyticsInsightUseCase.createAnalyticsInsight(AnalyticsRestMapper.toCommand(request)));
    }

}
