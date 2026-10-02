/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryQueryController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.controller
 *
 * @Description : Exposes telemetry reading history, trend, latest-value, and reference metadata APIs.
 *
 */
package dz.sh.hidra.modules.telemetry.api.rest.controller;

import dz.sh.hidra.modules.telemetry.application.port.in.TelemetryQueryUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.TelemetryQueryUseCase.Page;
import dz.sh.hidra.modules.telemetry.application.port.in.TelemetryQueryUseCase.QualityCodeView;
import dz.sh.hidra.modules.telemetry.application.port.in.TelemetryQueryUseCase.ReadingView;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/telemetry")
public class TelemetryQueryController {

    private final TelemetryQueryUseCase useCase;

    public TelemetryQueryController(TelemetryQueryUseCase useCase) {
        this.useCase = Objects.requireNonNull(useCase, "TelemetryQueryUseCase must not be null.");
    }

    @GetMapping("/points/{pointId}/readings")
    public Page<ReadingView> readings(
            @PathVariable String pointId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant to,
            @RequestParam(required = false) String state,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size
    ) {
        return useCase.readings(pointId, from, to, state, page, size);
    }

    @GetMapping("/points/{pointId}/readings/latest")
    public ReadingView latest(@PathVariable String pointId) {
        return useCase.latestReading(pointId);
    }

    @GetMapping("/points/{pointId}/trend")
    public List<ReadingView> trend(
            @PathVariable String pointId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant to,
            @RequestParam(defaultValue = "1000") int limit
    ) {
        return useCase.trend(pointId, from, to, limit);
    }

    @GetMapping("/reference/reading-states")
    public List<String> readingStates() {
        return useCase.readingStates();
    }

    @GetMapping("/reference/quality-codes")
    public List<QualityCodeView> qualityCodes() {
        return useCase.qualityCodes();
    }
}
