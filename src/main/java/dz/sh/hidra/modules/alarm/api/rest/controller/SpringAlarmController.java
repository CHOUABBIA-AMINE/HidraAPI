/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SpringAlarmController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.api.rest.controller
 *
 * @Description : Spring MVC adapter exposing alarm REST endpoints.
 *
 */
package dz.sh.hidra.modules.alarm.api.rest.controller;
import dz.sh.hidra.modules.alarm.api.rest.mapper.AlarmRestMapper;
import dz.sh.hidra.modules.alarm.api.rest.request.AcknowledgeAlarmRequest;
import dz.sh.hidra.modules.alarm.api.rest.request.CloseAlarmRequest;
import dz.sh.hidra.modules.alarm.api.rest.request.RaiseAlarmRequest;
import dz.sh.hidra.modules.alarm.api.rest.response.AlarmResponse;
import dz.sh.hidra.modules.alarm.application.port.in.AcknowledgeAlarmUseCase;
import dz.sh.hidra.modules.alarm.application.port.in.CloseAlarmUseCase;
import dz.sh.hidra.modules.alarm.application.port.in.RaiseAlarmUseCase;
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
 * Spring MVC adapter exposing alarm REST endpoints.
 */
@RestController
@Validated
@RequestMapping("/api/v1/alarm")
public class SpringAlarmController implements AlarmController {

    private final AcknowledgeAlarmUseCase acknowledgeAlarmUseCase;
    private final CloseAlarmUseCase closeAlarmUseCase;
    private final RaiseAlarmUseCase raiseAlarmUseCase;

    public SpringAlarmController(
            AcknowledgeAlarmUseCase acknowledgeAlarmUseCase,
            CloseAlarmUseCase closeAlarmUseCase,
            RaiseAlarmUseCase raiseAlarmUseCase
    ) {
        this.acknowledgeAlarmUseCase = Objects.requireNonNull(acknowledgeAlarmUseCase, "AcknowledgeAlarmUseCase must not be null.");
        this.closeAlarmUseCase = Objects.requireNonNull(closeAlarmUseCase, "CloseAlarmUseCase must not be null.");
        this.raiseAlarmUseCase = Objects.requireNonNull(raiseAlarmUseCase, "RaiseAlarmUseCase must not be null.");
    }

    @GetMapping("/capabilities")
    public Map<String, Object> capabilities() {
        return Map.of(
                "module", "alarm",
                "mission", "Control alarm lifecycle for pipeline operations, telemetry deviations, safety priorities, and operator accountability.",
                "objectives", List.of(
                "Raise operational alarms from manual, monitoring, or telemetry sources.",
                "Acknowledge alarms with actor and organization evidence.",
                "Close alarms with reason, review, and workflow traceability."
        ),
                "operations", List.of(
                "acknowledgeAlarm",
                "closeAlarm",
                "raiseAlarm"
        ),
                "resourceEndpoints", List.of(
                "POST /api/v1/alarm/alarms/acknowledgements",
                "POST /api/v1/alarm/alarms/closures",
                "POST /api/v1/alarm/alarms"
        )
        );
    }

    @Override
    @PostMapping({"/acknowledge-alarm", "/alarms/acknowledgements"})
    public String acknowledgeAlarm(@Valid @RequestBody AcknowledgeAlarmRequest request) {
        Objects.requireNonNull(request, "AcknowledgeAlarmRequest must not be null.");
        return acknowledgeAlarmUseCase.acknowledgeAlarm(AlarmRestMapper.toCommand(request));
    }

    @Override
    @PostMapping({"/close-alarm", "/alarms/closures"})
    public String closeAlarm(@Valid @RequestBody CloseAlarmRequest request) {
        Objects.requireNonNull(request, "CloseAlarmRequest must not be null.");
        return closeAlarmUseCase.closeAlarm(AlarmRestMapper.toCommand(request));
    }

    @Override
    @PostMapping({"/raise-alarm", "/alarms"})
    public AlarmResponse raiseAlarm(@Valid @RequestBody RaiseAlarmRequest request) {
        Objects.requireNonNull(request, "RaiseAlarmRequest must not be null.");
        return AlarmRestMapper.toResponse(raiseAlarmUseCase.raiseAlarm(AlarmRestMapper.toCommand(request)));
    }

}
