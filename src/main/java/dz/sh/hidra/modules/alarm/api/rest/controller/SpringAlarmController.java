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
import dz.sh.hidra.modules.alarm.api.rest.request.RaiseAlarmRequest;
import dz.sh.hidra.modules.alarm.api.rest.response.AlarmResponse;
import dz.sh.hidra.modules.alarm.application.port.in.AcknowledgeAlarmUseCase;
import dz.sh.hidra.modules.alarm.application.port.in.RaiseAlarmUseCase;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Objects;

/**
 * Spring MVC adapter exposing alarm REST endpoints.
 */
@RestController
@Validated
@RequestMapping("/api/v1/alarm")
public final class SpringAlarmController implements AlarmController {

    private final RaiseAlarmUseCase raiseAlarmUseCase;
    private final AcknowledgeAlarmUseCase acknowledgeAlarmUseCase;

    public SpringAlarmController(
            RaiseAlarmUseCase raiseAlarmUseCase,
            AcknowledgeAlarmUseCase acknowledgeAlarmUseCase
    ) {
        this.raiseAlarmUseCase = Objects.requireNonNull(raiseAlarmUseCase, "RaiseAlarmUseCase must not be null.");
        this.acknowledgeAlarmUseCase = Objects.requireNonNull(acknowledgeAlarmUseCase, "AcknowledgeAlarmUseCase must not be null.");
    }


    @Override
    @PostMapping("/raise-alarm")
    public AlarmResponse raiseAlarm(@Valid @RequestBody RaiseAlarmRequest request) {
        Objects.requireNonNull(request, "RaiseAlarmRequest must not be null.");
        return AlarmRestMapper.toResponse(raiseAlarmUseCase.raiseAlarm(AlarmRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping("/acknowledge-alarm")
    public String acknowledgeAlarm(@Valid @RequestBody AcknowledgeAlarmRequest request) {
        Objects.requireNonNull(request, "AcknowledgeAlarmRequest must not be null.");
        return acknowledgeAlarmUseCase.acknowledgeAlarm(AlarmRestMapper.toCommand(request));
    }

}
