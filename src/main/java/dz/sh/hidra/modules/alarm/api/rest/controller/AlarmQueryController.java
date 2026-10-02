/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmQueryController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.api.rest.controller
 *
 * @Description : Exposes active/history/detail alarm reads and audited shelving operations.
 *
 */
package dz.sh.hidra.modules.alarm.api.rest.controller;

import dz.sh.hidra.modules.alarm.application.port.in.AlarmQueryUseCase;
import dz.sh.hidra.modules.alarm.application.port.in.AlarmQueryUseCase.AlarmView;
import dz.sh.hidra.modules.alarm.application.port.in.AlarmQueryUseCase.Page;
import dz.sh.hidra.modules.alarm.application.port.in.AlarmQueryUseCase.ShelvingView;
import dz.sh.hidra.modules.alarm.application.port.in.ManageAlarmShelvingUseCase;
import dz.sh.hidra.modules.alarm.application.port.in.ManageAlarmShelvingUseCase.ShelveAlarmCommand;
import dz.sh.hidra.modules.alarm.application.port.in.ManageAlarmShelvingUseCase.UnshelveAlarmCommand;
import dz.sh.hidra.platform.security.CurrentActorResolver;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/alarm/alarms")
public class AlarmQueryController {

    private final AlarmQueryUseCase queryUseCase;
    private final ManageAlarmShelvingUseCase shelvingUseCase;
    private final CurrentActorResolver actorResolver;

    public AlarmQueryController(
            AlarmQueryUseCase queryUseCase,
            ManageAlarmShelvingUseCase shelvingUseCase,
            CurrentActorResolver actorResolver
    ) {
        this.queryUseCase = Objects.requireNonNull(queryUseCase, "AlarmQueryUseCase must not be null.");
        this.shelvingUseCase = Objects.requireNonNull(shelvingUseCase, "ManageAlarmShelvingUseCase must not be null.");
        this.actorResolver = Objects.requireNonNull(actorResolver, "CurrentActorResolver must not be null.");
    }

    @GetMapping
    public Page<AlarmView> alarms(
            @RequestParam(defaultValue = "active") String view,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String severityId,
            @RequestParam(required = false) String topologyAssetId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant to,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size
    ) {
        return queryUseCase.alarms(view, state, severityId, topologyAssetId, from, to, page, size);
    }

    @GetMapping("/{id}")
    public AlarmView alarm(@PathVariable String id) {
        return queryUseCase.alarm(id);
    }

    @GetMapping("/{id}/shelvings")
    public List<ShelvingView> shelvings(@PathVariable String id) {
        return queryUseCase.shelvings(id);
    }

    @PostMapping("/{id}/shelvings")
    public String shelve(
            @PathVariable String id,
            @Valid @RequestBody ShelveAlarmRequest request,
            @RequestHeader(name = "X-Correlation-Id", required = false) String correlationId
    ) {
        return shelvingUseCase.shelve(new ShelveAlarmCommand(
                id, request.shelvingReasonId(), request.reasonText(), actorResolver.currentActorId().value(),
                request.shelvedUntil(), correlationId
        ));
    }

    @PostMapping("/{id}/shelvings/{shelvingId}/unshelve")
    public String unshelve(
            @PathVariable String id,
            @PathVariable String shelvingId,
            @RequestHeader(name = "X-Correlation-Id", required = false) String correlationId
    ) {
        return shelvingUseCase.unshelve(new UnshelveAlarmCommand(
                id, shelvingId, actorResolver.currentActorId().value(), correlationId
        ));
    }

    public record ShelveAlarmRequest(
            @NotBlank String shelvingReasonId,
            String reasonText,
            @Future Instant shelvedUntil
    ) { }
}
